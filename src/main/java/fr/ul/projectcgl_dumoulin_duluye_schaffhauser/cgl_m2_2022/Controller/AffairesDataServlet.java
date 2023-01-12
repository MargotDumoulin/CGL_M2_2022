package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import com.google.gson.Gson;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.AffaireDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.AffaireEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Affaire;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.CommissionPerso;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(

        name = "AffairesData",
        urlPatterns = {"/affaires-data"}
)
public class AffairesDataServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get pagination parameters
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int pageSize = Integer.parseInt(request.getParameter("length"));

        // Get and initialize affaires
        List<Affaire> affaires = new ArrayList<>();
        Stream<AffaireEntity> affairesEntities;
        String appId = request.getParameter("appId");
        Long numberOfResults;
        
        if (appId != null && appId.matches("-?\\d+(\\.\\d+)?")) {
            affairesEntities = AffaireDAO.getInstance().getAllByApporteurId(pageSize, start, Long.parseLong(appId));
            numberOfResults = AffaireDAO.getInstance().getAllByApporteurId(Long.parseLong(appId)).count();
        } else {
            affairesEntities = AffaireDAO.getInstance().getAll(pageSize, start);
             numberOfResults = AffaireDAO.getInstance().getAll().count();
        }

        affairesEntities.forEach(a -> {
            ApporteurEntity appEnt = a.getApporteur();
            Apporteur apporteur = new Apporteur(appEnt.getId(), false, appEnt.getNom(), appEnt.getPrenom(), getParrain(appEnt.getParrain()));
            List<CommissionPerso> comList = CommissionDAO.getInstance().getCommissionsByAffaire(a.getId()).toList();

            affaires.add(new Affaire(a.getId(), apporteur, a.getDate(), a.getCommissionGlobale(), comList));
        });


        // Send response
        String jsonAffaires = new Gson().toJson(affaires);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"draw\":" + draw + ",\"data\":" + jsonAffaires + ", \"recordsTotal\":" + numberOfResults + ",\"recordsFiltered\":" + numberOfResults + "}");
    }

    private Apporteur getParrain(ApporteurEntity parrEnt) {
        if (parrEnt == null) {
            return null;
        }

        ApporteurEntity parrFromParrEnt = parrEnt.getParrain();
        if (parrFromParrEnt == null) {
            return new Apporteur(parrEnt.getId(), false, parrEnt.getNom(), parrEnt.getPrenom());
        }

        return new Apporteur(parrEnt.getId(), false, parrEnt.getNom(), parrEnt.getPrenom(), getParrain(parrFromParrEnt));
    }
}