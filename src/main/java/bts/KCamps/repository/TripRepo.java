package bts.KCamps.repository;

import bts.KCamps.model.BoughtTrip;
import bts.KCamps.model.CampChange;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepo extends CrudRepository<BoughtTrip, Long> {
    List<BoughtTrip> findAllByChange(CampChange change);
}
