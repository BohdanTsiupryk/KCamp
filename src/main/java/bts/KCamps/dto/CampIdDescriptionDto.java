package bts.KCamps.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampIdDescriptionDto {
    private Long id;
    private String description;
    private int wage = 0;

    public CampIdDescriptionDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public void incrementWage() {
        this.wage++;
    }

    public boolean isWageNotZero() {
        return this.wage != 0;
    }
}
