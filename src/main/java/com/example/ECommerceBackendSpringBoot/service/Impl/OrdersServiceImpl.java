package com.example.ECommerceBackendSpringBoot.service.Impl;

import com.example.ECommerceBackendSpringBoot.Dto.Request.OrderRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.ItemResponseDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.OrderResponseDto;
import com.example.ECommerceBackendSpringBoot.Transformers.ItemTransformer;
import com.example.ECommerceBackendSpringBoot.Transformers.OrdersTransformer;
import com.example.ECommerceBackendSpringBoot.entity.*;
import com.example.ECommerceBackendSpringBoot.exception.*;
import com.example.ECommerceBackendSpringBoot.repository.CardRepository;
import com.example.ECommerceBackendSpringBoot.repository.OrdersRepository;
import com.example.ECommerceBackendSpringBoot.repository.ProductRepository;
import com.example.ECommerceBackendSpringBoot.repository.UserRepository;
import com.example.ECommerceBackendSpringBoot.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    OrdersRepository ordersRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    private JavaMailSender emailSender;
    @Override
    public OrderResponseDto placeDirectOrder(OrderRequestDto orderRequestDto) throws Exception {
        User user;
        Product product;
        try{
            user = userRepository.findById(orderRequestDto.getUserId()).get();
        }
        catch (Exception e) {
            throw new UserNotFoundException("there is no user with this userId");
        }
        try {
            product = productRepository.findById(orderRequestDto.getProductId()).get();
        }
        catch (Exception e) {
            throw new ProductNotFoundException("there is no product with this productId");
        }
        Card card = cardRepository.findByCardNo(orderRequestDto.getCardNo());

        if(card==null || card.getCardCvv()!=orderRequestDto.getCvv() || card.getUser() != user){
            throw new InvalidCardException("Your card is not valid!!");
        }

        Item item = Item.builder()
                .requiredQuantity(orderRequestDto.getRequiredQuantity())
                .product(product)
                .build();

        try{
            productService.decreaseProductQuantity(item);
        }
        catch (Exception e){
            throw new Exception(e.getMessage());
        }

        Orders order = new Orders();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));
        order.setTotalCost(item.getRequiredQuantity() * product.getPrice());
        order.setCardUsed(generateMaskedCard(card.getCardNo()));
        order.setUser(user);
        order.getItems().add(item);

        user.getOrders().add(order);
        item.setOrders(order);
        product.getItems().add(item);

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
                "Product Name: "+product.getProductName() + "\n" +
                "\n" +
                "We have received your payment using " + card.getCardType() + " card" + ", and your order will be shipped to the address you provided: " +user.getAddress()+ ".\n" +
                "\n" +
                "If you have any questions or concerns regarding your order, please feel free to contact our customer service team at shopwavecustomersupport@gmail.com .\n" +
                "\n" +
                "Thank you again for choosing our ShopWave site for your shopping needs. We hope you have a great shopping experience!\n" +
                "\n" +
                "Best regards,\n" +
                "\n" +
                "Pratik Bhagwat\n" +
                "ShopWave";
        String subject = "Your order is confirmed!";


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("shopwaveservice@gmail.com");
        message.setTo(user.getEmailId());
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

        /* <----------------------------------- sending email end --------------------------------------------> */


        OrderResponseDto orderResponseDto = OrdersTransformer.orderToOrderResponseDto(savedOrder);

        List<ItemResponseDto> itemResponseDtoList = new ArrayList<>();

        for (Item i : savedOrder.getItems()) {
            ItemResponseDto itemResponseDto = ItemTransformer.itemToItemResponseDto(i);
            itemResponseDtoList.add(itemResponseDto);
        }
        orderResponseDto.setItems(itemResponseDtoList);

        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getAllOrdersOfAUser(int userId) throws UserNotFoundException {
        User user;
        try{
            user = userRepository.findById(userId).get();
        }
        catch (Exception e) {
            throw new UserNotFoundException("there is no user with this userId");
        }
        List<Orders> orders = user.getOrders();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Orders orders1 : orders) {
            List<Item> items = orders1.getItems();
            List<ItemResponseDto> itemResponseDtos = new ArrayList<>();

            for (Item item : items) {
                ItemResponseDto itemResponseDto = ItemTransformer.itemToItemResponseDto(item);
                itemResponseDtos.add(itemResponseDto);
            }
            OrderResponseDto orderResponseDto = OrdersTransformer.orderToOrderResponseDto(orders1);
            orderResponseDto.setItems(itemResponseDtos);
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }

    @Override
    public List<OrderResponseDto> getRecentFiveOrdersOfUser(int userId){
        List<Orders> orders = ordersRepository.getRecent5OrdersOfUser(userId);
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();

        for (Orders orders1 : orders) {
            List<Item> items = orders1.getItems();
            List<ItemResponseDto> itemResponseDtos = new ArrayList<>();

            for (Item item : items) {
                ItemResponseDto itemResponseDto = ItemTransformer.itemToItemResponseDto(item);
                itemResponseDtos.add(itemResponseDto);
            }
            OrderResponseDto orderResponseDto = OrdersTransformer.orderToOrderResponseDto(orders1);
            orderResponseDto.setItems(itemResponseDtos);
            orderResponseDtoList.add(orderResponseDto);
        }
        return orderResponseDtoList;
    }

    @Override
    public String deleteOrderOfAUser(int orderId) throws OrderDoesNotExistsException {
        Orders order;
        try{
            order = ordersRepository.findById(orderId).get();
        }
        catch (Exception e) {
            throw new OrderDoesNotExistsException("there is no order with this orderId");
        }

        User user = order.getUser();
        List<Orders> orders = user.getOrders();

        orders.removeIf(orders1 -> orders1.getOrderId() == orderId);
        ordersRepository.delete(order);
        userRepository.save(user);
        return "order deleted successfully";
    }

    @Override
    public OrderResponseDto getTheHighestValueOrderEver() {
        Orders order = ordersRepository.getTheHighestValueOrderEver();
        OrderResponseDto orderResponseDto = OrdersTransformer.orderToOrderResponseDto(order);
        List<Item> items = order.getItems();
        List<ItemResponseDto> itemResponseDtos = new ArrayList<>();

        for (Item item : items) {
            ItemResponseDto itemResponseDto = ItemTransformer.itemToItemResponseDto(item);
            itemResponseDtos.add(itemResponseDto);
        }
        orderResponseDto.setItems(itemResponseDtos);
        return orderResponseDto;
    }
    public String generateMaskedCard(String cardNo){
        String maskedCardNo = "";
        for(int i = 0;i<cardNo.length()-4;i++)
            maskedCardNo += 'X';
        maskedCardNo += cardNo.substring(cardNo.length()-4);
        return maskedCardNo;

    }
    public Orders placeOrder(User user,Card card) throws ProductOutOfStockException {
        Cart cart = user.getCart();

        Orders order = new Orders();
        order.setOrderNo(String.valueOf(UUID.randomUUID()));
        order.setCardUsed(generateMaskedCard(card.getCardNo()));
        order.setUser(user);

        List<Item> orderedItems = new ArrayList<>();
        for(Item item: cart.getItems()){
            try{
                productService.decreaseProductQuantity(item);
                orderedItems.add(item);
            } catch (Exception e) {
                throw new ProductOutOfStockException("Product Out of stock");
            }
        }
        order.setItems(orderedItems);

        for (Item item: orderedItems) {
            item.setOrders(order);
        }

        order.setTotalCost(cart.getTotalCost());
        return order;
    }
}
