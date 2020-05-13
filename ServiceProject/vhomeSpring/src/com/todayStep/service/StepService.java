package com.todayStep.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.entity.TodayStepData;
import com.todayStep.dao.StepDao;


@Service
@Transactional(readOnly = false)
public class StepService {
	@Resource
	private StepDao stepDao;
	
	public long getLatestStep() {
		return stepDao.getLatestStep();
	}
	
	public void saveStep(TodayStepData stepData) {
		stepDao.saveStep(stepData);
	}
	
	public void deleteStep() {
		stepDao.deleteStep();
	}
}
