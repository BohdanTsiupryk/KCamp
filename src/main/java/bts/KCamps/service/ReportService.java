package bts.KCamps.service;

import bts.KCamps.model.BoughtTrip;
import bts.KCamps.model.Camp;
import bts.KCamps.model.CampChange;
import bts.KCamps.model.Child;
import bts.KCamps.model.User;
import bts.KCamps.model.report.CampReport;
import bts.KCamps.model.report.ChangeReport;
import bts.KCamps.repository.TripRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TripRepo tripRepo;

    public CampReport getCampReport(Camp camp) {
        CampReport campReport = new CampReport();

        for (CampChange change : camp.getChanges()) {
            campReport.getChangeReportList().add(getChangesreport(change));
        }

        campReport.addInfo("Найменування табору", camp.getNameCamp());
        campReport.addInfo("Представник табору", camp.getAuthor().getUsername());
        campReport.addInfo("Рейтинг", String.valueOf(camp.getRating()));
        campReport.addInfo("Кількість доданих фотографій", String.valueOf(camp.getCampPhotos().size()));

        campReport.setInterests(camp.getInteresting());
        campReport.setChildhoods(camp.getChildhoods());
        campReport.setLocations(camp.getLocations());

        return campReport;
    }

    public ChangeReport getChangesreport(CampChange change) {
        ChangeReport changeReport = new ChangeReport();

        Map<String, String> changeMap = new LinkedHashMap<>();
        List<Child> childList = new ArrayList<>();

        int places = change.getPlaces();
        int freePlace = change.getFreePlace();
        int price = change.getPrice();

        changeMap.put("Всього місць", String.valueOf(places));
        changeMap.put("Вільних місць", String.valueOf(freePlace));
        changeMap.put("Куплено місць", String.valueOf(places - freePlace));
        changeMap.put("Ціна за місце", String.valueOf(price));
        changeMap.put("Зароблено", String.valueOf(price * (places - freePlace)));

        List<BoughtTrip> boughtTrips = tripRepo.findAllByChange(change);

        boughtTrips.stream()
                .map(BoughtTrip::getChild)
                .forEach(childList::addAll);

        Map<String, Long> childCitizenships = new LinkedHashMap<>(
                childList.stream()
                .map(Child::getCitizenship)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        Map<Integer, Long> childAge = new LinkedHashMap<>(
                childList.stream()
                .map(Child::getAge)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        Map<String, Long> userCity = new LinkedHashMap<>(
                boughtTrips.stream()
                .map(BoughtTrip::getOwner)
                .map(User::getCity)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        long sum = cityCounter(userCity);

        Map<String, Double> diagram = new HashMap<>();
        for (String s : userCity.keySet()) {
            double val = (double) userCity.get(s) / sum * 100;
            diagram.put(s, (double) Math.round(val * 10d) / 10d);
        }

        changeReport.setDiagram(diagram);
        changeReport.setUserCity(userCity);
        changeReport.setCitizenship(childCitizenships);
        changeReport.setInfo(changeMap);
        changeReport.setChildAge(childAge);

        return changeReport;
    }

    private long cityCounter(Map<String, Long> userCity) {
        long sum = 0;
        for (String s : userCity.keySet()) {
            sum += userCity.get(s);
        }
        return sum;
    }
}
