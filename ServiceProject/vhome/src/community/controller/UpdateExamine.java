package community.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import community.service.PostService;
import entity.PostBean;

/**
 * Servlet implementation class UpdateExamine
 */
@WebServlet("/UpdateExamine")
public class UpdateExamine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateExamine() {
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
		String examineString=request.getParameter("examineString");
		String id=request.getParameter("id");
		out.println(examineString);
		out.println(id);
		
		PostService postService=new PostService();
		postService.changeExamineByPId(id, examineString);
		if(examineString.equals("已审核")) {
			int id1=Integer.parseInt(id);
			PostBean postBean=postService.findPost(id1);
			postService.savePost(postBean);
		}
		
		response.sendRedirect("/vhome/ShowExamine");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
