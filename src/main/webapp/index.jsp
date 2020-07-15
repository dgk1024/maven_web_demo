<%--
  Created by IntelliJ IDEA.
  User: 李轩
  Date: 2020/7/8
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<script src="js/jquery-3.4.1.min.js"></script>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>login_page</title>
</head>
<body>
<br><br><br><br>
<div class="col-sm-4"></div>
<div class="col-sm-4">
    <form action="${pageContext.request.contextPath}/loginServlet" method="post" id="loginForm">
        <h1 id="title">Login Page</h1><br>
        <div class="form-group" id="message">
            <input type="text" name="user_name" class="form-control" id="username" placeholder="username" required/><br/>
            <input type="password" name="user_password" class="form-control" id="password" placeholder="password" required/>
        </div>
        <div class="form-inline">
            <input type="text" name="code" class="form-control" id="code" placeholder="code"
                   style="width: 30%;"/>
            <a href="javascript:refreshCode()">
                <img src="${pageContext.request.contextPath}/getCodeServlet" title="看不清点击刷新" id="vcode"/>
            </a>
            <span id="error">
            <!-- 出错显示的信息框 -->
        <c:if test="${error!=null}">
            <span class="alert alert-danger" role="alert" style="width: 100%;">${error}</span>
        </c:if>
        </span>
        </div>
        <hr/>
        <div class="form-group" style="text-align: center;">
            <input class="btn btn btn-primary" type="button" value="login" id="login">
            <a href="${pageContext.request.contextPath}/register.jsp">注册账号</a>
        </div>
    </form>

</div>
<div class="col-sm-4"></div>


</body>
<script type="text/javascript">
    //点击图片刷新验证码
    function refreshCode() {
        //获取验证码图片对象
        var vcode = document.getElementById("vcode");
        //设置其src属性
        vcode.src = "${pageContext.request.contextPath}/getCodeServlet?time=" + new Date().getTime();
    }

    $(function () {
        var flagCode = false;
        /**
         * 给验证码输入框绑定事件
         * 当输入框内容被改变时,发送异步请求
         * 根据请求返回结果提醒用户
         */
        $("#code").change(() => {
            $.post(
                "${pageContext.request.contextPath}/checkCodeServlet",  //请求的路径
                {               //请求的参数
                    code: $("#code").val()
                },
                function (responseData) {     //回调函数
                    if (decodeURI(responseData) == "验证码错误") {
                        $("#error").html("<span class=\"alert alert-danger\" " +
                            "role=\"alert\" style=\"width: 100%;\">验证码错误</span>");
                    } else {
                        $("#error").html("<span class=\"alert alert-success\" " +
                            "role=\"alert\" style=\"width: 100%;\">验证通过</span>");
                        flagCode =true;
                    }
                },
                "text"          //响应结果的类型
            );
        });
        $("#login").click(function(){
            if(flagCode==true&$("#username").val()!=''&$("#password").val()!=''){
                $.post(
                    "${pageContext.request.contextPath}/loginServlet",  //请求的路径
                    {               //请求的参数
                        user_name: $("#username").val(),
                        user_password:$("#password").val()
                    },
                    function (res) {     //回调函数
                        if (decodeURI(res) == "验证通过") {
                            location.href="${pageContext.request.contextPath}/chatRoom.jsp";
                        } else {
                            $("#error").html("<span class=\"alert alert-danger\" " +
                                "role=\"alert\" style=\"width: 100%;\">"+decodeURI(res)+"</span>");
                        }
                    },
                    "text"          //响应结果的类型
                );
            }else{
                $("#error").html("<span class=\"alert alert-danger\" " +
                    "role=\"alert\" style=\"width: 100%;\">请完善信息</span>");
            }
        });
    });
</script>
</html>

