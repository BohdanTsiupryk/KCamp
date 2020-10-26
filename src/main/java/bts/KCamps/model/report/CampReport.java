package bts.KCamps.model.report;

import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class CampReport {
    private List<ChangeReport> changeReportList = new ArrayList<>();
    private Map<String, String> campInfo = new LinkedHashMap<>();
    private Set<Interesting> interests;
    private Set<Location> locations;
    private Set<Childhood> childhoods;

    public void addInfo(String key, String value) {
        campInfo.put(key, value);
    }
}
