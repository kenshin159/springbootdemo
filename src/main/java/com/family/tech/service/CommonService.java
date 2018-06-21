package com.family.tech.service;

import java.util.List;

public interface CommonService<T, ID> {

	T findById(ID id);

	List<T> findAll();

	T save(T object);

	List<T> save(List<T> objects);

	void delete(T object);

	void delete(List<T> objects);

}
