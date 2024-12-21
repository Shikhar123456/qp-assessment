package com.example.grocery.repository;

import com.example.grocery.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Item,Long> {
}
