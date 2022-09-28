package com.coche.service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coche.service.model.Coche;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Integer> {
	
	List<Coche> findByUsuarioId(int usuarioId);

}
