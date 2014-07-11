package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.service.ReportService;

/**
 * Servlet implementation class ReportServlet
 */
public class ReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/json;charset=UTF-8");
		 request.setCharacterEncoding("UTF-8");
		 
		 PrintWriter out = response.getWriter();
		 
		 String id=request.getParameter("id");
		 String type=request.getParameter("type");
		 
		 switch (type) {
		case "out":
			 System.out.println("ajax getout");
			 System.out.println(new ReportService().getOutTypeDetail(Integer.parseInt(id)));
			 out.print(new ReportService().getOutTypeDetail(Integer.parseInt(id)));
			break;
		case "ten":
			 System.out.println("ajax getout");
			 System.out.println(new ReportService().getTenDays(Integer.parseInt(id)));
			 out.print(new ReportService().getTenDays(Integer.parseInt(id)));
			break;

		default:
			break;
		}
		 
		
	}

}
