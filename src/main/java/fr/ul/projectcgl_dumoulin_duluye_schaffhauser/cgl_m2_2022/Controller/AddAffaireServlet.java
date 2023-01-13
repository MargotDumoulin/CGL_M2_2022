package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.AffaireDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
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

        String affId = request.getParameter("affId");

        if (affId != null && affId.matches("-?\\d+(\\.\\d+)?")) {
            AffaireEntity affaire = AffaireDAO.getInstance().getById(Long.parseLong(affId));
            request.setAttribute("affId", affId);
            request.setAttribute("montant", affaire.getCommissionGlobale());
            request.setAttribute("date", affaire.getDate());
            request.setAttribute("apporteurs", Collections.singletonList(affaire.getApporteur()));
            System.out.println("on passe ICI DONC EN MODIFIER !!");
            request.setAttribute("operation", "Modifier");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("add_affaire.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AffaireEntity affaire = new AffaireEntity();
            Long affId = (request.getParameter("affId") != null) ? Long.parseLong(request.getParameter("affId")) : null;
            Double montant = !request.getParameter("montant").equals("") ? Double.parseDouble(request.getParameter("montant")) : null;
            Date date = !request.getParameter("date").equals("")  ? new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("date")) : null;
            Long apporteurId = request.getParameter("apporteur").equals("") ? -1 : Long.parseLong(request.getParameter("apporteur"));

            request.setAttribute("montant", montant);
            request.setAttribute("date", date);
            request.setAttribute("affId", affId);


            if (affId != null) request.setAttribute("operation", "Modifier");
            else request.setAttribute("operation", "Ajouter");

            // Validate the input
            if (montant == null)
                request.setAttribute("errorMessageMontant", "Le montant est obligatoire");
            if (date == null)
                request.setAttribute("errorMessageDate", "La date est obligatoire");
            if (apporteurId == -1)
                request.setAttribute("errorMessageApporteur", "Le champ apporteur est obligatoire");

            if (request.getAttribute("errorMessageMontant") != null
                    || request.getAttribute("errorMessageDate") != null
                    || request.getAttribute("errorMessageApporteur") != null) {

                Stream<ApporteurEntity> apporteurs = ApporteurDAO.getInstance().getAll();
                request.setAttribute("apporteurs", apporteurs.toArray());
                RequestDispatcher dispatcher = request.getRequestDispatcher("add_affaire.jsp");
                dispatcher.forward(request, response);
                return;
            }

            affaire.setId(affId);
            affaire.setDate(date);
            affaire.setApporteur(ApporteurDAO.getInstance().getById(apporteurId));
            affaire.setCommissionGlobale(montant);

            if (affaire.getId() == null) {
                AffaireDAO.getInstance().insert(affaire);
            } else {
                AffaireDAO.getInstance().update(affaire);
            }

            response.sendRedirect("affaires");
        } catch (ParseException error) {
            System.out.println("il y a ERREEUUUR");
        }

    }
}
