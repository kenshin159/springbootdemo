package com.family.tech.workflow;

public abstract class AbstractWorkFlow<T> {
	public abstract void preStep(T pojo);

	public abstract void validationStep(T pojo);

	public abstract void commonStep(T pojo);

	public abstract void postStep(T pojo);

	void process(T pojo) {
		preStep(pojo);
		validationStep(pojo);
		commonStep(pojo);
		postStep(pojo);
	}
}
