<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<h1>Create Account</h1>

<div class="card bg-light">
    <div class="card-body">
        <form:form method="POST" modelAttribute="hike">
            <sec:csrfInput />
            <form:errors path="*" cssClass="alert alert-danger" element="div" />

            <div class="form-group my-1">
                <label for="name">Name:</label>
                <form:input class="form-control" path="name" />
                <form:errors path="name" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="description">Description:</label>
                <form:textarea class="form-control" path="description" rows="4" />
                <form:errors path="description" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="website">Website:</label>
                <form:input path="website" class="form-control" />
                <form:errors path="website" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="date">Date:</label>
                <form:input path="date" class="form-control" type="date"/>
                <form:errors path="date" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <label for="category">Category:</label>
                <form:select path="category" multiple="false" class="form-control">
                    <form:option value="" label="--- Select ---" />
                    <form:options items="${categories}" />
                </form:select>
                <form:errors path="category" cssClass="alert alert-warning"
                             element="div" />
            </div>
            <div class="form-group my-1">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form:form>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>