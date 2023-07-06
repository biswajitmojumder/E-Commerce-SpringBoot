package com.example.ECommerceBackendSpringBoot.service.Impl;

import com.example.ECommerceBackendSpringBoot.Dto.Request.CheckOutCartRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Request.ItemRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.CartResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.ItemResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.Transformers.CartTransformer;
import com.example.ECommerceBackendSpringBoot.Transformers.ItemTransformer;
import com.example.ECommerceBackendSpringBoot.Transformers.OrdersTransformer;
import com.example.ECommerceBackendSpringBoot.entity.*;
import com.example.ECommerceBackendSpringBoot.exception.CardIsEmptyException;
import com.example.ECommerceBackendSpringBoot.exception.InvalidCardException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.repository.CardRepository;
import com.example.ECommerceBackendSpringBoot.repository.CartRepository;
import com.example.ECommerceBackendSpringBoot.repository.OrdersRepository;
import com.example.ECommerceBackendSpringBoot.repository.UserRepository;
import com.example.ECommerceBackendSpringBoot.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    OrdersServiceImpl ordersService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Override
    public CartResponseDto addToCart(int userId, Item item) {
        User user = userRepository.findById(userId).get();
        Cart cart = user.getCart();

        int newTotal = cart.getTotalCost() + item.getRequiredQuantity()*item.getProduct().getPrice();
        cart.setTotalCost(newTotal);
        cart.getItems().add(item);
        cart.setNumberOfItems(cart.getItems().size());

        item.setCart(cart);
        Cart savedCart = cartRepository.save(cart);

        CartResponseDto cartResponseDto = CartTransformer.cartToCartResponseDto(savedCart);
        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        for (Item item1 : savedCart.getItems()) {
            ItemResponseDto itemResponseDto = ItemTransformer.itemToItemResponseDto(item1);
            itemResponseDtoList.add(itemResponseDto);
        }
        cartResponseDto.setItems(itemResponseDtoList);
        return cartResponseDto;
    }

    @Override
    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws Exception {
        User user;
        try{
             user = userRepository.findById(checkOutCartRequestDto.getUserId()).get();
        }
        catch (Exception e) {
            throw new UserNotFoundException("there is no user with this userId");
        }

        Card card = cardRepository.findByCardNo(checkOutCartRequestDto.getCardNo());
        if(card==null || card.getCardCvv()!=checkOutCartRequestDto.getCvv() || card.getUser() != user){
            throw new InvalidCardException("Your card is not valid!!");
        }

        Cart cart = user.getCart();
        if(cart.getNumberOfItems()==0){
            throw new CardIsEmptyException("Cart is empty!!");
        }

        try{
            Orders order = ordersService.placeOrder(user,card);
            user.getOrders().add(order);
            resetCart(cart);

            Orders savedOrder = ordersRepository.save(order);

            /* <----------------------------------- sending email start --------------------------------------------> */

            //?Sending the email for successfully Ordered the item or product to the user email id.

            //?this is the message we want to send to the user email id after successful order placed
            String text = "Dear " + user.getUserName() + ",\n" +
                    "\n" +
                    "Thank you for shopping with us! We are delighted to inform you that your order has been successfully placed and confirmed. We are currently processing your order and will notify you once it has been shipped.\n" +
                    "\n" +
                    "Here are the details of your order:\n" +
                    "\n" +
                    "Order Number: " + order.getOrderNo() + "\n" +
                    "Order Date: "+ order.getOrderDate() + "\n" +
                    "Total Cost: "+ order.getTotalCost() + "\n" +
                    "Total Number Of Items: " + order.getItems().size() +
                    "\n" +
                    "We have received your payment using " + card.getCardType() + " card" + ", and your order will be shipped to the address you provided: " +user.getAddress()+ ".\n" +
                    "\n" +
                    "If you have any questions or concerns regarding your order, please feel free to contact our customer service team at shopwavecustomersupport@gmail.com .\n" +
                    "\n" +
                    "Thank you again for choosing our ShopWave site for your shopping needs. We hope you have a great shopping experience!\n" +
                    "\n" +
                    "Best regards,\n" +
                    "\n" +
                    "Deepak Kumar\n" +
                    "ShopWave";
            String subject = "Your order is confirmed!";


            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sendingspringemails@gmail.com");
            message.setTo(user.getEmailId());
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);

            /* <----------------------------------- sending email end --------------------------------------------> */


            OrderResponseDto orderResponseDto = OrdersTransformer.orderToOrderResponseDto(savedOrder);
            List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

            for (Item item: savedOrder.getItems()) {
                ItemResponseDto itemResponseDto = ItemTransformer.itemToItemResponseDto(item);
                itemResponseDtoList.add(itemResponseDto);
            }
            orderResponseDto.setItems(itemResponseDtoList);
            return orderResponseDto;
        }
        catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public String removeFromCart(int userId, int itemId) throws UserNotFoundException {
        User user;
        try{
            user = userRepository.findById(userId).get();
        }
        catch (Exception e) {
            throw new UserNotFoundException("there is no user with this userId");
        }
        Cart cart = user.getCart();
        List<Item> items = cart.getItems();

        items.removeIf(item -> item.getItemId() == itemId);

        cartRepository.save(cart);

        return "item removed form cart successfully";
    }

    public void resetCart(Cart cart){
        cart.setTotalCost(0);
        for(Item item: cart.getItems()){
            item.setCart(null);
        }
        cart.setNumberOfItems(0);
        cart.getItems().clear();
    }
}
