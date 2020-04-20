package com.study.spring.transcation.service;

import com.study.spring.transcation.base.service.IBaseService;
import com.study.spring.transcation.entity.Account;

/**
 * @ClassName UserSevice
 * @Author: pouldai
 * @Date: 2020/4/16 17:54
 * @Description:
 * @Version V1.0
 */
public interface IOutUserSevice extends IBaseService<Account,Integer> {

    boolean addMoney(Double money, Integer  id) throws  Exception;
    boolean mouseMoneyByRequied(Double money, Integer id) throws  Exception;
    public boolean mouseMoneyError(Double money, Integer id) throws Exception;

}
