<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>服务管理</title>
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
<%--<form:form id="searchForm" modelAttribute="weixinUserInfo" action="${ctx}/basic/weixinUserInfo/" method="post" class="breadcrumb form-search">--%>
    <%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
    <%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
    <%--<ul class="ul-form">--%>
        <%--<li><label>加入日期：</label>--%>
            <%--<input name="beginInDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                   <%--value="<fmt:formatDate value="${weixinUserInfo.beginInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
                   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> ---%>
            <%--<input name="endInDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
                   <%--value="<fmt:formatDate value="${weixinUserInfo.endInDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
                   <%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
        <%--</li>--%>
        <%--<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>--%>
        <%--<li class="clearfix"></li>--%>
    <%--</ul>--%>
<%--</form:form>--%>
<sys:message content="${message}"/>


<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>当天数据</th>
        <th>浏览量</th>
        <th>新增用户</th>
        <th>付费订单</th>
        <th>实际收入</th>
        <th>黄金会员</th>
        <th>钻石会员</th>
        <th>铂金会员</th>
        <th>至尊会员</th>

    </tr>
    </thead>
    <tbody>
        <tr>
            <td></td>
            <td>${DayHits}</td>
            <td>${newUserDay}</td>
            <td>${OrderDay}</td>
            <td>${TotalFeeDay}</td>
            <td>${UserHZGradeDay}</td>
            <td>${UserZSGradeDay}</td>
            <td>${UserBJGradeDay}</td>
            <td>${UserZZGradeDay}</td>
        </tr>
    </tbody>
</table>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>当月数据</th>
        <th>浏览量</th>
        <th>新增用户</th>
        <th>付费订单</th>
        <th>实际收入</th>
        <th>黄金会员</th>
        <th>钻石会员</th>
        <th>铂金会员</th>
        <th>至尊会员</th>

    </tr>
    </thead>
    <tbody>
    <tr>
        <td></td>
        <td>${MonthHits}</td>
        <td>${newUserMonth}</td>
        <td>${OrderMonth}</td>
        <td>${TotalFeeMonth}</td>
        <td>${UserHZGradeMonth}</td>
        <td>${UserZSGradeMonth}</td>
        <td>${UserBJGradeMonth}</td>
        <td>${UserZZGradeMonth}</td>
    </tr>
    </tbody>
</table>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>总数据</th>
        <th>总览量</th>
        <th>总用户</th>
        <th>付费订单</th>
        <th>实际收入</th>
        <th>黄金会员</th>
        <th>钻石会员</th>
        <th>铂金会员</th>
        <th>至尊会员</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td></td>
        <td>${AllHits}</td>
        <td>${newUserAll}</td>
        <td>${OrderAll}</td>
        <td>${TotalFeeAll}</td>
        <td>${UserHZGradeAll}</td>
        <td>${UserZSGradeAll}</td>
        <td>${UserBJGradeAll}</td>
        <td>${UserZZGradeAll}</td>
    </tr>
    </tbody>
</table>
</body>
</html>