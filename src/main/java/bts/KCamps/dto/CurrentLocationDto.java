package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentLocationDto {
    private double lat;
    private double lng;
    private int maxDistance;
}
