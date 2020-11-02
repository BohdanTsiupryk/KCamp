package bts.KCamps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.jni.Local;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"parent","trips"})
@EqualsAndHashCode(exclude = {"parent","trips"})
@Entity
@Table
public class Child {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String fullName;

    private String document;

    private LocalDate birthday;

    private String citizenship;

    private String specialWishes;

    @ManyToOne(optional = false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "parent_id")
    private User parent;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "child_trip",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
    private Set<BoughtTrip> trips;

    public Child(String fullName, String document, LocalDate birthday, String citizenship, User parent, String specialWishes) {
        this.fullName = fullName;
        this.document = document;
        this.birthday = birthday;
        this.citizenship = citizenship;
        this.parent = parent;
        this.specialWishes = specialWishes;
    }

    public Integer getAge() {
        return LocalDate.now().getYear() - birthday.getYear();
    }
}
