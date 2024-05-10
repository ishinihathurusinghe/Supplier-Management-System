<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<%@ page import="Models.DatabaseHelper" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update Supplier</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h1 class="mt-4">Update Supplier</h1>
        <form class="mt-4" action="updateSupplier" method="post">
            <% 
                // Retrieve supplier details by ID from request parameter
                String id = request.getParameter("id");
                try {
                    Connection con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
                    if (con != null) {
                        PreparedStatement pst = con.prepareStatement("SELECT * FROM supplier WHERE id = ?");
                        pst.setString(1, id);
                        ResultSet rs = pst.executeQuery();
                        if (rs.next()) {
            %>
                            <input type="hidden" name="id" value="<%= rs.getString("id") %>">
                            <div class="form-group">
                                <label for="supplierName">Supplier Name</label>
                                <input type="text" class="form-control" name="supplierName" id="supplierName" value="<%= rs.getString("name") %>">
                            </div>
                            <div class="form-group">
                                <label for="supplierCode">Supplier Code</label>
                                <input type="text" class="form-control" name="supplierCode" id="supplierCode" value="<%= rs.getString("code") %>">
                            </div>
                            <div class="form-group">
                                <label for="contactNumber">Contact Number</label>
                                <input type="text" class="form-control" name="contactNumber" id="contactNumber" value="<%= rs.getString("contact_number") %>">
                            </div>
                            <div class="form-group">
                                <label for="address">Address</label>
                                <textarea class="form-control" name="address" id="address" rows="3"><%= rs.getString("address") %></textarea>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" name="email" id="email" value="<%= rs.getString("email") %>">
                            </div>
                            <button type="submit" class="btn btn-primary">Update</button>
            <%
                        }
                        rs.close();
                        pst.close();
                        con.close();
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            %>
        </form>
    </div>
</body>
</html>
