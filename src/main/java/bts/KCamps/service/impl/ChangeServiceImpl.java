package bts.KCamps.service.impl;

import bts.KCamps.exception.NotFoundException;
import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.repository.CampRepo;
import bts.KCamps.repository.ChangeRepo;
import bts.KCamps.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChangeServiceImpl implements ChangeService {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ChangeRepo changeRepo;
    private final CampRepo campRepo;

    @Transactional
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
            changeRepo.save(campChange);
        }
    }

    @Transactional
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

    @Override
    public CampChange getById(Long changeId) {
        return changeRepo.findById(changeId)
                .orElseThrow(() -> new NotFoundException(changeId.toString(), CampChange.class));

    }
}
