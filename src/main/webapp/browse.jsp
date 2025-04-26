<%
    String query = request.getParameter("query");
    if (query == null) query = "";
%>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="css/styles.css"/>
        <title>IoT Bay - Browse: <%= query %></title>
    </head>
    <link rel="stylesheet" href="css/styles.css"/>
    <form action="browse.jsp" method="get" id="searchForm">
        <input type="text" name="query" value="<%= query %>" placeholder="Search Products..." id="searchInput">
        <button type="submit">Search</button>
    </form>
</html>