package com.example.grocery.controller;

import com.example.grocery.Exception.IllegalArgumentException;
import com.example.grocery.entity.Item;
import com.example.grocery.entity.Order;
import com.example.grocery.factory.ApplicationFactory;
import com.example.grocery.service.OrderService;
import com.example.grocery.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;
    @GetMapping("/getItemList")
    public ResponseEntity<?> getlistOfItems(){
        List<Item> item_list= userService.getlistOfItems();
        return new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("List of all products", HttpStatus.FOUND,item_list),HttpStatus.FOUND);
    }

    @Autowired
    private OrderService orderService;

    // Place an order
    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestParam Long userId,
                                            @RequestBody List<OrderRequest> orderRequest) throws IllegalArgumentException {
        logger.info("UserController.placeOrder: placeOrder API called");
        List<Long> itemIds = orderRequest.stream().map(OrderRequest::getItemId).toList();
        List<Integer> quantities = orderRequest.stream().map(OrderRequest::getQuantity).toList();
        Order order = orderService.placeOrder(userId, itemIds, quantities);
        return new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("order placed", HttpStatus.OK,order),HttpStatus.OK);

    }

    public static class OrderRequest {
        private Long itemId;
        private int quantity;

        public Long getItemId() {
            return itemId;
        }

        public void setItemId(Long itemId) {
            this.itemId = itemId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

}
