<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supplier Management System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous" rel="stylesheet" type="text/css"/>
    <!-- Custom CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa; /* Light gray background */
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
        }
        .button {
            display: inline-block;
            padding: 12px 24px;
            margin: 10px;
            background-color: #007bff; /* Primary color */
            color: #fff;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            border: none;
        }
        .button:hover {
            background-color: #0056b3; /* Darker shade of primary color */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Supplier Management System</h1>
        <p>Efficiently manage your suppliers with our system</p>
        <!-- Sign In Button -->
        <a href="signin.jsp" class="button">Sign In</a>
        <!-- Sign Up Button -->
        <a href="signup.jsp" class="button">Sign Up</a>
    </div>
</body>
</html>
