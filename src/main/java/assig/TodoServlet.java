package assig;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/todos")
public class TodoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int projectId = Integer.parseInt(request.getParameter("project_id"));
        String description = request.getParameter("description");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO todo (project_id, description) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, projectId);
            stmt.setString(2, description);
            stmt.executeUpdate();
            response.sendRedirect("projects.html");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



