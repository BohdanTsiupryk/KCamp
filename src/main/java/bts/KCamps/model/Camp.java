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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

import static bts.KCamps.model.Camp.GET_COORDINATES;
import static bts.KCamps.model.Camp.GET_DESCRIPTIONS;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"author", "changes", "comments", "coordinate"})
@EqualsAndHashCode(exclude = {"author", "changes", "comments", "coordinate"})
@NamedQueries(value = {
        @NamedQuery(name = GET_DESCRIPTIONS, query = "SELECT c.id, c.description FROM Camp c"),
        @NamedQuery(name = GET_COORDINATES, query = "SELECT c.id, c.coordinate.latitude, c.coordinate.longitude FROM Camp c")
})
@Entity
@Table
public class Camp {
    public static final String GET_DESCRIPTIONS = "getDescriptions";
    public static final String GET_COORDINATES = "getCoordinates";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String address;

    private String nameCamp;

    @Column(length = 2048)
    private String description;

    private String mainPicName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private float rating;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE})
    private GoogleCampCoordinate coordinate;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<CampChange> changes = new HashSet<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "camp_photo", joinColumns = @JoinColumn(name = "camp_id"))
    private Set<String> campPhotos = new HashSet<>();

    @ElementCollection(targetClass = Interesting.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CAMP_INTEREST", joinColumns = @JoinColumn(name = "camp_id"))
    @Enumerated(EnumType.STRING)
    private Set<Interesting> interesting;

    @ElementCollection(targetClass = Childhood.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "childhood", joinColumns = @JoinColumn(name = "camp_id"))
    @Enumerated(EnumType.STRING)
    private Set<Childhood> childhoods;

    @ElementCollection(targetClass = Location.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "CAMP_LOCATION", joinColumns = @JoinColumn(name = "camp_id"))
    @Enumerated(EnumType.STRING)
    private Set<Location> locations;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
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
