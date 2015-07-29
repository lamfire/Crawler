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
        <h2>增加节点</h2>
        <div id="showArea" class="view">
            <form id="submitform" method="post" action="#">
            	<br/>
                <div>
                	<div><label>Name：</label><input id="nodename" name="nodename" type="text" class="G-text" placeholder="请输入节点名称"></div>
                	<div><label>URL：</label><input id="nodeurl"  name="nodeurl" type="text" class="G-text" placeholder="请输入节点URL地址"></div>
                    <div><label>&nbsp;</label><button type="button" onclick="submitForm();">确定</button><button type="reset" >重设</button></div>
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

    function submitForm(){
        var nodename = $("#nodename").val();
        var nodeurl = $("#nodeurl").val();

        if(nodename == ""){
            alert("Please input arg 'Name'.");
            $("#nodename").focus();
            return;
        }

        if(nodeurl == ""){
            alert("Please input arg 'URL'.");
            $("#nodeurl").focus();
            return;
        }

        var form_data = $('#submitform').serialize();
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/node/add.do",
            data: form_data,
            dataType : "json",
            success: function(responseBody){
                console.log(responseBody);

                if(responseBody.result){
                    parent.$.fancybox.close();
                }else{
                    alert(responseBody.error);
                }
            }
        });
    };

</script>
</html>
