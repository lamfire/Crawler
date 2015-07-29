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
                	<div><label>用户名：</label><input name="username" type="text" class="G-text" placeholder="请输入用户名"></div>
                	<div><label>旧密码：</label><input name="oldpassword" type="password" class="G-text" placeholder="请输入旧密码"></div>
                    <div><label>新密码：</label><input id="password" name="password" type="password" class="G-text" placeholder="请输入新密码"/></div>
                    <div><label>重复新密码：</label><input id="repassword" name="repassword" type="password" class="G-text" placeholder="请再次输入新密码"/></div>
                    <div><label>&nbsp;</label><button type="submit" >确定</button><button type="reset" >重设</button>
                    </div>
                    <div>
                    	<p style="color:#ff5500;">${message}</p>
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

</script>
</html>
