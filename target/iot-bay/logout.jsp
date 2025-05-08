<%@ page import="javax.servlet.http.*" %>
<%
  session.invalidate();
  response.sendRedirect("index.jsp");
%>