package com.example.demo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Order;
import com.example.demo.enums.OrderStatus;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long>{

	Order findByUserIdAndOrderStatus(long id, OrderStatus pending);	

}
