<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Dashboard</title>
    
    <% 
    if(session.getAttribute("username") == null){
        response.sendRedirect("signin.jsp");
    }
    %>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous" rel="stylesheet" type="text/css"/>
    <style>
        .sidenav {
            height: 100%;
            width: 250px;
            position: fixed;
            z-index: 1;
            top: 0;
            left: 0;
            background-color: #343a40;
            padding-top: 20px;
        }

        .sidenav a {
            padding: 10px 15px;
            text-decoration: none;
            font-size: 18px;
            color: #ffffff;
            display: block;
        }

        .sidenav a:hover {
            background-color: #212529;
        }
        
        .content {
            margin-left: 250px;
            padding: 20px;
        }
    </style>
</head>
<body>
    <!-- Side Navigation -->
    <div class="sidenav">
        <a href="#" class="active">Dashboard</a>
        <a href="supplier.jsp">Supplier</a>
        <!-- Add more navigation links as needed -->
    </div>

    <!-- Main Content -->
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <h1>Welcome to the Dashboard</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <% 
                    String message = (String)request.getAttribute("Message");
                    if(message != null){
                    %>
                    <div class="alert alert-success" role="alert">
                        <%=message%>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
