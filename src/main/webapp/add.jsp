<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.kn.sadurska.usermanagement.User" scope="session"/>
<html>
<head>
    <title>User management. Add</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/add" method="post">
    First Name <input type="text" name="firstName" value="">
    Last Name <input type="text" name="lastName" value="">
    Date of birth <input type="text" name="date"
                         value=""/>
    <input type="submit" name="okButton" value="Ok">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test="${requestScope.error != null}">
    <script>
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>