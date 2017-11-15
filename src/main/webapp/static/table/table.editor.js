!function ($) {
    "use strict";

    var VanTableEditor = function (table,options) {
        return this.init(table,options);
    }
    VanTableEditor.prototype = {
        Constructor: VanTableEditor,
        options:{
            editorClass:"vanTableEditor",
            moreWidth:13,//生成编辑控件时，需要减去的宽度
            curTd:null,
            editorComplete:null//单元格完成事件
        },
        init:function(table,options){
            var me = this;
            this.table=table;
            this.options=this.getOptions(options);
            $(this.table).addClass(this.options.editorClass);
            this.addEvent();
            this.addDocumentClick();
            return me;
        },
        addDocumentClick:function(){
            var me = this;
            $(document).click(function(event) {
                var editorTd = $(event.target).closest("."+me.options.editorClass);
                //如果点击的控件不是当前编辑的表格
                if(editorTd.length==0){
                    me._setCurCellVal( $("."+me.options.editorClass+" td.curVanEditor"))
                }
            });
        },
        _setCurCellVal:function(curEditor){
            if(curEditor.length>0){
                var val =  curEditor.find(".val").val();
                curEditor.html(val);
                //单元格编辑完成回调
                if(this.options.editorComplete){
                    this.options.editorComplete(curEditor);
                }
            }
        },
        addEvent:function(){
            var me = this;
            var options = me.options;

            this.table.find("td").click(function(){
                var curTd = $(this);
                if(curTd.find(".vanEditorWidget").length>0){
                    return;
                }
                //上次点击时，正在编辑的单元格完成
                me._setCurCellVal(me.table.find("td.curVanEditor"));
                //本次点击的单元格
                me.options.curTd =curTd;
                me.table.find("td").removeClass("curVanEditor");
                curTd.addClass("curVanEditor");
                var width= curTd.width()- options.moreWidth;
                var type = curTd.attr("widgetType");
                var tdText = curTd.text();
                if(type){
                    curTd.html("");
                }
                if(type=='combobox'){
                    me.comboboxWidget(curTd,width,tdText);
                }else if(type=='proSelect'){
                    me.proSelectWidget(curTd,width,tdText);
                }else if(type=='text'){
                    me.textWidget(curTd,width,tdText)
                }
            })
        },
        textWidget:function(td,width,tdText){
            var textWidget = $("<input class='vanEditorWidget val' type='text' class='text' maxlength='32' style='width:"+width+"px;'/>");
            textWidget.val(tdText);
            td.append(textWidget);
            textWidget.focus();
        },comboboxWidget:function(td,width,tdText){
            var selectId = $(td).attr("selectId");
            var widget = $("#"+selectId).clone();
            widget.addClass("vanEditorWidget").addClass("val");
            widget.css("width",width);
            widget.css("display","block");
            widget.val(tdText);
            td.append(widget);
            widget.focus();

        },proSelectWidget:function(td,width,tdText){
            var me  = this;
            var html = "<div class='input-append vanEditorWidget'>"+
                        "   <input  type='hidden' value=''/>"+
                        "   <input  type='text'  maxlength='4000' class='text val' style='width:"+(width-32)+"px'/>"+
                        "    <a href='javascript:' class='btn'>"+
                        "    <i class='icon-search'></i>"+
                        "   </a>&nbsp;&nbsp;"+
                        "</div>"

            var textWidget = $(html);
            var text = textWidget.find(".text");
            text.val(tdText);
            td.append(textWidget);
            textWidget.find(".val").focus();
            textWidget.find(".btn").click(function(){
                me.openProWindow();
                return false;//防止IE9，IE10点击所在单位选择框触发onbeforeunload事件弹出是否要离开提示
            })
        },
        getOptions: function (options) {
            return  $.extend(this.options,options);
        },
        openProWindow:function(){
               var me =this;
                // 正常打开
                var options = {
                    title:	"选择产品",
                    content:	"a/tw/pro/subPro/proSelectList",
                    area:	["900px", "540px"],
                    btn:	["确定","清除","关闭"],
                    success: function(layero,index){
                    },
                    yes: function(index, layero){
                        var s = $(layero).find("iframe")[0].contentWindow.$("#headTable tbody :checkbox:checked");
                        if(s.length>1){
                            top.layer.msg("只能选择一行记录！");
                            return false;
                        }
                        if(s.length==0){
                            top.layer.msg("请选择记录！");
                            return false;
                        }
                        var data = $(s).data();
                        for(var d in data){
                            me.options.curTd.parent().children("[name='"+d+"']").html(data[d]);
                            me.options.editorComplete(me.options.curTd);
                        }
                        top.layer.close(index);
                    },
                    btn2: function(index, layero){
                        top.layer.close(index);
                    }
                };
                top.windowOpen(options);
        }
    }
    $.fn.VanTableEditor = function (options) {
        return new VanTableEditor(this,options);
    }
    $.fn.VanTableEditor.Constructor = VanTableEditor;
}(window.jQuery)