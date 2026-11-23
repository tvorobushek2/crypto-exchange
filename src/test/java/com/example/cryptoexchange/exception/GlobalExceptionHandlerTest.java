package com.example.cryptoexchange.exception;

import com.example.cryptoexchange.exception.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();
    private final MockHttpServletRequest request = new MockHttpServletRequest("GET", "/api/users/999");

    @Test
    public void testUserNotFoundReturns404() {
        ResponseEntity<ApiErrorResponse> response = handler.handleUserNotFound(
                new UserNotFoundException(999L), request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("999"));
    }

    @Test
    public void testUserAlreadyExistsReturns409() {
        ResponseEntity<ApiErrorResponse> response = handler.handleUserAlreadyExists(
                new UserAlreadyExistsException("test@mail.com"), request);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void testInvalidUserDataReturns400() {
        ResponseEntity<ApiErrorResponse> response = handler.handleInvalidUserData(
                new InvalidUserDataException("пустое поле"), request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testOrderNotFoundReturns404() {
        ResponseEntity<ApiErrorResponse> response = handler.handleOrderNotFound(
                new OrderNotFoundException(5L), request);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testInsufficientBalanceReturns400() {
        ResponseEntity<ApiErrorResponse> response = handler.handleInsufficientBalance(
                new InsufficientBalanceException(1L, 1000.0, 50.0), request);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testGenericExceptionReturns500() {
        ResponseEntity<ApiErrorResponse> response = handler.handleGeneric(
                new RuntimeException("boom"), request);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}