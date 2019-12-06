package community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import community.service.CommentService;
import community.service.GoodPostService;
import community.service.PostService;
import entity.PostBean;

/**
 * Servlet implementation class GetPostServlet
 */
@WebServlet("/GetPostServlet")
public class GetPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetPostServlet() {
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
		String data = null;
		List<PostBean> list = null;
		list = (new PostService()).findPost();
		System.out.println("getPostServlet中获得"+list.size()+"条数据");
		for(int i=0;i<list.size();i++) {
			int likeNum = (new GoodPostService()).findGoodPostCount(list.get(i).getId());
			list.get(i).setLikeNum(likeNum);
			int commentNum = (new CommentService()).findCommentCount(list.get(i).getId());
			list.get(i).setCommentNum(commentNum);
		}
		Gson gson = new Gson();
		data = gson.toJson(list);
		out.write(data);
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
