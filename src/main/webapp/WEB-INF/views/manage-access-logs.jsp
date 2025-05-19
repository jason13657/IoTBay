<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.AccessLog" %>

<!DOCTYPE html>
<html>
<head>
    <title>Access Logs</title>
</head>
<body>
    <h1>Access Logs</h1> 
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Action</th>
                <th>Timestamp</th>
            </tr>
        </thead>
        <tbody>
        <%
            List<AccessLog> logs = (List<AccessLog>) request.getAttribute("accessLogs");
                if (logs != null) {
                    for (AccessLog log : logs) {
        %>
            <tr>
                <td><%= log.getId() %></td>
                <td><%= log.getUserId() %></td>
                <td><%= log.getAction() %></td>
                <td><%= log.getTimestamp() %></td>
            </tr>
        <%
                }
            } else {
        %>
            <tr>
                <td colspan="5" style="text-align:center;">No Access Logs available.</td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
        <style>
        body {
            font-family: Arial, Helvetica, sans-serif;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            margin: 20px auto;
        }
        th, td {
            border: 1px solid #888;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #eee;
        }
        h1 {
            text-align: center;
            margin-top: 30px;
        }
        td.image-url {
        max-width: 200px;
        word-break: break-all;
        white-space: normal;
        overflow-wrap: break-word;
    }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        textarea {
            width: 100%;
            padding: 8px 12px;
            margin: 6px 0 12px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
            box-sizing: border-box;
        }

        label {
            font-weight: bold;
            margin-top: 8px;
            display: block;
        }

        /* Table Styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: white;
            border-radius: 6px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
        }

        th, td {
            padding: 12px 16px;
            text-align: left;
            border-bottom: 1px solid #ddd;
            word-wrap: break-word;
            max-width: 200px;
        }

        th {
            background-color: #f2f2f2;
            font-weight: bold;
        }
          </style>
</body>
</html>
