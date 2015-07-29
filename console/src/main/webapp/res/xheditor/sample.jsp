<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>xheditor sample</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/js/jquery.1.6.1.min.js"></script>
</head>

<body>
<center>
<textarea name="content" id="content" style="width:800px; height:500px;" placeholder="详细说明"></textarea>
</center>
<script type="text/javascript" src="${pageContext.request.contextPath}/res/xheditor/xheditor-1.1.11-zh-cn.min.js"></script>
<script type="text/javascript">
	var editorConf = {width:800,
		height:500,
		tools : 'Cut,Copy,Paste,Pastetext,|,Blocktag,Fontface,FontSize,Bold,Italic,Underline,Strikethrough,FontColor,BackColor,SelectAll,Removeformat,|,Align,List,Outdent,Indent,|,Link,Unlink,Anchor,Img,Hr,Emot,Table,|,Source,Preview,Print,Fullscreen',
		upBtnText : '上传',
		upImgUrl : '!${pageContext.request.contextPath}/inc/_xheditor_image_upload.jsp'
	};	
	
	$(document).ready(function(){
		var editor = $('#content').xheditor(editorConf);
	});
</script>
</body>
</html>
