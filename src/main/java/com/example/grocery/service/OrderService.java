package com.example.grocery.service;

import com.example.grocery.Exception.IllegalArgumentException;
import com.example.grocery.entity.Item;
import com.example.grocery.entity.Order;
import com.example.grocery.entity.OrderItem;
import com.example.grocery.repository.GroceryRepo;
import com.example.grocery.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    GroceryRepo groceryRepo;

    @Autowired
    OrderRepo orderRepo;

    public Order placeOrder(Long userId, List<Long> itemIds, List<Integer> quantities) throws IllegalArgumentException {
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (int i = 0; i < itemIds.size(); i++) {
            Long itemId = itemIds.get(i);
            int quantity = quantities.get(i);

            // Fetch the item from the database
            Item item = groceryRepo.findById(itemId).orElse(null);
            if (item == null || item.getQuantity() < quantity) {
                throw new IllegalArgumentException("Item not available or insufficient stock");
            }

            // Create an OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setGroceryItem(item);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(item.getPrice());

            // Update stock
            item.setQuantity(item.getQuantity() - quantity);

            orderItems.add(orderItem);
            totalAmount += item.getPrice() * quantity;
        }

        // Save the order
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        return orderRepo.save(order);
    }
}
