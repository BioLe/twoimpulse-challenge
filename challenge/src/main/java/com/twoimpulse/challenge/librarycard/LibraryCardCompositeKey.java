package com.twoimpulse.challenge.librarycard;

import com.twoimpulse.challenge.library.Library;
import com.twoimpulse.challenge.person.Person;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class LibraryCardCompositeKey implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private long libraryCardId;

    @OneToOne(targetEntity = Library.class)
    @JoinColumn(name = "l_id", referencedColumnName = "l_id")
    Library library;

    @OneToOne(targetEntity = Person.class)
    @JoinColumn(name = "p_id", referencedColumnName = "p_id")
    Person person;
//    private long libraryId;
//    private long personId;

}
