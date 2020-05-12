package com.user.service;

import com.user.dao.GetMoreDao;

public class GetInfo {
	private String[] info;
	public String[] getInform(String phone) {
		GetMoreDao dao = new GetMoreDao();
		return dao.getMore(phone);
	}
}
