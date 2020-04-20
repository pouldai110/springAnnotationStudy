package com.study.spring.transcation.dao;

import com.study.spring.transcation.base.repository.BaseRepository;
import com.study.spring.transcation.entity.Account;
import org.springframework.stereotype.Repository;

/**
 * @ClassName AccountDao
 * @Author: pouldai
 * @Date: 2020/4/15 23:40
 * @Description:
 * @Version V1.0
 */

@Repository
public interface AccountDao extends BaseRepository<Account,Integer> {

}
