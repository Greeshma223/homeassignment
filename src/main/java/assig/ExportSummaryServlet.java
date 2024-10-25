package assig;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/export")
public class ExportSummaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/markdown");
        response.setHeader("Content-Disposition", "attachment; filename=project_summary.md");

        try (PrintWriter out = response.getWriter(); Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement()) {
            // Query for projects
            String projectQuery = "SELECT * FROM project";
            ResultSet projectResult = stmt.executeQuery(projectQuery);

            out.println("# Project Summary\n");
            while (projectResult.next()) {
                int projectId = projectResult.getInt("id");
                String title = projectResult.getString("title");
                out.printf("## Project ID: %d\n", projectId);
                out.printf("- Title: %s\n", title);

                // Query for todos related to this project
                String todoQuery = "SELECT * FROM todo WHERE project_id = " + projectId;
                ResultSet todoResult = stmt.executeQuery(todoQuery);
                out.println("- Todos:");

                while (todoResult.next()) {
                    String description = todoResult.getString("description");
                    String status = todoResult.getString("status");
                    out.printf("  - %s (Status: %s)\n", description, status);
                }
                out.println(); // Add an empty line for better readability
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


