package com.community.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.community.service.AttentionService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetCountServlet
 */
@WebServlet("/GetCountServlet")
public class GetCountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out = response.getWriter();
		String personId = null;
		String dataOut = null;
		personId = request.getParameter("personId");
		JSONObject jo = new JSONObject();
		//查询关注人数量
		int attentionNum = (new AttentionService()).findAttentionCount(personId);
		//查询粉丝的数量
		int funsNum = (new AttentionService()).findFunsCount(personId);
		jo.put("attentionNum", attentionNum);
		jo.put("funsNum", funsNum);
		dataOut = jo.toString();
		out.write(dataOut);
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
