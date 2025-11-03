package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import entities.Guerreiro;
import entities.Lutadores;

public class RosterService {
	private final List<Guerreiro> lutadores = new ArrayList<>();
	
	 public UUID adicionarLutador(Guerreiro g) {
	        if (g == null) throw new IllegalArgumentException("lutador não pode ser nulo");
	        // validações adicionais de negócio (ex: nomes únicos) podem entrar aqui
	        lutadores.add(g);
	        return g.getId();
	    }
	
	 public boolean update(UUID id, Lutadores novoTipo,
             Double novaForca, Double novaDefesa, Double novoAlcance) {
			Guerreiro g = findById(id).orElse(null);
			if (g == null) return false;
			
			
			if (novoTipo != null) g.setTipoGuerreiro(novoTipo);
			if (novaForca != null) g.setForca(novaForca);
			if (novaDefesa != null) g.setDefesa(novaDefesa);
			if (novoAlcance != null) g.setAlcance(novoAlcance);
			
			return true;
			}
	
	public boolean removerLutador(UUID id) {
		return lutadores.removeIf(g -> g.getId().equals(id));
		//Quando você faz lutadores.stream().filter(...), o filter espera um Predicate<Guerreiro>, 
		//ou seja, uma função que recebe um Guerreiro e retorna boolean.
	}
	
	public Optional<Guerreiro> findById(UUID id){
		if(id == null) return Optional.empty();
		
		return lutadores.stream().filter(g -> g.getId().equals(id)).findFirst();
		
	}
	
	
	public void listarLutadores() {
		for (Guerreiro x : lutadores) {
			System.out.println(x);
		}
	}
	
	public int size() {
	    return lutadores.size();
	}
	
	public List<Guerreiro> listAll() {
	    return java.util.Collections.unmodifiableList(new ArrayList<>(lutadores));
	}
	
	public List<Guerreiro> listSortedByRatingDesc() {
	    return lutadores.stream()
	            .sorted() // usa compareTo do Guerreiro
	            .collect(Collectors.toUnmodifiableList());
	}
}
