<%@ page import="java.util.Objects" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<% DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); %>
<% Double montant =  request.getAttribute("montant") == null ? 0 : (Double) request.getAttribute("montant"); %>
<% String date = request.getAttribute("date") == null ? dateFormat.format(new Date()) : dateFormat.format(request.getAttribute("date")); %>

<!DOCTYPE html>
<html>
    <head>
        <title>${operation} une affaire</title>
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
                        <h1 class="h2" id="affaires-title">${operation} une affaire</h1>
                    </div>

                    <c:if test="${errorMessage != null}">
                        <p style="color: red;">${errorMessage}</p>
                    </c:if>

                    <form action="add_affaire${operation == 'Modifier' ? '?affId='.concat(affId) : ''}" method="post">

                        <!-- DATE -->
                        <c:if test="${errorMessageDate.length() > 0}">
                            <div class="alert alert-danger" role="alert">
                                <c:out value="${errorMessageDate}"/>
                            </div>
                        </c:if>
                        <div class="form-floating mb-5">
                            <input type="date" class="form-control ${errorMessageDate.length() > 0 ? 'is-invalid' : ''}" id="date" name="date" value="<%= date %>">
                            <label for="date">Date:</label>
                        </div>

                        <!-- MONTANT -->
                        <c:if test="${errorMessageMontant.length() > 0}">
                            <div class="alert alert-danger" role="alert">
                                <c:out value="${errorMessageMontant}"/>
                            </div>
                        </c:if>
                        <div class="form-floating mb-5">
                            <input type="number" class="form-control ${errorMessageMontant.length() > 0 ? 'is-invalid' : ''}" id="montant" name="montant" value="<%= montant %>">
                            <label for="montant">Montant:</label>
                        </div>

                        <!-- APPORTEUR -->
                        <c:if test="${errorMessageApporteur.length() > 0}">
                            <div class="alert alert-danger" role="alert">
                                <c:out value="${errorMessageApporteur}"/>
                            </div>
                        </c:if>
                        <div class="form-floating mb-5">
                            <select class="form-select" name="apporteur" id="apporteur">
                                <c:if test="${operation != 'Modifier'}">
                                    <option value="" selected>Aucun</option>
                                </c:if>
                                <c:forEach items="${apporteurs}" var="apporteur" varStatus="status">
                                    <option value="<c:out value="${apporteur.id}"/>"><c:out value="${apporteur.nom}"/> - <c:out value="${apporteur.prenom}"/></option>
                                </c:forEach>
                            </select>
                            <label for="apporteur">Apporteur d'affaire :</label>
                        </div>

                        <input type="submit"  class="btn btn-success" value="${operation}">
                    </form>
                </main>
            </div>
        </div>
    </body>
</html>