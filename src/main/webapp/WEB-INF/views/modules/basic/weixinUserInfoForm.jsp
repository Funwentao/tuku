<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/basic/weixinUserInfo/">微信用户列表</a></li>
		<li class="active"><a href="${ctx}/basic/weixinUserInfo/form?id=${weixinUserInfo.id}">微信用户<shiro:hasPermission name="basic:weixinUserInfo:edit">${not empty weixinUserInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="basic:weixinUserInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="weixinUserInfo" action="${ctx}/basic/weixinUserInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">等级：</label>
			<div class="controls">
				<form:select path="grade" class="input-xlarge required" maxlength="64">
					<form:option value="" label="请选择"/>
					<c:forEach items="${servicesList}" var="s">
						<form:option value="${s.serviceContent}">${s.serviceContent}</form:option>
					</c:forEach>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">禁言：</label>
			<div class="controls">
				<form:select path="gossip" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('gossip_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="basic:weixinUserInfo:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>