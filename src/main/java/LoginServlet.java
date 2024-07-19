
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Pattern idPattern = Pattern.compile("^.{1}.{2}@.{2}$");
		Pattern passwordPattern = Pattern.compile("^(?=.*#).{7}\\d$");

		
		String id = request.getParameter("id");
		String password = request.getParameter("password");

		
		Matcher idMatcher = idPattern.matcher(id);
		Matcher passwordMatcher = passwordPattern.matcher(password);

		
		response.setContentType("text/html");

		
		PrintWriter pw = response.getWriter();

		
		pw.println("<html><link rel='stylesheet' href='css/bootstrap.css'>");
		if (idMatcher.matches() && passwordMatcher.matches()) {
			pw.println("<center>");
			pw.println("<h6 class= 'text-success'>Login Successfully</h6>");
			pw.println("</center>");
			pw.println("<head>");
			pw.println("<script>\r\n" + "	function setServlet(servlet) {\r\n"
					+ "		document.getElementById('targetServlet').value = servlet;\r\n" + "	}\r\n" + "</script>");
			pw.println("</head>");
			pw.println("<body <body class=\"container-fluid card mt-4 p-4\" style=\"width: 40rem;\"><center>");
			pw.println("<form action='mainservlet' method='post'>");
			pw.println("<h3 class=\"bg-danger text-white text-center \">Student Details</h3>");
			pw.println("<table class=\"tabel tabel-hover p-4\">");
			pw.println("<tr>\r\n" + "				<td>SNO</td>\r\n"
					+ "				<td><input type=\"hidden\" id=\"targetServlet\" name=\"servlet\" value=\"\"></td>\r\n"
					+ "				<td><input type=\"text\" class=\"mb-2\" name=\"sno\" requird></td>\r\n"
					+ "				<td><button class='btn btn-primary m-2' type=\"button\"\r\n"
					+ "					onclick=\"setServlet('search'); this.form.submit();\">Search</button></td>\r\n"
					+ "			</tr>");
			pw.println("<tr>\r\n" + "				<td>Name</td>\r\n"
					+ "				<td><input type=\"hidden\" id=\"targetServlet\" name=\"servlet\" value=\"\"></td>\r\n"
					+ "				<td><input type=\"text\" class=\"mb-2\" name=\"name\"></td>\r\n"
					+ "			</tr>");
			pw.println("<tr>\r\n" + "				<td>Fees</td>\r\n"
					+ "				<td><input type=\"hidden\" id=\"targetServlet\" name=\"servlet\" value=\"\"></td>\r\n"
					+ "				<td><input type=\"number\" class=\"mb-2\" name=\"fees\"></td>\r\n"
					+ "			</tr>");
			pw.println("<tr>\r\n" + "				<td><button class='btn btn-primary m-2' type=\"button\"\r\n"
					+ "					onclick=\"setServlet('register'); this.form.submit();\">Register</button></td>\r\n"
					+ "				<td><button class='btn btn-primary m-2' type=\"button\"\r\n"
					+ "					onclick=\"setServlet('remove'); this.form.submit();\">Remove</button></td>\r\n"
					+ "	</tr>");
			
			pw.println("<tr><td> <a class='text-danger' href='show'>Show All Student Details </a></td></tr>");
			pw.println("</table>");
			pw.println("</form>");
		} else {
			pw.println("<center><h3 class='text-danger'>Invalid Id or Password Format</h3>");
			pw.println("<p>Please Enter valid UserId And Password..!</p>");
            RequestDispatcher rs=request.getRequestDispatcher("login.html");
            rs.include(request, response);
		}
		pw.println("</center></body></html>");
	}
}
