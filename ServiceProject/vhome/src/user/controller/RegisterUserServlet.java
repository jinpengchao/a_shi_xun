package user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import community.service.PostService;
import entity.PostBean;
import entity.User;
import user.service.UserService;

/**
 * Servlet implementation class RegisterUserServlet
 */
@WebServlet("/register")
public class RegisterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String data = br.readLine();
		Gson gson = new Gson();
		User user = null;
		user = gson.fromJson(data, User.class);
		if(null==user) {
			System.out.println("注册消息未获取");
		}
		String phone = user.getPhone();
		String password = user.getPassword();
		String registerTime = user.getRegisterTime();
		String nikeName = user.getNikeName();
		String id = user.getId();
		String wechat = user.getWechat();
		String qq = user.getQq();
		int type = user.getType();
		UserService userService = new UserService();
		if(userService.notExists(phone)) {
			userService.insertUser(phone, password, registerTime, id, wechat, qq, type);
			userService.insertUserInfo(phone, id, nikeName, "", "", "rc_default_portrait.png", type);
			System.out.println("注册成功！");
			out.write("yes");
		}else
			out.write("no");
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
