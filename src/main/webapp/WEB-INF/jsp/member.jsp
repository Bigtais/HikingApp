
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<h1>Member Details:</h1>

<h5>First Name: <c:out value="${member.firstName}"/> </h5>

<h5>Last Name: <c:out value="${member.lastName}"/> </h5>

<h5>Email: <c:out value="${member.email}"/> </h5>

<table class="table table-hover">
    <c:choose>
        <c:when test="${member.hikes.size() > 0}">
            <tr>
                <th>Name Of Hike</th>
                <th>Category Name</th>
                <th>Date</th>
            </tr>
            <c:forEach items="${member.hikes}" var="hike">
                <c:url var="detail" value="/hike/${hike.id}"/>
                <tr>
                    <td><a href="${detail}"> <c:out value="${hike.name}" /></a></td>
                    <td><c:out value="${hike.category.name}" /></td>
                    <td><c:out value="${hike.date}" /></td>
                </tr>
            </c:forEach>
        </c:when>

        <c:otherwise>
            <tr>
                <td colspan="3" style="text-align: center">No hikes created yet</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>

<c:url var="change" value="/account/password-edit" />
<p>
    <a class="btn btn-info" href="${change}">Edit password</a>
</p>

<c:url var="create" value="/hike/edit" />
<p>
    <a class="btn btn-info" href="${create}">Create new hike</a>
</p>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>