(function(sid){
    if(window.MY !== undefined){
        console.log("命名空间已存在，初始化终止，请确认是否有重复加载。");
        return false
    };
	var $my = function(tag,manner){},
    RA,
    toStr = Object.prototype.toString,
	author = "EdwinChen",
	Nv = "0.1";
	$my.Browser = document.all?"IE":"FF";
	$my.isIE = document.all?true:false;
	$my.isFF = (window.XMLHttpRequest && navigator.appName === "Netscape")?true:false;
	/*当前激活模块。*/
    $my.active = {};
	/*框架加载状态*/
	$my.ready = false;
	/*模板*/
	$my.Template = {};
    /*缓存对象*/
    $my.cache = {};

	/*
	数组相关操作。作为Array类的派生类。外部也可以直接调用
	最大
	*/
	Array.prototype.Max = function() {
	    return Math.max.apply({},this);
	};
	/*最小*/
	Array.prototype.Min = function() {
	    return Math.min.apply({},this);
	};
	/*数据类型判断*/
	function chker(o,type){
		return toStr.call(o) === "[object "+type+"]";
	};

    function getEl(id, el) {
        if (!id) {
            return false;
        }

        var tag, type = typeof (id), nt = id.nodeType;

        if (id && !el) {
            /*返回指定的html对象;*/
            tag = type === "string" ? document.getElementById(id) : (nt !== undefined) ? id : false;
            return tag;

        } else if (id && el && typeof (el) === "string") {
            /*返回某个id或html标签中包含的制定类型的标签集合*/
            tag = type === "string" ? document.getElementById(id).getElementsByTagName(el) : (nt !== undefined) ? id.getElementsByTagName(el) : false;
            return tag;

        }
    };

	/*这个主要为对象操作的方法集，不在此方法集中的方法为直接引用型的。如MY.expand是直接调用的。*/
	$my.Method = $my.prototype  = {
		Instantiated: function(tags,manner){
			var tag = tags;
		}
	};
    $my.expand  = function(){

		var a = arguments,len = a.length,s = this,ss = $my.expand;
		if(len === 0){
				return false;
			}else{

				if(len === 1 &&chker(a[0],"Object")){
					for(var i in a[0]){
						/*只能扩展方法,不能覆盖已有的对象,参数类型必须为function*/
						if(chker(a[0][i],"Function") && s[i] === undefined){
							s[i] = a[0][i];
						}
					}
				}else if(len === 2 && chker(a[1],"Object")){
					for(var i in a[1]){
						if(a[0] === $my){
							$my[i] = a[1][i];
						}else if(!chker(a[0],"Function")){
							a[0][i] = a[1][i];
						}
					}
				}else{
					if(!$my[a[0]]){
						$my[a[0]] = a[1];
					}
				}
			}
	};
	/*外部可直接调用的函数集*/
	var Fn = {
		NSregister:function(n){
			/*命名空间不允许存在空格*/
			var White = n.match(trimExp)===null?0:n.match(trimExp);
			if(!n || White.length > 0){
				return false;
			}
			
			var nsList = n.split("."),i,root = $my.Method;
			if(nsList.length === 1){
				root[nsList[0]] = root[nsList[0]] || {};
				return root[nsList[0]];
			}else{
				for(i = 0;i<nsList.length;i++){
					root[nsList[i]] = root[nsList[i]] || {};
					root = root[nsList[i]];
					if(i = nsList.length-1){
						return root[nsList[i]];
					}
				}
			}
			nsList = null;
			root = null;
		},
		/*
			模板操作方法
			MY.XTpl("test","<div>{0}{1}{2}{3}{4}{5}</div>");
			MY.Template.test
		*/
		XTpl:function(Xname,tpl){
			if((Xname && tpl) && (typeof(Xname) === "string" && typeof(tpl) === "string")){
				this.Template[Xname] = tpl;
			}else{
				alert("缺少参数或传入数据类型错误，第一个参数必须是String类型，第二个参数必须是String类型。")
			}
		},
		/*获取html对象的方法外部接口*/
		getEl:function(id,el){
			return getEl(id,el);
		},
		/*
		格式化字符串
		var strr = MY.formatStr("<div>{0}{1}{2}{3}{4}</div>",["a","b","c","d","e"]);alert(strr);
		*/
		formatStr:function(strTpl,dataArr){
			/*strTpl必须是string类型，dataArr必须是Array类型*/
			if(strTpl && typeof(strTpl) === "string" && dataArr && chker(dataArr,"Array")){
				var str = strTpl,
					aNum = str.match(/\{[0-9]*\}/g);
			    for(var i = 0;i < aNum.length;i++){
			        var tStr = "",
			            _i = aNum[i].match(/\d+/);
			        _i = parseInt(_i[0]);
			        tStr = str.replace(/\{[0-9]*\}/,dataArr[_i]);
			        str = tStr;
			    }
			    try{
			        return str;
			    }finally{
			        str = null;
			        //arr = null;
			    }
			}else{
				alert("缺少参数或传入数据类型错误，第一个参数必须是String类型，第二个参数必须是Array类型。")
			}
		},
		/* var arr = []; MY.isArr(arr);//true */
		isArr:function(o){
			return chker(o,"Array");
		},
		/* function a(){};MY.isFn(a);//true */
		isFn:function(o){
			return chker(o,"Function");
		},
		/* var s = 123; MY.isNum(s);//true */
		isNum:function(o){
			return chker(o,"Number");
		},
		/* var o = {}; MY.isObj(o);//true */
		isObj:function(o){
			return chker(o,"Object");
		},
		/* MY.arrMax([1,2,3]);//3 */
		arrMax:function(arr){
			var Max = isNaN(arr.Max())?"数组内存在非数字类型的数据":arr.Max();
			return Max;
		},
		/*MY.arrMax([1,2,3]);//1*/
		arrMin:function(arr){
			var Min = isNaN(arr.Min())?"数组内存在非数字类型的数据":arr.Min();
			return Min;
		},
		/*返回文档body。不能在初始化的时候做。加载的时候document.body为nll。执行一次后document.body被缓存。下面的jq检测同理*/
		xBody:function(){
			if(xBody === undefined || xBody === null){
				xBody = document.body;
			}
			return xBody;
		},
		/*检查是否已经加载jq框架*/
		chkJQPack:function (){
			if(jQ === undefined){
				jQ = (window.$ === undefined || window.jQuery === undefined)?false:true;
				if(!jQ){
					alert("查询不到jq框架，请检查页面");
				}
				return jQ;
			}
			return jQ;
            
		},
        /*高度平衡*/
        Balance:function(tagArr,footer){
            var theMaxH,tmpArr = [],tmpArr2 = [],reT =false,foot;
            if(!!footer){
                foot = $(footer);
                foot.hide();
            }
            $.each(tagArr,function(i,n){
                tmpArr.push($(n).height());
                tmpArr2.push($(n));
            });
            theMaxH = MY.arrMax(tmpArr);
            $.each(tmpArr2,function(i,n){
                n.height(theMaxH);
            });
            foot.show();
           if (!!footer){

               $(window).bind("resize",function(){
                   if(!reT){
                       reT = true;
                       foot.hide();
                       var _h,_oh = 0;
                       var innnerTag = tmpArr2[1].first();
                       tmpArr2[0].css("height","100%");
                       tmpArr2[1].css("height","100%");
                       var _tt = setInterval(function(){
                           _h = innnerTag[0].scrollHeight;
                           if(_oh != _h){
                               _oh = _h;
                           }else{
                               clearInterval(_tt);
                               tmpArr2[1].height(_h);
                               tmpArr2[0].height(_h);
                               foot.show();
                               reT = false;
                           }
                       },20);
                   }});
                }
        },
        /*前端分页*/
        Pager:function(id,el){
            var tags = $(id+" "+el);
            $.each(tags,function(){
                $(this).bind("click",function(){
                    return false;
                });
            });
        },
        /*选项卡*/
        Tab:function(o){

            if(!o || !o.bar){return false;}

            function theTab(o){
                this.o = o;/*original*/
                this.bar = $(o.bar);
                this.tabBnts = this.bar.find((o.el?o.el:"li"));
                this.content = (o.content && o.content!==null)?$(o.content):false;
                if(this.bar.length ===0 || this.tabBnts.length ===0){
                    console.log("Elements not found.");
                    return false;
                }
                this.now = {sn:-1,tab:null,content:null};
                this.init();
            }

            theTab.prototype = {
                init:function(){
                    var that = this,barMinW =0;
                    $.each(this.tabBnts,function(i,n){
                        var _t = $(n);
                       _t.bind("click",function(){
                           if(!that.content){
                               return true;
                           }else{
                               that.content[that.now.sn].hide();
                               that.content[i].show();
                           }
                           that.now.tab.removeClass("act");
                           that.now.tab = $(this);
                           this.className = "act";
                           that.now.sn = i;
                       });
                        if(_t.hasClass("act")){
                            _t.find("a:first").bind("click",function(){
                                return false;
                            });
                            that.now.sn = i;
                            that.now.tab = _t;
                        }
                        barMinW += _t.outerWidth(true);
                    });

                    this.bar.width((barMinW+10));
                }
            }
            
            return new theTab(o);
        },
        /*用户图片列表*/
        userPhotoList:function(o){
            /*{boxId:"#theUserImgsList",selectHelper:{all:"#chkHelper",inverse:"#inverse"}}*/
            if(!o){return false;}
            
            function thePhotos(o){

                this.o = o;
                /*主容器*/
                this.box = $(o.boxId);
                /*照片数量*/
                this.photos = $(o.boxId+" img");
                /*是否有多选/反选功能*/
                this.selectHelper = o.selectHelper?{}:false;
                if(this.selectHelper){
                    this.selectHelper.All = $(o.selectHelper.all);
                    this.selectHelper.Inverse =$(o.selectHelper.inverse);
                }
                /*是否有多选*/
                this.hasChkbox = o.hasChkbox?o.hasChkbox:true;
                /*多选框*/
                this.checkboxs = $(o.boxId+" > ul input:checkbox");
                /*已选中的*/
                this.checkeds = {};
                /*未选中的*/
                this.noCheck = {};
                
                if(this.photos.length ===0){
                    console.log("no photo.");
                    return false;
                }
                
                this.innt();
            }

            thePhotos.prototype = {
                /*初始化*/
                innt:function(){
                    var that = this;

                    if(this.hasChkbox){
                        
                        /*有带多选框*/
                        $.each(this.photos,function(i,n){
                            
                            $(this).bind("click",function(){
                                that.setCheck(i);
                            });

                            that.checkboxs.eq(i).bind("change",function(){
                                that.setCheck(i);
                            });

                            that.noCheck[i] = that.checkboxs[i];
                        });

                        if(this.selectHelper){
                            /*有多选反选控件*/

                            /*多选*/
                            this.selectHelper.All.bind("click",function(){
                                MY.selectAllAndInverse.call(that,{
                                    all:true,
                                    chkedName:"checkeds",
                                    allName:"checkboxs",
                                    invName:"noCheck",
                                    el:this,
                                    fel:that.selectHelper.Inverse
                                });
                            });

                            /*反选*/
                            this.selectHelper.Inverse.bind("click",function(){
                                MY.selectAllAndInverse.call(that,{
                                    all:false,
                                    chkedName:"checkeds",
                                    allName:"checkboxs",
                                    invName:"noCheck",
                                    el:this,
                                    fel:that.selectHelper.All
                                });
                            });
                        }

                    }
                    
                },
                /*多选框相关操作*/
                setCheck:function(i){

                    var cTag = this.checkboxs.eq(i),now;
                    
                    cTag.attr("checked",function(){
                        now =  !this.checked
                        return now;
                    });
                    
                    if(now){
                        this.checkeds[i] = cTag;
                        this.noCheck[i] = null;
                        delete(this.noCheck[i]);
                    }else{
                        this.noCheck[i] = cTag;
                        this.checkeds[i] = null;
                        delete(this.checkeds[i]);
                    }
                    
                }
            }

            return new thePhotos(o);
        },
        /*全选，反选*/
        selectAllAndInverse:function(o){
            /*{
            all:true,                               是否全选
            chkedName:"checkeds",       已选中的多选框对象
            allName:"checkboxs",            全部多选框JQ对象
            invName:"noCheck",              未选中的多选框对象
            el:this,                                    事件源DOM对象
            fel:this.selectHelper.Inverse       对应的反选/多选的标签的JQ对象
            }*/
            var that = this;

            if(o.all){
                /*全选*/
                if(o.el.checked){
                    /*全选选中*/
                    MY.opCache.Save(o.chkedName,this[o.chkedName]);
                    MY.opCache.Save(o.invName,this[o.invName]);
                    $.each(this[o.allName],function(i,n){
                        this.checked = true;
                        that[o.chkedName][i] = $(n);
                        that[o.invName][i] = null;
                        delete(that[o.invName][i]);
                    });
                    
                }else{
                    /*全选取消,返回之前选中的状态，而不是全部消除选中的状态*/
                    var _chked = MY.opCache.Get(o.chkedName);
                    this[o.chkedName] = _chked;
                    this[o.invName] = MY.opCache.Get(o.invName);
                    $.each(this[o.allName],function(i){

                        if(_chked[i]){
                            this.checked = true;
                        }else{
                            this.checked = false;
                        }

                    });
                    MY.opCache.Del(o.chkedName);
                    MY.opCache.Del(o.invName);
                }

            }else{
                /*反选*/
                MY.opCache.Save(o.chkedName,this[o.invName]);
                MY.opCache.Save(o.invName,this[o.chkedName]);
                $.each(this[o.allName],function(){
                    this.checked = !this.checked;
                });
                this[o.invName] = MY.opCache.Get(o.invName);
                this[o.chkedName] = MY.opCache.Get(o.chkedName);
            }
            /*不管怎样都会取消另一个的状态*/
            o.fel.attr("checked",false);
        },
        /*数据/对象缓存*/
        opCache: (function(){
            var oldObj = {};
            return {
                /*获取指定名称的数据*/
                Get:function(n){
                    return oldObj[n];
                },
                /*保存数据*/
                Save:function(n,o){
                    oldObj[n] = {};
                    for(var m in o){
                        oldObj[n][m] = o[m];
                    }
                },
                /*删除数据*/
                Del:function(n){
                    delete(oldObj[n]);
                },
                /*销毁闭包内部缓存对象*/
                kill:function(){
                    oldObj = null;
                }
            }
        })(),
        sideNav:function(o){
            /*MY.sideNav({id:"#imNav"});*/
            var tEl = o.titleEl?o.titleEl:"strong";
            var actCls = o.cls?o.cls:"act";
            var lEl = o.listEl?o.listEl:"Ul";
            var isAct = null;
            var titles = $(o.id+" "+tEl);
            var lists = $(o.id+" "+lEl);
            $.each(titles,function(i,n){
                var actNow = $(this).parent().find(".act:first");
                if(actNow.length > 0){
                    actNow.parent().css("height","inherit");
                    isAct = $(this);
                    $(this).attr("isOpen",true);
                }else{
                    $(this).attr("isOpen",true);
                }
                $(this).bind("click",function(){//绑定点击事件
                    var notOpen = $(this).attr("isOpen") == "true"?false:true;
                    if(notOpen){
                        lists.eq(i).animate({
                            height:"90%"
                        });
                        $(this).attr("isOpen","true");
                    }else{
                        lists.eq(i).animate({
                            height:0
                        });
                        $(this).attr("isOpen","false");
                    }
                });
                
                $(this).bind("mouseout",function(){//绑定鼠标移动事件
                    //$(this).css("color","#333");
                    //$(this).css("background","#c8c8c8");
                });
                $(this).bind("mouseover",function(){//绑定鼠标移动事件
                    //$(this).css("color","#444");
                    //$(this).css("background","#ccc");
                });
            });
        },
        thePhotosList:function(o){
            function photosList(o){
                if(!o.setFn){
                    return false;
                }
                this.hasFn = o.setFn;
                this.bntDom = {};
                /*是否有多选/反选功能*/
                this.selectHelper = o.selectHelper?{}:false;
                if(this.selectHelper){
                    this.selectHelper.All = $(o.selectHelper.all);
                    this.selectHelper.Inverse =$(o.selectHelper.inverse);
                    /*多选框*/
                    this.checkboxs = $(o.id+" input:checkbox");
                    /*已选中的*/
                    this.checkeds = {};
                    /*未选中的*/
                    this.noCheck = {};
                }
                this.innt();
            }

            photosList.prototype = {
                innt:function(){
                    var that = this;
                    for(var n in this.hasFn){
                        this.bntDom[n] = n !== "custom"?$(this.hasFn[n]):$(this.hasFn[n].id);
                        if(n === "custom" && this.hasFn[n].fn){
                            this.custom = this.hasFn[n].fn;
                        }
                        (function(_n){
                            that.bntDom[_n].bind("click",function(){
                                that[_n]();
                            });
                        })(n);
                    }
                    if(this.selectHelper){
                        $.each(this.checkboxs,function(i,n){
                            that.checkboxs.eq(i).bind("change",function(){
                                that.setCheck(i,this.checked);
                            });
                            that.noCheck[i] = that.checkboxs[i];
                        });

                        /*多选*/
                        this.selectHelper.All.bind("click",function(){
                            MY.selectAllAndInverse.call(that,{
                                all:true,
                                chkedName:"checkeds",
                                allName:"checkboxs",
                                invName:"noCheck",
                                el:this,
                                fel:that.selectHelper.Inverse
                            });
                        });

                        /*反选*/
                        this.selectHelper.Inverse.bind("click",function(){
                            MY.selectAllAndInverse.call(that,{
                                all:false,
                                chkedName:"checkeds",
                                allName:"checkboxs",
                                invName:"noCheck",
                                el:this,
                                fel:that.selectHelper.All
                            });
                        });
                    }
                },
                hot:function(){
                     console.log("hot");
                },
                pass:function(){
                    console.log("pass");
                },
                noPass:function(){
                    console.log("noPass");
                },
                noHot:function(){
                    console.log("noHot");
                },
                custom:function(){

                },
                setCheck:function(i,chk){
                    console.log(i)
                    var cTag = this.checkboxs.eq(i),now = chk;

                    if(chk === undefined){
                        cTag.attr("checked",function(){
                            now =  !this.checked
                            return now;
                        });
                    }

                    if(now){
                        this.checkeds[i] = cTag;
                        this.noCheck[i] = null;
                        delete(this.noCheck[i]);
                    }else{
                        this.noCheck[i] = cTag;
                        this.checkeds[i] = null;
                        delete(this.checkeds[i]);
                    }

                }
            }

            return new photosList(o);
        }
	};
	/*追加*/
	$my.expand($my,Fn);
	$my.Method.Instantiated.prototype = $my.Method;
	$my.ready = true;
	window.MY = $my;
})();


(function(){
	MY.sideNav({id:"#imNav"});
})();