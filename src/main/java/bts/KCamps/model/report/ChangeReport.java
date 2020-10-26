package bts.KCamps.model.report;

import lombok.Data;

import java.util.Map;

@Data
public class ChangeReport {
    private Map<String, String> info;
    private Map<String, Long> citizenship;
    private Map<String, Long> userCity;
    private Map<Integer, Long> childAge;
    private Map<String, Double> diagram;
}
