package bts.KCamps.controllers;

import bts.KCamps.dto.CurrentLocationDto;
import bts.KCamps.model.Camp;
import bts.KCamps.model.GoogleCampCoordinate;
import bts.KCamps.service.SearchService;
import bts.KCamps.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @Value("${google.apiKey}")
    private String apiKey;

    @GetMapping
    public String searchFromCamps(@RequestParam String query, Model model) {
        List<Camp> camps = searchService.findByParameters(query);
        model.addAttribute("camps", camps);
        ControllerUtil.addTags(model);
        return "index";
    }

    @PostMapping("/location")
    public String searchLocation(String address, int maxDistance, Model model) {
        List<Camp> camps = searchService.findByLocation(address, maxDistance);
        if (camps.isEmpty()) {
            return "redirect:index";
        }

        List<GoogleCampCoordinate> coords = camps.stream().map(Camp::getCoordinate).collect(Collectors.toList());

        model.addAttribute("camps", camps);
        model.addAttribute("coords", coords);
        model.addAttribute("apiKey", apiKey);
        ControllerUtil.addTags(model);
        return "search";
    }
}
