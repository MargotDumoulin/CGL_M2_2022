<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Projet CGL</title>
    <link href="assets/style/main.css" rel="stylesheet" type="text/css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link href="assets/style/dashboard.css" rel="stylesheet">
</head>
<body>
<jsp:include page="main_header.jsp" />

<div class="container-fluid">
    <div class="row">
        <jsp:include page="main_nav.jsp" />

        <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">


            <div>
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Affaires</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <button type="button" class="btn btn-sm btn-outline-secondary">Ajouter</button>
                    </div>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Date</th>
                            <th scope="col">Commission</th>
                            <th scope="col">Apporteur</th>
                            <th scope="col">Commission apporteur</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${affaires}" var="affaire" varStatus="status">
                                <tr>
                                    <td><c:out value="${ affaire.id }" /></td>
                                    <td><fmt:formatDate value="${ affaire.date }" pattern="yyyy-MM-dd" /></td>
                                    <td><c:out value="${ affaire.commission }" />€</td>
                                    <td><c:out value="${ affaire.apporteur.prenom }" /> <c:out value="${ affaire.apporteur.nom }" /></td>
                                    <td>
                                        <c:forEach items="${affaire.commissionsPerso}" var="entry">
                                            <c:if test = "${entry.key == affaire.apporteur.id}">
                                                <c:out value="${entry.value}" />€
                                            </c:if>

                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>
</div>

<script src="../assets/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>
<script src="dashboard.js"></script>
</body>
</html>
