package com.example.demo.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.CartItems;

@Repository
public interface CartItemsRepo extends JpaRepository<CartItems, Long> {

	Optional<CartItems> findByUserIdAndProductIdAndOrderId(Long id, Long id2, Long id3);
	
	List<CartItems> findByOrderId(Long orderId);

}
