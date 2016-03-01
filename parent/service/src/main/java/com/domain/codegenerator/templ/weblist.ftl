
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="../../common/common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${entityNameLower}管理</title>
    <link href="${ctx}/css/main.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${'$'}{ctx}/js/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/pub.js"></script>
    <script type="text/javascript" src="${ctx}/js/page.js"></script>
</head>
<body>
<form action="${ctx}/jsp/${entityNameLower}" id="mainForm" name="mainForm" method="GET">
<div>
    <table width="100%" border="0" cellpadding="0" cellspacing="1" class="table_data2" align="center">
        <tr>
            <th width="5%">
                <input type="checkbox" name="selectAll" onclick="selectAllCheck(this);"/>
            </th>
           <#list properties as property>
           <#if property.fieldName != "id">
               <th>
               ${property.fieldChineseName}
               </th>
           </#if>

           </#list>
        </tr>

        <c:forEach items="${'$'}{page.result}" var="${entityNameLower}" >
            <tr>
                <td align="center">
                    <input type="checkbox" name="sn" id="sn" value="${'$'}{${entityNameLower}.id}" />
                </td>
            <#list properties as property>
                <#if property.fieldName != "id">
                    <td>
                    ${'$'}{${entityNameLower}.${property.fieldName}}
                    </td>
                </#if>
            </#list>

            </tr>
        </c:forEach>

    </table>
</div>
<div>
    </table>
    <table cellpadding="0" cellspacing="0" width="100%" style="margin-top:10px; ">
        <tr height="17">
            <td align="right">
                <input name="Submit" type="button" class="button02" value="添加"
                       onclick="view(NEW);"/>
                <input name="Submit" type="button" class="button02" value="编辑"
                       onclick="view(EDIT)"/>
                <input name="Submit3" type="button" class="button02" value="查看"
                       onclick="view(VIEW)"/>
                <input name="Submit3" type="button" class="button02" value="删除"
                       onclick="deleteRecord()"/>
            </td>
        </tr>
    </table>
</div>
    <jsp:include page="../../common/page.jsp"></jsp:include>
</form>
</body>
</html>