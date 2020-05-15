package community.controller;

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

import com.google.gson.Gson;

import community.service.AttentionService;
import entity.AttentionBean;
import entity.User;
import net.sf.json.JSONObject;
import user.service.UserService;

/**
 * Servlet implementation class IfAttention
 */
@WebServlet("/IfAttention")
public class IfAttention extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IfAttention() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
		String phones[] = data.split("-");
		String myPhone = phones[0];
		String oppositePhone = phones[1];
		System.out.println(myPhone+oppositePhone);
		AttentionService attention = new AttentionService();
		int n = attention.ifAttention(myPhone, oppositePhone);
		out.write(n+"");
		System.out.println(n+"");
		out.flush();
		out.close();
		br.close();
		is.close();
	}

}
