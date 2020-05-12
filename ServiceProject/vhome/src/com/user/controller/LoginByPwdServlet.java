package com.user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import com.entity.User;
import net.sf.json.JSONObject;
import com.user.service.UserService;

/**
 * Servlet implementation class LoginByPwdServlet
 */
@WebServlet("/pwdlogin")
public class LoginByPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginByPwdServlet() {
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
			System.out.println("客户端登录消息未获取");
		}
		String phone = user.getPhone();
		String password = user.getPassword();
		
		UserService userService = new UserService();
		User u = userService.selectUser(phone, password);
		if(u != null) {
			int type = u.getType();
			String p = u.getPhone();
			String pwd = u.getPassword();
			JSONObject json = new JSONObject();
			json.put("p", p);
			json.put("pwd", pwd);
			json.put("type", type);
			out.write(json.toString());
		}
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
