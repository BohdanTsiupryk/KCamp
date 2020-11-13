package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentLocationDto {
    private float lat;
    private float lng;
    private int maxDistance;
}
