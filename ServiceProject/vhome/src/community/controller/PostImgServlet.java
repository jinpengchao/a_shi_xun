package community.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class PostImgServlet
 */
@WebServlet("/PostImgServlet")
public class PostImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostImgServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyyMMddHHmmssSSS");
					String name = sdf.format(new java.util.Date());
					name = name + "." + file.getFileExt();// 得到文件的扩展名
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
