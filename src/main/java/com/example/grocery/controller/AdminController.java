package com.example.grocery.controller;


import com.example.grocery.entity.Item;
import com.example.grocery.factory.ApplicationFactory;
import com.example.grocery.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    // Add new grocery items to the system
    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody Item item){

        adminService.addItem(item);
        return new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("All products", HttpStatus.CREATED),HttpStatus.CREATED);
    }

    // View existing grocery items
    @Cacheable(cacheNames = "items")
    @GetMapping("/getAllItems")
    public ResponseEntity<?> getAllItems(){
        return new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("All items fetched successfully", HttpStatus.FOUND,adminService.getAllItems()),HttpStatus.FOUND);
    }

    // Remove grocery items from the system
    @CacheEvict(cacheNames = "items")
    @DeleteMapping("/deleteItem/{id}")
    public ResponseEntity<?> removeItems(@PathVariable Long id){
        if(adminService.removeItem(id)){
            return new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("item deleted successfully", HttpStatus.OK),HttpStatus.OK);
        }
        return new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("item not found", HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    @CachePut(cacheNames = "items")
    @PutMapping("/updateItem/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody Item updatedItem){
        Item updatedGroceryItem = adminService.updateItem(id, updatedItem);
        return updatedGroceryItem != null ?
                new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("Item updated successfully", HttpStatus.OK, updatedGroceryItem),HttpStatus.OK) : new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("Item not found", HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }

    @CachePut(cacheNames = "items")
    @PutMapping("/{id}/inventory")
    public ResponseEntity<?> updateInventory(@PathVariable Long id, @RequestParam int quantity) {
        boolean isUpdated = adminService.updateInventory(id, quantity);
        return isUpdated ?
                new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("Item updated successfully", HttpStatus.OK),HttpStatus.OK) : new ResponseEntity<>(ApplicationFactory.getInstance().createResponse("Item not found", HttpStatus.NOT_FOUND),HttpStatus.NOT_FOUND);
    }


}
