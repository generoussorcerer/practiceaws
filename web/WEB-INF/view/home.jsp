
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
<h1 align = "center">Welcome to the KKW Hotel!</h1>
<p align = "center">We are happy to see you on our website! Choose the action below to perform</p>
<p align = "center">Last visit: ${cookie['lastEnterTime'].getValue()}</p>
<p align = "center">Visit count: ${cookie['usageCount'].getValue()}</p>

<div class="md-padding">
    <div class="container-fluid">
        <div class="row">
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=printRooms">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Print all available rooms of certain type at defined time</h5>
                        </div>
                    </div>
                </a>
            </div>
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=unpaidReceipts">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Print all unpaid receipts</h5>
                        </div>
                    </div>
                </a>
            </div>
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=createBooking">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Create a booking</h5>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>

<div class="md-padding">
    <div class="container-fluid">
        <div class="row">
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=confirmBooking">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Confirm a booking and send a receipt</h5>
                        </div>
                    </div>
                </a>
            </div>
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=unconfirmedBookings">
                    <div class="card h-100">
                        <div class="card-body ">
                            <h5 class="card-title">Print all unconfirmed bookings</h5>
                        </div>
                    </div>
                </a>
            </div>
            <div class ="col-md-4">
                <a href="${pageContext.request.contextPath}/serv?command=payReceipt">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title">Pay receipts</h5>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
