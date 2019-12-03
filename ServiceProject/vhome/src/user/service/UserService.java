package user.service;

import entity.User;
import user.dao.UserDao;

public class UserService {
	//注册用户
	public void insertUser(String phone, String password, String registerTime, String id, String wechat, String qq, int type) {
		UserDao userDao = new UserDao();
		userDao.registerUser(phone, password, registerTime, id, wechat, qq, type);
	}
	//向用户信息表添加个人信息
	public void insertUserInfo(String phone, String id, String nikeName, String sex, String area, String headerImg, int type) {
		UserDao userDao = new UserDao();
		userDao.addUserInfo(phone, id, nikeName, sex, area, headerImg, type);
	}
	//用户登录
	public User selectUser(String phone, String password) {
		UserDao userDao = new UserDao();
		return userDao.pwdLogin(phone, password);
	}
}
