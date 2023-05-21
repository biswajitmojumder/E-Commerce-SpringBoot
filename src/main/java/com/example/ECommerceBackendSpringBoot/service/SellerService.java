package com.example.ECommerceBackendSpringBoot.service;

import com.example.ECommerceBackendSpringBoot.Dto.Request.SellerEmailRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Request.SellerIdRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Request.SellerRequestDto;
import com.example.ECommerceBackendSpringBoot.Dto.Response.SellerResponseDto;
import com.example.ECommerceBackendSpringBoot.exception.SellerAlreadyExistsException;
import com.example.ECommerceBackendSpringBoot.exception.SellerNotFoundException;

import java.util.List;

public interface SellerService {
    String addSeller(SellerRequestDto sellerRequestDto) throws SellerAlreadyExistsException;

    SellerResponseDto getSellerByEmail(SellerEmailRequestDto sellerEmailRequestDto) throws SellerNotFoundException;

    SellerResponseDto getSellerById(SellerIdRequestDto sellerIdRequestDto) throws SellerNotFoundException;

    List<SellerResponseDto> getAllSellers();

    SellerResponseDto updateSellerByEmail(String emailId, SellerRequestDto sellerRequestDto);

    String deleteSellerByEmail(SellerEmailRequestDto sellerEmailRequestDto) throws SellerNotFoundException;

    String deleteSellerById(SellerIdRequestDto sellerIdRequestDto) throws SellerNotFoundException;
}
