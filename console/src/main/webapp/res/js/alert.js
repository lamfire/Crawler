(function(){
	if(window._alert !== undefined){
		return;
	}
	$('body').append("<div id='dialog' title='提示'><p id='dialog-content'></p></div>");
	window.alert = function(text){
		$("#dialog-content").html(text);
		$('#dialog').dialog({
					modal: true,
					draggable:false,
					resizable:false,
					buttons: {
						"Ok": function() { 
							$(this).dialog("close"); 
						}
					}
				});
	};
	window._alert = function(){};
})();