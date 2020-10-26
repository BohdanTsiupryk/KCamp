package bts.KCamps.model;

import bts.KCamps.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "Ім'я користувача не може бути пустим")
    @Column(name = "username")
    private String username;

    @NotBlank(message = "Будь ласка заповніть ПБІ")
    @Column(name = "fullName")
    private String fullName;

    @NotBlank(message = "Пароль не може бути пустим")
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "phone", length = 14)
    private String phoneNumber;

    @JsonIgnore
    private String activationCode;
    private boolean active;

    @Email(message = "Введіть коректну елеткоронну адресу (потрібно для пітвердження користувача)")
    @NotBlank(message = "Email не може бути пустим")
    @Column(name = "email", unique = true)
    private String email;

    @OneToMany(mappedBy = "parent", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Child> child = new HashSet<>();

    @ManyToOne(optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private UserAddress address;

    @OneToOne(mappedBy = "user", optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JsonIgnore
    private UserInfo userInfo;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Set<Role> role = new HashSet<>();

    @OneToMany(mappedBy="owner", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.EAGER)
    private Set<BoughtTrip> boughtTrips = new HashSet<>();

    public boolean isAdmin() {
        return role.contains(Role.ADMIN);
    }

    //GETERS & SETERS

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
