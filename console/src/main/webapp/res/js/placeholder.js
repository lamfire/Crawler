(function(){
	var forms = $("body").find("form");
	var inputs = $("body").find("input:not(:button,:submit,:reset,:radio,:checkbox,:file,:hidden)");
	
	var warnBgColor = "#fff0f0";//警告背景颜色
	var warnFtColor = "#666"; //警告文字颜色
	
	var focusBgColor = "#fff9dd";//焦点背景颜色
	
	var placeFtColor = "#bbb"; //替换文字颜色
	
	var normalBgColor = "#fff"; //正常输入框背景颜色
	var normalFtColor = "#333"; //正常输入框文字颜色
	
	//警告
	function warnMessage(input){
		if($.browser.msie){//IE
			//内容变更事件
			input.bind("blur",function(){
				blurInput($(this));
			});
			
			//获得焦点事件
			input.bind("focus",function(){
				focusInput($(this));
			});
			var placeText = $(input).attr("placeholder");
			$(input).val(placeText);
			$(input).animate({opacity: 0.2}, { duration: 50 });
			$(input).css("background",warnBgColor);
			$(input).css("color",warnFtColor);
			$(input).animate({opacity: 1.0}, { duration: 500 });
			return;
		}
		//
		input.addClass("Anim-leftRight");
		input.focus();
		input.bind("blur",function(){
            $(this).removeClass("Anim-leftRight");
        });
	}
	
	//获得焦点
	function focusInput(input){
		$(input).css("background",focusBgColor);
		$(input).css("color",normalFtColor);
		var placeText = $(input).attr("placeholder");
		var value = $(input).val();
		if($.trim(placeText)===$.trim(value)){
			$(input).val("");
		}
	}
	
	//内容更改
	function blurInput(input){
		$(input).css("background",normalBgColor);
		var placeText = $(input).attr("placeholder");
		var value = $(input).val();
		if($.trim(value)===""){
			$(input).val(placeText);
			$(input).css("color",placeFtColor);
		}
	}

	//遍历表单并设置提交验证事件
	function bindFormSubmitEvent(){
		$.each(forms,function(index,form){
			$(form).bind("submit",function(){
				var result = true;
				var inputs = $(this).find('input:not(:button,:submit,:reset,:hidden)');
	
				//遍历检查输入项是否为空
				$.each(inputs,function(index,input){
					var placeText = $(this).attr("placeholder");
					var check = $(this).attr("check");
					var value = $(this).val();

					if(check == undefined || check!=='false'){
						if($.trim(value) === ""){
							result = false;
							warnMessage($(this));
							return result;
						}
						
						if($.trim(placeText)===$.trim(value)){
							result = false;
							warnMessage($(this));
							return result;
						}
					}
					//修正输入文字
					if(value === placeText){
						$(this).val("");
					}
				});
				return result;
			});
		});
	}
	
	if($.browser.msie){//IE
		//遍历出需要替换的输出框
		$.each(inputs,function(index,node){
			var input = $(node);
			var type = input.attr("type");
			
			//替换"text"和"password"输入框
			if(type ==="text" || type==="password"){
				//内容变更事件
				input.bind("blur",function(){
					blurInput($(this));
				});
				
				//获得焦点事件
				input.bind("focus",function(){
					focusInput($(this));
				});
				
				//初始文字
				input.blur();
			}
		});
	}
	
	//遍历表单并设置提交验证事件
	bindFormSubmitEvent();
})();