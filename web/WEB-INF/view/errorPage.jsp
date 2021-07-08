<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="EN">
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

<a href="${pageContext.request.contextPath}/serv?command=home">
    <h3 align = "center"><fmt:message key="index.kkwhotel" /></h3></a>


<div class="sm-padding">
    <div class="container-fluid">
        <div class="row">
            <div class ="col-md-4 offset-md-4">
                <div class="card error-card">
                    <div class="card-body">
                        <h1 align="center">Error ${pageContext.errorData.statusCode}</h1>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class ="col-md-6 offset-md-3">
                <div class="card error-card">
                    <div class="card-body">
                        <p align="center">${pageContext.errorData.throwable.toString()}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>