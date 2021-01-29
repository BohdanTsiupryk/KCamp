package bts.KCamps.service.impl;

import bts.KCamps.dto.CampIdDescriptionDto;
import bts.KCamps.dto.CampIdLocationDto;
import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import bts.KCamps.exception.NotFoundException;
import bts.KCamps.model.Camp;
import bts.KCamps.model.Comment;
import bts.KCamps.model.GoogleCampCoordinate;
import bts.KCamps.model.User;
import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.CommentsRepo;
import bts.KCamps.service.AmazonClient;
import bts.KCamps.service.CampService;
import bts.KCamps.service.utilService.GoogleCallService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CampServiceImpl implements CampService {
    private final CampRepo campRepo;
    private final CommentsRepo commentsRepo;
    private final GoogleCallService callService;
    private final AmazonClient amazonClient;

    @Transactional
    @CacheEvict({"description", "location"})
    public void deleteCamp(Camp camp) {
        campRepo.delete(camp);
        camp.getCampPhotos()
                .forEach(url -> amazonClient.deleteFileFromS3Bucket(url));
        amazonClient.deleteFileFromS3Bucket(camp.getMainPicName());
    }

    @Transactional
    @CacheEvict({"description", "location"})
    public void updateCamp(Map<String, String> form, User user, MultipartFile image) {
        Optional<Camp> campFromDb = campRepo.findById(Long.valueOf(form.get("id")));

        if (campFromDb.isPresent()) {
            Camp camp = campFromDb.get();
            if (camp.getAuthor().getId() == user.getId()) {
                camp.setNameCamp(form.get("campName"));
                camp.setAddress(form.get("address"));
                camp.setDescription(form.get("description"));
                camp.setCoordinate(callService.getCampCoordinate(camp));
                setNewMainPicture(camp, image);
                campRepo.save(camp);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Camp> findSortedByRating() {
        return campRepo.findAll().stream()
                .sorted(Comparator.comparingDouble(Camp::getRating).reversed())
                .collect(Collectors.toList());
    }

    @Transactional
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

    @Transactional
    @CacheEvict({"description", "location"})
    public void addCamp(Camp camp, User user, MultipartFile image) {
        camp.setAuthor(user);
        setNewMainPicture(camp, image);
        GoogleCampCoordinate campCoordinate = callService.getCampCoordinate(camp);
        camp.setCoordinate(campCoordinate);
        campRepo.save(camp);
    }

    @Transactional(readOnly = true)
    public List<Camp> findByTag(Location location, Interesting interest, Childhood childhood) {
        List<Camp> all = campRepo.findAll();

        return all.stream()
                .filter(camp -> camp.getLocations().contains(location))
                .filter(camp -> camp.getChildhoods().contains(childhood))
                .filter(camp -> camp.getInteresting().contains(interest))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Camp> findByTags(Set<Location> locations, Set<Interesting> interests, Set<Childhood> childhoods) {
        List<Camp> all = campRepo.findAll();

        return all.stream()
                .filter(o -> o.getLocations().containsAll(locations))
                .filter(o -> o.getInteresting().containsAll(interests))
                .filter(o -> o.getChildhoods().containsAll(childhoods))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addNewPhoto(Long id, Set<MultipartFile> photos) {
        Optional<Camp> campFromDb = campRepo.findById(id);

        if (campFromDb.isPresent()) {
            Camp camp = campFromDb.get();
            for (MultipartFile photo : photos) {
                camp.getCampPhotos().add(uploadImage(photo));
            }
            campRepo.save(camp);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Camp getById(Long campId) {
        return campRepo.findById(campId)
                .orElseThrow(() -> new NotFoundException(campId.toString(), Camp.class));
    }

    @Override
    public List<Camp> getAllByAuthor(User user) {
        return campRepo.findAllByAuthor(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampIdDescriptionDto> getAllDescriptions() {
        return campRepo.getDescriptions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CampIdLocationDto> getAllLocations() {
        return campRepo.getCampsCoordinate();
    }

    @Override
    public List<Camp> findByIds(List<Long> ids) {
        return campRepo.findAllByIdIn(ids);
    }

    private void setNewMainPicture(Camp camp, MultipartFile image) {
        if (image != null && !image.getOriginalFilename().isEmpty()) {
            camp.setMainPicName(uploadImage(image));
        }
    }

    private String uploadImage(MultipartFile image) {
        return amazonClient.uploadFile(image);
    }
}
