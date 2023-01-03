package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Affaire;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet(
        name = "Apporteurs",
        urlPatterns = {"/apporteurs"}
)
public class ApporteursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Apporteur app1 = new Apporteur(0, true, "DUMOULIN", "Margot");
        Apporteur app2 = new Apporteur(1, false, "DULUYE", "Antony");
        List<Apporteur> apporteurs = new ArrayList<Apporteur>();
        apporteurs.add(app1);
        apporteurs.add(app2);

        request.setAttribute("apporteurs", apporteurs);
        request.getRequestDispatcher("/apporteurs.jsp").forward(request, response);
    }
}
