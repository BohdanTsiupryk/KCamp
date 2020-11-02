package bts.KCamps.controllers;

import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.UserRepo;
import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.User;
import bts.KCamps.service.CampService;
import bts.KCamps.util.ControllerUtil;
import bts.KCamps.util.EnumUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Controller
public class CampController {
    private final CampRepo campRepo;
    private final UserRepo userRepo;
    private final CampService campService;

    @Value("${upload.path}")
    private String uploadPath;

    public CampController(CampRepo campRepo, UserRepo userRepo, CampService campService) {
        this.campRepo = campRepo;
        this.userRepo = userRepo;
        this.campService = campService;
    }

    @GetMapping("/camp/profile/{camp}")
    public String getCampProfile(@PathVariable Camp camp, Model model) {
        model.addAttribute("camp", camp);
        Set<CampChange> changes = new HashSet<>(camp.getChanges());
        model.addAttribute("changes", changes);

        return "campProfile";
    }

    @PostMapping("/add/comment")
    public String addComment(
            @AuthenticationPrincipal User user,
            @RequestParam Map<String, String> form) {
        String rate = form.get("rate");
        String comment = form.get("message");
        long campId = Long.parseLong(form.get("campId"));

        campService.addComment(campId, user, comment, rate);

        return "redirect:/camp/profile/" + campId;
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/camp/{camp}")
    public String editCampForm(
            @AuthenticationPrincipal User user,
            @PathVariable Camp camp,
            Model model
    ) {
        if (camp.getAuthor().getId() != user.getId()) {
            return "redirect:/profile";
        }

        model.addAttribute("camp", camp);

        return "campEdit";
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/camp/delete/{camp}")
    public String deleteCamp(@AuthenticationPrincipal User user, @PathVariable Camp camp) {
        if (camp.getAuthor().getId() != user.getId()) {
            return "redirect:/profile";
        }
        campService.deleteCamp(camp);

        return "redirect:/profile";
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/camp/update")
    public String updateCamp(
            @AuthenticationPrincipal User user,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "photos", required = false) Set<MultipartFile> photos,
            @RequestParam Map<String, String> form
    ) throws IOException {

        if (!photos.isEmpty()) {
            campService.addNewPhoto(Long.valueOf(form.get("id")), photos);
        }
        campService.updateCamp(form, user, image);

        return "redirect:/profile";
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @PostMapping("/camp/add")
    public String addCamp(
            @RequestParam("interestsf") String[] interests,
            @RequestParam("locationsf") String[] locations,
            @RequestParam("childhoodsf") String[] childhoods,
            @AuthenticationPrincipal User user,
            @Valid Camp camp,
            BindingResult bindingResult,
            Model model,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtil.getErrorMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("campError", true);
        } else {
            camp.setInteresting(EnumUtil.getInterests(interests));
            camp.setLocations(EnumUtil.getLocations(locations));
            camp.setChildhoods(EnumUtil.getChildhoods(childhoods));
            campService.addCamp(camp, user, image);
            model.addAttribute("campError", false);
            model.addAttribute("message", "Табір успішно доданий");
        }

        List<Camp> userCamps = campRepo.findAllByAuthor(user);
        Optional<User> userFromDb = userRepo.findById(user.getId());

        userFromDb.ifPresent(value -> model.addAttribute("userFormDb", value));

        model.addAttribute("userCamps", userCamps);
        ControllerUtil.addTags(model);

        return "/profile";
    }
}
