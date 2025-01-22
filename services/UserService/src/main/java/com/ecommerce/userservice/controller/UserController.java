package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.constants.ApplicationConstants;
import com.ecommerce.userservice.dto.*;
import com.ecommerce.userservice.model.UserEc;
import com.ecommerce.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        logger.info("Received sign up request for user with email: {}", signUpRequestDto.toString());
        UserResponseDto response = userService.signUp(signUpRequestDto);
        return ResponseEntity.ok(response);
    }



    @RequestMapping("/user")
    public UserEc getUserDetailsAfterLogin(Authentication authentication) {
        return userService.getUserDetailsAfterLogin(authentication);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin (@RequestBody LoginRequestDto loginRequest) {
        logger.info("Received login request for user with email: {}", loginRequest.getEmail());
        LoginResponseDto loginResponseDto = userService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER,loginResponseDto.jwtToken())
                .body(loginResponseDto);
    }
    @GetMapping("/validatetoken")
    public ResponseEntity<TokenValidateResponseDto> validateToken(@RequestHeader(ApplicationConstants.JWT_HEADER) String jwtToken) throws Exception {
        logger.info("Received request to validate token");
        return ResponseEntity.ok(userService.validateToken(jwtToken));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}