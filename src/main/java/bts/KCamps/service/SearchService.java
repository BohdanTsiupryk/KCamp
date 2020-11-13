package bts.KCamps.service;

import bts.KCamps.dto.CurrentLocationDto;
import bts.KCamps.model.Camp;

import java.util.List;

public interface SearchService {
    List<Camp> findByParameters(String param);

    List<Camp> findByLocation(CurrentLocationDto currentLocation);
}
