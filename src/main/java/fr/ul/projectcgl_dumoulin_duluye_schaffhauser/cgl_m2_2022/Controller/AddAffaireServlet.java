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
import java.util.Objects;
import java.util.stream.Stream;

@WebServlet(
        name = "AddAffaire",
        urlPatterns = {"/add_affaire"}
)
public class AddAffaireServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("operation", "Ajouter");

        Stream<ApporteurEntity> apporteurs = ApporteurDAO.getInstance().getAll();
        request.setAttribute("apporteurs", apporteurs.toArray());

        String appId = request.getParameter("appId");

        if (appId != null && !Objects.equals(appId, "")) {
            ApporteurEntity apporteur = ApporteurDAO.getInstance().getById(Long.parseLong(appId));
            request.setAttribute("appId", appId);
            request.setAttribute("nom", apporteur.getNom());
            request.setAttribute("prenom", apporteur.getPrenom());
            request.setAttribute("apporteurs", Collections.singletonList(apporteur.getParrain()));
            request.setAttribute("operation", "Modifier");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_affaire.jsp");
        dispatcher.forward(request, response);
    }
}
