package Controllers;

import Models.DatabaseHelper;
import java.io.IOException;
import java.io.PrintWriter;
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

/**
 * Servlet implementation class updateSupplier
 */
@WebServlet("/updateSupplier")
public class updateSupplier extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve form parameters
            String id = request.getParameter("id");
            String name = request.getParameter("supplierName");
            String contactNumber = request.getParameter("contactNumber");
            String code = request.getParameter("supplierCode");
            String address = request.getParameter("address");
            String email = request.getParameter("email");

            // Validate form fields
            if (id == null || id.trim().isEmpty() ||
                name == null || name.trim().isEmpty() ||
                contactNumber == null || contactNumber.trim().isEmpty() ||
                code == null || code.trim().isEmpty()) {
                // Required fields are missing, show error message
                request.setAttribute("ValidationMessage", "Please fill in all required fields.");
                request.getRequestDispatcher("update.jsp?id=" + id).forward(request, response);
                return; // Exit method
            }

            // Proceed with database update
            Connection con = null;
            PreparedStatement pst = null;
            try {
                con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
                if (con != null) {
                    String sql = "UPDATE supplier SET code=?, name=?, contact_number=?, address=?, email=? WHERE id=?";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, code);
                    pst.setString(2, name);
                    pst.setString(3, contactNumber);
                    pst.setString(4, address);
                    pst.setString(5, email);
                    pst.setString(6, id);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {                   
                        request.setAttribute("Message", "Supplier updated successfully.");
                    request.getRequestDispatcher("supplier.jsp").forward(request, response);
                    } else {
                        PrintWriter out = response.getWriter();
                        out.println("Failed to update supplier.");
                    }
                } else {
                    PrintWriter out = response.getWriter();
                    out.println("Failed to establish database connection.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(updateSupplier.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Close resources
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(updateSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Update Supplier Servlet";
    }
}
