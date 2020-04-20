package spring;

import com.mysql.cj.jdbc.MysqlDataSource;
import spring.common.Page;
import spring.common.PageResult;
import spring.orm.core.EntityOperation;
import spring.orm.core.QueryRule;
import spring.orm.demo.User;
import spring.orm.demo.UserDao;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.List;

/**
 * @ClassName ormTest
 * @Author: pouldai
 * @Date: 2020/4/13 22:08
 * @Description:
 * @Version V1.0
 */
public class ormTest {
    String url = "jdbc:mysql://127.0.0.1:3306/cdjcy?useUnicode=true&characterEncoding=UTF-8&useSSL=false&zeroDateTimeBehavior=convertToNull";
    String username = "root";
    String passowrd = "root";
    String driverClass = "com.mysql.jdbc.Driver";


    @Test
    public void testEntityOperation() {
        EntityOperation<User> userEntityOperation = new EntityOperation<>(User.class);
        System.out.println(userEntityOperation.toString());
    }


    @Test
    public void teseJdbc() throws Exception {

        DataSource mysqlDataSource = getDatasource();

        UserDao userDao = new UserDao();
        userDao.setDataSources(mysqlDataSource);
        User user = userDao.get(1L);
        System.out.println(user.toString());

    }

    /**
     * 根据查询规则查询
     *
     * @throws Exception
     */
    @Test
    public void testQueryRule() throws Exception {


        DataSource mysqlDataSource = getDatasource();
        UserDao userDao = new UserDao();
        userDao.setDataSources(mysqlDataSource);

        QueryRule queryRule = new QueryRule();
        queryRule.andEqual("sex", 0);


        List<User> select = userDao.select(queryRule);
        System.out.println(select.size());
        for (User user : select) {
            System.out.println(user.toString());
        }

    }


    /**
     * 根据查询规则查询
     *
     * @throws Exception
     */
    @Test
    public void testQueryRulePage() throws Exception {


        DataSource mysqlDataSource = getDatasource();
        UserDao userDao = new UserDao();
        userDao.setDataSources(mysqlDataSource);

        QueryRule queryRule = new QueryRule();
        Page page = new Page(1, 10);


        PageResult<User> pageResult = userDao.select(queryRule, page);
        System.out.println(pageResult.toString());

    }

    private DataSource getDatasource() {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(url);
        mysqlDataSource.setUser(username);
        mysqlDataSource.setPassword(passowrd);
        return mysqlDataSource;
    }
}

