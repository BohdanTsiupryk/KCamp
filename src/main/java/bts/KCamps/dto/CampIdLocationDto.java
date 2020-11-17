package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampIdLocationDto {
    private long campId;
    private double lat;
    private double lng;

    public CampIdLocationDto(long campId, String lat, String lng) {
        this.campId = campId;
        this.lat = Double.parseDouble(lat);
        this.lng = Double.parseDouble(lng);
    }
}
