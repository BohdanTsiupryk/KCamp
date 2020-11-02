package bts.KCamps.service;

import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.CommentsRepo;
import bts.KCamps.repository.UserRepo;
import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import bts.KCamps.model.Camp;
import bts.KCamps.model.Comment;
import bts.KCamps.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CampService {
    private final CampRepo campRepo;
    private final UserRepo userRepo;
    private final CommentsRepo commentsRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public void deleteCamp(Camp camp) {
        campRepo.delete(camp);
    }

    public void updateCamp(Map<String, String> form, User user, MultipartFile image) throws IOException {
        Optional<Camp> campFromDb = campRepo.findById(Long.valueOf(form.get("id")));

        if (campFromDb.isPresent()) {
            Camp camp = campFromDb.get();
            if (camp.getAuthor().getId() == user.getId()) {

                camp.setNameCamp(form.get("campName"));
                camp.setDescription(form.get("description"));
                setNewMainPicture(camp, image);
                campRepo.save(camp);
            }
        }
    }

    public List<Camp> findSortedByRating() {
        return StreamSupport.stream(campRepo.findAll().spliterator(), false)
                .sorted(Comparator.comparingDouble(Camp::getRating).reversed())
                .collect(Collectors.toList());
    }

    public void addComment(long campId, User user, String comment, String rate) {
        Optional<Camp> optionalCamp = campRepo.findById(campId);

        if (optionalCamp.isPresent()) {
            Camp camp = optionalCamp.get();
            Comment com = new Comment(Integer.valueOf(rate), comment, user.getUsername(), camp);
            camp.setNewRating(Integer.valueOf(rate));
            campRepo.save(camp);
            commentsRepo.save(com);
        }
    }

    public void addCamp(Camp camp,
                        User user,
                        MultipartFile image
    ) throws IOException {

        camp.setAuthor(user);
        setNewMainPicture(camp, image);

        campRepo.save(camp);
    }

    public List<Camp> findByTag(Location location, Interesting interest, Childhood childhood) {
        Iterable<Camp> all = campRepo.findAll();
        List<Camp> res = new ArrayList<>();

        for (Camp camp : all) {
            if (camp.getLocations().contains(location)) {
                res.add(camp);
            } else if (camp.getInteresting().contains(interest)) {
                res.add(camp);
            }   else if (camp.getChildhoods().contains(childhood)) {
                res.add(camp);
            }
        }
        return res;
    }

    public List<Camp> findByTags(Set<Location> locations, Set<Interesting> interests, Set<Childhood> childhoods) {
        Iterable<Camp> all = campRepo.findAll();

        return StreamSupport.stream(all.spliterator(), false)
                .filter(o -> o.getLocations().containsAll(locations))
                .filter(o -> o.getInteresting().containsAll(interests))
                .filter(o -> o.getChildhoods().containsAll(childhoods))
                .collect(Collectors.toList());
    }

    public void addNewPhoto(Long id, Set<MultipartFile> photos) throws IOException {
        Optional<Camp> campFromDb = campRepo.findById(id);

        if (campFromDb.isPresent()) {
            Camp camp = campFromDb.get();
            for (MultipartFile photo : photos) {
                camp.getCampPhotos().add(uploadImage(photo));
            }
            campRepo.save(camp);
        }
    }

    private void setNewMainPicture(Camp camp, MultipartFile image) throws IOException {
        if (image != null && !image.getOriginalFilename().isEmpty()) {
            camp.setMainPicName(uploadImage(image));
        }
    }

    private String uploadImage(MultipartFile image) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + image.getOriginalFilename();

        image.transferTo(new File(uploadPath + "/" + resultFileName));
        return resultFileName;
    }
}
