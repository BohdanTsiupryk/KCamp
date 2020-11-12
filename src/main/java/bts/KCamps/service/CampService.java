package bts.KCamps.service;

import bts.KCamps.dto.CampIdDescriptionDto;
import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import bts.KCamps.model.Camp;
import bts.KCamps.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CampService {
    void deleteCamp(Camp camp);

    void updateCamp(Map<String, String> form, User user, MultipartFile image) throws IOException;

    List<Camp> findSortedByRating();

    void addComment(long campId, User user, String comment, String rate);

    void addCamp(Camp camp, User user, MultipartFile image) throws IOException;

    List<Camp> findByTag(Location location, Interesting interest, Childhood childhood);

    List<Camp> findByTags(Set<Location> locations, Set<Interesting> interests, Set<Childhood> childhoods);

    void addNewPhoto(Long id, Set<MultipartFile> photos) throws IOException;

    Camp getById(Long campId);

    List<Camp> getAllByAuthor(User user);

    List<CampIdDescriptionDto> getAllDescriptions();

    List<Camp> findByIds(List<Long> ids);
}
