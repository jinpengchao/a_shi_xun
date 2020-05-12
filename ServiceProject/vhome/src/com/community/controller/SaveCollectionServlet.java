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
 * Servlet implementation class SaveCollectionServlet
 */
@WebServlet("/SaveCollectionServlet")
public class SaveCollectionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCollectionServlet() {
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
		String dataIn = null;
		CollectionBean collection = null;
		int i = 0;//接收插入数据返回的参数
		//获取插入的数据
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		dataIn = br.readLine();
		Gson gson = new Gson();
		collection = gson.fromJson(dataIn, CollectionBean.class);
		br.close();
		is.close();
		System.out.println("saveCollecionServlet收到的收藏数据是"+dataIn);
		//插入数据库
		CollectionService collectionService = new CollectionService();
		i = collectionService.saveCollection(collection);
		
		if(i>0) {
			//将返回数据传回客户端,返回数据需要是String类型，发送完关闭以便接收
			out.write(i+"");
			out.flush();
			out.close();
		}else {
			System.out.println("saveCollecionServlet插入数据出错！");
		}
		
	}

}
