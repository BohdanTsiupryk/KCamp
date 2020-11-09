package bts.KCamps.controllers;

import bts.KCamps.service.impl.ChangeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChangeController {

    private final ChangeServiceImpl changeService;

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/camp/change/add")
    public String addChange(@RequestParam Map<String, String> map ) throws NumberFormatException {
        long id = Long.parseLong(map.get("campId"));
        changeService.addChange(id, map);

        return "redirect:/camp/profile/" + id;
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/camp/change/update")
    public String updateChange(@RequestParam Map<String, String> map) throws NumberFormatException {
        long id = Long.parseLong(map.get("changeId"));
        changeService.updateChange(id, map);

        return "redirect:/camp/profile/" + map.get("campId");
    }
}
