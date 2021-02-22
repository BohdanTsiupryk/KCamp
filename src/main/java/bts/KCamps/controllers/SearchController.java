package bts.KCamps.controllers;

import bts.KCamps.dto.QuestionnaireDto;
import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import bts.KCamps.model.Camp;
import bts.KCamps.model.GoogleCampCoordinate;
import bts.KCamps.service.CampService;
import bts.KCamps.service.SearchService;
import bts.KCamps.util.ControllerUtil;
import bts.KCamps.util.EnumUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;
    private final CampService campService;

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
            model.addAttribute("empty", true);
            return "search";
        }

        List<GoogleCampCoordinate> coords = camps.stream()
                .map(Camp::getCoordinate)
                .collect(Collectors.toList());

        model.addAttribute("camps", camps);
        model.addAttribute("coords", coords);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("empty", false);
        ControllerUtil.addTags(model);
        return "search";
    }

    @PostMapping(value = "/questionnaire")
    public String handleQuestionnaire(@ModelAttribute QuestionnaireDto questionnaireDto, Model model) {
        Set<Interesting> interestingSet = questionnaireDto.getInterests() == null ? new HashSet<>() : EnumUtil.getInterests(questionnaireDto.getInterests());
        Set<Location> locationSet = questionnaireDto.getLocations() == null ? new HashSet<>() : EnumUtil.getLocations(questionnaireDto.getLocations());
        Set<Childhood> childhoodSet = questionnaireDto.getChildhoods() == null ? new HashSet<>() : EnumUtil.getChildhoods(questionnaireDto.getChildhoods());

        List<Camp> byParameters = searchService.findByParameters(questionnaireDto.getKeyWords());
        List<Camp> byLocation = searchService.findByLocation(questionnaireDto.getAddress(), questionnaireDto.getMaxDistance());
        List<Camp> byTags = campService.findByTags(locationSet, interestingSet, childhoodSet);

        byParameters.retainAll(byLocation);
        byParameters.retainAll(byTags);

        if (byParameters.isEmpty()) {
            model.addAttribute("empty", true);
            return "search";
        }

        List<GoogleCampCoordinate> coords = byParameters.stream()
                .map(Camp::getCoordinate)
                .collect(Collectors.toList());

        model.addAttribute("camps", byParameters);
        model.addAttribute("coords", coords);
        model.addAttribute("apiKey", apiKey);
        model.addAttribute("empty", false);
        ControllerUtil.addTags(model);
        return "search";
    }
}
