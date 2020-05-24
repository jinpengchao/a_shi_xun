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
import entity.PostExamineBean;
import jpushutil.JpushClientUtil;
import user.service.UserService;

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
		String rId=request.getParameter("currentrId");
		String phone=request.getParameter("currentPhone");
		String personId=request.getParameter("currentPersonId");
		out.println(examineString);
		out.println(id);
		
		PostService postService=new PostService();
		UserService us = new UserService();
		postService.changeExamineByPId(id, examineString);
		
		if(examineString.equals("已审核")) {
			int id1=Integer.parseInt(id);
			PostExamineBean postExamineBean=postService.findPostExamine(id1);
			PostBean postBean=postService.deleteExamine(postExamineBean);
			System.out.println(postBean.toString());
			postService.savePost(postBean);
			if (JpushClientUtil.sendToRegistrationId(rId, "",
					"", "", "") == 1) {
				System.out.println("success");
			}
			int idd = Integer.parseInt(id);
			us.insertAdminMessage(idd,"您的帖子已通过审核，点击查看",phone,personId,"-");
		}else {
			if (JpushClientUtil.sendToRegistrationId(rId, "",
					"", "", "") == 1) {
				System.out.println("success");
			}
			int idd = Integer.parseInt(id);
			us.insertAdminMessage(idd,"您的帖子没有通过审核，请检查是否违反社区规定",phone,personId,"-");
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
