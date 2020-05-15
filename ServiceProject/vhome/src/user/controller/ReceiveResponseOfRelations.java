package user.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import community.service.AttentionService;
import community.service.CollectionService;
import community.service.CommentService;
import community.service.GoodPostService;
import community.service.PostService;
import entity.AttentionBean;
import entity.CollectionBean;
import entity.GoodPostBean;
import entity.PostBean;
import entity.SendPerson;
import user.service.UserService;

/**
 * Servlet implementation class ReceiveResponseOfRelations
 */
@WebServlet("/ReceiveResponseOfRelations")
public class ReceiveResponseOfRelations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiveResponseOfRelations() {
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
		InputStream is = request.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String data = br.readLine();
		List<SendPerson> list = null;
		JSONObject json;
			try {
				json = new JSONObject(data);
				String receivePhone = json.getString("receivePhone");
				list = (new UserService()).findRequest(receivePhone);
				System.out.println("获得"+list.size()+"条添加请求");
				
				Gson gson = new Gson();
				data = gson.toJson(list);
				out.write(data);
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