package com.twoimpulse.challenge.librarycard;

import com.twoimpulse.challenge.library.Library;
import com.twoimpulse.challenge.person.Person;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDateTime;


@Entity(name = "library_card")
@Getter
@Setter
//@IdClass(LibraryCardCompositeKey.class)
@Table(
    name = "library_card",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"l_id", "p_id"})
})
public class LibraryCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(generator = "uuid2")
    //@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "lc_id")
    private long libraryCardId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "l_id")
    private Library library;


    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "p_id")
    private Person person;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    private LocalDateTime creationDate;

    public LibraryCard(){}

    public LibraryCard(Library library, Person person){
        this.library = library;
        this.person = person;
        this.creationDate = LocalDateTime.now();
    }

    public LibraryCard(Library library, Person person, LocalDateTime creationDate){
        this.library = library;
        this.person = person;
        this.creationDate = creationDate;
    }

}
