//头部下拉
$(document).ready(function () {

//菜单
    $(".Header .menu .addMenu").click(function () {
        var href = $(this).attr("href");
        windowOpenRb({title: "新建菜单", content: href,area:[800,500]});
    });




    $(".Header .xuxian a").click(function () {
        if ($(".RightSidebar02").css("display") == "block") {
            $(".RightSidebar02").fadeOut();
        }
        else {
            $(".RightSidebar02").fadeIn();
        }
        $(".RightSidebar02").find("iframe").attr("src", "include/设置顶部导航.html");
    });

    $(".Header .newPage a").click(function () {
        if ($(".RightSidebar02").css("display") == "block") {
            $(".RightSidebar02").fadeOut();
        }
        else {
            $(".RightSidebar02").fadeIn();
        }
        $(".RightSidebar02").find("iframe").attr("src", "include/新建页面.html");
    });


    $(".Header").mouseleave(function () {
        $(".Header .xuxian").fadeOut();
    });

    $(".Sidebar .xuxian a").click(function () {
        if ($(".RightSidebar02").css("display") == "block") {
            $(".RightSidebar02").fadeOut();
        }
        else {
            $(".RightSidebar02").fadeIn();
        }
        $(".RightSidebar02").find("iframe").attr("src", "include/设置左边导航.html");
    });
    $(".Sidebar").bind("mouseover", function () {
        $(this).find(".xuxian").fadeIn();
    });
    $(".Sidebar").bind("mouseleave", function () {
        $(this).find(".xuxian").fadeOut();
    });


//头部
    $(".Header .personal .photo").click(function () {
        if ($(this).find(".rightDown").css("display") == "block") {
            $(this).find(".rightDown").fadeOut();
        }
        else {
            $(this).find(".rightDown").fadeIn();
        }
    });
    $(".Header .personal .btnDiv .a1").click(function () {
        if ($(this).parent().siblings(".leftDown").css("display") == "block") {
            $(this).parent().siblings(".leftDown").fadeOut();
        }
        else {
            $(this).parent().siblings(".leftDown").fadeIn();
        }
    });
    $(".Header .personal .btnDiv .a2").click(function () {
        if ($(this).parent().siblings(".notice").css("display") == "block") {
            $(this).parent().siblings(".notice").fadeOut();
        }
        else {
            $(this).parent().siblings(".notice").fadeIn();
        }
    });
    $(".Header .personal .btnDiv .a3").click(function () {
        if ($(this).parent().siblings(".daiban").css("display") == "block") {
            $(this).parent().siblings(".daiban").fadeOut();
        }
        else {
            $(this).parent().siblings(".daiban").fadeIn();
        }
    });
    $(".Header .personal .btnDiv .a4").click(function () {
        if ($(this).parent().siblings(".help").css("display") == "block") {
            $(this).parent().siblings(".help").fadeOut();
        }
        else {
            $(this).parent().siblings(".help").fadeIn();
        }
    });
    $(".Header .menu .moreBtn, .H_menuBox .moreBtn").click(function () {
        if ($(this).find(".menuMore").css("display") == "block") {
            $(this).find(".menuMore").fadeOut();
            $(".Cover_layer").hide();
        }
        else {
            $(this).find(".menuMore").fadeIn();
            $(".Cover_layer").show();
        }
    });

    $(".leftDown").mouseleave(function () {
        $(".leftDown").fadeOut();
    });
    $(".rightDown").mouseleave(function () {
        $(".rightDown").fadeOut();
    });
    $(".notice").mouseleave(function () {
        $(".notice").fadeOut();
    });
    $(".daiban").mouseleave(function () {
        $(".daiban").fadeOut();
    });
    $(".help").mouseleave(function () {
        $(".help").fadeOut();
    });
    $(".Cover_layer").click(function () {
    	$(this).hide();
        $(".Header .menuMore").fadeOut();
    });
    //左侧菜单收缩
    $(".Submenu2 li h3").click(function () {
        $(this).find(".arrow").fadeIn();
        if ($(this).siblings(".sub").css("display") == "block") {
            $(this).siblings(".sub").fadeOut();
            $(this).find(".arrow").fadeOut();
        }
        else {
            $(this).siblings(".sub").fadeIn();
            $(this).find(".arrow").fadeIn();
        }
    });
    $(".Submenu2 li .sub").mouseleave(function () {
        $(".Submenu2 li .sub").fadeOut();
        $(".Submenu2 li h3 .arrow").fadeOut();
    });

    //左边菜单箭头改变宽度，改变中间内容的左间距；
    $(".Shrink .left").click(function () {
        $(".Sidebar02").css("width", "70px");
        $(".Shrink").css("width", "50px");
        $(".Sidebar02 li h3 em").hide();
        $(".Submenu2 li .sub").css("left", "70px");
        $(".Contain").css("left", "70px");
        $(".Contain_other").css("left", "70px");
        $(this).siblings(".right").show();
        $(this).hide();
    });
    $(".Shrink .right").click(function () {
        $(".Sidebar02").css("width", "160px");
        $(".Shrink").css("width", "100px");
        $(".Sidebar02 li h3 em").show();
        $(".Submenu2 li .sub").css("left", "160px");
        $(".Contain").css("left", "160px");
        $(".Contain_other").css("left", "160px");
        $(this).siblings(".left").show();
        $(this).hide();
    });

//右边设置区关闭，改变中间内容区的宽度；
    $("#frClose").click(function () {
        $(".RightSidebar02").fadeOut();
        $(".Contain").css("right", "0");
        $(".Contain02").css("right", "0");
    });
    $("#frClose").click(function () {
        $(".RightSidebar_pageDesign").fadeOut();
        $(".Contain_pageDesign").css("right", "0");
    });
    $("#frClose").click(function () {
        $(".RightSidebar_other").fadeOut();
    });

    //控件的第四个li去掉右间距
    $(".controls li").each(function (i, item) {
        if (Number(i + 1) % 4 == 0) {
            $(this).attr("class", "End");
        }
    });

    //设置左边导航，选颜色值
    $("#colorBtn01").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "0px");
            $(".Color_layer").css("top", "120px");
            $(".Color_layer").fadeOut();
        }
        else {
            $(".Color_layer").css("left", "0px");
            $(".Color_layer").css("top", "120px");
            $(".Color_layer").fadeIn();
        }
    });

    $("#colorBtn02").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "0px");
            $(".Color_layer").css("top", "197px");
            $(".Color_layer").fadeOut();
        }
        else {
            $(".Color_layer").css("left", "0px");
            $(".Color_layer").css("top", "197px");
            $(".Color_layer").fadeIn();
        }
    });

    $("#colorBtn03").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "40px");
            $(".Color_layer").css("top", "197px");
            $(".Color_layer").fadeOut();
        }
        else {
            $(".Color_layer").css("left", "40px");
            $(".Color_layer").css("top", "197px");
            $(".Color_layer").fadeIn();
        }
    });

//设置顶部菜单颜色弹出层
    $("#Dh_colorBtn01").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "98px");
            $(".Color_layer").css("top", "55px");
            $(".Color_layer").fadeOut();
            setColorLayerTarget("");
            $("#defineColor").css("background-color","#ffffff");
            $("#colorInput").val("#ffffff");
        }
        else {
            $(".Color_layer").css("left", "98px");
            $(".Color_layer").css("top", "55px");
            $(".Color_layer").fadeIn();
            setColorLayerTarget("Dh_colorBtn01");
        }
    });

    $("#Dh_colorBtn02").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "498px");
            $(".Color_layer").css("top", "55px");
            $(".Color_layer").fadeOut();
            setColorLayerTarget("");
            $("#defineColor").css("background-color","#ffffff");
            $("#colorInput").val("#ffffff");
        }
        else {
            $(".Color_layer").css("left", "498px");
            $(".Color_layer").css("top", "55px");
            $(".Color_layer").fadeIn();
            setColorLayerTarget("Dh_colorBtn02");
        }
    });
    $("#Dh_colorBtn04").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "98px");
            $(".Color_layer").css("top", "100px");
            $(".Color_layer").fadeOut();
            setColorLayerTarget("");
            $("#defineColor").css("background-color","#ffffff");
            $("#colorInput").val("#ffffff");
        }
        else {
            $(".Color_layer").css("left", "98px");
            $(".Color_layer").css("top", "100px");
            $(".Color_layer").fadeIn();
            setColorLayerTarget("Dh_colorBtn04");
        }
    });


    $("#Dh_colorBtn05").click(function () {
        if ($(".Color_layer").css("display") == "block") {
            $(".Color_layer").css("left", "0px");
            $(".Color_layer").css("top", "367px");
            $(".Color_layer").fadeOut();
            setColorLayerTarget("");
            $("#defineColor").css("background-color","#ffffff");
            $("#colorInput").val("#ffffff");
        }
        else {
            $(".Color_layer").css("left", "0px");
            $(".Color_layer").css("top", "367px");
            $(".Color_layer").fadeIn();
            setColorLayerTarget("Dh_colorBtn05");
        }
    });
    //隐藏颜色版
    $(".Color_layer").mouseleave(function () {
        $(this).fadeOut();
        setColorLayerTarget("");
        $("#defineColor").css("background-color","#ffffff");
        $("#colorInput").val("#ffffff");
    });


    //点击全屏、退出全屏
    $("#exit_fullScreen").hide();
    $("#fullScreen").click(function () {
        $("#exit_fullScreen").show();
        $(this).hide();
        $(parent.document).find(".Header").fadeOut();
        $(parent.document).find(".Sidebar").fadeOut();
        $(parent.document).find(".RightSidebar").css({"left": "0px", "right": "0px", "top": "0px"});
        $(parent.document).find(".RightSidebar02").fadeOut();
        $(parent.document).find(".Contain02").css({"left": "0px", "right": "0px", "top": "0px"});
    });

    $("#exit_fullScreen").click(function () {
        $("#fullScreen").show();
        $(this).hide();
        $(".RightSidebar_pageDesign").fadeIn();
        $(parent.document).find(".Header").fadeIn();
        $(parent.document).find(".Sidebar").fadeIn();
        $(parent.document).find(".RightSidebar").css({"left": "201px", "right": "10px", "top": "100px"});
        $(parent.document).find(".RightSidebar02").fadeIn();
        $(parent.document).find(".Contain02").css({"left": "201px", "right": "0px", "top": "100px"});
    });

    //左侧菜单点击展开下级菜单
    $(".Submenu li h3").bind("click", function () {
        if ($(this).siblings(".sub").css("display") == "none") {
            jQuery(this).find("a").addClass("acur");
            jQuery(this).siblings(".sub").show();
        }
        else {
            jQuery(this).find("a").removeClass("acur");
            jQuery(this).siblings(".sub").hide();
        }


    });
    $(".Submenu li .sub span b").bind("click", function () {
        if ($(this).parent().find("em").css("display") == "none") {
            jQuery(this).find("a").addClass("acur");
            jQuery(this).parent().find("em").css("display", "block");
        }
        else {
            jQuery(this).find("a").removeClass("acur");
            jQuery(this).parent().find("em").css("display", "none");
        }


    });

    $(".Submenu2").find("a").each(function () {
        $(this).bind("mouseover", function () {
            layer.tips("<a clas='menuEdit' href='javascript:void(0);'><i class='iconfont icon mr10 f16'>&#xe619;</i></a><a class='menuAdd' href='javascript:void(0);'><i class='iconfont icon f16'>&#xe602;</i></a>", this, {
                tips: [3, '#d8d8d8']
            });
        });

    })


//新建次级菜单--更换图标弹出页面
    $("#replaceIcon3").on("click", function () {
        parent.layer.open({
            type: 2,
            area: ['700px', '530px'],
            fix: false, //不固定
            maxmin: true,
            content: 'include/icon.html'
        });
    });


//逻辑模型管理
    $(".Module_menu li").click(function () {
        if ($(this).find(".sub").css("display") == "block") {
            $(this).find(".sub").fadeOut();
        }
        else {
            $(this).find(".sub").fadeIn();
        }
    });

    $(".Module_menu li").find("a").each(function () {
        $(this).bind("mouseover", function () {
            layer.tips("<a href='#'><i class='iconfont icon mr5 f16'>&#xe619;</i></a><a href='#'><i class='iconfont icon mr10 f16'>&#xe602;</i></a><a href='#'><i class='iconfont icon mr10 f16'>&#xe600;</i></a>", this, {
                tips: [3, '#d8d8d8']
            });
        });

    })

    $(".Module_btnDiv dl .add").on("click", function () {
        $(parent.document).find(".RightSidebar_other").fadeIn();
    });


//系统设置
    $(".System_btnDiv dl .add").on("click", function () {
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("src", "systemSetup/弹窗_添加菜单.html");
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("width", "555px");
        $(parent.document).find(".RightSidebar_other").fadeIn();
    });

    $(".System_btnDiv dl .addRole").on("click", function () {
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("src", "systemSetup/弹窗_添加角色.html");
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("width", "570px");
        $(parent.document).find(".RightSidebar_other").fadeIn();

    });

    $(".System_btnDiv dl .addKey").on("click", function () {
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("src", "systemSetup/弹窗_添加键值.html");
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("width", "570px");
        $(parent.document).find(".RightSidebar_other").fadeIn();

    });

//分配角色--弹出页面
    $(".Fenpei").on("click", function () {
        parent.layer.open({
            type: 2,
            area: ['800px', '600px'],
            fix: false, //不固定
            maxmin: true,
            title: '分配角色',
            content: 'systemSetup/分配角色.html'
        });
    });

//审核是否同意弹出层
    $(".btnCheck").click(function () {
        if ($(this).parent().siblings(".Workflow_popUp").css("display") == "block") {
            $(".Workflow_popUp").fadeOut();
        }
        else {
            $(".Workflow_popUp").fadeIn();
        }
    });

//菜单预览
    $(".Preview_lan .a1").click(function () {
        if ($(".RightSidebar_other").css("display") == "block") {
            $(".RightSidebar_other").fadeOut();
        }
        else {
            $(".RightSidebar_other").fadeIn();
        }
    });


//工作流
    $(".Workflow_box").on("click", function () {
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("src", "workflow/弹窗_属性.html");
        $(parent.document).find(".RightSidebar_other").find("iframe").attr("width", "300px");
        $(parent.document).find(".RightSidebar_other").fadeIn();

    });

//代办事项
    $(".btnSp").on("click", function () {
        parent.layer.open({
            type: 2,
            area: ['800px', '450px'],
            fix: false, //不固定
            maxmin: true,
            title: '审批记录',
            content: 'daiban/审批记录.html'
        });
    });
    $(".btnLc").on("click", function () {
        parent.layer.open({
            type: 2,
            area: ['800px', '450px'],
            fix: false, //不固定
            maxmin: true,
            title: '流程信息',
            content: 'daiban/流程信息.html'
        });
    });
    //$(".jericho_tab .tab_pages .tabs ul li:first").css({"width":"80px!important"});

  //表格tr选中换色
    $(".Mytable_box table tbody tr td").click(function(){
    	$(".Mytable_box table tbody tr").removeClass("trBg");
    	$(this).parent().addClass("trBg");
    	//$(this).parent().parent().parent().parent().siblings(".tipsDiv").fadeIn();
    });
    
    //表头浮动
    $(window).scroll(function () {
        var sTop = $(document).scrollTop();
        /////gettop
		 if($(this).scrollTop()>140){//当window的scrolltop距离大于1时，go to top按钮淡出，反之淡入
    $(".listHead").addClass("listHead_locate");
} else {
    $(".listHead").removeClass("listHead_locate");
}
    })
    
    //表头浮动
    $(window).scroll(function () {
        var sTop = $(document).scrollTop();
        /////gettop
		 if($(this).scrollTop()>140){//当window的scrolltop距离大于1时，go to top按钮淡出，反之淡入
    $(".listHead02").addClass("listHead_locate02");
} else {
    $(".listHead02").removeClass("listHead_locate02");
}
    }) 
});

/**
 *  设置选中colorLayer的目标
 *  @param colorBtnId
 */
function setColorLayerTarget(colorBtnId) {
    $(".Color_layer").attr("target", colorBtnId);
}



