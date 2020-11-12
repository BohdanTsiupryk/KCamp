package bts.KCamps.controllers;

import bts.KCamps.model.Camp;
import bts.KCamps.service.SearchService;
import bts.KCamps.util.ControllerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping
    public String searchFromCamps(@RequestParam String query, Model model) {
        List<Camp> camps = searchService.findByParameters(query);
        model.addAttribute("camps", camps);
        ControllerUtil.addTags(model);
        return "index";
    }
}
