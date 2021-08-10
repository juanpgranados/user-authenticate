package com.challenge.userauthenticate.service;

import com.challenge.userauthenticate.entity.User;
import com.challenge.userauthenticate.model.UserModel;
import com.challenge.userauthenticate.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceTests {

    @Autowired
    @InjectMocks
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @BeforeEach
    void mockObjects(){
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUserName("testuser");
        mockUser.setPassword("123");
        mockUser.setRoles("USER");
        mockUser.setName("John Doe");
        mockUser.setStatus(UserService.ACTIVE_USER);
        mockUser.setFailedAttempts(0);
        Mockito.when(userRepository.findByUserName("testuser")).thenReturn(Optional.of(mockUser));
    }

    @Test
    void testGetUser() {
        UserModel expectedUser = new UserModel(1L,"testuser", "123", "USER", "John Doe","ACTIVE", 0, null);
        UserModel userModel = userService.getUserByUsername("testuser");
        assertEquals(expectedUser.toString(),userModel.toString());
    }

    @Test
    void testUpdateStatus(){
        userService.updateStatus("testuser", UserService.LOCKED_USER);
        Mockito.verify(userRepository).save(any(User.class));
    }

    @Test
    void testIncreaseFailedAttempts(){
        userService.increaseFailedAttempts("testuser");
        Mockito.verify(userRepository).save(any(User.class));
    }

    @Test
    void testUnlockUsers(){
        userService.unlockUsers(new Date());
        Mockito.verify(userRepository).unlockUsers(any(Date.class));
    }
}
