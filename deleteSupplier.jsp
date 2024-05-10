<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.io.*,java.util.*,javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Supplier</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <h2>Delete Supplier</h2>
    <p>Are you sure you want to delete this supplier?</p>

    <%-- Retrieve the id parameter from the request --%>
    <% String id = request.getParameter("id"); %>

    <%-- Form for confirming the deletion --%>
    <form action="deleteSupplier" method="post">
        <input type="hidden" name="id" value="<%= id %>">
        <button type="submit" class="btn btn-danger">Confirm Delete</button>
        <a href="supplier.jsp" class="btn btn-secondary">Cancel</a>
    </form>

    <%-- Display success or error message --%>
    <% String message = (String)request.getAttribute("Message"); %>
    <% if (message != null) { %>
        <div class="alert alert-success mt-3" role="alert">
            <%= message %>
        </div>
    <% } %>
</div>

</body>
</html>
