package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@WebServlet(name = "Ajouter un apporteur",
        urlPatterns = {"/add_apporteur"})
public class AddApporteurServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("operation", "Ajouter");

        Stream<ApporteurEntity> parrains = ApporteurDAO.getInstance().getAllApporteursWithMaxChainLength();
        request.setAttribute("parrains", parrains.toArray());

        String appId = request.getParameter("appId");

        if (appId != null && !Objects.equals(appId, "")) {
            ApporteurEntity apporteur = ApporteurDAO.getInstance().getById(Long.parseLong(appId));
            request.setAttribute("appId", appId);
            request.setAttribute("nom", apporteur.getNom());
            request.setAttribute("prenom", apporteur.getPrenom());
            request.setAttribute("apporteurs", Collections.singletonList(apporteur.getParrain()));
            request.setAttribute("operation", "Modifier");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_apporteur.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ApporteurEntity apporteur = new ApporteurEntity();

        String appId = request.getParameter("appId");

        if (appId != null && !Objects.equals(appId, ""))
            request.setAttribute("operation", "Modifier");
        else
            request.setAttribute("operation", "Ajouter");

        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        Long parrainId = request.getParameter("parrain").equals("") ? -1 : Long.parseLong(request.getParameter("parrain"));

        request.setAttribute("appId", appId);
        request.setAttribute("nom", nom);
        request.setAttribute("prenom", prenom);

        // Validate the input
        if (nom == null || nom.trim().isEmpty())
            request.setAttribute("errorMessageNom", "Le nom est obligatoire");
        if (prenom == null || prenom.trim().isEmpty())
            request.setAttribute("errorMessagePrenom", "Le pr??nom est obligatoire");

        if (request.getAttribute("errorMessageNom") != null
                || request.getAttribute("errorMessagePrenom") != null
                || request.getAttribute("errorMessageParrain") != null) {
            Stream<ApporteurEntity> apporteurs = ApporteurDAO.getInstance().getAll();
            request.setAttribute("apporteurs", apporteurs.toArray());
            RequestDispatcher dispatcher = request.getRequestDispatcher("add_apporteur.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            apporteur.setId(Long.parseLong(appId));
        }
        catch (Exception ignored) {}
        apporteur.setPrenom(prenom);
        apporteur.setNom(nom);
        apporteur.setParrain(Optional.of(parrainId).map(ApporteurDAO.getInstance()::getById).orElse(null));

        if (apporteur.getId() == null) {
            ApporteurDAO.getInstance().insert(apporteur);
        }
        else {
            ApporteurDAO.getInstance().update(apporteur);
        }

        response.sendRedirect("apporteurs");
    }
}