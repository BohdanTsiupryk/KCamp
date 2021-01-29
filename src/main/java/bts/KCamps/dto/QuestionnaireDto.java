package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionnaireDto {
    private String address;
    private int maxDistance;
    private String keyWords;
    private String[] interests;
    private String[] locations;
    private String[] childhoods;
}
