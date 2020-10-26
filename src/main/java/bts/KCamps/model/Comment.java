package bts.KCamps.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int rate;

    @Column(length = 1000)
    private String comment;

    private String author;

    @ManyToOne(optional=false, cascade= CascadeType.REFRESH)
    @JoinColumn(name="camp_id")
    private Camp camp;

    public Comment(int rate, String comment, String author, Camp camp) {
        this.rate = rate;
        this.comment = comment;
        this.author = author;
        this.camp = camp;
    }
}
