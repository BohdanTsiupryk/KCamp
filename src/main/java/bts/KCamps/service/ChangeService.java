package bts.KCamps.service;

import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.ChangeRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
public class ChangeService {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ChangeRepo changeRepo;
    private final CampRepo campRepo;

    public ChangeService(ChangeRepo changeRepo, CampRepo campRepo) {
        this.changeRepo = changeRepo;
        this.campRepo = campRepo;
    }

    public void addChange(long id, Map<String, String> map) {
        Optional<Camp> optionalCamp = campRepo.findById(id);
        if (optionalCamp.isPresent()) {
            Camp camp = optionalCamp.get();

            LocalDate begin = LocalDate.parse(map.get("begin"), formatter);
            LocalDate end = LocalDate.parse(map.get("end"), formatter);
            String description = map.get("description");
            int number = Integer.parseInt(map.get("number"));
            int price = Integer.parseInt(map.get("price"));
            int places = Integer.parseInt(map.get("places"));

            CampChange campChange = new CampChange(number, description, places, places, begin, end, price, camp);
            camp.getChanges().add(campChange);
            campRepo.save(camp);
        }
    }

    public void updateChange(long id, Map<String, String> map) {
        Optional<CampChange> optionalChange = changeRepo.findById(id);
        if (optionalChange.isPresent()) {
            CampChange change = optionalChange.get();

            change.setPrice(Integer.parseInt(map.get("price")));
            change.setDescription(map.get("description"));
            change.setBeginDate(LocalDate.parse(map.get("beginDate"), formatter));
            change.setEndDate(LocalDate.parse(map.get("endDate"), formatter));
            change.setFreePlace(Integer.parseInt(map.get("places")) - (change.getPlaces() - change.getFreePlace()));
            change.setPlaces(Integer.parseInt(map.get("places")));

            changeRepo.save(change);
        }
    }
}
