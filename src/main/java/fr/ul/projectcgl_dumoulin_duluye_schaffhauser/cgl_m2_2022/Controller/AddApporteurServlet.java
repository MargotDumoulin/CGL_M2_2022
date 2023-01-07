package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;;
import java.io.*;
import java.util.stream.Stream;

@WebServlet(name = "Ajouter un apporteur",
        urlPatterns = {"/add_apporteur"})
public class AddApporteurServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Stream<ApporteurEntity> apporteurs = ApporteurDAO.getAll();
        request.setAttribute("apporteurs", apporteurs.toArray());

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_apporteur.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");

        request.setAttribute("nom", nom);
        request.setAttribute("prenom", prenom);

        int parrainId = request.getParameter("parrain").equals("") ?
                -1 : Integer.parseInt(request.getParameter("parrain"));

        // Validate the input
        if (nom == null || nom.trim().isEmpty())
            request.setAttribute("errorMessageNom", "Le nom est obligatoire");
        if (prenom == null || prenom.trim().isEmpty())
            request.setAttribute("errorMessagePrenom", "Le prénom est obligatoire");

        if (request.getAttribute("errorMessageNom") != null
                || request.getAttribute("errorMessagePrenom") != null
                || request.getAttribute("errorMessageParrain") != null)
        {
            Stream<ApporteurEntity> apporteurs = ApporteurDAO.getAll();
            request.setAttribute("apporteurs", apporteurs.toArray());
            RequestDispatcher dispatcher = request.getRequestDispatcher("add_apporteur.jsp");
            dispatcher.forward(request, response);
            return;
        }

        ApporteurDAO apporteurDAO = new ApporteurDAO();
        apporteurDAO.insertApporteur(nom, prenom, parrainId);

        response.sendRedirect("apporteurs");
    }
}