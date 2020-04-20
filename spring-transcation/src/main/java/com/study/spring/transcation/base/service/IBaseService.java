package com.study.spring.transcation.base.service;

import com.study.spring.transcation.base.repository.BaseRepository;

import java.io.Serializable;

/**
 * IBaseService.java对象
 * 通过IBaseService可以扩展通用的service方法.
 * @version 1.0.0.1
 * @author  daiyp
 */
public interface IBaseService<T, ID extends Serializable> {
	BaseRepository<T,ID> getBaseRepository();


}
