<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Create Category</h1>

<div class="card bg-light">
  <div class="card-body">
    <form:form method="POST" modelAttribute="category">
      <sec:csrfInput />

      <form:errors path="*" cssClass="alert alert-danger" element="div" />

      <div class="form-group my-1">
        <label for="name">Name:</label>
        <form:input class="form-control" path="name" />
        <form:errors path="name" cssClass="alert alert-warning"
                     element="div" />
      </div>
      <div class="form-group my-1">
        <button type="submit" class="btn btn-success">Create</button>
      </div>
    </form:form>
  </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>