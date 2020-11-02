package bts.KCamps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"camp"})
@EqualsAndHashCode(exclude = {"camp"})
@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int rate;

    @Column(length = 2048)
    private String message;

    private String author;

    @ManyToOne(optional = false, cascade = {CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "camp_id")
    private Camp camp;

    public Comment(int rate, String comment, String author, Camp camp) {
        this.rate = rate;
        this.message = comment;
        this.author = author;
        this.camp = camp;
    }
}
