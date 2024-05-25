<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Password Forgotten</h1>

<div class="card bg-light">
    <div class="card-body">
        <form method="POST">

            <div class="form-group my-1">
                <label for="email">E-mail:</label>
                <input id="email" name="email" class="form-control" />
            </div>
            <div class="form-group my-1">
                <button type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>