package bts.KCamps.repository;

import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChangeRepo extends CrudRepository<CampChange, Long> {
    List<CampChange> findAllByParentCamp(Camp camp);
}
