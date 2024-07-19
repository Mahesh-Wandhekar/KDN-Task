import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/search")
public class Search extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "system", "nareshit");
            System.out.println("Search Connection done");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("sno");
        System.out.println(id);
        response.setContentType("text/html");
        try {
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM student WHERE sno = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                PrintWriter pw = response.getWriter();
                pw.println("<html>");
                pw.println("<head>");
                pw.println("<link rel='stylesheet' href='css/bootstrap.css'>");
                pw.println("</head>");
                pw.println("<body class='container-fluid card mt-4 p-4' style='width: 40rem;>");
                pw.println("<center>");
                pw.println("<table class='table table-hover p-4'>");
                pw.println("<h2 class=\"bg-danger text-white text-center \">Edit Information</h2>");
                pw.println("<form action='update' method='post' class='p-4' >");
                pw.println("<tr><td>SNO&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                pw.println("<td><input type='text' class='mb-2' name='sno' readonly value='" + rs.getString(1) + "'></td></tr></br>");
                pw.println("<tr><td>Name&nbsp;&nbsp;&nbsp;</td>");
                pw.println("<td><input type='text' class='mb-2' name='name' value='" + rs.getString(2) + "'></td></tr></br>");
                pw.println("<tr><td>Fees&nbsp;&nbsp;&nbsp;&nbsp;</td>");
                pw.println("<td><input type='number' class='mb-2' name='fees' value='" + rs.getString(3) + "'></td></tr></br>");
                pw.println("<tr><td><input type='submit' value='update' class='btn btn-primary'></td></tr></br>");
                pw.println("<tr><td> <a class='text-danger' href='show'>Show All Student Details </a></td></tr>");
                pw.println("</form></table></center></body></html>");
                System.out.println("Search record found");
            } else {
                PrintWriter pw = response.getWriter();
                response.setContentType("text/html");
                pw.println("<html><body><p>No record found for SNO: " + id + "</p></body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
