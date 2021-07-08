<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                <div class="card h-1000">
                    <div class="card-body">
                        <div class="page-form" >
                            <form class="form-signUp"  method="post"
                                  action="${pageContext.request.contextPath}/serv?command=signUp">
                                <h1><fmt:message key="signUp.create" /></h1>
                                <input name = "inputName" id="inputName" class="form-control" placeholder="User" required="" autofocus="">

                                <label><fmt:message key="signUp.name" /></label>

                                <input type="password" name ="inputPassword" id="inputPassword" class="form-control" placeholder="Password" required="">

                                <label><fmt:message key="signUp.password" /></label>
                                <div class ="radioRole">
                                    <div class="form-check form-check-inline ">
                                        <input class="form-check-input" type="radio" name="inputRole" id="adminInput" value="1">
                                        <label class="form-check-label" for="adminInput">Client</label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <input class="form-check-input" type="radio" name="inputRole" id="clientInput"  value="2"  checked>
                                        <label class="form-check-label" for="clientInput">Administrator</label>
                                    </div>

                                </div>

                                <button class="btn btn-lg btn-primary btn-block"
                                        type="submit">OK</button>
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
