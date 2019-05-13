/**
 * Created by grigo on 4/23/17.
 */

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "EVENTANNOTS" )
public class EventAnnot {
    private Long id;

    private String title;
    private Date date;

    public EventAnnot() {
        // this form used by Hibernate
    }

    public EventAnnot(String title, Date date) {
        // for application use, to create new events
        this.title = title;
        this.date = date;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EVENT_DATE")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}