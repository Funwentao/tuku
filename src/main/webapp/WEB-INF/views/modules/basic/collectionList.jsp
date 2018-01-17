<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>收藏管理</title>
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
		<li class="active"><a href="${ctx}/basic/collection/">收藏列表</a></li>
		<shiro:hasPermission name="basic:collection:edit"><li><a href="${ctx}/basic/collection/form">收藏添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="collection" action="${ctx}/basic/collection/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户ID：</label>
				<form:input path="userId" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<li><label>图集ID：</label>
				<form:input path="galleryId" htmlEscape="false" maxlength="255" class="input-medium"/>
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
				<th>图集ID</th>
				<th>备注信息</th>
				<shiro:hasPermission name="basic:collection:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="collection">
			<tr>
				<td><a href="${ctx}/basic/collection/form?id=${collection.id}">
					${collection.userId}
				</a></td>
				<td>
					${collection.galleryId}
				</td>
				<td>
					${collection.remarks}
				</td>
				<shiro:hasPermission name="basic:collection:edit"><td>
    				<a href="${ctx}/basic/collection/form?id=${collection.id}">修改</a>
					<a href="${ctx}/basic/collection/delete?id=${collection.id}" onclick="return confirmx('确认要删除该收藏吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>