<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮箱管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#tableHead").click(function() {
			if ($(this).attr("checked")) {
				$("#contentTable tbody :checkbox[name='rowSelect']").attr("checked", true);
					} else {$("#contentTable tbody :checkbox[name='rowSelect']").attr("checked", false);
				}
		});
		
		$("#btnAdd").click(function(){
			window.location.href="${ctx}/sys/mail/form";	
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/sys/mail/list");
		$("#searchForm").submit();
	}
	function page2(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#loginName").val("");
		$("#searchForm").attr("action", "${ctx}/sys/mail/list");
		$("#searchForm").submit();
		return false;
	}
	function deleteMails(){
		var mailIds = "";
		$("input[type='checkbox'][name='rowSelect']:checked").each(function(){
			mailIds += $(this).val() + ",";
		});
		if (mailIds.length == 0){
			top.layer.msg("请至少选择一条记录");
			return ;
		} else {
			mailIds = mailIds.substring(0,mailIds.length-1);
			confirmy("确定要删除这些记录吗？","${ctx}/sys/mail/delete?ids=" + mailIds);
		}
	}
	function confirmy(mess, href) {
		top.$.jBox.confirm(mess, '系统提示', function(v, h, f) {
			if (v == 'ok') {
				location = href;
			}
		}, {buttonsFocus : 1});
		top.$('.jbox-body .jbox-icon').css('top', '55px');
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/mail/list">邮箱管理</a></li>
		<li><a href="${ctx}/sys/mail/form">邮箱添加</a></li>
	</ul>
	<br>
	<div style="display: none;">
	<iframe id="data_import_frame" name="data_import_frame" width="100%" height="120px" 
		scrolling="no" frameborder="0"></iframe>
	</div>
	<form:form id="searchForm" modelAttribute="mailMap" class="breadcrumb form-search" method="post" action="${ctx}/sys/mail/list">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input id="historyPageNo" name="bean['historyPageNo']" type="hidden" value="${mailMap.bean.historyPageNo }" />
		<input id="historyPageSize" name="bean['historyPageSize']" type="hidden" value="${mailMap.bean.historyPageSize }" />
		<table border="0">
			<tr>
				<td><label>帐号:</label><form:input id="loginName" path="bean['LOGIN_NAME']" htmlEscape="false"
						maxlength="32" class="input-small"/></td>
				<td colspan="2" style="text-align: right;">&nbsp;&nbsp;<input
					id="btnSubmit" class="btn btn-primary" type="submit" value="查询"
					onclick="return page()"/>&nbsp;&nbsp; <input id="btnCancel"
					class="btn btn-primary" type="button" value="重置"
					onclick="return page2()"/>&nbsp;&nbsp; <input id="btnAdd"
					class="btn btn-primary" type="button" value="新增"/>&nbsp;&nbsp;<input 
					id="btnDelete" class="btn btn-warning" type="button" value="删除"
					onclick="return deleteMails()"/>
				</td>
			</tr>
		</table>
	</form:form>
	<div style="display: none;">
		<tags:message content="${message}"/>
	</div>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="12px"><input type="checkbox" id="tableHead"></th>
				<th>帐号</th>
				<th>发送服务器</th>
				<th>发送端口</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="bean">
				<td><input type="checkbox" name="rowSelect" value="${bean['ID']}" /></td>
				<td><span style="color: ${bean['DEFAULT_FLAG']  eq '1' ? 'red':'black'};">${bean['LOGIN_NAME']}</span></td>
				<td><span style="color: ${bean['DEFAULT_FLAG']  eq '1' ? 'red':'black'};">${bean['SMTP_HOST']}</span></td>
				<td><span style="color: ${bean['DEFAULT_FLAG']  eq '1' ? 'red':'black'};">${bean['SMTP_PORT']}</span></td>
				<td>
				<a href="${ctx}/sys/mail/form?id=${bean['ID']}">修改</a>&nbsp;|&nbsp;
				<a href="${ctx}/sys/mail/delete?ids=${bean['ID']}" onclick="return confirmy('确定要删除该条记录吗?',this.href)">删除</a>
				<c:choose>
					<c:when test="${bean['DEFAULT_FLAG'] == '0'}">
						&nbsp;|&nbsp;<a href="${ctx}/sys/mail/default?id=${bean['ID']}">设为默认</a>
					</c:when>
				</c:choose>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>