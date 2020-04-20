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
 * @Description:测试spring事务管理，外部事务为NEVER
 * @Version V1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TranscationTestNever {
    @Autowired
    private InUserSevice inUserSevice;

    @Autowired
    private IOutUserSevice outUserSevice;

    /**
     * @Description 内部传播性REQUIED测试  资金默认1000
     *
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     * @result
     * 1：资金900 由于outUserSevice支持事务，但testTranscationRequired当前无事务， outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 2：资金1000，执行添加报异常，事务回滚
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
    public  void testTranscationRequired() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,1);
        inUserSevice.addMoneyErrorByRequied(100d,2);

    }

    /**
     * @Description 内部事务传播性REQUIES_NEW测试  资金默认1000
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     * @result
     * 1：资金900 由于outUserSevice支持事务，但testTranscationRequired当前无事务， outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 2：资金1000，执行添加报异常，事务回滚
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
    public  void testTranscationRequiresNew() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,3);
        inUserSevice.addMoneyErrorByReqiresNew(100d,4);
    }

    /**
     * @Description 传播性SOUPPORTS测试 资金默认1000
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     * @result
     * 1：资金900 由于outUserSevice支持事务，但testTranscationRequired当前无事务，
     * outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 2：资金1100，由于testTranscationSoupports无事务，inUserSevice.addMoneyErrorBySupports以无事务方式方式进行，
     * 数据添加成功后手动抛出异常不会进行回滚操作
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
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
     * @result
     * 1：资金900 由于outUserSevice支持事务，但testTranscationRequired当前无事务，
     * outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 2：资金1100，inUserSevice.addMoneyErrorByNotSupports以无事务方式方式进行，
     * 数据添加成功后手动抛出异常不会进行回滚操作
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
    public  void testTranscationNotSoupport() throws  Exception{
        outUserSevice.mouseMoneyByRequied(100d,7);
        inUserSevice.addMoneyErrorByNotSupports(100d,8);

    }

    /**
     * @Description 传播性MANDTORY测试
     * @date 2020/4/19 7:41 PM
     * @author pouldai
     * @param
     * @return void
     * @version v1.0
     * @result
     * 1：资金900 由于outUserSevice支持事务，但testTranscationMandtory当前无事务，
     * outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 2：资金1000，inUserSevice.addMoneyErrorByMandtor邀请必须存在外部事务调用，
     * 直接抛出异常org.springframework.transaction.IllegalTransactionStateException，不会进行方法执行
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
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
     * 11：资金900 由于outUserSevice支持事务，但testTranscationMandtory当前无事务，
     * outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 12：资金1000，inUserSevice.addMoneyErrorByMandtor邀请必须存在外部事务调用，
     * 直接抛出异常org.springframework.transaction.IllegalTransactionStateException，不会进行方法执行
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
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
     * 11：资金900 由于outUserSevice支持事务，但testTranscationMandtory当前无事务，
     * outUserSevice.mouseMoneyByRequied会新建事务
     * outUserSevice.mouseMoneyByRequied执行成功，事务提交，资金扣除
     * 12：资金1000，与reqired类似
     */
    @Test
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
    public  void testTranscationNested() throws Exception{
        outUserSevice.mouseMoneyByRequied(100d,13);
        inUserSevice.addMoneyErrorByNested(100d,14);

    }
}
