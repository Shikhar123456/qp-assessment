package com.example.grocery.repository;

import com.example.grocery.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryRepo extends JpaRepository<Item,Long> {


}
