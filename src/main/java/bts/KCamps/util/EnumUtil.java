package bts.KCamps.util;

import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;

import java.util.HashSet;
import java.util.Set;

public class EnumUtil {

    public static Set<Interesting> getInterests(String[] interests) {
        Set<Interesting> newInterests = new HashSet<>();

        for (String interest : interests) {
            newInterests.add(Interesting.getByName(interest));
        }
        return newInterests;
    }

    public static Set<Location> getLocations(String[] locations) {
        Set<Location> newLocation = new HashSet<>();

        for (String local : locations) {
            newLocation.add(Location.getByName(local));
        }
        return newLocation;
    }

    public static Set<Childhood> getChildhoods(String[] childhoods) {
        Set<Childhood> newChildhoods = new HashSet<>();

        for (String hood : childhoods) {
            newChildhoods.add(Childhood.valueOf(hood));
        }
        return newChildhoods;
    }
}
