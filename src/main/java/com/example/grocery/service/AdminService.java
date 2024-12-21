package com.example.grocery.service;

import com.example.grocery.controller.UserController;
import com.example.grocery.entity.Item;
import com.example.grocery.repository.GroceryRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    GroceryRepo groceryRepo;

    public void addItem(Item item){
        groceryRepo.save(item);
        logger.info("AdminService.addItem: item saved");
    }

    public List<Item> getAllItems() {
        return groceryRepo.findAll();
    }

    public boolean removeItem(Long id) {
        if(groceryRepo.existsById(id)){
            groceryRepo.deleteById(id);
            return true;
        }
        return false;
    }


    public Item updateItem(Long id, Item updatedItem) {
        if (groceryRepo.existsById(id)) {
            updatedItem.setId(id);
            return groceryRepo.save(updatedItem);
        }
        return null;
    }

    public boolean updateInventory(Long id, int quantity) {
        Optional<Item> itemOptional = groceryRepo.findById(id);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setQuantity(item.getQuantity() + quantity);
            groceryRepo.save(item);
            return true;
        }
        return false;
    }
}
