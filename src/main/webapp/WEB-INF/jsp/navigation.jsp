
<c:url var="home" value="/" />
<c:url var="categoryList" value="/category/list" />
<c:url var="myAccount" value="/account" />
<c:url var="register" value="/register" />
<c:url var="login" value="/login" />
<c:url var="logout" value="/logout" />
<c:url var="forgot" value="/password-forgot/ask-mail" />
<c:url var="hikeFind" value="/hike/find" />

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="${home}">Home</a></li>
            <li class="nav-item"><a class="nav-link" href="${categoryList}">Categories</a></li>
            <sec:authorize access="isAuthenticated()">
                <li class="nav-item"><a class="nav-link" href="${myAccount}">My Account</a></li>
                <li class="nav-item"><a class="nav-link" href="${logout}">Logout</a></li>
            </sec:authorize>
            <sec:authorize access="!isAuthenticated()">
                <li class="nav-item"><a class="nav-link" href="${register}">Register</a></li>
                <li class="nav-item"><a class="nav-link" href="${login}">Login</a></li>
                <li class="nav-item"><a class="nav-link" href="${forgot}">Password Forgot</a></li>
            </sec:authorize>
        </ul>
    </div>
</nav>
