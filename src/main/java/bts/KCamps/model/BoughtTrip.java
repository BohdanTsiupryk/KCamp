package bts.KCamps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BoughtTrip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name="change_id")
    private CampChange change;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="child_trip",
            joinColumns=@JoinColumn(name="trip_id"),
            inverseJoinColumns=@JoinColumn(name="child_id"))
    private Set<Child> child = new HashSet<>();
    
    @Column(length = 255)
    private String orderId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "boughtTrips")
    private User owner;

    public BoughtTrip(CampChange change, Child child, User owner) {
        this.change = change;
        this.owner = owner;
        this.child.add(child);
    }
}
