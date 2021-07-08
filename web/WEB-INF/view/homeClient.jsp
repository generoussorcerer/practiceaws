
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
<h1 align = "center"><fmt:message key="index.welcome"/></h1>
<p align = "center"><fmt:message key="home.happy"/></p>
<p align = "center"><fmt:message key="home.lastVisit"/> ${cookie['lastEnterTime'].getValue()}</p>
<p align = "center"><fmt:message key="home.visitCount"/> ${cookie['usageCount'].getValue()}</p>

<div class="md-padding">
    <div class="container-fluid">
        <div class="row">
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=createBooking">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="home.create"/></h5>
                        </div>
                    </div>
                </a>
            </div>
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=printRooms">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="home.allRooms"/></h5>
                        </div>
                    </div>
                </a>
            </div>
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=payReceipt">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title"><fmt:message key="home.pay"/></h5>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>


<button class="open-button" onclick="openForm()">Chat</button>

<div class="form-popup" id="myForm" name="myForm">
    <ul>
        <li>
            <h1>Chat</h1></li>
            <input type="hidden" name="username" id = "username" value="${sessionScope.clientID}"> </li>
        <li>
            <label for="msg"><b>Message</b></label></li>
        <li>
            <textarea placeholder="Enter message.." id ="msg" name="msg" required></textarea></li>
        <li>
            <textarea readonly="true" name="log" id="log"></textarea></li>
        <li>
            <button type="button" class="btn" onclick="send();" >Send</button></li>
        <li>
            <button type="button" class="btn cancel" onclick="closeForm()">Close</button></li>
    </ul>
</div>

<script src="script/websocket.js"></script>

</body>
</html>
