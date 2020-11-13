package bts.KCamps.repository;

import bts.KCamps.dto.CampIdDescriptionDto;
import bts.KCamps.dto.CampIdLocationDto;

import java.util.List;

public interface CustomCampRepo<T> {

    List<CampIdDescriptionDto> getDescriptions();

    List<CampIdLocationDto> getCampsCoordinate();
}
