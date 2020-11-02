package bts.KCamps.service;

import bts.KCamps.model.CampChange;

import java.util.Map;

public interface ChangeService {

    void addChange(long id, Map<String, String> map);

    void updateChange(long id, Map<String, String> map);

    CampChange getById(Long changeId);
}
