<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>


<h1>Categories</h1>

<table class="table table-hover">
    <c:choose>
        <c:when test="${categories.size() > 0}">
            <c:forEach items="${categories}" var="category">
                <c:url var="detail" value="/category/${category.id}"/>
                <tr>
                    <td><a href="${detail}"> <c:out value="${category.name}" /></a></td>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr>
                <td style="text-align: center">No categories found</td>
            </tr>
        </c:otherwise>
    </c:choose>

</table>

<sec:authorize access="hasAnyAuthority('ADMIN')">
    <c:url var="create" value="/category/create" />
    <p>
        <a class="btn btn-info" href="${create}">Create new category</a>
    </p>
</sec:authorize>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>