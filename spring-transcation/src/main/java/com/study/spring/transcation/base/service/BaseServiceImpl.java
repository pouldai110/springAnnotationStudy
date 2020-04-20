package com.study.spring.transcation.base.service;


import com.study.spring.transcation.base.repository.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class BaseServiceImpl<T, ID extends Serializable> implements
		IBaseService<T, ID> {

	@Autowired
	private BaseRepository<T, ID> baseRepository;

	@Override
	public BaseRepository<T, ID> getBaseRepository() {
		return baseRepository;
	}

	public String genericMethod() {
		System.out.println("doSomething");
		return "doSomething";
	}

}
