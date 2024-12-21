package com.example.grocery.service;

import com.example.grocery.entity.Item;
import com.example.grocery.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    public List<Item> getlistOfItems() {
        List<Item> all = userRepo.findAll();
        List<Item> tempList = new ArrayList<>();
        for(Item item: all){
            if(item.getQuantity()>0){
                tempList.add(item);
            }
        }
        return tempList;
    }
}
