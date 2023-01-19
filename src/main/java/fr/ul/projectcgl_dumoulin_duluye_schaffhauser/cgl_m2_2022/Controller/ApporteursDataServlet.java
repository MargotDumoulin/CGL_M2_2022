package fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Controller;

import com.google.gson.Gson;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.ApporteurDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.DAO.CommissionDAO;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Entity.ApporteurEntity;
import fr.ul.projectcgl_dumoulin_duluye_schaffhauser.cgl_m2_2022.Model.Apporteur;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@WebServlet(

        name = "ApporteursData",
        urlPatterns = {"/apporteurs-data"}
)
public class ApporteursDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        // Get pagination parameters
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int pageSize = Integer.parseInt(request.getParameter("length"));

        // Get orderable parameters
        String columns[] = {"id", "nom", "prenom", "affilie", "totalCommissionsMCourant", "totalCommissionsMM1", "totalCommissionsMM2"};
        int columnToOrder = Integer.parseInt(request.getParameter("order[0][column]"));
        String orderDirection = request.getParameter("order[0][dir]");

        // Sort by id desc by default
        if (columns[columnToOrder].length() == 0) {
            columnToOrder = 1;
            orderDirection = "asc";
        }

        // Get and initialize apporteurs
        List<Apporteur> apporteurs = new ArrayList<>();
        Stream<ApporteurEntity> apporteursEntities = ApporteurDAO.getInstance().getAll(pageSize, start, columns[columnToOrder], orderDirection);
        long numberOfResults = ApporteurDAO.getInstance().getAllNbOfResults();

        apporteursEntities.forEach(s -> {
            LocalDate currentDate = LocalDate.now();
            LocalDate m1Date = LocalDate.from(currentDate).minusMonths(1);
            LocalDate m2Date = LocalDate.from(currentDate).minusMonths(2);

            Double resMM1 = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(m1Date.getMonthValue(), m1Date.getYear(), s.getId());
            Double resMM2 = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(m2Date.getMonthValue(), m2Date.getYear(), s.getId());
            Double resMC = CommissionDAO.getInstance().getTotalByMonthAndApporteurId(currentDate.getMonthValue(), currentDate.getYear(), s.getId());
            Boolean isAffilie = ApporteurDAO.getInstance().getIsAffilie(s.getId());

            apporteurs.add(new Apporteur(s.getId(), isAffilie, s.getNom(), s.getPrenom(), s.isDeleted(), (resMC != null ? resMC : 0D), (resMM1 != null ? resMM1 : 0D), (resMM2 != null ? resMM2 : 0D)));
        });

        // Send response
        String jsonApporteurs = new Gson().toJson(apporteurs);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"draw\":" + draw + ",\"data\":" + jsonApporteurs + ", \"recordsTotal\":" + numberOfResults + ",\"recordsFiltered\":" + numberOfResults + "}");
    }
}
