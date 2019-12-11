package user.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class UploadHeaderImgServlet
 */
@WebServlet("/uploadHeader")
public class UploadHeaderImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadHeaderImgServlet() {
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
		// TODO Auto-generated method stub
		doGet(request, response);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		SmartUpload smartUpload = new SmartUpload();
		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
			String msg = smartUpload.getRequest().getParameter("msg");
			if (msg != null)
				msg = new String(msg.getBytes("GBK"), "utf-8");
			com.jspsmart.upload.Files files = smartUpload.getFiles();
			for (int i = 0; i < files.getCount(); i++) {
				com.jspsmart.upload.File file = files.getFile(i);
				if (!file.isMissing()) {
					String name = file.getFileName();
					String filename = this.getServletContext().getRealPath("/")
							+ "images\\" + name;
					System.out.println("文件地址"+filename);
					file.saveAs(filename);
				}
			}
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
