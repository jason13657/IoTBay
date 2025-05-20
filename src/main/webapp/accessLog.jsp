<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="model.AccessLog" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    List<AccessLog> accessLogList = (List<AccessLog>) request.getAttribute("accessLogList");
    String error = (String) request.getAttribute("error");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
    <title>My Access Logs</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f8f8f8; }
        .container { max-width: 700px; margin: 40px auto; background: #fff; padding: 32px; border-radius: 8px; box-shadow: 0 2px 8px #ddd; }
        h2 { margin-bottom: 24px; }
        form { margin-bottom: 24px; }
        label { margin-right: 8px; }
        input[type="date"] { margin-right: 16px; }
        .error { color: #c00; margin-bottom: 16px; }
        table { width: 100%; border-collapse: collapse; background: #fafafa; }
        th, td { padding: 10px 8px; border-bottom: 1px solid #e0e0e0; text-align: left; }
        th { background: #f0f0f0; }
        tr:hover { background: #f9f9f9; }
        .no-data { color: #888; text-align: center; padding: 24px 0; }
    </style>
</head>
<body>
<div class="container">
    <h2>View My Access Logs</h2>
    <% if (error != null) { %>
        <div class="error"><%= error %></div>
    <% } %>

    <% if (accessLogList != null) { %>
    <div>Number of logs: <%= accessLogList.size() %></div>
    <% } %>
    <form method="get" action="<%= request.getContextPath() %>/api/accessLog">
        <label for="startDate">Start Date:</label>
        <input type="date" id="startDate" name="startDate" value="<%= request.getParameter("startDate") != null ? request.getParameter("startDate") : "" %>"/>
        <label for="endDate">End Date:</label>
        <input type="date" id="endDate" name="endDate" value="<%= request.getParameter("endDate") != null ? request.getParameter("endDate") : "" %>"/>
        <button type="submit">Search</button>
        <a href="<%= request.getContextPath() %>/api/accessLog" style="margin-left:18px; color:#555; text-decoration:underline;">Show All</a>
    </form>
    <table>
        <tr>
            <th>No.</th>
            <th>Action</th>
            <th>Timestamp</th>
        </tr>
        <% if (accessLogList != null && !accessLogList.isEmpty()) {
            int idx = 1;
            for (AccessLog log : accessLogList) { %>
                <tr>
                    <td><%= idx++ %></td>
                    <td><%= log.getAction() %></td>
                    <td><%= log.getTimestamp().format(dtf) %></td>
                </tr>
        <%  }
           } else { %>
            <tr>
                <td colspan="3" class="no-data">No access logs found.</td>
            </tr>
        <% } %>
    </table>
</div>
</body>
</html>
