package com.example.ECommerceBackendSpringBoot.controller;

import com.example.ECommerceBackendSpringBoot.Dto.Request.UserRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.UserResponseDto;
import com.example.ECommerceBackendSpringBoot.enums.CardType;
import com.example.ECommerceBackendSpringBoot.exception.NoUserFoundWithThisEmailException;
import com.example.ECommerceBackendSpringBoot.exception.ThereIsNoUserWithThisCardTypeException;
import com.example.ECommerceBackendSpringBoot.exception.UserAlreadyExistException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/add")
    public String addUser(@RequestBody UserRequestDto userRequestDto) throws UserAlreadyExistException{
        return userService.addUser(userRequestDto);
    }
    @GetMapping("/get-all")
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/get-by-email")
    public UserResponseDto getUserByEmail(@RequestParam("emailId") String emailId) throws UserNotFoundException {
        return userService.getUserByEmail(emailId);
    }
    @GetMapping("/get/{age}")
    public List<UserResponseDto> getAllUsersGreaterThanAge(@PathVariable int age) {
        return userService.getAllUsersGreaterThanAge(age);
    }
    @GetMapping("/get-by/{cardType}")
    public List<UserResponseDto> getAllUsersWithCardType(@PathVariable CardType cardType) throws ThereIsNoUserWithThisCardTypeException {
        return userService.getAllUsersWithCardType(cardType);
    }
    @PutMapping("/update-by-email")
    public String updateUserByEmail(@RequestParam("emailId") String emailId,@RequestBody UserRequestDto userRequestDto) throws NoUserFoundWithThisEmailException {
        return userService.updateUserByEmail(emailId,userRequestDto);
    }
    @DeleteMapping("/delete-by-mob")
    public String deleteByMob(@RequestParam("mobNo") String mobNo) throws UserNotFoundException {
        return userService.deleteByMob(mobNo);
    }
}
