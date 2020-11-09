package bts.KCamps.controllers;

import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.Comment;
import bts.KCamps.model.User;
import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.CommentsRepo;
import bts.KCamps.repository.UserRepo;
import bts.KCamps.service.CampService;
import bts.KCamps.service.GoogleCallService;
import bts.KCamps.service.UserService;
import bts.KCamps.service.impl.CampServiceImpl;
import bts.KCamps.util.ControllerUtil;
import bts.KCamps.util.EnumUtil;
import lombok.RequiredArgsConstructor;
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

@Controller
@RequiredArgsConstructor
public class CampController {
    private final CampService campService;
    private final UserService userService;
    private final CommentsRepo commentsRepo;
    private final GoogleCallService googleCallService;

    @Value("${google.apiKey}")
    private String apiKey;

    @GetMapping("/camp/profile/{campId}")
    public String getCampProfile(@PathVariable Long campId, Model model) {
        Camp camp = campService.getById(campId);
        Set<CampChange> changes = new HashSet<>(camp.getChanges());
        Set<Comment> allByCamp = commentsRepo.findAllByCampId(campId);
        camp.setComments(allByCamp);
        model.addAttribute("camp", camp);
        model.addAttribute("changes", changes);
        model.addAttribute("apiKey", apiKey);

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
    @GetMapping("/camp/{campId}")
    public String editCampForm(
            @AuthenticationPrincipal User user,
            @PathVariable Long campId,
            Model model) {
        Camp camp = campService.getById(campId);
        if (camp.getAuthor().getId() != user.getId()) {
            return "redirect:/profile";
        }

        model.addAttribute("camp", camp);

        return "campEdit";
    }

    @PreAuthorize("hasAuthority('MODERATOR')")
    @GetMapping("/camp/delete/{campId}")
    public String deleteCamp(@AuthenticationPrincipal User user, @PathVariable Long campId) {
        Camp camp = campService.getById(campId);

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
            Camp camp,
            BindingResult bindingResult,
            Model model,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtil.getErrorMap(bindingResult);
            model.mergeAttributes(errorMap);
            model.addAttribute("campError", true);
        } else {
            camp.setInteresting(EnumUtil.getInterests(interests));
            camp.setLocations(EnumUtil.getLocations(locations));
            camp.setChildhoods(EnumUtil.getChildhoods(childhoods));
            String[] campCoordinationByAddress = googleCallService.getCampCoordinationByAddress(camp.getAddress());
            camp.setLongitude(campCoordinationByAddress[0].replace(",", "."));
            camp.setLatitude(campCoordinationByAddress[1].replace(",", "."));
            campService.addCamp(camp, user, image);
            model.addAttribute("campError", false);
            model.addAttribute("message", "Табір успішно доданий");
        }

        List<Camp> userCamps = campService.getAllByAuthor(user);
        User userFromDb = userService.findById(user.getId());

        model.addAttribute("userFormDb", userFromDb);
        model.addAttribute("userCamps", userCamps);
        ControllerUtil.addTags(model);

        return "/profile";
    }
}
