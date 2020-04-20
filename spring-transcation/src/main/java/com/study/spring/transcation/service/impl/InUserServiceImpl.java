package com.study.spring.transcation.service.impl;

import com.study.spring.transcation.base.repository.BaseRepository;
import com.study.spring.transcation.base.service.BaseServiceImpl;
import com.study.spring.transcation.dao.AccountDao;
import com.study.spring.transcation.entity.Account;
import com.study.spring.transcation.service.InUserSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @ClassName UserServiceImpl
 * @Author: pouldai
 * @Date: 2020/4/19 16:37
 * @Description:
 * @Version V1.0
 */
@Service
@Slf4j
public class InUserServiceImpl extends BaseServiceImpl<Account,Integer> implements InUserSevice {
    @Autowired
    private AccountDao accountDao;


    @Override
    public BaseRepository<Account, Integer> getBaseRepository() {
        return accountDao;
    }


    @Override

    public boolean addMoney(Double money, Integer id) throws  Exception {

        Optional<Account> ptional = accountDao.findById(id);
        Account account = ptional.get();

        account.setMoney(account.getMoney() + money);

        getBaseRepository().save(account);

        return true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public boolean addMoneyErrorByRequied(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        throw  new Exception("测试内部传播使用事务Propagation.REQUIRED");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public boolean addMoneyErrorByReqiresNew(Double money, Integer id) throws Exception {
        this.addMoney(money,id);

        throw  new Exception("测试传播内部使用事务Propagation.REQUIRES_NEW错误");

    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = {Exception.class})
    public boolean addMoneyErrorBySupports(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        throw  new Exception("测试传播内部使用事务Propagation.SUPPORTS错误");
    }

    @Override
    @Transactional(propagation=Propagation.NOT_SUPPORTED,rollbackFor = {Exception.class} )
    public boolean addMoneyErrorByNotSupports(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        throw  new Exception("测试传播内部使用事务Propagation.SUPPORTS错误");
    }

    @Override
    @Transactional(propagation=Propagation.MANDATORY,rollbackFor = {Exception.class} )
    public boolean addMoneyErrorByMandtory(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        throw  new Exception("测试传播内部使用事务Propagation.MANDATORY错误");
    }

    @Override
    @Transactional(propagation=Propagation.NEVER,rollbackFor = {Exception.class} )
    public boolean addMoneyErrorByNever(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        throw  new Exception("测试传播内部使用事务Propagation.NEVER错误");
    }

    @Override
    @Transactional(propagation=Propagation.NESTED,rollbackFor = {Exception.class} )
    public boolean addMoneyErrorByNested(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        throw  new Exception("测试传播内部使用事务Propagation.NESTED错误");
    }

    @Override
    public boolean addMoneyErrorError(Double money, Integer id) throws Exception {
        this.addMoney(money,id);
        return false;
    }

//    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public boolean mouseMoney(Double money, Integer id) throws  Exception {
        Optional<Account> ptional = accountDao.findById(id);
        Account account = ptional.get();

        if(account.getMoney()-money<0){
            throw  new Exception("余额不足");
        }
        account.setMoney(account.getMoney() - money);

        getBaseRepository().save(account);
        return true;
    }

    @Override
    public boolean addMoneyError(Double money, Integer id) throws Exception {
        Optional<Account> ptional = accountDao.findById(id);
        Account account = ptional.get();

        account.setMoney(account.getMoney() + money);

        throw  new Exception("添加金额失败");
//        getBaseRepository().save(account);


    }
}
