package com.example.ECommerceBackendSpringBoot.controller;

import com.example.ECommerceBackendSpringBoot.Dto.Request.SellerEmailRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Request.SellerIdRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Request.SellerRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.SellerResponseDto;
import com.example.ECommerceBackendSpringBoot.exception.SellerAlreadyExistsException;
import com.example.ECommerceBackendSpringBoot.exception.SellerNotFoundException;
import com.example.ECommerceBackendSpringBoot.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {
    @Autowired
    SellerService sellerService;
    @PostMapping("/add")
    public String addSeller(@RequestBody SellerRequestDto sellerRequestDto) throws SellerAlreadyExistsException {
        return sellerService.addSeller(sellerRequestDto);
    }
    @GetMapping("/get-by-email")
    public SellerResponseDto getSellerByEmail(@RequestBody SellerEmailRequestDto sellerEmailRequestDto) throws SellerNotFoundException {
        return sellerService.getSellerByEmail(sellerEmailRequestDto);
    }
    @GetMapping("/get-by-id")
    public SellerResponseDto getSellerById(@RequestBody SellerIdRequestDto sellerIdRequestDto) throws SellerNotFoundException {
        return sellerService.getSellerById(sellerIdRequestDto);
    }
    @GetMapping("/get-all")
    public List<SellerResponseDto> getAllSellers() {
        return sellerService.getAllSellers();
    }
    @PutMapping("/update-by-email")
    public SellerResponseDto updateSellerByEmail(@RequestParam("emailId") String emailId,@RequestBody SellerRequestDto sellerRequestDto) {
        return sellerService.updateSellerByEmail(emailId,sellerRequestDto);
    }
    @DeleteMapping("/delete-by-email")
    public String deleteSellerByEmail(@RequestBody SellerEmailRequestDto sellerEmailRequestDto) throws SellerNotFoundException {
        return sellerService.deleteSellerByEmail(sellerEmailRequestDto);
    }
    @DeleteMapping("/delete-by-id")
    public String deleteSellerById(@RequestBody SellerIdRequestDto sellerIdRequestDto) throws SellerNotFoundException {
        return sellerService.deleteSellerById(sellerIdRequestDto);
    }
}
