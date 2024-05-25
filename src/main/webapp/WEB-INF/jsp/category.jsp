<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>


<h1>Category: <c:out value="${category.name}"/></h1>

<table class="table table-hover">

    <c:choose>
        <c:when test="${category.hikes.size() > 0 }">
            <tr>
                <th>Name Of Hike</th>
                <th>Date</th>
                <sec:authorize access="isAuthenticated()">
                    <th>Website</th>
                    <th>Creator Name</th>
                </sec:authorize>
            </tr>
            <c:forEach items="${category.hikes}" var="hike">
                <c:url var="detail" value="/hike/${hike.id}"/>
                <tr>
                    <td><a href="${detail}"> <c:out value="${hike.name}" /></a></td>
                    <td><c:out value="${hike.date}" /></td>
                    <sec:authorize access="isAuthenticated()">
                        <td><c:out value="${hike.website}" /></td>
                        <td><c:out value="${hike.creator.firstName}" /></td>
                    </sec:authorize>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr>
                <td colspan="4" style="text-align: center">No hikes for this category</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>