package user.service;

import entity.User;

import java.util.List;

import entity.AdminMessage;
import entity.NewTicketBody;
import entity.ParentUserInfo;
import entity.SendPerson;
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
	//用户密码登录
	public User selectUser(String phone) {
		UserDao userDao = new UserDao();
		return userDao.pwdLogin(phone);
	}
	//用户密码登录
	public int getUserType(String phone) {
		UserDao userDao = new UserDao();
		return userDao.getType(phone);
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
	
	public void insertQuestions(String name, String phone,String registationID,String content,String subject,int status) {
		UserDao userDao = new UserDao();
		userDao.saveQuestion(name, phone,registationID,content,subject,status);
	}
	public void insertAnsewer(int id,String phone, String content,String registrationID) {
		UserDao userDao = new UserDao();
		userDao.saveAnswers(id,phone,content,registrationID);
	}
	public void insertAdminMessage(int id,String phone,String content) {
		UserDao userDao = new UserDao();
		userDao.saveMessage(id,phone,content);
	}
	public void changeQuestionsType(int id) {
		UserDao userDao = new UserDao();
		userDao.updateQuestions(id);
	}
	public List<NewTicketBody> selectQuestions(int status) {
		UserDao userDao = new UserDao();
		return userDao.findAllQuestuins(status);
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
	public void insertRelation(String receivePhone,String receiveName,String sendPhone,String sendName,String setName) {
		UserDao userDao = new UserDao();
		userDao.addNewRelation(receivePhone, receiveName,sendPhone,sendName,setName);
	}
	public void insertRelationRequest(String sendPhone,String sendName,String receivePhone,int type) {
		UserDao userDao = new UserDao();
		userDao.addRelationRequest(sendPhone,sendName,receivePhone,type);
	}
	//查找关联的父母
	public List<String> selectParentPhone(String sendPhone) {
		UserDao userDao = new UserDao();
		return userDao.findMyRelation(sendPhone);
	}
	public List<SendPerson> findRequest(String phone) {
		UserDao userDao = new UserDao();
		return userDao.queryRequset(phone);
	}
	public void changeRelations(String phone,String send_phone,String type) {
		UserDao userDao = new UserDao();
		userDao.updateRelations(phone,send_phone,type);
	}
	public List<AdminMessage> findAllAdminMessage(String phone) {
		UserDao userDao = new UserDao();
		return userDao.selectAllAdminMessage(phone);
	}
	public void changeAdminMessageReadable(int id) {
		UserDao userDao = new UserDao();
		userDao.updateReadable(id);
	}
	public String selectContentAndroidandHTML(String postId) {
		UserDao userDao = new UserDao();
		return userDao.getContentAndroidandHTML(postId);
	}
	public void findParentInfo() {
		//to do 查找父母信息
	}

	public void findChildrenInfo() {
		//to do 查找子女信息
	}
}
