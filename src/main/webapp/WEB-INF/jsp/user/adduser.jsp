<%--
  Created by IntelliJ IDEA.
  User: 32414
  Date: 2020/4/10
  Time: 9:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="fm" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${pageContext.request.contextPath }/statics/calendar/WdatePicker.js"></script>

</head>
<body>
<%--@elvariable id="user" type=""--%>
<fm:form method="post" modelAttribute="user" action="${pageContext.request.contextPath }/user/addsave">
    <fm:errors path="userCode"/><br/>
    用户编码：<fm:input path="userCode"/><br/>
    <fm:errors path="userName"/><br/>
    用户名称：<fm:input path="userName"/><br/>
    <fm:errors path="userPassword"/><br/>
    用户密码：<fm:password path="userPassword"/><br/>
    性别：<fm:radiobutton path="gender" value="1"/>男<fm:radiobutton path="gender" value="2"/>女<br/>
    <fm:errors path="birthday"/><br/>
    用户生日：<fm:input path="birthday" Class="Wdate" readonly="readonly" onclick="WdatePicker();"/><br/>

    用户地址：<fm:input path="address"/><br/>
    用户电话：<fm:input path="phone"/><br/>
    用户角色：
    <fm:radiobutton path="userRole" value="1"/>系统管理员
    <fm:radiobutton path="userRole" value="2"/>经理
    <fm:radiobutton path="userRole" value="3" checked="checked"/>普通用户
    <br/>
    <input type="submit" value="保存"/>
</fm:form>
</body>
</html>
