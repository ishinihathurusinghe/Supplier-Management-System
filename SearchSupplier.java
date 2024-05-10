import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Models.DatabaseHelper;

@WebServlet("/searchSupplier")
public class SearchSupplier extends HttpServlet {

    // Supplier class to represent supplier data
    private static class Supplier {
        private int id;
        private String name;
        private String contactNumber;
        private String address;
        private String email;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContactNumber() {
            return contactNumber;
        }

        public void setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the search query from the request
        String query = request.getParameter("query");

        // Perform the search in the database
        List<Supplier> searchResults = performSearch(query);

        // Forward the search results to the JSP page for display
        request.setAttribute("searchResults", searchResults);
        request.getRequestDispatcher("supplier.jsp").forward(request, response);
    }

    // Method to perform the search in the database
    private List<Supplier> performSearch(String query) {
        List<Supplier> searchResults = new ArrayList<>();

        // Query the database with the search query and retrieve matching suppliers
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
            if (con != null) {
                String sql = "SELECT * FROM supplier WHERE name LIKE ? OR contact_number LIKE ? OR address LIKE ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + query + "%");
                pst.setString(2, "%" + query + "%");
                pst.setString(3, "%" + query + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setId(rs.getInt("id"));
                    supplier.setName(rs.getString("name"));
                    supplier.setContactNumber(rs.getString("contact_number"));
                    supplier.setAddress(rs.getString("address"));
                    supplier.setEmail(rs.getString("email"));
                    searchResults.add(supplier);
                }
            }
        } catch (SQLException | ClassNotFoundException ex) {
        } finally {
            // Close resources
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
          
