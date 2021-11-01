package com.twoimpulse.challenge.person;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class SignUpForm {
    private String fullName;
    private String email;
    private String password;
}
