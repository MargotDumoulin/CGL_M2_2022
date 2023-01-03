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

        Apporteur app = new Apporteur(0, true, "DUMOULIN", "Margot");
        Map<Integer, Double> commissionsPerso = new HashMap<Integer, Double>();
        commissionsPerso.put(0, 95.0);
        commissionsPerso.put(1, 5.0);
        Affaire aff = new Affaire(0, app, new Date(), 100, commissionsPerso);
        List<Affaire> affaires = new ArrayList<Affaire>();
        affaires.add(aff);

        request.setAttribute("affaires", affaires);
        request.getRequestDispatcher("/affaires.jsp").forward(request, response);
    }
}
