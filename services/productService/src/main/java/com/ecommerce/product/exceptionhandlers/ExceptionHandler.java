package com.ecommerce.product.exceptionhandlers;

import com.ecommerce.product.dtos.ProductNotFoundExceptionDto;
import com.ecommerce.product.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    // Add exception handling code here
    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> handleInstanceNotFoundException(ProductNotFoundException exception) {
        ProductNotFoundExceptionDto productNotFoundExceptionDto = new ProductNotFoundExceptionDto();
        productNotFoundExceptionDto.setErrorCode(exception.getId());
        productNotFoundExceptionDto.setMessage(exception.getMessage());
        return new ResponseEntity<>(productNotFoundExceptionDto, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ProductNotFoundExceptionDto> handleNullException(ProductNotFoundException exception) {
        ProductNotFoundExceptionDto productNotFoundExceptionDto = new ProductNotFoundExceptionDto();
        productNotFoundExceptionDto.setErrorCode(exception.getId());
        productNotFoundExceptionDto.setMessage(exception.getMessage());
        return new ResponseEntity<>(productNotFoundExceptionDto, HttpStatus.NOT_FOUND);
    }
}
