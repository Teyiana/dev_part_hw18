package org.example.controller;

import org.example.dto.UserDTO;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    @Spy
    private UserController userController;

    @Test
    private void testCreate() {
        UserDTO userToCreate = mock(UserDTO.class);
        UserDTO userToReturn = mock(UserDTO.class);
        when(userService.create(userToCreate)).thenReturn(userToReturn);

        UserDTO result = userController.create(userToCreate);

        assertEquals(userToReturn, result);
        verify(userService).create(userToCreate);
    }

    @Test
    private void testRead() {
        List<UserDTO> users = mock(List.class);
        when(userService.findAllUsers()).thenReturn(users);

        List<UserDTO> result = userController.read();

        assertEquals(users, result);
        verify(userService).findAllUsers();
    }

    @Test
    private void testReadById() {
        long id = 1L;
        UserDTO user = mock(UserDTO.class);
        when(userService.findUserById(id)).thenReturn(user);
        ResponseEntity result = userController.read(id);

        assertEquals(user, result);
        verify(userService).findUserById(id);
    }

    @Test
    private void testUpdate() {
        UserDTO userToUpdate = mock(UserDTO.class);
        UserDTO userToReturn = mock(UserDTO.class);
        when(userService.update(userToUpdate)).thenReturn(userToReturn);

        UserDTO result = userController.update(userToUpdate);

        assertEquals(userToReturn, result);
        verify(userService).update(userToUpdate);
    }

    @Test

    private void testDelete() {
        long id = 1L;
        ResponseEntity result = userController.delete(id);

        verify(userService).deleteUserById(id);
        assertEquals(ResponseEntity.ok().build(), result);
    }



}
