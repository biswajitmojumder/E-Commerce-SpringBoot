package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.UserRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.UserResponseDto;
import com.example.ECommerceBackendSpringBoot.enums.CardType;
import com.example.ECommerceBackendSpringBoot.exception.NoUserFoundWithThisEmailException;
import com.example.ECommerceBackendSpringBoot.exception.ThereIsNoUserWithThisCardTypeException;
import com.example.ECommerceBackendSpringBoot.exception.UserAlreadyExistException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.repository.UserRepository;
//import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    @Autowired
    UserRepository userRepository = null;

    String addUser(UserRequestDto userRequestDto) throws UserAlreadyExistException;

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUserByEmail(String emailId) throws UserNotFoundException;

    List<UserResponseDto> getAllUsersGreaterThanAge(int age);

    List<UserResponseDto> getAllUsersWithCardType(CardType cardType) throws ThereIsNoUserWithThisCardTypeException;

    String updateUserByEmail(String emailId,UserRequestDto userRequestDto) throws NoUserFoundWithThisEmailException;

    String deleteByMob(String mobNo) throws UserNotFoundException;
}
