package com.todayStep.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.TodayStepData;
import com.todayStep.service.StepService;


@Controller
@RequestMapping("manageStep")
public class StepController {

	@Resource
	private StepService stepService;

	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public void toDelete() {
		stepService.deleteStep();
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public void toSave(@RequestParam("stepInfo")String data) {
		if(data!=null&&!(data.equals(" "))) {
			TodayStepData stepData = new TodayStepData();
			stepData.set_id(0);
			stepData.setStep(Long.valueOf(data));
			stepService.saveStep(stepData);
		}
	}
	
	@RequestMapping(value="/find",method=RequestMethod.POST)
	public void toFind(HttpServletResponse response) {
		String newStep = String.valueOf(stepService.getLatestStep());
		try {
			response.getWriter().write(newStep);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
