<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/basic/weixinUserInfo/">用户管理</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="weixinUserInfo" action="${ctx}/basic/weixinUserInfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="openid" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="nickname" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>加入日期：</label>
				<input name="beginInDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${weixinUserInfo.beginInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> -
				<input name="endInDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${weixinUserInfo.endInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>等级：</label>
				<form:input path="grade" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>

			<li><label>禁言：</label>
				<form:select path="gossip" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('gossip_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>用户ID</th>
				<th>用户头像</th>
				<th>名称</th>
				<th>等级</th>
				<th>城市</th>
				<th>性别</th>
				<th>禁言</th>
				<th>加入时间</th>
				<shiro:hasPermission name="basic:weixinUserInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="weixinUserInfo">
			<tr>
				<td>
					${weixinUserInfo.openid}
				</td>
				<td>
					<img src="${weixinUserInfo.headimgurl}" style="width: 40px;height:40px;">
				</td>
				<td>
					${weixinUserInfo.nickname}
				</td>
				<td>
					${weixinUserInfo.grade}
				</td>
				<td>
					${weixinUserInfo.city}
				</td>
				<td>
					${weixinUserInfo.sex}
				</td>
				<td>
					${fns:getDictLabel(weixinUserInfo.gossip, 'gossip_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${weixinUserInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="basic:weixinUserInfo:edit"><td>
    				<a href="${ctx}/basic/weixinUserInfo/form?id=${weixinUserInfo.id}">修改</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>