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

@WebServlet("/remove")
public class Remove extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection con;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:ORCL", "system", "nareshit");
            System.out.println("Remove Connection done");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String sno = request.getParameter("sno");
        String name = request.getParameter("name");
        String fees = request.getParameter("fees");

        try (PrintWriter pw = response.getWriter()) {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM student WHERE sno = ? AND name = ? AND fees = ?");
            pstmt.setString(1, sno);
            pstmt.setString(2, name);
            pstmt.setString(3, fees);

            int rowsDeleted = pstmt.executeUpdate(); 

            pw.println("<html><body>");
            if (rowsDeleted > 0) {
                pw.println("<h3>Record is Deleted</h3>");
                System.out.println("Record is Deleted");
            } else {
                pw.println("<h3>No matching record found to delete</h3>");
                System.out.println("No matching record found to delete");
            }
            pw.println("</body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
