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
import javax.persistence.OneToOne;
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

    private String address;

    private String nameCamp;

    @Column(length = 2048)
    private String description;

    private String mainPicName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    private float rating;

    private String latitude;

    private String longitude;

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
