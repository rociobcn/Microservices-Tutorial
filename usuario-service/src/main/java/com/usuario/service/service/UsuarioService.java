package com.usuario.service.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.usuario.service.entity.Usuario;
import com.usuario.service.feignclients.CocheFeignClient;
import com.usuario.service.feignclients.MotoFeignClient;
import com.usuario.service.model.Coche;
import com.usuario.service.model.Moto;
import com.usuario.service.repository.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	private RestTemplate restTemplate; 
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CocheFeignClient cocheFeignClient;
	
	@Autowired
	private MotoFeignClient motoFeignClient;
	

	
	public List <Usuario> getAll(){
		return usuarioRepository.findAll();
	}
	
	public Usuario getUusarioById(int id) {
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Usuario save(Usuario usuario) {
		Usuario nuevoUsuario = usuarioRepository.save(usuario);
		return nuevoUsuario;
	}
	
	public List<Coche> getCoches(int usuarioId){
		List<Coche> coches = restTemplate.getForObject("http://localhost:8002/coche/usuario/" + usuarioId, List.class);
		return coches;
	}
	
	public List<Moto> getMotos(int usuarioId){
		List<Moto> motos = restTemplate.getForObject("http://localhost:8003/moto/usuario/" + usuarioId, List.class);
		return motos;
	}
	
	public Coche saveCoche(int usuarioId, Coche coche) {
		coche.setUsuarioId(usuarioId);
		Coche nuevoCoche = cocheFeignClient.save(coche);
		return nuevoCoche;
	}
	
	public Moto saveMoto(int usuarioId, Moto moto) {
		moto.setUsuarioId(usuarioId);
		Moto nuevaMoto = motoFeignClient.save(moto);
		return nuevaMoto;
	}
	
	public Map<String, Object> getUsuarioAndVehiculos(int usuarioId){
		Map<String,Object> resultado = new HashMap<>();
		Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
		if (usuario == null) {
			resultado.put("Mensaje", "El usuario no existe");
			return resultado;
		}
		resultado.put("Usuario", usuario);
		List<Coche> coches = cocheFeignClient.getCoches(usuarioId);
		if(coches.isEmpty()) {
			resultado.put("Coches", "El usuario no tiene coches");
		} else {
			resultado.put("Coches", coches);
		}
		List<Moto> motos = motoFeignClient.getMotos(usuarioId);
		if(coches.isEmpty()) {
			resultado.put("Motos", "El usuario no tiene motos");
		} else {
			resultado.put("Motos", motos);
		}
		return resultado;
	}

}
