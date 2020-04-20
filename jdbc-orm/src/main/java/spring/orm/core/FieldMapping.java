package spring.orm.core;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author pouldai
 * @version v1.0
 * @Description 定义为属性和字段的映射关系
 * @date 2020/4/12 下午4:43
 */
@Slf4j
public class FieldMapping {
    final boolean insertable;    //是否可插入
    final boolean updatable;        //是否可修改
    final String columnName;        //对应的列名
    final boolean id;            //对应的ID
    final Method getter;            //对应的getter方法
    final Method setter;            //对应的setter方法
    final Class enumClass;        //如果是枚举类，将枚举的字节码保存下来
    final String fieldName;        //对应的字段名称
    final Field field;            //对应的反射字段

    public FieldMapping(Method getter, Method setter, Field field) {
        this.getter = getter;
        this.setter = setter;
        //如果是枚举类型，则将其标记下来
        this.enumClass = getter.getReturnType().isEnum() ? getter.getReturnType() : null;
        Column column = field.getAnnotation(Column.class);
        //可插入
        this.insertable = column == null || column.insertable();
        //是否可修改
        this.updatable = column == null || column.updatable();
        //如果添加了@Column注解，则使用用户自定义列名，否则，默认为属性名
        this.columnName = column == null ? field.getName() : ("".equals(column.name()) ? field.getName() : column.name());
        //如果添加了@Id
        this.id = field.isAnnotationPresent(Id.class);
        //字段名称
        this.fieldName = field.getName();
        //保存字段
        this.field = field;
    }

    @SuppressWarnings("unchecked")
    Object get(Object target) throws Exception {
        Object r = getter.invoke(target);
        return enumClass == null ? r : Enum.valueOf(enumClass, (String) r);
    }

    @SuppressWarnings("unchecked")
    void set(Object target, Object value) throws Exception {
        if (enumClass != null && value != null) {
            value = Enum.valueOf(enumClass, (String) value);
        }
        //BeanUtils.setProperty(target, fieldName, value);
        try {
            if (value != null) {
                setter.invoke(target, setter.getParameterTypes()[0].cast(value));
            }
        } catch (Exception e) {
            /**
             * 出错原因如果是boolean字段 mysql字段类型 设置tinyint(1)
             */
            log.error("filedName:{},value{}", fieldName, value);
            e.printStackTrace();

        }

    }
}
