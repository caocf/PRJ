<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2017/3/29
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试</title>
    <script src="js/common/jquery-1.5.2.min.js"></script>
</head>
<body>
    <h1>错误</h1>
<script>
    var xhr = new XMLHttpRequest();
    //content = "shipname="+encodeURIComponent($("#shipid").val());
    xhr.onload = function()
    {
        //var objectURL = URL.createObjectURL(this.response);
        alert(xhr.response);
        /*        img = document.getElementById('zsylimg');
        img.src = objectURL;
        img.onload = function(e) {
            window.URL.revokeObjectURL(this.src);
        };*/
    } ;
    xhr.open("POST", "test", true);
    xhr.responseType = "blob";
    //  xhr.setRequestHeader("Content-Length",content.length);
    xhr.setRequestHeader("CONTENT-TYPE","application/x-www-form-urlencoded");
    xhr.send(null);
</script>
</body>
</html>
