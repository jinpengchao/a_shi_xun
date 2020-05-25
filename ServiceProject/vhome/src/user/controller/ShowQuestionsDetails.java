package user.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import community.service.PostService;
import entity.NewTicketBody;
import entity.PostExamineBean;
import user.service.UserService;

/**
 * Servlet implementation class ShowQuestions
 */
@WebServlet("/ShowQuestionsDetails")
public class ShowQuestionsDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowQuestionsDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String data = br.readLine();
		JSONObject json;
		try {
			json = new JSONObject(data);
			String content = json.getString("content");
			String phone = json.getString("phone");
			UserService u = new UserService();
			NewTicketBody question=u.selectQuestions(content,phone);
			Gson gson = new Gson();
			String cao = gson.toJson(question);
			out.write(cao);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
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
