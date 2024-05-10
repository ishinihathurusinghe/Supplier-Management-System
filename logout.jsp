<%-- 
    Document   : logout
    Created on : Apr 20, 2024, 1:37:15 PM
    Author     : Malshan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if(session.getAttribute ("username") != null){
        session.invalidate();
        response.sendRedirect("signin.jsp");
    }


%>
