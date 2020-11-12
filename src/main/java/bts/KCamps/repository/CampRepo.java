package bts.KCamps.repository;

import bts.KCamps.model.Camp;
import bts.KCamps.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CampRepo extends CrudRepository<Camp, Long>, CustomCampRepo<Camp> {
    List<Camp> findAllByAuthor(User user);

    Optional<Camp> findById(Long id);

    List<Camp> findAll();

    List<Camp> findAllByIdIn(List<Long> ids);
}