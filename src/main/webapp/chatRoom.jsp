<%--
  Created by IntelliJ IDEA.
  User: 李轩
  Date: 2020/7/7
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored ="false" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<html>
<meta name="viewport" content="width=device-width, initial-scale=1">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" rel="stylesheet">
    <title>ChatRoom</title>
</head>
<body>
<br><br>
<!--页眉-->
<header class="container-fluid">
    <div class="row">
        <div class="col-sm-1">
        </div>
        <div class="col-sm-3">
            <div class="row">
                <h1 style="text-indent:-1em;font-size:70px">Hello, world!</h1>
                <h4>User: dgk1024</h4>
                <h4>Date: 2020/7/11</h4>
                <h4>Time: 01:24</h4>
                <h4>To change this template use File</h4>
<%--                <div class="jumbotron">--%>
<%--                    <h1>Hello, world!</h1>--%>
<%--                    <p>User: dgk1024</p>--%>
<%--                    <p>Date: 2020/7/11</p>--%>
<%--                    <p>Time: 01:24</p>--%>
<%--                    <p>To change this template use File</p>--%>
<%--                    <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>--%>
<%--                </div>--%>
            </div>
            <div class="row">
                <div class="row"><h3 style="text-indent:-2em;"><b>online_user:</b></h3></div>
                <div class="row">
                    <marquee scrollamount="1" direction="up" id="onlineUsers">
                        <!--用于展示在线人员-->
                        <!--用于展示在线人员-->
                        <!--用于展示在线人员-->
                        <!--用于展示在线人员-->
                        <p class="glyphicon glyphicon-user" aria-hidden="true" style="text-indent:7em;">userName</p><br>
                    </marquee>
                </div>
            </div>
        </div>
        <div class="col-sm-6" style="overflow-y: auto;height:50%;" id="content">
            <ul class="list-group" id="msgArea">
                <!--聊天记录-->
                <!--聊天记录-->
                <!--聊天记录-->
                <!--聊天记录-->
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6"></div>
        <!--
            onsubmit="return false;"阻止表单的默认提交行为
            若未阻止此行为,使用输入框点击回车时表单会被提交
            页面被刷新,聊天记录清空,达不到需要的效果
        -->
        <form class="navbar-form navbar-left" role="search" onsubmit="return false;">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Message" id="msg">
            </div>
            <button type="button" class="btn btn-default" id="send">send</button>
        </form>
    </div>
    <hr>
</header>
</body>
<script src="js/jquery-3.4.1.min.js"></script>
<script type="text/javascript">
    //全局变量
    var toName;
    var username = "${user.user_name}";
    var webSocket;
    //var HOST;
    $(function () {
        // $.ajax({
        //     url:'getHostServlet',
        //     type:'get',
        //     async:false,
        //     dataType: 'text',
        //     success:function(res){HOST=res;},
        //     error:function*(){alert('连接错误');}
        // });
        //创建webSocket
        /**
         * 以下websocket的地址需要根据用户访问的host建立相应的连接
         * testGetUrl中展示了获取各类参数的一些方法
         */
        webSocket =
            new WebSocket('ws://' + window.location.host + '/chatWebsocket');
        /** 绑定事件
         * onopen:    连接建立时调用
         * onmessage: 接收服务端消息时调用
         * onclose:   连接断开时调用
         * onerror:   连接错误时调用
         * @param event
         */
        webSocket.onopen = function (event) {onOpen(event)};
        webSocket.onmessage = function (event) {onMessage(event)};
        webSocket.onclose = function (event) {onClose(event)};
        webSocket.onerror = function (event) {onError(event)};
        function onOpen(event) {};
        function onClose(event) {};
        function onError(event) {};
        function onMessage(event) {
            var data = event.data;
            var res = JSON.parse(data);
            //判断消息类型,在指定位置打印
            if (res.isSystem) {
                //更新在线人员
                $("#onlineUsers").html('');
                for (let i = 0; i < res.message.length; i++) {
                    $("#onlineUsers").html($("#onlineUsers").html() +
                        '<p class="glyphicon glyphicon-user" aria-hidden="true" style="text-indent:7em;">：' +
                        res.message[i] + '</p><br>'
                    );
                }
            } else {
                $("#msgArea").html($("#msgArea").html() +
                    '<li class="list-group-item glyphicon glyphicon-envelope">' + res.message + '</li>');
                <!--让-->
                $('#content').scrollTop($('#msgArea').height());
            }
        };
        function sendMsg(){
            if($("#msg").val()!=''){
                var message = {
                    toName: "multiChat",
                    message: $("#msg").val()
                };
                //发送msg
                webSocket.send(JSON.stringify(message));
                $("#msg").val("");
            }else{
                alert("不能发送空信息")
            }
        }
        /**
         * 此处还未完善,仅实现了多人聊天功能
         */
        $("#send").click(()=>{
            sendMsg();
        });
        $("#msg").keypress((event)=>{
            if(event.keyCode==13){
                sendMsg();
            }
            $("#msg").focus();
        })
    });
</script>
</html>
