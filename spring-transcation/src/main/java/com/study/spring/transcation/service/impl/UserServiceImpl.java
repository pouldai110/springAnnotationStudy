package com.study.spring.transcation.service.impl;

import com.study.spring.transcation.base.repository.BaseRepository;
import com.study.spring.transcation.base.service.BaseServiceImpl;
import com.study.spring.transcation.dao.AccountDao;
import com.study.spring.transcation.entity.Account;
import com.study.spring.transcation.service.IOutUserSevice;
import com.study.spring.transcation.service.UserSevice;
import com.study.spring.transcation.service.InUserSevice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName UserServiceImpl
 * @Author: pouldai
 * @Date: 2020/4/19 16:37
 * @Description:
 * @Version V1.0
 */
@Service
@Slf4j
public class UserServiceImpl  extends BaseServiceImpl<Account,Integer> implements UserSevice
{

    @Autowired
    private AccountDao accountDao;


    @Autowired
    private IOutUserSevice outUserSevice;

    @Autowired
    private InUserSevice inUserSevice;

    @Override
    public BaseRepository<Account, Integer> getBaseRepository() {
        return accountDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = {Exception.class})
    public boolean transforeMoneyByRequiredNew(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoney(money,inId);
        throw  new Exception("测试事务");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = {Exception.class})
    public boolean transforeMoneyByRequired(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoneyError(money,inId);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = {Exception.class})
    public boolean transforeMoneyBySoupport(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoneyError(money,inId);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED,rollbackFor = {Exception.class})
    public boolean transforeMoneyByNotSoupport(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoneyError(money,inId);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY,rollbackFor = {Exception.class})
    public boolean transforeMoneyByMANDATORY(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoneyError(money,inId);
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.NEVER,rollbackFor = {Exception.class})
    public boolean transforeMoneyByNever(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoneyError(money,inId);
        return true;
    }


    @Override
    @Transactional(propagation = Propagation.NESTED,rollbackFor = {Exception.class})
    public boolean transforeMoneyByNested(Double money, Integer outId, Integer inId) throws Exception {
        outUserSevice.mouseMoneyByRequied(money,outId);
        inUserSevice.addMoneyError(money,inId);
        return true;
    }




}
