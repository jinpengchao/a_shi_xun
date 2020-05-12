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

import org.json.JSONException;
import org.json.JSONObject;

import com.community.service.CommentService;
import com.community.service.PostService;
import com.community.service.ReplyService;
import com.entity.ParentUserInfo;
import com.user.service.UserService;


/**
 * Servlet implementation class SaveUserHeaderImgServlet
 */
@WebServlet("/saveheaderImg")
public class SaveUserHeaderImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveUserHeaderImgServlet() {
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
		String d = br.readLine();
		UserService userService = new UserService();
		try {
			JSONObject json = new JSONObject(d);
			String phone = json.getString("phone");
			int type = json.getInt("type");
			//修改用户表和帖子表中信息
			ParentUserInfo pui = userService.selectUserInfo(phone, type);
			String personId = pui.getId();
			String headimg = json.getString("fileName");
			userService.updateUserHeaderImg(phone, type, headimg);
			(new PostService()).changImgById(headimg, personId);
			(new CommentService()).changeImg(headimg, personId);
			(new ReplyService()).changeLogo(headimg, personId);
			System.out.println(headimg);
			out.write(headimg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
