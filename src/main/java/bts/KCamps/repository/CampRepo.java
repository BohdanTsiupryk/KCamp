package bts.KCamps.repository;

import bts.KCamps.model.Camp;
import bts.KCamps.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CampRepo extends CrudRepository<Camp, Long> {
    List<Camp> findAllByAuthor(User user);
}