package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "Apporteurs",
        urlPatterns = {"/apporteurs"}
)
public class ApporteursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Apporteur> apporteurs = new ArrayList<>();

        Apporteur app1 = new Apporteur(0L, true, "DUMOULIN", "Margot");
        Apporteur app2 = new Apporteur(1L, false, "DULUYE", "Antony");

        apporteurs.add(app1);
        apporteurs.add(app2);

        request.setAttribute("apporteurs", apporteurs);
        request.getRequestDispatcher("/apporteurs.jsp").forward(request, response);
    }
}
