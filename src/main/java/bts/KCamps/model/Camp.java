package bts.KCamps.model;

import bts.KCamps.enums.Childhood;
import bts.KCamps.enums.Interesting;
import bts.KCamps.enums.Location;
import bts.KCamps.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"author","changes","comments"})
@EqualsAndHashCode(exclude = {"author","changes","comments"})
@Entity
@Table
public class Camp {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "address")
    private String address;

    @Column(name = "nameCamp")
    private String nameCamp;

    @Column(name = "description", length = 4096)
    private String description;

    @Column(name = "mainPicName")
    private String mainPicName;

    @ElementCollection(targetClass = Childhood.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "childhood", joinColumns = @JoinColumn(name = "camp_id"))
    @Enumerated(EnumType.STRING)
    private Set<Childhood> childhoods;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "rating")
    private float rating;

    @ElementCollection(targetClass = Interesting.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CAMP_INTEREST", joinColumns = @JoinColumn(name = "camp_id"))
    @Enumerated(EnumType.STRING)
    private Set<Interesting> interesting;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "change_id")
    private Set<CampChange> changes = new HashSet<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "camp_photo", joinColumns = @JoinColumn(name = "camp_id"))
    private Set<String> campPhotos = new HashSet<>();

    @ElementCollection(targetClass = Location.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CAMP_LOCATION", joinColumns = @JoinColumn(name = "camp_id"))
    @Enumerated(EnumType.STRING)
    private Set<Location> locations;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "camp")
    private Set<Comment> comments = new HashSet<>();

    public Camp(String nameCamp, String description, String[] interests, String[] locations, String[] childhoods) {
        this.nameCamp = nameCamp;
        this.description = description;
        this.interesting = EnumUtil.getInterests(interests);
        this.locations = EnumUtil.getLocations(locations);
        this.childhoods = EnumUtil.getChildhoods(childhoods);
    }

    public Camp(String nameCamp, String description, User author, String[] interests, String[] locations, String[] childhoods) {
        this.nameCamp = nameCamp;
        this.description = description;
        this.author = author;
        this.interesting = EnumUtil.getInterests(interests);
        this.locations = EnumUtil.getLocations(locations);
        this.childhoods = EnumUtil.getChildhoods(childhoods);
    }

    public void setNewRating(int rating) {
        if (this.rating != 0) {
            this.rating = (this.rating + rating) / 2;
        } else {
            this.rating = rating;
        }
    }
}
