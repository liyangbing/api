<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${entityNameLower}管理</title>
    <link href="${'$'}{ctx}/css/main.css" rel="stylesheet" type="text/css"/>

    <style type="text/css">
        div {
            display: block;
            padding: 5px 0;
            text-align:left;
            margin:5px 10px;
        }
    </style>
    <script type="text/javascript" src="${'$'}{ctx}/js/pub.js"></script>
    <script type="text/javascript" src="${'$'}{ctx}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${'$'}{ctx}/js/dataNoTime.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>

<div>
    <form id="${entityNameLower}" name="${entityNameLower}" action="${'$'}{ctx}/jsp/${entityNameLower}/" method="post">

        <input type="hidden" name="id" value="0">
        <h2 class="reg">
            添加${entityNameLower}
        </h2>

    <#list properties as property>
        <#if property.fieldName != "id">
        <div>
            <label>${property.fieldChineseName}</label>
            <input type="text" name="${property.fieldName}"  class="inputtxt" value="${'$'}{${entityNameLower}.${property.fieldName}}">
        </div>
    </#if>
    </#list>

        <div>
            <input type="submit" name="operButton" value="提交" class="button02"/>
        </div>
    </form>
</div>


</body>
</html>
