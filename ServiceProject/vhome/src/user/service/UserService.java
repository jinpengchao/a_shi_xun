package user.service;

import entity.User;

import java.util.List;

import entity.ParentUserInfo;
import user.dao.UserDao;

public class UserService {
	//注册用户
	public void insertUser(String phone, String password, String registerTime, String id, String wechat, String qq, int type) {
		UserDao userDao = new UserDao();
		userDao.registerUser(phone, password, registerTime, id, wechat, qq, type);
	}
	//检查用户是否存在
	public boolean notExists(String phone) {
		UserDao userDao = new UserDao();
		return userDao.exist(phone);
	}
	//用户密码登录
	public User selectUser(String phone, String password) {
		UserDao userDao = new UserDao();
		return userDao.pwdLogin(phone, password);
	}
	//用户验证码登录
		public User loginBycode(String phone) {
			UserDao userDao = new UserDao();
			return userDao.codeLogin(phone);
		}
	//向用户信息表添加个人信息
	public void insertUserInfo(String phone, String id, String nikeName, String sex, String area, String headerImg, int type) {
		UserDao userDao = new UserDao();
		userDao.addUserInfo(phone, id, nikeName, sex, area, headerImg, type);
	}
	//查询用户信息
	public ParentUserInfo selectUserInfo(String phone, int type) {
		UserDao userDao = new UserDao();
		return userDao.findUserInfo(phone, type);
	}
	//按照id查找个人信息
	public ParentUserInfo selectUserInfo(String id) {
		return (new UserDao()).findUserInfo(id);
	}
	//修改密码
	public void updatePwd(String phone,String newPwd) {
		UserDao userDao = new UserDao();
		userDao.changePwd(phone, newPwd);
	}
	//修改个人信息
	public void updateUserInfo(String phone,int type,String flag , String data) {
		UserDao userDao = new UserDao();
		userDao.changeUserInfo(phone, type,flag, data);
	}
	//添加用户头像图片
	public void updateUserHeaderImg(String phone,int type,String headimg) {
		UserDao userDao = new UserDao();
		userDao.saveHeaderImg(phone, type, headimg);
	}
	//添加子女父母关联
	public void insertRelation(String receivePhone,String sendPhone,int receiveType) {
		UserDao userDao = new UserDao();
		userDao.addNewRelation(receivePhone, sendPhone,receiveType);
	}
	//查找关联的父母
	public List<String> selectParentPhone(String sendPhone) {
		UserDao userDao = new UserDao();
		return userDao.findMyRelation(sendPhone);
	}
}
