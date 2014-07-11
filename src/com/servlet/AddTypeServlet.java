package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.QuickTypeService;
import com.service.UserService;

/**
 * Servlet implementation class addType
 */
@WebServlet("/addType")
public class AddTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddTypeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml;charset=UTF-8");

		PrintWriter out = response.getWriter();
		System.out.println("AJAX addType invoked!");
		String type = request.getParameter("type");
		String id=request.getParameter("userID");
		String dir=request.getParameter("dir");
		System.out.println("ID:"+id+"ADD type:"+type);
		
		QuickTypeService qts=new QuickTypeService();
		Boolean res=qts.addType(type, id,Integer.parseInt(dir));
		
		if (res) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>ok</msg></result>");
		}
		if (!res) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<result><msg>error</msg></result>");
		}
		out.flush();
		out.close();
	}

}
