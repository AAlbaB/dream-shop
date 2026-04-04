package com.spring.dreamshop.service.user;

import com.spring.dreamshop.dto.UserDto;
import com.spring.dreamshop.model.User;
import com.spring.dreamshop.request.CreateUserRequest;
import com.spring.dreamshop.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);

    User createUser(CreateUserRequest request);

    User updateUser(UserUpdateRequest request, Long userId);

    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);

    User getAuthenticatedUser();
}
