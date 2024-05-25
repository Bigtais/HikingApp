<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Received Code</h1>

<div class="card bg-light">
    <div class="card-body">
        <form method="POST">
            <sec:csrfInput />

            <div class="form-group my-1">
                <label for="code">Write the received code:</label>
                <input id="code" name="code" class="form-control" />
            </div>
            <div class="form-group my-1">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
        <p>If the code corresponds to the one received by mail, this code will become your current password.</p>
        <p>It's recommended to change this password afterward.</p>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>