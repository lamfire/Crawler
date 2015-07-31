<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!--页头-->
<div class="page-header">
	<span class="title">欢迎使用 Crawler Console v1.0.0</span>
    <div class="wellcome">
        <a id="chpassword" href="${pageContext.request.contextPath}/box_password.jsp">${ADMIN.account}</a>
        |
        <a href="${pageContext.request.contextPath}/logout.do">退出</a>
    </div>
</div>
<script type="text/javascript">
(function(){
    $("a#chpassword").fancybox({
				'padding'			: 0,
				'scrolling'			:'no',
				'autoScale'			: false,
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'width'				: '50',
				'height'			: '35',
				'type'				: 'iframe'
		});
})();
</script>
