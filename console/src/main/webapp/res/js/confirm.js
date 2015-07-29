(function(){
	if(window._confirm !== undefined){
		return;
	}

	$('body').append("<div id='dialog' title='提示'><p id='dialog-content'></p></div>");
	window.confirm = function(text,callback){
		$("#dialog-content").html(text);
		$('#dialog').dialog({
					modal: true,
					draggable:false,
					resizable:false,
					buttons: {
						"Ok": function() { 
							$(this).dialog("close");
                            if(callback){
                                callback(true);
                            }
						},
                        "Cancel": function() {
                            $(this).dialog('close');
                            if(callback){
                                callback(false);
                            }
                        }
					}
				});
        return false;
	};
	window._confirm = function(){};
})();

/*
 confirm("Confirm remove this item ?",function(isOK){
     if(isOK){
        //----OK
     }else{
        //---Cancel
     }
 });
*/