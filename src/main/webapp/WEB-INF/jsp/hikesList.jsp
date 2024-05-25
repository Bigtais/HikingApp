<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<c:url var="hikeFind" value="/hike/find" />

<h1>Hikes</h1>

<form action="${hikeFind}" method="post">
    <sec:csrfInput />
    <div class="card bg-primary">
        <div class="card-body">
            <h5 style="display: inline">Search for a hike :</h5>
            <input name="name" size="10" class="mx-3" />
            <input class="btn btn-info mx-3" type="submit" value="Find" />
        </div>
    </div>
</form>

<table class="table table-hover">
    <tr>
        <th>Name Of Hike</th>
        <th>Date</th>
        <sec:authorize access="isAuthenticated()">
            <th>Website</th>
        </sec:authorize>
    </tr>
    <c:choose>
        <c:when test="${hikes.size() > 0}">
            <c:forEach items="${hikes}" var="hike">
                <c:url var="detail" value="/hike/${hike.id}"/>
                <tr>
                    <td><a href="${detail}"> <c:out value="${hike.name}" /></a></td>
                    <td><c:out value="${hike.date}" /></td>
                    <sec:authorize access="isAuthenticated()">
                        <td><c:out value="${hike.website}" /></td>
                    </sec:authorize>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr>
                <td colspan="5" style="text-align: center">No hikes found</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>