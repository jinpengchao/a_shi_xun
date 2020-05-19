package user.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import community.service.PostService;
import entity.NewTicketBody;
import entity.PostExamineBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import user.service.UserService;

/**
 * Servlet implementation class ShowQuestions
 */
@WebServlet("/ShowQuestions")
public class ShowQuestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowQuestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		List<NewTicketBody> questionsList = new ArrayList<>();
		UserService u = new UserService();
		String s = request.getParameter("questions_status");
		int status = Integer.parseInt(s);
		questionsList=u.selectQuestions(status);
		request.setAttribute("all_questions", questionsList);
		request.setAttribute("questions_status", status);
//		//转化为JSON传
//		JSONArray array = new JSONArray();
//		for(int i =0;i<questionsList.size();i++) {
//			JSONObject jsonObject=new JSONObject();
//			jsonObject.put("name",questionsList.get(i).getCreatorName());
//			jsonObject.put("phone",questionsList.get(i).getCreatorPhone());
//			jsonObject.put("subject",questionsList.get(i).getSubject());
//			jsonObject.put("content",questionsList.get(i).getContent());
//			array.add(jsonObject); 
//		}
//		File file = new File(this.getServletContext().getRealPath("/")+"json\\linksList.json");
//		System.out.println(this.getServletContext().getRealPath("/")+"json\\linksList.json");
//        PrintStream ps = new PrintStream(new FileOutputStream(file));
//        ps.println(array.toString());// 往文件里写入字符串
//        ps.close();
		request.getRequestDispatcher("/page/links/questions.jsp").forward(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
