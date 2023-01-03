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
          <h1 class="h2">Apporteurs d'affaires</h1>
          <div class="btn-toolbar mb-2 mb-md-0">
            <button type="button" class="btn btn-sm btn-outline-secondary">Ajouter</button>
          </div>
        </div>
        <div class="table-responsive">
          <table class="table table-striped table-sm">
            <thead>
            <tr>
              <th scope="col">Id</th>
              <th scope="col">Nom</th>
              <th scope="col">Prénom(s)</th> <!-- TODO: prévoir plusieurs prénoms ? -->
              <th scope="col">Affilié</th>
              <th scope="col">Somme commission mois N</th>
              <th scope="col">Sommes commissions N-1</th>
              <th scope="col">Sommes commissions N-2</th>
              <th scope="col">Affaires directes</th>
            </tr>
            </thead>
            <tbody>
              <!-- TODO: permettre trier chacun des champs -->
              <c:forEach items="${apporteurs}" var="apporteur" varStatus="status">
                <tr>
                  <td><c:out value="${ apporteur.id }" /></td>
                  <td><c:out value="${ apporteur.nom }" /></td>
                  <td><c:out value="${ apporteur.prenom }" /></td>
                  <td>
                    <c:choose>
                      <c:when test="${apporteur.affilie}">
                        Oui
                      </c:when>
                      <c:otherwise>
                        Non
                      </c:otherwise>
                    </c:choose>
                  </td>
                  <td>
                    Somme N
                  </td>
                  <td>
                    Somme N-1
                  </td>
                  <td>
                    Somme N-2
                  </td>
                  <td><a class="btn btn-success" href="affaires?appId=${apporteur.id}" role="button">Voir</a></td>
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
