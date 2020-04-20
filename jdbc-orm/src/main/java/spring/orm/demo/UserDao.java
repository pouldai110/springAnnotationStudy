package spring.orm.demo;

import spring.orm.core.BaseDaoSupport;

import javax.sql.DataSource;

/**
 * @ClassName UserDao
 * @Author: pouldai
 * @Date: 2020/4/13 22:15
 * @Description:
 * @Version V1.0
 */

public class UserDao extends BaseDaoSupport<User, Long> {


    public void setDataSources(DataSource dataSource) {
        super.setDataSourceWrite(dataSource);
        super.setDataSourceReadOnly(dataSource);
    }
}
