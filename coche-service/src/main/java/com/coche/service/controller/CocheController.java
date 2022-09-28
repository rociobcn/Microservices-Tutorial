package com.coche.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coche.service.model.Coche;
import com.coche.service.service.CocheService;



@RestController
@RequestMapping("/coche")
public class CocheController {
	
	@Autowired
	CocheService cocheservice;
	
	@GetMapping
	public ResponseEntity<List <Coche>> getCoches(){
		List <Coche> coches = cocheservice.getAll();
		if(coches.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(coches);
	}
	@GetMapping("/{id}")
	public ResponseEntity<Coche> obtenerCoche(@PathVariable("id") int id){
		Coche coche = cocheservice.getCocheById(id);
		if(coche == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(coche);
	}
	
	@PostMapping
	public ResponseEntity<Coche> guardarCoche(@RequestBody Coche coche){
		Coche nuevoCoche = cocheservice.save(coche);
		return ResponseEntity.ok(nuevoCoche);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List <Coche>> getCochesporUsuariosId (@PathVariable("usuarioId") int id ){
		List <Coche> coches = cocheservice.byUsuarioId(id);
		if(coches.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(coches);
	}
	

}
