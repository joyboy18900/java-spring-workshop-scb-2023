package com.example.day1.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCommandServiceTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void createUser_success() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Surakiat");
        userRequest.setLastName("Sangkla");

        UserCommandService userCommandService = new UserCommandService(userRepository);
        userCommandService.createUser(userRequest);
    }

    @Test
    void createUser_failure_duplicate_firstname() {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName("Surakiat");
        userRequest.setLastName("Sangkla");

        List<UserEntity> results = new ArrayList<>();
        results.add(new UserEntity());
        when(userRepository.findByFirstName("Surakiat")).thenReturn(results);

        UserCommandService service = new UserCommandService(userRepository);
        Exception exception = assertThrows(
                DuplicateFirstnameException.class, () -> {
                    service.createUser(userRequest);
                });
        assertEquals("", exception.getMessage());
    }
}