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
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import community.service.PostService;
import entity.PostBean;
import entity.PostExamineBean;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class ShowExamine
 */
@WebServlet("/ShowExamine")
public class ShowExamine extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowExamine() {
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
		List<PostExamineBean> postBeans1=(new PostService()).findBeansByExamine("待审核");
		List<PostExamineBean> postBeans2=(new PostService()).findBeansByExamine("已审核");
		List<PostExamineBean> postBeans3=(new PostService()).findBeansByExamine("审核失败");
		out.print(postBeans2.get(0).getExamineString());
		request.setAttribute("examine", postBeans1);
		request.setAttribute("examine1", postBeans2);
		request.setAttribute("examine2", postBeans3);
		List<PostExamineBean> postExamineBeans=(new PostService()).findAll();
		
		//发送所有待审核、已审核、审核失败帖子数据
		JSONObject jsonobject=new JSONObject();
		jsonobject.put("examineString", postExamineBeans);
		String jdata=jsonobject.toString();
		out.write(jdata);
		out.flush();
		out.close();
		System.out.println(jsonobject.toString());
		
//		request.getRequestDispatcher("/list.jsp").forward(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
