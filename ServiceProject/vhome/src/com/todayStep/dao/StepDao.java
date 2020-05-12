package com.todayStep.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.entity.TodayStepData;

@Repository
public class StepDao {
	@Resource
	private SessionFactory sessionFactory;
	
	public long getLatestStep() {
		String hql="from TodayStepData order by id desc ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<TodayStepData> steps = query.getResultList();
		TodayStepData step = steps.get(0);
		return step.getStep();
	}
	
	public void saveStep(TodayStepData stepData) {
		this.sessionFactory.getCurrentSession().save(stepData);
	}
	
	public void deleteStep() {
		Query delete = this.sessionFactory.getCurrentSession().createQuery("delete from TodayStepData where _id>0");
		delete.executeUpdate();
	}
}
