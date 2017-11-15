	
	function chooseClick(eleId,nameLevel,checked,idName,extId,nodesLevel,urlData,ctx){
		openTree(eleId,nameLevel,checked,idName,extId,nodesLevel,urlData,ctx);
	}
	
	function openTree(eleId,nameLevel,checked,idName,extId,nodesLevel,urlData,ctx,module,title){
		// 是否限制选择，如果限制，设置为disabled
		eleIdS = "#"+eleId;
		idNameS = "#"+idName;
		if ($(eleIdS).attr("disabled")){
			return true;
		}
		
		var showAllPath = $(eleIdS).attr("showAllPath");
        var nameLevel = nameLevel?nameLevel:"1";
        var url = "iframe:"+ctx+"/sys/subject/subjecttreeselect?";
        url = url + "url="+encodeURIComponent(urlData);
        url = url + "&module="+module+"&checked="+checked;
        url = url + "&extId="+extId+"&nodesLevel="+nodesLevel;
        url = url + "&selectIds="+$(eleIdS).val();
        var titlet = "选择"+$(eleIdS).attr("title");
		// 正常打开	
		top.$.jBox.open(url,titlet, 300, 420, {
			buttons:{"确定":"ok", "清除":"clear","关闭":true}, submit:function(v, h, f){
				if (v=="ok"){
					var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
					var ids = [], names = [], nodes = [];
					if (checked == "true"){
						nodes = tree.getCheckedNodes(true);
					}else{
						nodes = tree.getSelectedNodes();
					}
					for(var i=0; i<nodes.length; i++) {//<c:if test="${checked}">
						debugger;
						if (nodes[i].level == 0){
							top.$.jBox.tip("不能选择父节点（"+nodes[i].name+"）请重新选择。");
							return false;
						}
						
						
                        var t_node = nodes[i];
                        var t_name = "";
                        var name_l = 0;
                        
                        if(showAllPath){//显示全部节点
                        	var t_node = nodes[i];
                            var t_name = "";
                            var name_l = 0;
                            do{
                            	ids.push(t_node.id);
                                name_l++;
                                t_name = t_node.name + "," + t_name;
                                t_node = t_node.getParentNode();
                            }while(t_node);
                            ids = ids.reverse();
                        }else{//显示部份节点
                        	ids.push(nodes[i].id);
                        	do{
                                name_l++;
                                t_name = t_node.name + "," + t_name;
                                t_node = t_node.getParentNode();
                            }while(name_l < nameLevel);
                        }
                        if(t_name != ''){
                        	t_name = t_name.substring(0,t_name.length-1);
                        }
						names.push(t_name);//<c:if test="${!checked}">
						break; // 如果为非复选框选择，则返回第一个选择  </c:if>
					}
					$(eleIdS).val(ids);
					$(idNameS).val(names);
					ItemShow(ids);
				}//<c:if test="${allowClear}">
				else if (v=="clear"){
					$(eleIdS).val("");
					$(idNameS).val("");
                }//</c:if>
			}, loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			}
		});
	}
	
	function ItemShow(ids){
		
	}