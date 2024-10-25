package assig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

import java.sql.*;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO project (title) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            stmt.executeUpdate();
            response.sendRedirect("projects.html");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

