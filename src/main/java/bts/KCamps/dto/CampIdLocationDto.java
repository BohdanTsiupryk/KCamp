package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CampIdLocationDto {
    private long campId;
    private float lat;
    private float lng;

    public CampIdLocationDto(long campId, String lat, String lng) {
        this.campId = campId;
        this.lat = Float.parseFloat(lat);
        this.lng = Float.parseFloat(lng);
    }
}
