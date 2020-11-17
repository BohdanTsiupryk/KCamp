package bts.KCamps.util;

import bts.KCamps.dto.CampIdLocationDto;
import bts.KCamps.dto.CurrentLocationDto;

import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class SearchUtil {

    public static long getDistance(CampIdLocationDto campLocation, CurrentLocationDto currentLocation) {
        double lat1 = toRad(currentLocation.getLat());
        double lng1 = toRad(currentLocation.getLng());
        double lat2 = toRad(campLocation.getLat());
        double lng2 = toRad(campLocation.getLng());

        double delta = lng2 - lng1;

        double cl1 = cos(lat1);
        double cl2 = cos(lat2);
        double deltaCos = cos(delta);

        double sl2 = sin(lat2);
        double sl1 = sin(lat1);
        double deltaSin = sin(delta);

        double y = sqrt(pow(cl2 * deltaSin, 2) + pow(cl1 * sl2 - sl1 * cl2 * deltaCos, 2));
        double x = sl1 * sl2 + cl1 * cl2 * deltaCos;

        return Math.round(atan2(y, x) * 6372795);
    }

    private static double toRad(double rad) {
        return rad * PI / 180;
    }

}
