<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goodbye!</title>
    <style>
        body {
            background: #f6f8fc;
            font-family: 'Segoe UI', Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .goodbye-container {
            background: #fff;
            padding: 48px 36px;
            border-radius: 12px;
            box-shadow: 0 2px 16px #e0e0e0;
            text-align: center;
        }
        h2 {
            color: #1976d2;
            margin-bottom: 18px;
        }
        p {
            color: #444;
            font-size: 1.15em;
            margin-bottom: 28px;
        }
        a {
            color: #1976d2;
            text-decoration: none;
            font-weight: bold;
            font-size: 1em;
            border: 1px solid #1976d2;
            padding: 8px 18px;
            border-radius: 6px;
            transition: background 0.2s, color 0.2s;
        }
        a:hover {
            background: #1976d2;
            color: #fff;
        }
        .emoji {
            font-size: 2.5em;
            margin-bottom: 18px;
        }
    </style>
</head>
<body>
<div class="goodbye-container">
    <div class="emoji">👋</div>
    <h2>Goodbye!</h2>
    <p>
        Thank you for visiting.<br>
        We hope to see you again soon.
    </p>
    <a href="<%= request.getContextPath() %>/index.jsp">Go to Home</a>
</div>
</body>
</html>
