package Controllers;

import Models.DatabaseHelper;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;  
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteSupplier")
public class DeleteSupplier extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the id parameter from the request
        String id = request.getParameter("id");

        // Perform deletion
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
            if (con != null) {
                String sql = "DELETE FROM supplier WHERE id=?";
                pst = con.prepareStatement(sql);
                pst.setString(1, id);
                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                     request.setAttribute("Message", "Supplier deleted successfully.");
                    request.getRequestDispatcher("supplier.jsp").forward(request, response);
                } else {
                    
                    response.getWriter().println("Failed to delete supplier.");
                }
            } else {
                // Handle database connection failure
                response.getWriter().println("Failed to establish database connection.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DeleteSupplier.class.getName()).log(Level.SEVERE, null, ex);
            // Handle any exceptions that occur during deletion
            // You can redirect to an error page or display an error message here
            response.getWriter().println("An error occurred during deletion.");
        } finally {
            // Close resources
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DeleteSupplier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Delete Supplier Servlet";
    }
}
