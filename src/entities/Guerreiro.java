package entities;

import java.util.Objects;
import java.util.UUID;

public class Guerreiro implements Comparable<Guerreiro> {

	private double forca;
	private double defesa;
	private double alcance;
	private final UUID id;
	private Lutadores tipoGuerreiro;
	
	public Guerreiro(double forca, double defesa, double alcance, Lutadores tipoGuerreiro) {
		this.forca = forca * 0.5;
		this.defesa = defesa * 0.3;
		this.alcance = alcance * 0.2;
		this.tipoGuerreiro = tipoGuerreiro;
		this.id = UUID.randomUUID(); // <-- gera o ID aqui
	}
	

	public UUID getId() { return id; }

    public double getForca() { return forca; }
    public void setForca(double forca) { this.forca = forca; }

    public double getDefesa() { return defesa; }
    public void setDefesa(double defesa) { this.defesa = defesa; }

    public double getAlcance() { return alcance; }
    public void setAlcance(double alcance) { this.alcance = alcance; }

    public Lutadores getTipoGuerreiro() { return tipoGuerreiro; }
    public void setTipoGuerreiro(Lutadores tipoGuerreiro) { this.tipoGuerreiro = tipoGuerreiro; }


	public double rating() {
		return forca * 0.5 + defesa * 0.3 + alcance *0.2;
	}
	
   @Override
    public int compareTo(Guerreiro o) {
        return Double.compare(o.rating(), this.rating());
    }


   @Override
   public int hashCode() {
	return Objects.hash(id);
   }


   @Override
   public boolean equals(Object obj) {
	if (this == obj) return true;
	if (!(obj instanceof Guerreiro)) return false;
	Guerreiro other = (Guerreiro) obj;
	return id.equals(other.id);
   }
   
   @Override
   public String toString() {
       return "Guerreiro{" +
              "id=" + id +
              ", tipo=" + tipoGuerreiro +
              ", forca=" + forca +
              ", defesa=" + defesa +
              ", alcance=" + alcance +
              ", rating=" + String.format("%.2f", rating()) +
              '}';
   }
   
   
   
}
	
	
	
