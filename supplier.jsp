<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="Models.DatabaseHelper"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Supplier</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <h1 class="mt-4">Supplier</h1>
        <div class="alert alert-danger mt-4" id="validationMessage" style="display:none;">
             <!-- Validation messages will be displayed here -->
        </div>

        <div class="row mt-4">
            <!-- Supplier Form Column -->
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Add Supplier</h5>
                        <form id="sup" method="POST" action="supplier">
                            <div class="form-group">
                                <label for="supplierName">Supplier Name</label>
                                <input type="text" class="form-control" name="supplierName" id="supplierName" placeholder="Enter supplier name">
                            </div>
                            <div class="form-group">
                                <label for="supplierName">Supplier Code</label>
                                <input type="text" class="form-control" name="supplierCode" id="supplierCode" placeholder="Enter supplier Code">
                            </div>
                            <div class="form-group">
                                <label for="contactNumber">Contact Number</label>
                                <input type="text" class="form-control" id="contactNumber" name="contactNumber" placeholder="Enter contact number">
                            </div>
                            <div class="form-group">
                                <label for="address">Address</label>
                                <textarea class="form-control" id="address" name="address" rows="3" placeholder="Enter address"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Enter email">
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <button type="reset" class="btn btn-warning ml-2 ">Reset</button>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Supplier Details Table Column -->
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Supplier Details</h5>
                        <form method="GET" action="searchSupplier">
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" placeholder="Search suppliers..." name="query">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-secondary" type="submit">Search</button>
                                </div>
                            </div>
                        </form>

                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Supplier Name</th>
                                    <th scope="col">Contact Number</th>
                                    <th scope="col">Actions</th> <!-- Added a new column for actions -->
                                </tr>
                            </thead>
                            <tbody>
                                
                                
                                <!-- Retrieve supplier data from servlet and generate table rows dynamically -->
                                <%
                                    try {
                                        Connection con = DatabaseHelper.connectToDatabase("jdbc:mysql://localhost:3306/jspcurd", "root", "");
                                        if (con != null) {
                                            Statement stmt = con.createStatement();
                                            ResultSet rs = stmt.executeQuery("SELECT * FROM supplier");
                                            while (rs.next()) {
                                %>
                                                <tr>
                                                    <td><%= rs.getString("name") %></td>
                                                    <td><%= rs.getString("contact_number") %></td>
                                                    <td>
                                                        <!-- Edit and Delete buttons -->
                                                        <a href="update.jsp?id=<%= rs.getString("id") %>" class="btn btn-primary btn-sm">Edit</a>
                                                        <a href="deleteSupplier.jsp?id=<%= rs.getString("id") %>" class="btn btn-danger btn-sm">Delete</a>

                                                    </td>
                                                </tr>
                                <%
                                            }
                                            rs.close();
                                            stmt.close();
                                            con.close();
                                        }
                                    } catch (SQLException | ClassNotFoundException ex) {
                                        ex.printStackTrace();
                                    }
                                %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
    // Check if ValidationMessage exists and display it
    var validationMessage = "<%= request.getAttribute("Message") %>";
    var messageBox = document.getElementById("validationMessage");
    if (validationMessage) {
        messageBox.innerHTML = validationMessage;
        messageBox.style.display = "block";
    } else {
        messageBox.style.display = "none";
    }
</script>

</body>
</html>
