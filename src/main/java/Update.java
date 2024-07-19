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
import java.sql.SQLException;

@WebServlet("/Update")
public class Update extends HttpServlet {

    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "system", "nareshit");
            System.out.println("Update connection done");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("JDBC Driver not found", e);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Error connecting to database", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sno = request.getParameter("sno");
        String name = request.getParameter("name");
        String fees = request.getParameter("fees");

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        try {
            PreparedStatement pstmt = con.prepareStatement("UPDATE Student SET name=?, fees=? WHERE sno=?");
            pstmt.setString(1, name);
            pstmt.setString(2, fees);
            pstmt.setString(3, sno);
            int rowsUpdated = pstmt.executeUpdate();

            pw.println("<html><body><center>");
            if (rowsUpdated > 0) {
                pw.println("<h3 class='text-success'>Update Successfully Done</h3>");
                pw.println("<a href='login'>Back</a>");
            } else {
                pw.println("<h3 class='text-danger'>No record found with the given SNO</h3>");
                pw.println("<a href='login'>Back</a>");
            }
            pw.println("</center></body></html>");

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            pw.println("<html><body><center>");
            pw.println("<h3>Error updating record: " + e.getMessage() + "</h3>");
            pw.println("</center></body></html>");
        } finally {
            pw.close();
        }
    }

    @Override
    public void destroy() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
