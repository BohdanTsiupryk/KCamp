package bts.KCamps.controllers;

import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.User;
import bts.KCamps.model.report.CampReport;
import bts.KCamps.model.report.ChangeReport;
import bts.KCamps.service.ReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@PreAuthorize("hasAuthority('MODERATOR')")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/report/{change}")
    public String getChangeReport(
            @AuthenticationPrincipal User user,
            @PathVariable CampChange change,
            Model model) {
        if (user.getId() == change.getParentCamp().getAuthor().getId()) {

            ChangeReport changeReport = reportService.getChangesreport(change);

            model.addAttribute("campName", change.getParentCamp().getNameCamp());
            model.addAttribute("isCampReport", false);
            model.addAttribute("report", changeReport);

            return "report";
        } else {
            model.addAttribute("statusCode", 403);
            model.addAttribute("exception", "Відмовлено у доступі. Недостатньо прав.");

            return "/error";
        }
    }

    @GetMapping("/report/camp/{camp}")
    public String getCampReport(
            @AuthenticationPrincipal User user,
            @PathVariable Camp camp,
            Model model) {
        if (user.getId() == camp.getAuthor().getId()) {
            CampReport campReport = reportService.getCampReport(camp);

            model.addAttribute("campName", camp.getNameCamp());
            model.addAttribute("isCampReport", true);
            model.addAttribute("report", campReport);

            return "report";
        } else {
            model.addAttribute("statusCode", 403);
            model.addAttribute("exception", "Відмовлено у доступі. Недостатньо прав.");

            return "/error";
        }
    }
}
