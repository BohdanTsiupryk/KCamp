package bts.KCamps.util;

import bts.KCamps.dto.CampIdLocationDto;
import bts.KCamps.dto.CurrentLocationDto;
import org.junit.jupiter.api.Test;

class SearchUtilTest {

    @Test
    void getDistance() {
        CampIdLocationDto campLocation1 = new CampIdLocationDto(1, 77.1539, -139.398);
        CurrentLocationDto currentLocation1 = new CurrentLocationDto(-77.1804, -139.55, 1);

        CampIdLocationDto campLocation2 = new CampIdLocationDto(1, 77.1539, 120.398);
        CurrentLocationDto currentLocation2 = new CurrentLocationDto(77.1804, 129.55, 1);

        CampIdLocationDto campLocation3 = new CampIdLocationDto(1, 77.1539, -120.398);
        CurrentLocationDto currentLocation3 = new CurrentLocationDto(77.1804, 129.55, 1);

        long distance1 = SearchUtil.getDistance(campLocation1, currentLocation1);
        long distance2 = SearchUtil.getDistance(campLocation2, currentLocation2);
        long distance3 = SearchUtil.getDistance(campLocation3, currentLocation3);

        if (distance1 != 17166029L || distance2 != 225883 || distance3 != 2332669) {
            throw new RuntimeException();
        }
    }
}