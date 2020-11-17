package bts.KCamps.service.utilService;

import bts.KCamps.dto.CurrentLocationDto;
import bts.KCamps.model.Camp;
import bts.KCamps.model.GoogleCampCoordinate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleCallService {

    private final RestTemplate restTemplate;

    @Value("${google.apiKey}")
    private String apiKey;

    private JsonObject getCampCoordinationByAddress(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?key={apiKey}&address={address}";
        ResponseEntity<String> json = restTemplate.getForEntity(url, String.class, apiKey, address);

        JsonObject object = JsonParser.parseString(json.getBody()).getAsJsonObject();

        return object;
    }

    public CurrentLocationDto findCoordinateByAddress(String address) {
        try {
            JsonObject obj = getCampCoordinationByAddress(address);
            JsonObject results = obj.getAsJsonArray("results")
                    .get(0)
                    .getAsJsonObject();
            JsonObject location = results
                    .getAsJsonObject("geometry")
                    .getAsJsonObject("location");

            String formattedAddress = results.getAsJsonPrimitive("formatted_address").getAsString();
            String lat = location.getAsJsonPrimitive("lat").getAsString();
            String lng = location.getAsJsonPrimitive("lng").getAsString();

            return new CurrentLocationDto(Double.parseDouble(lat), Double.parseDouble(lng));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GoogleCampCoordinate getCampCoordinate(Camp camp) {
        try {
            JsonObject obj = getCampCoordinationByAddress(camp.getAddress());
            JsonObject results = obj.getAsJsonArray("results")
                    .get(0)
                    .getAsJsonObject();
            JsonObject location = results
                    .getAsJsonObject("geometry")
                    .getAsJsonObject("location");

            String formattedAddress = results.getAsJsonPrimitive("formatted_address").getAsString();
            String lat = location.getAsJsonPrimitive("lat").getAsString();
            String lng = location.getAsJsonPrimitive("lng").getAsString();

            return GoogleCampCoordinate.of()
                    .campId(camp)
                    .formattedAddress(formattedAddress)
                    .latitude(lat)
                    .longitude(lng)
                    .build();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }
}
