<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/res/css/style.css"
			type="text/css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/res/js/jquery.1.6.1.min.js"></script>
		<title>上传文件</title>
		<script type="text/javascript">
		var src = "";
		var unloadme = unloadme || function() {
			callback(null);//返回null，直接关闭当前窗口
		}
			
		function checkUploading() {
			var doc = $(window.frames["resultContent"].document).text();
			if(doc != ""){
				window.clearInterval(oTimer);
				var obj = eval("("+doc+")");
				src = obj.url
				$("#loading").hide();
				callback(src);
				unloadme();
			}
		}
	
		function doSubmit(){
			$("#formDiv").hide();
			$("#loading").show();
			$("#uploadForm").submit();
		}
		var oTimer = setInterval('checkUploading()',1000);
	</script>
	</head>
	<body>
		<iframe name="resultContent" id="resultContent" style="display: none;"></iframe>
		<div class="G-form">
			<br><br>
		</div>
		<div class="G-form" id="formDiv">
			<form id="uploadForm"
				action="${pageContext.request.contextPath}/xheditorImageUpload.do"
				method="post" enctype="multipart/form-data" target="resultContent">
				<center>
					<input type="file" name="file" />
					<input type="button" value="上传" onclick="doSubmit()" />
				</center>
			</form>
		</div>
		<div class="G-form">
			<center>
			<img id="loading" src="${pageContext.request.contextPath}/res/images/loading.gif"
			style="display: none;" />	
			</center>
		</div>
	</body>
</html>