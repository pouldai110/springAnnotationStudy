package com.study.spring.transcation.service.impl;

import com.study.spring.transcation.base.repository.BaseRepository;
import com.study.spring.transcation.base.service.BaseServiceImpl;
import com.study.spring.transcation.dao.AccountDao;
import com.study.spring.transcation.entity.Account;
import com.study.spring.transcation.service.IOutUserSevice;
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
public class OutUserServiceImpl extends BaseServiceImpl<Account,Integer> implements IOutUserSevice {

    @Autowired
    private AccountDao accountDao;


    @Override
    public BaseRepository<Account, Integer> getBaseRepository() {
        return accountDao;
    }


    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public boolean addMoney(Double money, Integer id) throws  Exception {
        Optional<Account> ptional = accountDao.findById(id);
        Account account = ptional.get();

        account.setMoney(account.getMoney() + money);

        getBaseRepository().save(account);
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean mouseMoneyByRequied(Double money, Integer id) throws  Exception {
        Optional<Account> ptional = accountDao.findById(id);
        Account account = ptional.get();
        if(account.getMoney()-money<0){
            throw  new Exception("余额不足");
        }
        account.setMoney(account.getMoney() - money);
        getBaseRepository().save(account);
        return  false;

    }



    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean mouseMoneyError(Double money, Integer id) throws Exception {
        Optional<Account> ptional = accountDao.findById(id);
        Account account = ptional.get();
        if(account.getMoney()-money<0){
            throw  new Exception("余额不足");
        }
        account.setMoney(account.getMoney() - money);
        throw new Exception("减少资金失败");

    }
}
