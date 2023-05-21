package com.example.ECommerceBackendSpringBoot.service.Impl;

import com.example.ECommerceBackendSpringBoot.Dto.Request.ItemRequestDto;
import com.example.ECommerceBackendSpringBoot.Transformers.ItemTransformer;
import com.example.ECommerceBackendSpringBoot.entity.Item;
import com.example.ECommerceBackendSpringBoot.entity.Product;
import com.example.ECommerceBackendSpringBoot.entity.User;
import com.example.ECommerceBackendSpringBoot.enums.ProductStatus;
import com.example.ECommerceBackendSpringBoot.exception.ProductNotFoundException;
import com.example.ECommerceBackendSpringBoot.exception.ProductOutOfStockException;
import com.example.ECommerceBackendSpringBoot.exception.UserNotFoundException;
import com.example.ECommerceBackendSpringBoot.repository.ItemRepository;
import com.example.ECommerceBackendSpringBoot.repository.ProductRepository;
import com.example.ECommerceBackendSpringBoot.repository.UserRepository;
import com.example.ECommerceBackendSpringBoot.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemsServiceImpl implements ItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;
    @Override
    public Item addItem(ItemRequestDto itemRequestDto) throws UserNotFoundException, ProductNotFoundException, ProductOutOfStockException {
        User user;
        Product product;
        try{
            user = userRepository.findById(itemRequestDto.getUserId()).get();
        }
        catch (Exception e) {
            throw new UserNotFoundException("there is no user with this userId");
        }
        try{
           product = productRepository.findById(itemRequestDto.getProductId()).get();
        }
        catch (Exception e) {
            throw new ProductNotFoundException("there is no product with this productId");
        }

        if(itemRequestDto.getRequiredQuantity() > product.getQuantity() || product.getProductStatus() == ProductStatus.OUT_OF_STOCK) {
            throw new ProductOutOfStockException("Sorry Product out Stock!!!");
        }
        Item item = ItemTransformer.itemRequestDtoToItem(itemRequestDto);
        item.setProduct(product);
        product.getItems().add(item);

        return itemRepository.save(item);
    }
}
