package com.usuario.service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.entity.Usuario;
import com.usuario.service.model.Coche;
import com.usuario.service.model.Moto;
import com.usuario.service.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping
	public ResponseEntity<List <Usuario>> getUsuarios(){
		List <Usuario> usuarios = usuarioService.getAll();
		if(usuarios.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Usuario> obtenerUsuario(@PathVariable("id") int id){
		Usuario usuario = usuarioService.getUusarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(usuario);
	}
	
	@PostMapping
	public ResponseEntity<Usuario> guardarUsuario(@RequestBody Usuario usuario){
		Usuario nuevoUsuario = usuarioService.save(usuario);
		return ResponseEntity.ok(nuevoUsuario);
	}
	
	@GetMapping("/coches/{usuarioId}")
	public ResponseEntity<List<Coche>> listarCoches(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUusarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Coche> coches = usuarioService.getCoches(id);
		return ResponseEntity.ok(coches);
	}
	
	@GetMapping("/motos/{usuarioId}")
	public ResponseEntity<List<Moto>> listarMotos(@PathVariable("usuarioId") int id){
		Usuario usuario = usuarioService.getUusarioById(id);
		if(usuario == null) {
			return ResponseEntity.notFound().build();
		}
		List<Moto> motos = usuarioService.getMotos(id);
		return ResponseEntity.ok(motos);
	}
	
	@PostMapping("/coche/{usuarioId}")
	public ResponseEntity<Coche> guardarCoche(@PathVariable("usuarioId") int id, @RequestBody Coche coche){
		Coche nuevoCoche = usuarioService.saveCoche(id, coche);
		return ResponseEntity.ok(nuevoCoche);
	}
	
	@PostMapping("/moto/{usuarioId}")
	public ResponseEntity<Moto> guardarMoto(@PathVariable("usuarioId") int id, @RequestBody Moto moto){
		Moto nuevaMoto = usuarioService.saveMoto(id, moto);
		return ResponseEntity.ok(nuevaMoto);
	}
	
	@GetMapping("/todos/{usuarioId}")
	public ResponseEntity<Map<String, Object>> getTodosLosVehiculos(@PathVariable("usuarioId") int id){
		Map<String,Object> resultado = usuarioService.getUsuarioAndVehiculos(id);
		return ResponseEntity.ok(resultado);
	}

}
