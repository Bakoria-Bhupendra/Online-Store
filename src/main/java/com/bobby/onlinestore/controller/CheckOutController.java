package com.bobby.onlinestore.controller;
import com.bobby.onlinestore.Dtos.CheckOutRequest;
import com.bobby.onlinestore.Dtos.CheckOutResponse;
import com.bobby.onlinestore.Dtos.ErrorDto;
import com.bobby.onlinestore.exceptions.CartEmptyException;
import com.bobby.onlinestore.exceptions.CartNotFoundException;
import com.bobby.onlinestore.services.CheckOutService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckOutController {
    private final CheckOutService checkOutService;

    @PostMapping
    public CheckOutResponse checkout(@Valid @RequestBody CheckOutRequest checkoutRequest){
        return checkOutService.checkout(checkoutRequest);
    }

    @ExceptionHandler({CartEmptyException.class, CartNotFoundException.class})
    public ResponseEntity<ErrorDto> handleException(Exception ex){
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}
