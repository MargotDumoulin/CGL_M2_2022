<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String nom =  request.getAttribute("nom") == null ? "" : (String) request.getAttribute("nom"); %>
<% String prenom = request.getAttribute("prenom") == null ? "" : (String) request.getAttribute("prenom"); %>

<!DOCTYPE html>
<html>
    <head>
        <title>${operation} un apporteur</title>
        <link href="assets/style/main.css" rel="stylesheet" type="text/css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
        <link href="assets/style/dashboard.css" rel="stylesheet">
    </head>
    <body>
    <jsp:include page="main_header.jsp" />

    <div class="container-fluid">
        <div class="row">
            <jsp:include page="main_nav.jsp"/>
            <main class="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">${operation} un apporteur</h1>
                </div>

                <c:if test="${errorMessage != null}">
                    <p style="color: red;">${errorMessage}</p>
                </c:if>
                <c:choose>
                    <c:when test="${operation == 'Modifier'}">
                        <form action="add_apporteur?appId=${appId}" method="post">
                    </c:when>
                    <c:otherwise>
                        <form action="add_apporteur" method="post">
                    </c:otherwise>
                </c:choose>
                    <c:if test="${errorMessageNom.length() > 0}">
                        <div class="alert alert-danger" role="alert">
                            <c:out value="${errorMessageNom}"/>
                        </div>
                    </c:if>
                    <div class="form-floating mb-5">
                        <c:choose>
                            <c:when test="${errorMessageNom.length() > 0}">
                                <input type="text" class="form-control is-invalid" id="nom" name="nom" value="<%= nom %>">
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" id="nom" name="nom" value="<%= nom %>">
                            </c:otherwise>
                        </c:choose>
                        <label for="nom">Nom:</label>
                    </div>
                    <c:if test="${errorMessagePrenom.length() > 0}">
                        <div class="alert alert-danger" role="alert">
                            <c:out value="${errorMessagePrenom}"/>
                        </div>
                    </c:if>
                    <div class="form-floating mb-5">
                        <c:choose>
                            <c:when test="${errorMessagePrenom.length() > 0}">
                                <input type="text" class="form-control is-invalid" id="prenom" name="prenom" value="<%= prenom %>">
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" id="prenom" name="prenom" value="<%= prenom %>">
                            </c:otherwise>
                        </c:choose>
                        <label for="prenom">Pr??nom:</label>
                    </div>
                    <div class="form-floating mb-5">
                        <select class="form-select" name="parrain" id="parrain" aria-label="S??lectionner le parin">
                            <c:if test="${operation != 'Modifier'}">
                                <option value="" selected>Aucun</option>
                            </c:if>
                            <c:forEach items="${parrains}" var="parrain" varStatus="status">
                                <option value="<c:out value="${parrain.id}"/>"><c:out value="${parrain.nom}"/> - <c:out value="${parrain.prenom}"/></option>
                            </c:forEach>
                        </select>
                        <label for="parrain">Parrain (optionnel):</label>
                    </div>
                    <input type="submit"  class="btn btn-success" value="${operation}">
                </form>
            </main>
        </div>
    </div>
    </body>
</html>
