	var countNum=0;
	$(document).ready(function() {
			
			$("body").bind('keydown', function(event) {
				keyPress(event);
			});
			$("body").bind('keyup', function(event) {
				countNum=0;
			});
			$("body").click(function(){
				if(countNum==2){
					countNum=0;
					getPath();
				}
				countNum=0;
			});
		});
	function keyPress(e)
			{
				countNum=0;
				if((e.shiftKey)&&(e.keyCode!=16)){countNum++;}
				if((e.altKey)&&(e.keyCode!=18)){countNum++;}
				if((e.ctrlKey)&&(e.keyCode!=17)){countNum++;}
			}
	function getPath(){
		var pathName = window.document.location.pathname;
		var projectName = pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		var partPath = pathName.substring(pathName.substr(1).indexOf('/')+1);
		var adminPath = partPath.substring(0,partPath.substr(1).indexOf('/')+1);
		pathName = projectName+adminPath;
		$.ajax({
			data:"url="+window.location.pathname,
			type:"GET",
			dataType:'json',
			url:pathName+"/sys/mapping",
			error:function(data){  
	        },  
	        success:function(data){
	        	if(data==null)return;
	        	var options = {
	        			title:	"Controller Path",
	        			type: 1, 
	        			content:"<p style='font-size:15px;margin-top:20px;margin-left:10px;'>&nbsp;&nbsp;"+data.path+"&nbsp;&nbsp;</p>",
	        			area:	["650px", "150px"],
	        			btn:	["关闭"],
	        			yes: function(index, layero){
	        				top.layer.close(index);
	        			}
	        		};
	        		top.windowOpen(options);
			}
		});
	}