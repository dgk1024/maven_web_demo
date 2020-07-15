<%--
  Created by IntelliJ IDEA.
  User: 李轩
  Date: 2020/7/12
  Time: 4:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored ="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="test"></div>
<script src="js/jquery-3.4.1.min.js"></script>
<script >
    $(function(){
        $("#test").html($("#test").html()+"window.location.pathname--"+window.location.pathname+'<br>');
        $("#test").html($("#test").html()+"window.location.href--"+window.location.href+'<br>');
        $("#test").html($("#test").html()+"window.location.port--"+window.location.port+'<br>');
        $("#test").html($("#test").html()+"window.location.protocol--"+window.location.protocol+'<br>');
        $("#test").html($("#test").html()+"window.location.hash--"+window.location.hash+'<br>');
        $("#test").html($("#test").html()+"window.location.host--"+window.location.host+'<br>');
        $("#test").html($("#test").html()+"window.location.search--"+window.location.search+'<br>');
    });
</script>
</body>
</html>
