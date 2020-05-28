package community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import community.service.PostService;
import entity.PostBean;

/**
 * Servlet implementation class SelectFromAllPosts
 */
@WebServlet("/SelectFromAllPosts")
public class SelectFromAllPosts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectFromAllPosts() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		PrintWriter out = response.getWriter();
		List<PostBean> preList = new ArrayList<>();
		//获取需要查找的数据
		String data = null;
		data  = request.getParameter("selectData");
		
		PostService postService = new PostService();
		preList = postService.findPost();
		List<PostBean> lastList = new ArrayList<>();
		for(PostBean bean:preList) {
			if (bean.select().contains(data)) {
				lastList.add(bean);
			}
		}
		Gson gson = new Gson();
		String postData =  gson.toJson(lastList);
		out.write(postData);
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
