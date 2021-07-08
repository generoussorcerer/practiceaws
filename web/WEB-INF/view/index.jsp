<html lang="en">
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

<h1 align = "center">Welcome</h1>
<p align = "center">Choose a file to upload</p>

<div class="md-padding">
  <div class="container-fluid">
    <div class="row">
      <div class ="col-md-4 offset-md-4">
        <form action = "${pageContext.request.contextPath}/serv?command=index" method = "post" enctype = "multipart/form-data">
          <input type = "file" name = "file" webkitdirectory multiple/> <br />
          <input type = "submit" value = "Upload File" />
        </form>
      </div>
    </div>
  </div>
</div>

</body>
</html>
