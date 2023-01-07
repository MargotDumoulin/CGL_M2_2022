<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Projet CGL</title>
  <link href="assets/style/homepage.css" rel="stylesheet" type="text/css">
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
      <h1 class="mb-5">Modification des paramètres de l'application</h1>
      <form action="settings" method="post">
        <c:forEach items="${settings}" var="setting" varStatus="status">
          <div class="form-floating mb-5">
            <input type="text" class="form-control" id="${setting.code}" name="${setting.code}" value="${setting.valeur}">
            <label for="${setting.code}">${setting.label}</label>
          </div>
        </c:forEach>
        <input type="submit"  class="btn btn-success" value="Valider les modifications">
        <a class="btn btn-danger" href="index.jsp">Annuler</a>
      </form>
    </main>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/feather-icons@4.28.0/dist/feather.min.js" integrity="sha384-uO3SXW5IuS1ZpFPKugNNWqTZRRglnUJK6UAZ/gxOX80nxEkN9NcGZTftn6RzhGWE" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js" integrity="sha384-zNy6FEbO50N+Cg5wap8IKA4M/ZnLJgzc6w2NqACZaK0u0FXfOWRRJOnQtpZun8ha" crossorigin="anonymous"></script>
</body>
</html>