<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .error-container {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            padding: 40px;
            max-width: 500px;
            text-align: center;
        }

        h2 {
            color: #dc3545;
            font-size: 28px;
            margin-bottom: 20px;
        }

        .error-message {
            color: #333;
            font-size: 18px;
            margin-bottom: 30px;
            word-wrap: break-word;
        }

        .home-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.2s ease-in-out;
        }

        .home-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h2>Error Occurred</h2>
    <div class="error-message">
        <%= request.getAttribute("errorMessage") %>
    </div>
    <a class="home-button" href="<%= request.getContextPath() %>/index.jsp">Go Back to Home</a>
</div>
</body>
</html>
