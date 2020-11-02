package bts.KCamps.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.comparator.Comparators;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"parentCamp"})
@EqualsAndHashCode(exclude = {"parentCamp"})
@Entity
@Table
public class CampChange {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "changeNumber")
    private int number;

    @Column(length = 2048)
    private String description;

    private int places;

    private int freePlace;

    private LocalDate beginDate;

    private LocalDate endDate;

    private int price;

    @ManyToOne(optional = false, cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "changes")
    private Camp parentCamp;

    public CampChange(int number, String description, int places, int freePlace, LocalDate beginDate, LocalDate endDate, int price, Camp camp) {
        this.number = number;
        this.description = description;
        this.places = places;
        this.freePlace = freePlace;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.price = price;
        this.parentCamp = camp;
    }

    public int duration() {
        return endDate.get(ChronoField.DAY_OF_YEAR) - beginDate.get(ChronoField.DAY_OF_YEAR);
    }
}
