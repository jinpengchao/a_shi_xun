package user.controller;

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

import org.json.JSONException;
import org.json.JSONObject;

import user.service.UserService;

/**
 * Servlet implementation class SendRequsetOfRelation
 */
@WebServlet("/SendRequsetOfRelation")
public class SendRequsetOfRelation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendRequsetOfRelation() {
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
		doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/text;charset=utf-8");
		InputStream is = request.getInputStream();
		PrintWriter out = response.getWriter();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String data = br.readLine();
		
		JSONObject json;
		try {
			json = new JSONObject(data);
			String sendPhone = json.getString("sendPhone");
			String sendName = json.getString("sendName");
			String receivePhone = json.getString("receivePhone");
			int type = 0;
			UserService userService = new UserService();
			userService.insertRelationRequest(sendPhone,sendName,receivePhone,type);
			
			out.write("ojbk");
			System.out.println(sendPhone+sendName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}