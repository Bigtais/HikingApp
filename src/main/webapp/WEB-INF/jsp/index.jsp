<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<h1>Hiking Management Application</h1>

<form action="${hikeFind}" method="post">
	<sec:csrfInput />
<%--	Add text: search for a hike--%>
	<div class="card bg-primary">
		<div class="card-body">
			<h5 style="display: inline">Search for a hike :</h5>
			<input name="name" size="10" class="mx-3" />
			<input class="btn btn-info mx-3" type="submit" value="Find" />
		</div>
	</div>
</form>


<%@ include file="/WEB-INF/jsp/footer.jsp"%>
