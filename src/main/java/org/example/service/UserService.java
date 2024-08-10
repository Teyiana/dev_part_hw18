package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.UserDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;


    public List<UserDTO> findAllUsers() {
        List<User> usersList = userRepository.findAll();
        return usersList.stream().map(UserService::toDto).toList();

    }


    public UserDTO create(UserDTO userDto) {
        if(userDto.getId() > 0){
            throw new IllegalArgumentException("Id must be not set");
        }

        if(!StringUtils.hasText(userDto.getName())){
            throw new IllegalArgumentException("Name must be set");
        }
        if(userDto.getAge() < 0){
            throw new IllegalArgumentException("Age must be set");
        }

        User user = new User();
        user.setUserName(userDto.getName());
        user.setAge(userDto.getAge());
        user = userRepository.save(user);
        userDto.setId(user.getUserId());
        return userDto;
    }

    public UserDTO findUserById(long id){
        if(id <= 0){
            throw new IllegalArgumentException("Id is not valid: id=" + id);
        }

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return toDto(user.get());
        }
        throw new IllegalArgumentException("User not found, id: " + id);
    }

    public void deleteUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("User not found, id: " + id);
        }
    }

    public UserDTO update(UserDTO user) {
        User userNew = userRepository.findById(user.getId()).orElseThrow();
        if(user.getName() != null){
            userNew.setUserName(user.getName());
        }
        if(user.getAge() < 0){
            userNew.setAge(user.getAge());
        }
        userRepository.save(userNew);
      return  toDto(userNew);

    }

    public static UserDTO toDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(entity.getUserId());
        dto.setName(entity.getUserName());
        dto.setAge(entity.getAge());
        return dto;
    }


}
