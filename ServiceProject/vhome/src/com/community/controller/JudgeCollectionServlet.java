package com.community.controller;

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

import com.community.service.CollectionService;
import com.entity.CollectionBean;
import com.google.gson.Gson;


/**
 * Servlet implementation class JudgeCollectionServlet
 */
@WebServlet("/JudgeCollectionServlet")
public class JudgeCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JudgeCollectionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String exist = null;
		String dataIn = null;
		CollectionBean collection = null;
		//获取查询的数据
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		dataIn = br.readLine();
		Gson gson = new Gson();
		collection = gson.fromJson(dataIn, CollectionBean.class);
		br.close();
		is.close();
		System.out.println("JudgeCollecionServlet收到的判断数据是"+dataIn);
		//进行判断
		exist = (new CollectionService()).findCollecion(collection);
		if("exist".equals(exist)) {
			System.out.println("JudgeCollecionServlet已经收藏过！");
		}else {
			System.out.println("JudgeCollecionServlet没有收藏过！");
		}
		out.write(exist);
		out.flush();
		out.close();
	}

}
