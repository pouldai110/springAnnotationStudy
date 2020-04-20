package com.study.spring.transcation;

import com.study.spring.transcation.service.IOutUserSevice;
import com.study.spring.transcation.service.InUserSevice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @ClassName TranscationTestRequired
 * @Author: pouldai
 * @Date: 2020/4/17 12:27
 * @Description:测试spring事务管理 结果与TranscationTestRequired类似
 * @Version V1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TranscationTestRequiredNew {
    @Autowired
    private InUserSevice inUserSevice;

    @Autowired
    private IOutUserSevice outUserSevice;

    /**
     * @Description 传播性REQUIED测试
     * 如果有事务则加入事务，如果没有事务，则创建一个新的（默认值）
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationRequired() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,1);
        inUserSevice.addMoneyErrorByRequied(100d,2);

    }

    /**
     * @Description 内部事务传播性REQUIES_NEW测试
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationRequiresNew() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,3);
        inUserSevice.addMoneyErrorByReqiresNew(100d,4);
    }

    /**
     * @Description 传播性SOUPPORTS测试
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationSoupports() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,5);
        inUserSevice.addMoneyErrorBySupports(100d,6);
    }

    /**
     * @Description 传播性NOT_SOUPPORT测试
     *
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationNotSoupport() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,7);
        inUserSevice.addMoneyErrorByNotSupports(100d,8);

    }

    /**
     * @Description 传播性NEVER测试
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationMandtory() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,9);
        inUserSevice.addMoneyErrorByMandtory(100d,10);

    }

    /**
     * @Description 传播性NEVER测试
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationNever() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,11);
        inUserSevice.addMoneyErrorByNever(100d,12);

    }


    /**
     * @Description 传播性NESTED测试
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public  void testTranscationNested() throws Exception{
        outUserSevice.mouseMoneyByRequied(100d,13);
        inUserSevice.addMoneyErrorByNested(100d,14);

    }
}
