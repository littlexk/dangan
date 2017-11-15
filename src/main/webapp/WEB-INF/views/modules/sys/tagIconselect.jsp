<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
    <title>图标选择</title>
    <%@include file="/WEB-INF/views/include/head.jsp" %>
    <%--    <%@include file="/WEB-INF/views/include/bnsNewCss.jsp" %>--%>

    <meta charset="utf-8"/>
    <link rel="stylesheet" href="${ctxStatic}/bns/css/user_icon/demo.css">
    <link rel="stylesheet" href="${ctxStatic}/bns/css/user_icon/iconfont.css">

    <script type="text/javascript">
        $(document).ready(function () {

            $("body").css({"background-color": "#fff", "min-width": "inherit"});

            $("#icons li").click(function () {
                $("#icons li i").css({"color": ""});
                $(this).children("i").css({"color": "#00a0e9"});
                var icon = $(this).children("i").attr("name");
                $("#icon").val(icon);
            });
            $("#icons li").each(function () {
                if ($(this).text() == "${value}") {
                    $(this).click();
                }
            });
           /* $("#icons li").dblclick(function () {
                //top.$.jBox.getBox().find("button[value='ok']").trigger("click");
                var icon = $(this).find("i").attr("name");
                window.parent.createIcon(icon, "${id}");
                window.parent.closeLayer();
            });*/
        });
    </script>
</head>
<body>
<input type="hidden" id="icon" value="${value}"/>
<%--<ul class="the-icons clearfix" id="icons">--%>
<%--<li><i class="icon-glass"></i>glass</li>--%>
<%--<li><i class="icon-music"></i>music</li>--%>
<%--<li><i class="icon-search"></i>search</li>--%>
<%--<li><i class="icon-envelope"></i>envelope</li>--%>
<%--<li><i class="icon-heart"></i>heart</li>--%>
<%--<li><i class="icon-star"></i>star</li>--%>
<%--<li><i class="icon-star-empty"></i>star-empty</li>--%>
<%--<li><i class="icon-user"></i>user</li>--%>
<%--<li><i class="icon-film"></i>film</li>--%>
<%--<li><i class="icon-th-large"></i>th-large</li>--%>
<%--<li><i class="icon-th"></i>th</li>--%>
<%--<li><i class="icon-th-list"></i>th-list</li>--%>
<%--<li><i class="icon-ok"></i>ok</li>--%>
<%--<li><i class="icon-remove"></i>remove</li>--%>
<%--<li><i class="icon-zoom-in"></i>zoom-in</li>--%>
<%--<li><i class="icon-zoom-out"></i>zoom-out</li>--%>
<%--<li><i class="icon-off"></i>off</li>--%>
<%--<li><i class="icon-signal"></i>signal</li>--%>
<%--<li><i class="icon-cog"></i>cog</li>--%>
<%--<li><i class="icon-trash"></i>trash</li>--%>
<%--<li><i class="icon-home"></i>home</li>--%>
<%--<li><i class="icon-file"></i>file</li>--%>
<%--<li><i class="icon-time"></i>time</li>--%>
<%--<li><i class="icon-road"></i>road</li>--%>
<%--<li><i class="icon-download-alt"></i>download-alt</li>--%>
<%--<li><i class="icon-download"></i>download</li>--%>
<%--<li><i class="icon-upload"></i>upload</li>--%>
<%--<li><i class="icon-inbox"></i>inbox</li>--%>

<%--<li><i class="icon-play-circle"></i>play-circle</li>--%>
<%--<li><i class="icon-repeat"></i>repeat</li>--%>
<%--<li><i class="icon-refresh"></i>refresh</li>--%>
<%--<li><i class="icon-list-alt"></i>list-alt</li>--%>
<%--<li><i class="icon-lock"></i>lock</li>--%>
<%--<li><i class="icon-flag"></i>flag</li>--%>
<%--<li><i class="icon-headphones"></i>headphones</li>--%>
<%--<li><i class="icon-volume-off"></i>volume-off</li>--%>
<%--<li><i class="icon-volume-down"></i>volume-down</li>--%>
<%--<li><i class="icon-volume-up"></i>volume-up</li>--%>
<%--<li><i class="icon-qrcode"></i>qrcode</li>--%>
<%--<li><i class="icon-barcode"></i>barcode</li>--%>
<%--<li><i class="icon-tag"></i>tag</li>--%>
<%--<li><i class="icon-tags"></i>tags</li>--%>
<%--<li><i class="icon-book"></i>book</li>--%>
<%--<li><i class="icon-bookmark"></i>bookmark</li>--%>
<%--<li><i class="icon-print"></i>print</li>--%>
<%--<li><i class="icon-camera"></i>camera</li>--%>
<%--<li><i class="icon-font"></i>font</li>--%>
<%--<li><i class="icon-bold"></i>bold</li>--%>
<%--<li><i class="icon-italic"></i>italic</li>--%>
<%--<li><i class="icon-text-height"></i>text-height</li>--%>
<%--<li><i class="icon-text-width"></i>text-width</li>--%>
<%--<li><i class="icon-align-left"></i>align-left</li>--%>
<%--<li><i class="icon-align-center"></i>align-center</li>--%>
<%--<li><i class="icon-align-right"></i>align-right</li>--%>
<%--<li><i class="icon-align-justify"></i>align-justify</li>--%>
<%--<li><i class="icon-list"></i>list</li>--%>

<%--<li><i class="icon-indent-left"></i>indent-left</li>--%>
<%--<li><i class="icon-indent-right"></i>indent-right</li>--%>
<%--<li><i class="icon-facetime-video"></i>facetime-video</li>--%>
<%--<li><i class="icon-picture"></i>picture</li>--%>
<%--<li><i class="icon-pencil"></i>pencil</li>--%>
<%--<li><i class="icon-map-marker"></i>map-marker</li>--%>
<%--<li><i class="icon-adjust"></i>adjust</li>--%>
<%--<li><i class="icon-tint"></i>tint</li>--%>
<%--<li><i class="icon-edit"></i>edit</li>--%>
<%--<li><i class="icon-share"></i>share</li>--%>
<%--<li><i class="icon-check"></i>check</li>--%>
<%--<li><i class="icon-move"></i>move</li>--%>
<%--<li><i class="icon-step-backward"></i>step-backward</li>--%>
<%--<li><i class="icon-fast-backward"></i>fast-backward</li>--%>
<%--<li><i class="icon-backward"></i>backward</li>--%>
<%--<li><i class="icon-play"></i>play</li>--%>
<%--<li><i class="icon-pause"></i>pause</li>--%>
<%--<li><i class="icon-stop"></i>stop</li>--%>
<%--<li><i class="icon-forward"></i>forward</li>--%>
<%--<li><i class="icon-fast-forward"></i>fast-forward</li>--%>
<%--<li><i class="icon-step-forward"></i>step-forward</li>--%>
<%--<li><i class="icon-eject"></i>eject</li>--%>
<%--<li><i class="icon-chevron-left"></i>chevron-left</li>--%>
<%--<li><i class="icon-chevron-right"></i>chevron-right</li>--%>
<%--<li><i class="icon-plus-sign"></i>plus-sign</li>--%>
<%--<li><i class="icon-minus-sign"></i>minus-sign</li>--%>
<%--<li><i class="icon-remove-sign"></i>remove-sign</li>--%>
<%--<li><i class="icon-ok-sign"></i>ok-sign</li>--%>

<%--<li><i class="icon-question-sign"></i>question-sign</li>--%>
<%--<li><i class="icon-info-sign"></i>info-sign</li>--%>
<%--<li><i class="icon-screenshot"></i>screenshot</li>--%>
<%--<li><i class="icon-remove-circle"></i>remove-circle</li>--%>
<%--<li><i class="icon-ok-circle"></i>ok-circle</li>--%>
<%--<li><i class="icon-ban-circle"></i>ban-circle</li>--%>
<%--<li><i class="icon-arrow-left"></i>arrow-left</li>--%>
<%--<li><i class="icon-arrow-right"></i>arrow-right</li>--%>
<%--<li><i class="icon-arrow-up"></i>arrow-up</li>--%>
<%--<li><i class="icon-arrow-down"></i>arrow-down</li>--%>
<%--<li><i class="icon-share-alt"></i>share-alt</li>--%>
<%--<li><i class="icon-resize-full"></i>resize-full</li>--%>
<%--<li><i class="icon-resize-small"></i>resize-small</li>--%>
<%--<li><i class="icon-plus"></i>plus</li>--%>
<%--<li><i class="icon-minus"></i>minus</li>--%>
<%--<li><i class="icon-asterisk"></i>asterisk</li>--%>
<%--<li><i class="icon-exclamation-sign"></i>exclamation-sign</li>--%>
<%--<li><i class="icon-gift"></i>gift</li>--%>
<%--<li><i class="icon-leaf"></i>leaf</li>--%>
<%--<li><i class="icon-fire"></i>fire</li>--%>
<%--<li><i class="icon-eye-open"></i>eye-open</li>--%>
<%--<li><i class="icon-eye-close"></i>eye-close</li>--%>
<%--<li><i class="icon-warning-sign"></i>warning-sign</li>--%>
<%--<li><i class="icon-plane"></i>plane</li>--%>
<%--<li><i class="icon-calendar"></i>calendar</li>--%>
<%--<li><i class="icon-random"></i>random</li>--%>
<%--<li><i class="icon-comment"></i>comment</li>--%>
<%--<li><i class="icon-magnet"></i>magnet</li>--%>

<%--<li><i class="icon-chevron-up"></i>chevron-up</li>--%>
<%--<li><i class="icon-chevron-down"></i>chevron-down</li>--%>
<%--<li><i class="icon-retweet"></i>retweet</li>--%>
<%--<li><i class="icon-shopping-cart"></i>shopping-cart</li>--%>
<%--<li><i class="icon-folder-close"></i>folder-close</li>--%>
<%--<li><i class="icon-folder-open"></i>folder-open</li>--%>
<%--<li><i class="icon-resize-vertical"></i>resize-vertical</li>--%>
<%--<li><i class="icon-resize-horizontal"></i>resize-horizontal</li>--%>
<%--<li><i class="icon-hdd"></i>hdd</li>--%>
<%--<li><i class="icon-bullhorn"></i>bullhorn</li>--%>
<%--<li><i class="icon-bell"></i>bell</li>--%>
<%--<li><i class="icon-certificate"></i>certificate</li>--%>
<%--<li><i class="icon-thumbs-up"></i>thumbs-up</li>--%>
<%--<li><i class="icon-thumbs-down"></i>thumbs-down</li>--%>
<%--<li><i class="icon-hand-right"></i>hand-right</li>--%>
<%--<li><i class="icon-hand-left"></i>hand-left</li>--%>
<%--<li><i class="icon-hand-up"></i>hand-up</li>--%>
<%--<li><i class="icon-hand-down"></i>hand-down</li>--%>
<%--<li><i class="icon-circle-arrow-right"></i>circle-arrow-right</li>--%>
<%--<li><i class="icon-circle-arrow-left"></i>circle-arrow-left</li>--%>
<%--<li><i class="icon-circle-arrow-up"></i>circle-arrow-up</li>--%>
<%--<li><i class="icon-circle-arrow-down"></i>circle-arrow-down</li>--%>
<%--<li><i class="icon-globe"></i>globe</li>--%>
<%--<li><i class="icon-wrench"></i>wrench</li>--%>
<%--<li><i class="icon-tasks"></i>tasks</li>--%>
<%--<li><i class="icon-filter"></i>filter</li>--%>
<%--<li><i class="icon-briefcase"></i>briefcase</li>--%>
<%--<li><i class="icon-fullscreen"></i>fullscreen</li>--%>
<%--</ul>--%>

<div class="main">

    <ul class="icon_lists" id="icons">
        <li>
            <i class="icon iconfont" name="xe782">&#xe782;</i>
        </li>
        <li>
            <i class="icon iconfont" name="xe783">&#xe783;</i>
        </li>
        <li>
            <i class="icon iconfont" name="xe737">&#xe737;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe720">&#xe720;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe721">&#xe721;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe784">&#xe784;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe707">&#xe707;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe738">&#xe738;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe722">&#xe722;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe785">&#xe785;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe739">&#xe739;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe787">&#xe787;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe708">&#xe708;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe704">&#xe704;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe717">&#xe717;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe723">&#xe723;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe709">&#xe709;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe73a">&#xe73a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe73b">&#xe73b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe787">&#xe787;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe788">&#xe788;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe789">&#xe789;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe70a">&#xe70a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe78a">&#xe78a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe703">&#xe703;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe78b">&#xe78b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe73c">&#xe73c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe70b">&#xe70b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe78c">&#xe78c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe73d">&#xe73d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe777">&#xe777;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe777">&#xe777;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe78d">&#xe78d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe778">&#xe778;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe73e">&#xe73e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe78e">&#xe78e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe718">&#xe718;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe78f">&#xe78f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe790">&#xe790;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe791">&#xe791;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe792">&#xe792;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe779">&#xe779;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe73f">&#xe73f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe740">&#xe740;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe793">&#xe793;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe794">&#xe794;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77a">&#xe77a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe741">&#xe741;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe742">&#xe742;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe795">&#xe795;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe797">&#xe797;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe797">&#xe797;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe70c">&#xe70c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe724">&#xe724;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe798">&#xe798;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77b">&#xe77b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe799">&#xe799;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe725">&#xe725;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe743">&#xe743;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe70d">&#xe70d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe70e">&#xe70e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe744">&#xe744;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe745">&#xe745;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe717">&#xe717;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe727">&#xe727;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe79a">&#xe79a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77c">&#xe77c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe79b">&#xe79b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77d">&#xe77d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe79c">&#xe79c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe747">&#xe747;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe747">&#xe747;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe748">&#xe748;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe79d">&#xe79d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe79e">&#xe79e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe79f">&#xe79f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a0">&#xe7a0;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77e">&#xe77e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe749">&#xe749;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77f">&#xe77f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a1">&#xe7a1;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a2">&#xe7a2;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe727">&#xe727;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe74a">&#xe74a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe74b">&#xe74b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe74c">&#xe74c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe74d">&#xe74d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe719">&#xe719;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe770">&#xe770;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe74e">&#xe74e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe771">&#xe771;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe74f">&#xe74f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe750">&#xe750;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a3">&#xe7a3;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe751">&#xe751;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe728">&#xe728;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe71a">&#xe71a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe772">&#xe772;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe752">&#xe752;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe700">&#xe700;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a4">&#xe7a4;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a5">&#xe7a5;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe773">&#xe773;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a7">&#xe7a7;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe70f">&#xe70f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a7">&#xe7a7;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a8">&#xe7a8;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe707">&#xe707;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7a9">&#xe7a9;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe729">&#xe729;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe72a">&#xe72a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7aa">&#xe7aa;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe774">&#xe774;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7ab">&#xe7ab;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe753">&#xe753;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe775">&#xe775;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe754">&#xe754;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7ac">&#xe7ac;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7ad">&#xe7ad;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7ae">&#xe7ae;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe755">&#xe755;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe777">&#xe777;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe72b">&#xe72b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe72c">&#xe72c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe72d">&#xe72d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe757">&#xe757;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe757">&#xe757;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7af">&#xe7af;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe72e">&#xe72e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe71b">&#xe71b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe71c">&#xe71c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b0">&#xe7b0;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe758">&#xe758;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe777">&#xe777;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe72f">&#xe72f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe710">&#xe710;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe759">&#xe759;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe711">&#xe711;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe701">&#xe701;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b1">&#xe7b1;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe778">&#xe778;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe71d">&#xe71d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b2">&#xe7b2;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe75a">&#xe75a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe779">&#xe779;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe71e">&#xe71e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77a">&#xe77a;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b3">&#xe7b3;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b4">&#xe7b4;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77b">&#xe77b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe730">&#xe730;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe75b">&#xe75b;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe705">&#xe705;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b5">&#xe7b5;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b7">&#xe7b7;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe731">&#xe731;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77c">&#xe77c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b7">&#xe7b7;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe712">&#xe712;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe75c">&#xe75c;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe732">&#xe732;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe713">&#xe713;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe733">&#xe733;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe75d">&#xe75d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77d">&#xe77d;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b8">&#xe7b8;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe714">&#xe714;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77e">&#xe77e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe77f">&#xe77f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7b9">&#xe7b9;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7ba">&#xe7ba;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7bb">&#xe7bb;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe75e">&#xe75e;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe71f">&#xe71f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7bc">&#xe7bc;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe734">&#xe734;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7bd">&#xe7bd;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe735">&#xe735;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7be">&#xe7be;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7bf">&#xe7bf;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe75f">&#xe75f;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7c0">&#xe7c0;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe715">&#xe715;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe702">&#xe702;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe780">&#xe780;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7c1">&#xe7c1;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe737">&#xe737;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe770">&#xe770;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe771">&#xe771;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe772">&#xe772;</i>
        </li>

        <li>
            <i class="icon iconfont" name="xe7c2">&#xe7c2;</i>
        </li>

        <li><i class="icon iconfont" name="xe7c3">&#xe7c3;</i></li>

        <li><i class="icon iconfont" name="xe773">&#xe773;</i></li>

        <li><i class="icon iconfont" name="xe7c4">&#xe7c4;</i></li>

        <li><i class="icon iconfont" name="xe774">&#xe774;</i></li>

        <li><i class="icon iconfont" name="xe7c5">&#xe7c5;</i></li>

        <li><i class="icon iconfont" name="xe781">&#xe781;</i></li>

        <li><i class="icon iconfont" name="xe775">&#xe775;</i></li>

        <li><i class="icon iconfont" name="xe7c7">&#xe7c7;</i></li>

        <li><i class="icon iconfont" name="xe63f">&#xe63f;</i></li>

        <li><i class="icon iconfont" name="xe619">&#xe619;</i></li>

        <li><i class="icon iconfont" name="xe617">&#xe617;</i></li>

        <li><i class="icon iconfont" name="xe66b">&#xe66b;</i></li>

        <li><i class="icon iconfont" name="xe66c">&#xe66c;</i></li>

        <li><i class="icon iconfont" name="xe64c">&#xe64c;</i></li>

        <li><i class="icon iconfont" name="xe656">&#xe656;</i></li>
        
        <!-- 2017.4.28 -->
        <li><i class="icon iconfont" name="xe663">&#xe663;</i></li>
        <li><i class="icon iconfont" name="xe614">&#xe614;</i></li>
        <li><i class="icon iconfont" name="xee87">&#xee87;</i></li>
        <li><i class="icon iconfont" name="xe666">&#xe666;</i></li>
        <li><i class="icon iconfont" name="xe6a0">&#xe6a0;</i></li>
        <li><i class="icon iconfont" name="xe628">&#xe628;</i></li>
        <li><i class="icon iconfont" name="xe625">&#xe625;</i></li>
        <li><i class="icon iconfont" name="xe61e">&#xe61e;</i></li>
        <li><i class="icon iconfont" name="xe642">&#xe642;</i></li>
        <li><i class="icon iconfont" name="xe698">&#xe698;</i></li>
        
        <li><i class="icon iconfont" name="xe63d">&#xe63d;</i></li>
        <li><i class="icon iconfont" name="xe616">&#xe616;</i></li>
        <li><i class="icon iconfont" name="xe66d">&#xe66d;</i></li>
        <li><i class="icon iconfont" name="xe90c">&#xe90c;</i></li>
        <li><i class="icon iconfont" name="xe621">&#xe621;</i></li>
        <li><i class="icon iconfont" name="xe627">&#xe627;</i></li>
        <li><i class="icon iconfont" name="xe667">&#xe667;</i></li>
        <li><i class="icon iconfont" name="xe61c">&#xe61c;</i></li>
        <li><i class="icon iconfont" name="xe624">&#xe624;</i></li>
        <li><i class="icon iconfont" name="xe626">&#xe626;</i></li>
        <li><i class="icon iconfont" name="xe629">&#xe629;</i></li>
        
        
        
        
        
        
        
        
        
        
        

    </ul>
</div>
</body>