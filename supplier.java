
package Controllers;

import Models.DatabaseHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Malshan
 */
@WebServlet(name = "supplier", urlPatterns = {"/supplier"})
public class supplier extends HttpServlet {

     // Method to validate email address format
    private boolean isValidEmail(String email) {
        // Regular expression for email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    // Method to validate contact number format
    private boolean isValidContactNumber(String contactNumber) {
        // Regular expression for contact number validation (example: 123-456-7890)
        String contactNumberRegex = "^\\d{3}-\\d{3}-\\d{4}$";
        return Pattern.matches(contactNumberRegex, contactNumber);
    }

    // Method to check if a supplier code already exists in the database
    private boolean isDuplicateSupplierCode(String code) throws SQLException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
            if (con != null) {
                String sql = "SELECT COUNT(*) FROM supplier WHERE code = ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, code);
                rs = pst.executeQuery();
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(supplier.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close resources
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }
    
    
    // Override the doPost method to handle form submission
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Retrieve form parameters
            String name = request.getParameter("supplierName");
            String contactNumber = request.getParameter("contactNumber");
            String code = request.getParameter("supplierCode");
            String address = request.getParameter("address");
            String email = request.getParameter("email");

            // Create PrintWriter for response
            PrintWriter out = response.getWriter();

            // Validate form fields
            if (name == null || name.trim().isEmpty() ||
                contactNumber == null || contactNumber.trim().isEmpty() ||
                code == null || code.trim().isEmpty()) {
                // Required fields are missing, show error message
                request.setAttribute("Message", "Please fill in all required fields.");
                request.getRequestDispatcher("supplier.jsp").forward(request, response);
                return; // Exit method
            }
            // Check if email address is valid
            if (!isValidEmail(email)) {
                request.setAttribute("Message", "Invalid email address.");
                request.getRequestDispatcher("supplier.jsp").forward(request, response);
                return; // Exit method
            }

            // Check if supplier code already exists in the database
            if (isDuplicateSupplierCode(code)) {
                request.setAttribute("Message", "Supplier code already exists. Please choose a different one.");
                request.getRequestDispatcher("supplier.jsp").forward(request, response);
                return; // Exit method
            }
            
            // Check if contact number is valid
            if (!isValidContactNumber(contactNumber)) {
                request.setAttribute("Message", "Invalid contact number format. Please use the format XXX-XXX-XXXX.");
                request.getRequestDispatcher("supplier.jsp").forward(request, response);
                return; // Exit method
            }


            // Other validation rules can be added here

            // Proceed with database insertion
            Connection con = null;
            PreparedStatement pst = null;
            try {
                con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
                if (con != null) {
                    String sql = "INSERT INTO supplier (code, name, contact_number, address, email) VALUES (?, ?, ?, ?, ?)";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, code);
                    pst.setString(2, name);
                    pst.setString(3, contactNumber);
                    pst.setString(4, address);
                    pst.setString(5, email);
                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        request.setAttribute("Message", "Supplier added successfully.");
                        response.sendRedirect("supplier.jsp");
                    } else {
                        out.println("Failed to insert supplier.");
                    }
                } else {
                    out.println("Failed to establish database connection.");
                }
            } catch (SQLException ex) {
                Logger.getLogger(supplier.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Close resources
                if (pst != null) {
                    try {
                        pst.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(supplier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(supplier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(signinController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(supplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Supplier Servlet";
    }
    
}
