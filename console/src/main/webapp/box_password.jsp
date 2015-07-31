<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/inc/_html_head.jsp"></jsp:include>
</head>
<body>
<!--主显示区域-->
<div class="winbox">
    <div class="wapper">
        <h2>修改用户密码</h2>
        <div id="showArea" class="view">
            <form id="submitform" method="post" action="#">
            	<br/>
                <div>
                	<div><label>旧密码：</label><input id="password" name="password" type="password" class="G-text" placeholder="请输入旧密码"></div>
                    <div><label>新密码：</label><input id="newpassword" name="newpassword" type="password" class="G-text" placeholder="请输入新密码"/></div>
                    <div><label>重复新密码：</label><input id="repassword" name="repassword" type="password" class="G-text" placeholder="请再次输入新密码"/></div>
                    <div><label>&nbsp;</label><button type="button" onclick="change_pwd()" >确定</button><button type="reset" >重设</button>
                    </div>
                    <div>
                    	<p id="message" style="color:#ff5500;"></p>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/placeholder.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/alert.js"></script>
<script type="text/javascript">
    function change_pwd(){
        var password = $("#password").val();
        var newpassword = $("#newpassword").val();
        var repassword = $("#repassword").val();
        if(password === ""){
            alert("请输入旧密码.");
            $("#password").focus();
            return;
        }

        if(newpassword === ""){
            alert("请输入新密码.");
            $("#newpassword").val("");
            $("#repassword").val("");
            $("#newpassword").focus();
            return;
        }

        if(newpassword !== repassword){
            alert("两次输入的新密码不相同,请重新输入.");
            $("#newpassword").val("");
            $("#repassword").val("");
            $("#newpassword").focus();
            return;
        }

        $("#message").empty();

        var formData = $("#submitform").serialize();
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/pwd.do",
            data:formData,
            dataType : "json",
            success: function(result){
                console.log(result);
                if(result.error){
                    $("#message").html(result.error);
                }else{
                    $("#message").html("修改密码成功,请牢记新密码.");
                }
            }
        });
    }
</script>
</html>
