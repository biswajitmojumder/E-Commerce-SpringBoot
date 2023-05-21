package com.example.ECommerceBackendSpringBoot.service.Impl;

import com.example.ECommerceBackendSpringBoot.Dto.Request.UserRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.UserResponseDto;
import com.example.ECommerceBackendSpringBoot.Transformers.UserTransformer;
import com.example.ECommerceBackendSpringBoot.entity.Card;
import com.example.ECommerceBackendSpringBoot.entity.Cart;
import com.example.ECommerceBackendSpringBoot.entity.User;
import com.example.ECommerceBackendSpringBoot.enums.CardType;
import com.example.ECommerceBackendSpringBoot.exception.NoUserFoundWithThisEmailException;
import com.example.ECommerceBackendSpringBoot.exception.ThereIsNoUserWithThisCardTypeException;
import com.example.ECommerceBackendSpringBoot.exception.UserAlreadyExistException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.repository.CardRepository;
import com.example.ECommerceBackendSpringBoot.repository.UserRepository;
import com.example.ECommerceBackendSpringBoot.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CardRepository cardRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Override
    public String addUser(UserRequestDto userRequestDto) throws UserAlreadyExistException{

        if(userRepository.findByEmailId(userRequestDto.getEmailId()) != null) throw new UserAlreadyExistException("user already exists");

        User user = UserTransformer.userRequestDtoToUser(userRequestDto);
        //?creating a empty cart for the user when the user is created.
        Cart cart = Cart.builder()
                .totalCost(0)
                .numberOfItems(0)
                .user(user)
                .build();
        user.setCart(cart);

        userRepository.save(user);
        /* <----------------------------------- sending email start --------------------------------------------> */

        //?Sending the email for successfully user account created to the user email id.

        //?this is the message we want to send to the user email id after successful account creation.
        String subject = "Welcome to ShopWave!";
        String text = "Dear " + user.getUserName() + ",\n" +
                "\n" +
                "We are excited to welcome you to ShopWave! Your account has been successfully created and is now ready to use. We would like to take this opportunity to thank you for choosing our platform for your shopping needs.\n" +
                "\n" +
                "As a member of our online community, you now have access to a wide range of high-quality products, competitive prices, and exceptional customer service. Whether you are looking for the latest gadgets, fashionable clothing, or household essentials, we have something for everyone.\n" +
                "\n" +
                "If you have any questions or need assistance, our dedicated support team is always here to help you. Don't hesitate to reach out to us at shopwavecustomersupport@gmail.com or by using the contact form on our website.\n" +
                "\n" +
                "Thank you again for joining ShopWave! We look forward to serving you and making your online shopping experience a memorable one.\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Deepak Kumar\n" +
                "\n" +
                "ShopWave Team";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shopwaveservice@gmail.com");
        message.setTo(user.getEmailId());
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

        /* <----------------------------------- sending email end --------------------------------------------> */
        return "user added successfully";
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user: users) {
            UserResponseDto userResponseDto = UserTransformer.userToUserResponseDto(user);
            userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    @Override
    public UserResponseDto getUserByEmail(String emailId) throws UserNotFoundException {
        try{
            User user = userRepository.findByEmailId(emailId);
            return UserTransformer.userToUserResponseDto(user);
        }
        catch (Exception e) {
            throw new UserNotFoundException("enter the valid email id");
        }
    }

    @Override
    public List<UserResponseDto> getAllUsersGreaterThanAge(int age) {
        //?getting all users form database
        List<User> users = userRepository.getAllUsersGreaterThanAge(age);

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        for (User user: users) {

                //?converting the user entity into userResponseDto.
                UserResponseDto userResponseDto = UserTransformer.userToUserResponseDto(user);

                userResponseDtoList.add(userResponseDto);
        }
        return userResponseDtoList;
    }

    @Override
    public List<UserResponseDto> getAllUsersWithCardType(CardType cardType) throws ThereIsNoUserWithThisCardTypeException {
        try{
            List<Card> cards = cardRepository.findByCardType(cardType);
            List<UserResponseDto> userResponseDtoList = new ArrayList<>();

            for (Card c: cards) {
                User user = c.getUser();
                UserResponseDto userResponseDto = UserTransformer.userToUserResponseDto(user);

                //?if a user has 2 or more cards with given card type then only add it once.
                if(!userResponseDtoList.contains(userResponseDto))userResponseDtoList.add(userResponseDto);
            }

            return userResponseDtoList;
        }
        catch (Exception e) {
            throw new ThereIsNoUserWithThisCardTypeException("there is no user with this card type");
        }
    }

    @Override
    public String updateUserByEmail(String emailId,UserRequestDto userRequestDto) throws NoUserFoundWithThisEmailException {
        try{
            User user = userRepository.findByEmailId(emailId);
            user.setUserName(userRequestDto.getUserName());
            user.setEmailId(userRequestDto.getEmailId());
            user.setAge(userRequestDto.getAge());
            user.setMobNo(userRequestDto.getMobNo());
            user.setAddress(userRequestDto.getAddress());


            userRepository.save(user);
            return "user updated successfully";
        }
        catch (Exception e) {
            throw new NoUserFoundWithThisEmailException("there is no user found with this email");
        }
    }

    @Override
    public String deleteByMob(String mobNo) throws UserNotFoundException {
       try{
           User user = userRepository.findByMobNo(mobNo);
           userRepository.delete(user);
           return "user deleted successfully";
       }
       catch (Exception e) {
           throw new UserNotFoundException("user not found with this mobNo");
       }
    }
}
