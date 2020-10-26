package bts.KCamps.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDate birthday;

    private String passportNumber;

    private String citizenship;

    @OneToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public UserInfo() {
    }

    public UserInfo(LocalDate birthday, String passportNumber, String citizenship, User user) {
        this.birthday = birthday;
        this.passportNumber = passportNumber;
        this.citizenship = citizenship;
        this.user = user;
    }
}
