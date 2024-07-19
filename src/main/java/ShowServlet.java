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

@WebServlet("/show")
public class ShowServlet extends HttpServlet {
    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "system", "nareshit");
            System.out.println("show connection done");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error connecting to database", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        try (PreparedStatement pstmt = con.prepareStatement("SELECT * FROM student")) {
            ResultSet rs = pstmt.executeQuery();

            pw.println("<html>");
            pw.println("<head><title>Student List</title><link rel='stylesheet' href='css/bootstrap.css'></head>");
            pw.println("<body class='container-fluid mt-4 p-4' style='width: 60rem;'>");
            pw.println("<center>");
            pw.println("<h2 class='bg-danger text-white text-center p-2'>Student List</h2>");
            pw.println("<table class='table table-hover' border='1'>");
            pw.println("<tr>");
            pw.println("<th>ID</th>");
            pw.println("<th>Name</th>");
            pw.println("<th>Fees</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getString(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("</tr>");
            }
            pw.println("</table>");
            pw.println("<a href='login' class='btn btn-primary mt-4'>Back</a>");
            pw.println("</center></body>");
            pw.println("</html>");
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<html><body><p>Error retrieving student data.</p></body></html>");
        } finally {
            pw.close();
        }
    }

    
    }

