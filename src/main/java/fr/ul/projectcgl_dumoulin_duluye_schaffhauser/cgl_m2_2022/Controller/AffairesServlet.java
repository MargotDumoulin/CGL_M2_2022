package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Affaire;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.*;

@WebServlet(
        name = "Affaires",
        urlPatterns = {"/affaires"}
)
public class AffairesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Apporteur app1 = new Apporteur(0L, true, "DUMOULIN", "Margot");
        Apporteur app2 = new Apporteur(1L, true, "DULUYE", "Antony");
        Map<Apporteur, Double> commissionsPerso = new HashMap<>();
        commissionsPerso.put(app1, 95.0);
        commissionsPerso.put(app2, 5.0);
        Affaire aff1 = new Affaire(0L, app1, new Date(), 100D, commissionsPerso);
        Affaire aff2 = new Affaire(1L, app2, new Date(), 100D, commissionsPerso);
        List<Affaire> affaires = new ArrayList<Affaire>();

        String appId = request.getParameter("appId");

        // Pour l'instant je mets juste en place la condition pour filtrer par id d'apporteur plus tard
        if (appId != null && appId.matches("-?\\d+(\\.\\d+)?")) {
            affaires.add(aff1);
        } else {
            affaires.add(aff1);
            affaires.add(aff2);
        }

        request.setAttribute("affaires", affaires);
        request.getRequestDispatcher("/affaires.jsp").forward(request, response);
    }
}
