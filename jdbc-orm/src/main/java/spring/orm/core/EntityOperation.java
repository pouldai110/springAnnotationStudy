package spring.orm.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author pouldai
 * @version v1.0
 * @Description 根据实体类映射获取数据对应关系信息
 * @date 2020/4/11 下午1:54
 * @return
 */
@Slf4j
public class EntityOperation<T> implements Serializable {


    //实体类对应的class字节码
    public Class<T> entityClass;
    //属性字段关系映射
    public final Map<String, FieldMapping> mappings;
    //结果映射
    public final RowMapper<T> rowMapper;

    //实体类对应的表名
    public final String tableName;
    //所有列名
    public String allColumn = "*";
    //主键字段
    public Field pkField;
    //主键列名
    public String pkColum;


    /**
     * 构建一个对实体类操作的反射工具类，每个实体类都对一个工具类实例
     *
     * @param clazz
     * @param pk
     */
    public EntityOperation(Class<T> clazz) {
        this.entityClass = clazz;
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            this.tableName = table.name();
        } else {
            this.tableName = entityClass.getSimpleName();
        }
        //只扫描写个了get和set方法的属性
        Map<String, Method> getters = EntityClassUtils.getPublicGetters(entityClass);
        Map<String, Method> setters = EntityClassUtils.getPublicSetters(entityClass);
        //将get和set对应的属性找出来
        Field[] fields = EntityClassUtils.getFiled(entityClass);
        //将属性名数据库表中的字段名一一对应上
        this.mappings = getFieldMappings(getters, setters, fields);
        //将主键字段保存下来，方便之后的删除和修改操作
        FieldMapping pk = getPkFromMapping(mappings);
        if (pk != null) {
            this.pkField = pk.field;
            this.pkColum = pk.columnName;
        } else {
            log.debug("未找到主键");
        }
        //将主键字段找出来
        this.allColumn = this.mappings.keySet().toString().replace("[", "").replace("]", "").replaceAll(" ", "");
        //创建一个结果集映射关系
        this.rowMapper = createRowMapper();
    }

    /**
     * 将resultSet中的字段名和实体类中的字段名一一对应
     *
     * @return
     */
    RowMapper<T> createRowMapper() {
        return new RowMapper<T>() {
            public T mapRow(ResultSet rs, int rowNum) throws SQLException {
                try {
                    //先创建实例
                    T t = entityClass.newInstance();
                    //迭代结果集中所有的列名
                    ResultSetMetaData meta = rs.getMetaData();
                    int columns = meta.getColumnCount();
                    String columnName;
                    //将列名和字段名关系对应上
                    for (int i = 1; i <= columns; i++) {
                        Object value = rs.getObject(i);
                        columnName = meta.getColumnName(i);
                        putFieldValue(t, columnName, value);
                    }
                    return t;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    /**
     * 往字段中设值
     *
     * @param t
     * @param columnName
     * @param value
     */
    protected void putFieldValue(T t, String columnName, Object value) {
        if (value != null) {
            FieldMapping pm = this.mappings.get(columnName.toUpperCase());
            if (pm != null) {
                try {
                    pm.set(t, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 从映射关系中找出主键
     *
     * @param mappings
     * @return
     */
    private FieldMapping getPkFromMapping(Map<String, FieldMapping> mappings) {
        for (Entry<String, FieldMapping> entry : mappings.entrySet()) {
            //如果为主键字段
            if (entry.getValue().id) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 获取属性和字段的映射关系
     *
     * @param getters
     * @param setters
     * @param fields
     * @return
     */
    private Map<String, FieldMapping> getFieldMappings(Map<String, Method> getters, Map<String, Method> setters, Field[] fields) {
        if (getters.size() == 0) {
            log.error("没有扫描到任何getter");
        }
        if (setters.size() == 0) {
            log.error("没有扫描到任何setters");
        }
        Map<String, FieldMapping> mappings = new HashMap<String, FieldMapping>();
        String name;
        for (Field field : fields) {
            //添加了@Transient注解忽略
            if (field.isAnnotationPresent(Transient.class)) continue;
            name = field.getName();
            Method setter = setters.get(name);
            Method getter = getters.get(name);
            //没有设置getter和setter则忽略
            if (setter == null || getter == null) {
                continue;
            }
            FieldMapping mapping = new FieldMapping(getter, setter, field);
            mappings.put(mapping.columnName.toUpperCase(), mapping);
        }
        if (mappings.size() == 0) {
            log.error("没有扫描到任何映射关系");
        }
        return mappings;
    }

    /**
     * 将结果集转换为实体类
     *
     * @param rs
     * @return
     */
    public T parse(ResultSet rs) {
        T t = null;
        if (null == rs) {
            return null;
        }
        Object value = null;
        try {
            t = (T) entityClass.newInstance();
            for (String columnName : mappings.keySet()) {
                try {
                    value = rs.getObject(columnName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                putFieldValue(t, columnName, value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return t;
    }

    /**
     * 将实体类转换为Map对象
     *
     * @param t
     * @return
     */
    public Map<String, Object> parse(T t) {
        Map<String, Object> _map = new HashMap<String, Object>();
        try {

            for (String columnName : mappings.keySet()) {
                Object value = mappings.get(columnName).getter.invoke(t);
                if (value == null)
                    continue;
                _map.put(columnName, value);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _map;
    }

    public void println(T t) {
        try {
            for (String columnName : mappings.keySet()) {
                Object value = mappings.get(columnName).getter.invoke(t);
                if (value == null)
                    continue;
                System.out.println(columnName + " = " + value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "EntityOperation{" +
                "entityClass=" + entityClass +
                ", mappings=" + mappings +
                ", rowMapper=" + rowMapper +
                ", tableName='" + tableName + '\'' +
                ", allColumn='" + allColumn + '\'' +
                ", pkField=" + pkField +
                ", pkColum='" + pkColum + '\'' +
                '}';
    }

}

