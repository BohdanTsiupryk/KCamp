package bts.KCamps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String city;
    private String address;

    @OneToMany(mappedBy="address", cascade = CascadeType.DETACH,fetch= FetchType.LAZY)
    @JsonIgnore
    private Set<User> inhabitants = new HashSet<>();

    public UserAddress(String city, String address, Set<User> inhabitants) {
        this.city = city;
        this.address = address;
        this.inhabitants = inhabitants;
    }
}

