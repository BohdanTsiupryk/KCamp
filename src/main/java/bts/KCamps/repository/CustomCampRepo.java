package bts.KCamps.repository;

import bts.KCamps.dto.CampIdDescriptionDto;

import java.util.List;

public interface CustomCampRepo<T> {

    List<CampIdDescriptionDto> getDescriptions();
}
