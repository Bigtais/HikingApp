<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<h1>Hike: <c:out value="${hike.name}"/></h1>

<h3>Description:</h3>
<p><c:out value="${hike.description}"/> </p>

<h5>Category: <c:out value="${hike.category.name}"/> </h5>

<h5>Date: <c:out value="${hike.date}"/> </h5>

<sec:authorize access="isAuthenticated()">

    <h5>Creator: <c:out value="${hike.creator.firstName}"/> </h5>

    <h5>Website: <c:out value="${hike.website}"/> </h5>

    <sec:authorize access="@hikeOwnerChecker.isOwner(principal, #hike)">
        <c:url var="edit" value="/hike/edit" >
            <c:param name="id" value="${hike.id}" />
        </c:url>
        <p>
            <a class="btn btn-info" href="${edit}">Edit hike</a>
        </p>

        <c:url var="delete" value="/hike/delete" >
            <c:param name="id" value="${hike.id}" />
        </c:url>
        <p>
            <a class="btn btn-danger" href="${delete}">Delete hike</a>
        </p>
    </sec:authorize>
</sec:authorize>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>