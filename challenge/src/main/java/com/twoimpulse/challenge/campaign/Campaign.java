package com.twoimpulse.challenge.campaign;

import com.twoimpulse.challenge.library.Library;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "campaign")
@Table(name = "campaign")
@Getter @Setter
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private long campaignId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "l_id")
    private Library library;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    @Column(name = "banner")
    private byte[] banner;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDate;

    @Column(name = "end_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDate;

    public Campaign(){}

    public Campaign(String title, String description, Library library, LocalDateTime startDate, LocalDateTime endDate, byte[] banner){
        this.title = title;
        this.description = description;
        this.library = library;
        this.startDate = startDate;
        this.endDate = endDate;
        this.banner = banner;
    }
}
