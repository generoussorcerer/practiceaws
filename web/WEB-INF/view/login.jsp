
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="bundle"/>

<html lang="${language}">
<head>
    <meta charset="UTF-8" />
    <title>KKW Hotel</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
          crossorigin="anonymous">
    <style>
        <%@include file="styles.css"%>
    </style>
</head>
<body>
<form method="get">
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>

<div class="md-padding">
    <div class="container-fluid">
        <div class="row">
            <div class ="col-md-6 offset-md-3">
                <div class="card h-100">
                    <div class="card-body">
                        <div class="page-form" >
                            <form class="form-signin" method="post"
                                  action="${pageContext.request.contextPath}/serv?command=login">
                                <h1><fmt:message key="index.login"/></h1>
                                <input id="inputName" name = "inputName" class="form-control" placeholder="User" required="" autofocus="">
                                <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="Password" required="">
                                <button class="btn btn-lg btn-primary btn-block" type="submit">OK</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
