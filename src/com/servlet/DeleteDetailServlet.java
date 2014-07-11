package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.entity.DetailEntity;
import com.service.DetailService;
import com.service.QuickTypeService;



public class DeleteDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDetailServlet() {
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
		System.out.println("AJAX addDetail invoked!");
		
		String id=request.getParameter("id");
		
		//System.out.println(DetailType+WalletID+DetailDir+DetailPrice+comment);
		
		
		QuickTypeService qs=new QuickTypeService();
		Boolean res=qs.deleteType(Integer.parseInt(id));
		
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
