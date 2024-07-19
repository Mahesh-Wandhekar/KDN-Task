package MainServlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String targetservlet=request.getParameter("servlet");
		String sno=request.getParameter("sno");
		String name=request.getParameter("name");
		String fees=request.getParameter("fees");
		System.out.println(targetservlet);
		System.out.println(sno);
		System.out.println(name);
		System.out.println(fees);
		if("search".equals(targetservlet)) {
			RequestDispatcher rs = request.getRequestDispatcher("search");
			rs.forward(request, response);
			System.out.println("Search servlet done");
		}else if ("register".equals(targetservlet)) {
			RequestDispatcher rs=request.getRequestDispatcher("register");
			rs.forward(request, response);
			System.out.println("Register servlet done");
			
		}else if("remove".equals(targetservlet)) {
			RequestDispatcher rs=request.getRequestDispatcher("remove");
			rs.forward(request, response);
			System.out.println("Remove servlet done");
			
		}
		
	}

}
