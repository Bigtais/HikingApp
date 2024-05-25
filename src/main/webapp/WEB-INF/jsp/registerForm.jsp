<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Create Account</h1>

<div class="card bg-light">
    <div class="card-body">
        <form:form method="POST" modelAttribute="clubMember">

            <form:errors path="*" cssClass="alert alert-danger" element="div" />

            <div class="form-group my-1">
                <label for="firstName">First Name:</label>
                <form:input class="form-control" path="firstName" />
                <form:errors path="firstName" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="lastName">Last Name:</label>
                <form:input class="form-control" path="lastName" rows="4" />
                <form:errors path="lastName" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="email">E-mail:</label>
                <form:input path="email" class="form-control" />
                <form:errors path="email" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="password">Password:</label>
                <form:password path="password" class="form-control" />
                <form:errors path="password" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>