package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentLocationDto {
    private double lat;
    private double lng;
    private int maxDistance;

    public CurrentLocationDto(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
