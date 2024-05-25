<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Modify Password</h1>

<div class="card bg-light">
    <div class="card-body">
        <form:form method="POST" modelAttribute="passwordRequestUtil">
            <sec:csrfInput />
            <form:errors path="*" cssClass="alert alert-danger" element="div" />

            <div class="form-group my-1">
                <label for="oldPassword">Old Password:</label>
                <form:password path="oldPassword" class="form-control" />
                <form:errors path="oldPassword" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="newPassword">New Password:</label>
                <form:password path="newPassword" class="form-control" />
                <form:errors path="newPassword" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="confirmPassword">Confirm Password:</label>
                <form:password path="confirmPassword" class="form-control" />
                <form:errors path="confirmPassword" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>


<%--<div class="form-group my-1">--%>
<%--    <label for="oldPassword">Old Password:</label>--%>
<%--    <input type="password" id="oldPassword" name="oldPassword" class="form-control" />--%>
<%--</div>--%>

<%--<div class="form-group my-1">--%>
<%--    <label for="newPassword">New Password:</label>--%>
<%--    <input type="password" id="newPassword" name="newPassword" class="form-control" />--%>
<%--</div>--%>

<%--<div class="form-group my-1">--%>
<%--    <label for="confirmPassword">Confirm Password:</label>--%>
<%--    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" />--%>
<%--</div>--%>