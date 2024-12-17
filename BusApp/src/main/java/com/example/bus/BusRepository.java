package com.example.bus;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {

	@SuppressWarnings("unchecked")
	com.example.bus.Bus save(com.example.bus.Bus bus);

}
