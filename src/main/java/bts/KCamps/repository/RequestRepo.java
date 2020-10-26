package bts.KCamps.repository;

import bts.KCamps.model.ModeratorRequest;
import org.springframework.data.repository.CrudRepository;

public interface RequestRepo extends CrudRepository<ModeratorRequest, Long> {
}
