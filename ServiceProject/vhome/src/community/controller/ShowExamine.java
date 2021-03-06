package community.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import community.service.AttentionService;
import community.service.CollectionService;
import community.service.CommentService;
import community.service.GoodPostService;
import community.service.PostService;
import entity.AttentionBean;
import entity.CollectionBean;
import entity.GoodPostBean;
import entity.Images;
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
//		out.print(postBeans2.get(0).getImgs());
		
		for(PostExamineBean post:postBeans1) {
			String dateString=post.getImgs();
			String dataString2="";
			//生成图片字符串,插进数据库时删除[""]
			for(int i=0;i<dateString.length();i++) {
				if(!String.valueOf(dateString.charAt(i)).equals("\"")&&!String.valueOf(dateString.charAt(i)).equals("[")&&!String.valueOf(dateString.charAt(i)).equals("]")) {
					dataString2+=dateString.charAt(i);
				}
			}
    		post.setImgs(dataString2);
		}
		for(PostExamineBean post:postBeans2) {
			String dateString=post.getImgs();
			String dataString2="";
			//生成图片字符串,插进数据库时删除[""]
			for(int i=0;i<dateString.length();i++) {
				if(!String.valueOf(dateString.charAt(i)).equals("\"")&&!String.valueOf(dateString.charAt(i)).equals("[")&&!String.valueOf(dateString.charAt(i)).equals("]")) {
					dataString2+=dateString.charAt(i);
				}
			}
    		post.setImgs(dataString2);
		} 
		for(PostExamineBean post:postBeans3) {
			String dateString=post.getImgs();
			String dataString2="";
			//生成图片字符串,插进数据库时删除[""]
			for(int i=0;i<dateString.length();i++) {
				if(!String.valueOf(dateString.charAt(i)).equals("\"")&&!String.valueOf(dateString.charAt(i)).equals("[")&&!String.valueOf(dateString.charAt(i)).equals("]")) {
					dataString2+=dateString.charAt(i);
				}
			}
    		post.setImgs(dataString2);
		}
		
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(PostExamineBean p:postBeans1) {
			try {
				String timeString=format.format(format.parse(p.getTime()));
				p.setTime(timeString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(PostExamineBean p:postBeans2) {
			try {
				String timeString=format.format(format.parse(p.getTime()));
				p.setTime(timeString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for(PostExamineBean p:postBeans3) {
			try {
				String timeString=format.format(format.parse(p.getTime()));
				p.setTime(timeString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		request.setAttribute("examine", postBeans1);
		request.setAttribute("examine1", postBeans2);
		request.setAttribute("examine2", postBeans3);
		
		//进行帖子的审核
		request.getRequestDispatcher("/page/news/newsList.jsp").forward(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
