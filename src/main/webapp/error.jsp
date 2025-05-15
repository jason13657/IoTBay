<%@ page contentType="text/html; charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <title>Error - Application</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f8fafc;
            color: #222;
            margin: 0;
            padding: 40px;
            text-align: center;
        }
        .error-container {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 6px 24px rgba(0,119,182,0.1);
            padding: 30px;
        }
        h1 {
            color: #e63946;
            margin-bottom: 20px;
        }
        p {
            font-size: 18px;
            margin-bottom: 10px;
        }
        .details {
            text-align: left;
            background: #f1f1f1;
            padding: 15px;
            border-radius: 8px;
            font-size: 14px;
            color: #555;
            margin-top: 20px;
            word-break: break-word;
        }
        a.home-link {
            display: inline-block;
            margin-top: 25px;
            padding: 12px 30px;
            background: #0077b6;
            color: white;
            text-decoration: none;
            border-radius: 20px;
            font-weight: bold;
            transition: background 0.3s ease;
        }
        a.home-link:hover {
            background: #005f87;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Oops! Something went wrong.</h1>
        <p>We're sorry, but an unexpected error occurred.</p>
        <p>Please try again later or contact support if the problem persists.</p>

        <c:if test="${not empty errorMessage}">
            <div class="details">
                <strong>Error Message:</strong> ${errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty exception}">
            <div class="details">
                <strong>Exception:</strong> ${exception.toString()}<br/>
                <strong>Status Code:</strong> ${pageContext.errorData.statusCode}<br/>
                <strong>Requested URI:</strong> ${pageContext.errorData.requestURI}
            </div>
        </c:if>

        <a href="<c:url value='/'/>" class="home-link">Back to Home</a>
    </div>
</body>
</html>
