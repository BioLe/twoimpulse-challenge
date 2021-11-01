package com.twoimpulse.challenge.person;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @Test
    void signUpWorksThroughAllLayers() throws Exception {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String salt = "twoimpulsechallengepasswordencryptionsecretphrase";
        String hashedPassword = passwordEncoder.encode(salt+"password");
        String email = "leonardo@gmail.com";
        Person Leo = new Person("Leonardo", email, hashedPassword, LocalDateTime.now());

        mockMvc.perform(post("/api/v1/person/auth/sign-up")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(Leo)))
                .andExpect(status().isCreated());

        Optional<Person> fetchedPerson  = personRepository.findByEmail(email);

        assertThat(fetchedPerson.get().getEmail()).isEqualTo(Leo.getEmail());
    }



}