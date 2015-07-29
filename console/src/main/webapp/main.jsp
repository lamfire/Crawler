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
<jsp:include page="/inc/_page_header.jsp"></jsp:include>

<div class="page-main">
	<div class="main">
	    <div class="wapper">
	        <div class="view">
	            <h2><span>节点列表</span> <span class="fr mr10 a-blank"><a id="addNode" href="${pageContext.request.contextPath}/box_add_node.jsp">添加</a> | <a  href="javascript:reloadNodes();">刷新</a></span></h2>
                <table class="thinLineTable" width="100%" border="1" cellpadding="1" cellspacing="1" bordercolor="#000" background="#333">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>节点名称</th>
                        <th>节点调用地址</th>
                        <th>访问速率/ms</th>
                        <th>状态</th>
                        <th>功能</th>
                    </tr>
                    </thead>
                    <tbody class="table-body">
                    </tbody>
                </table>
	        </div>
	    </div>
	</div>
</div>

<!--左导航菜单-->
<jsp:include page="/inc/_page_footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/alert.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/confirm.js"></script>
<script type="text/javascript">
    (function(){
        $("a#addNode").fancybox({
            'padding'			: 0,
            'scrolling'			:'no',
            'autoScale'			: false,
            'transitionIn'		: 'none',
            'transitionOut'		: 'none',
            'width'				: '50',
            'height'			: '35',
            'type'				: 'iframe',
            'onClosed' : function() {reloadNodes();}
        });
    })();

    function removeNode(name){
        if(name == ""){
            return;
        }

        confirm("确认删除:" + name,function(isTrue){
            if(isTrue){
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/node/remove.do?name="+name,
                    dataType : "json",
                    success: function(responseBody){
                        console.log(responseBody);
                        if(responseBody.result){
                            reloadNodes();
                        }
                    }
                });
            }
        });
    }

    function checkNode(name){
        if(name == ""){
            return;
        }
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/node/check.do?name="+name,
            dataType : "json",
            success: function(responseBody){
                console.log(responseBody);
                if(responseBody.result){
                    var id = format(name);
                    console.log(id);

                    setStatus(id,responseBody.speed,responseBody.status);
                }
            }
        });
    }

    function setStatus(id,speed,status){
        var color = "#00FF00";
        if(status != "OK"){
            color="#ff0000";
        }
        $("#speed_"+id).html('<b style="color:'+color+'">'+speed+'</b>');
        $("#status_"+id).html('<b style="color:'+color+'">'+status+'</b>');
    }

    function format(name){
        return name.replace(new RegExp("\\.", 'g'),"-");
    }

    function reloadNodes(){
        //empty
        $('.table-body').empty();

        //load
        var url = "${pageContext.request.contextPath}/node/list.do";

        $.ajax({
            type : "get",
            url : url,
            dataType: "json",
            success : function(data){
                var html='';
                var cout=0;
                console.log(data);
                $.each(data.result,function(key , value){
                    console.log(format(key));
                    console.log(value);
                    cout++;
                    html+='<tr class="trow1" id="'+format(key)+'"><td>'+cout+'</td><td>'+key+'</td><td>'+value+'</td><td id="speed_'+format(key)+'">-</td><td id="status_'+format(key)+'">-</td><td><a href="javascript:removeNode(\''+key+'\');">移除</a> | <a href="javascript:checkNode(\''+key+'\');">检测</a></td><tr>';
                });
                $('.table-body').append(html);

                $.each(data.result,function(key , value){
                    checkNode(key);
                });
            }
        });
    }

    $(document).ready(function(){
        reloadNodes();
    });
</script>
</html>