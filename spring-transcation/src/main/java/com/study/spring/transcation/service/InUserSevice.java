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
public interface InUserSevice extends IBaseService<Account,Integer> {



    boolean addMoney(Double money, Integer  id) throws Exception;

    boolean addMoneyErrorByRequied(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorByReqiresNew(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorBySupports(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorByNotSupports(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorByMandtory(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorByNever(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorByNested(Double money, Integer id) throws  Exception;
    boolean addMoneyErrorError(Double money, Integer id) throws  Exception;

    boolean addMoneyError(Double money, Integer id) throws  Exception;

}
