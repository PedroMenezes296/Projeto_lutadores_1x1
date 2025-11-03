package application;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

import entities.DefaultBattleJudge;
import entities.Guerreiro;
import entities.Lutadores;
import entities.Outcome;
import services.RosterService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		RosterService roster = new RosterService();
		while(true) {
		System.out.println("\n----------Menu----------");
		System.out.println("\n1) Adicionar um lutador");
		System.out.println("\n2) Remover um lutador");
		System.out.println("\n3) Escolher lutadores para 1x1");
		System.out.println("\n4) Listar ranking e ID");
		System.out.println("\n5) Sair");
		int opcao = sc.nextInt();
		
		switch(opcao) {
		
		case 1:
			int i = 0;
			System.out.println("\n----------Criando Lutador----------");
			for (Lutadores tipo : Lutadores.values()) {
			    System.out.println(i+1 + ") " + tipo);
			    i++;
			}
			System.out.print("\nInforme o tipo do lutador: ");
			int opcaoLutador = sc.nextInt();
			Lutadores tipoLutador = Lutadores.values()[opcaoLutador-1];
			System.out.println("\n Voce escolheu: " + tipoLutador);
			
			System.out.print("\nInforme a forca: ");
			double forca = sc.nextDouble();
			System.out.print("\nInforme a defesa: ");
			double defesa = sc.nextDouble();
			System.out.print("\nInforme a alcance: ");
			double alcance = sc.nextDouble();
			Guerreiro guerreiro = new Guerreiro(forca,defesa,alcance, tipoLutador);
			UUID id = roster.adicionarLutador(guerreiro);
			System.out.println("\nLutador salvo! ID: " + id );
			break;
			
		case 2:
			List<Guerreiro> lista = roster.listSortedByRatingDesc();
			imprimirComIndices(lista);
			System.out.printf("\nInforme o numero do Lutador que deseja excluir: ");
			int numeroEscolhidoRemover = sc.nextInt();
			Guerreiro guerreiroParaRemocao = lista.get(numeroEscolhidoRemover);
			roster.removerLutador(guerreiroParaRemocao.getId());
			break;
			
		case 3:
			if(roster.size() < 2) {
				System.out.println("\nPrecisa de pelo menos 2 lutadores para duelar.");
		        break;
			}
			List<Guerreiro> lista1 = roster.listSortedByRatingDesc();
			imprimirComIndices(lista1);
			System.out.println("\nNumero do lutador A: ");
			int NumeroLutador1 = sc.nextInt();
			System.out.println("\nNumero do lutador B: ");
			int NumeroLutador2 = sc.nextInt();
			
			if(NumeroLutador1 < 1 || NumeroLutador1 > lista1.size() || 
				NumeroLutador2 < 1 || NumeroLutador2 > lista1.size()) {
				 System.out.println("Índice inválido.");
				break;
			}
			Guerreiro A = lista1.get(NumeroLutador1 - 1);
			Guerreiro B = lista1.get(NumeroLutador2 - 1);
			System.out.println("\nSelecionados:");
		    System.out.println("A -> " + A.getId() + " | " + A.getTipoGuerreiro());
		    System.out.println("B -> " + B.getId() + " | " + B.getTipoGuerreiro());
		    
		    Outcome resultado = new DefaultBattleJudge().decide(A, B);
		    System.out.println("Resultado: " + resultado);
			break;
			
		case 4:
			// pega a lista ordenada pelo compareTo (rating desc)
		    List<Guerreiro> ranking = roster.listSortedByRatingDesc();

		    if (ranking.isEmpty()) {
		        System.out.println("\nNenhum lutador cadastrado ainda.");
		        break;
		    }

		    System.out.println("\n--- Ranking por Rating (desc) ---");
		    imprimirRanking(ranking);
		    break;
			
		case 5:
			
			break;
		}
		
		
		}
		
		//sc.close();
	}
	
	private static void imprimirComIndices(List<Guerreiro> lista) {
		 if (lista == null || lista.isEmpty()) {
		        System.out.println("(Nenhum lutador para listar)");
		        return;
		    }
		 System.out.println("\nF = Forca\nD = Defesa\nA = Alcance\n");
		for (int i=0; i<lista.size(); i++) {
			Guerreiro g = lista.get(i);
			System.out.printf("%d) ID=%s | rating=%.2f | tipo=%s | F=%.1f D=%.1f A=%.1f%n\n",
	                i + 1, g.getId(), g.rating(), g.getTipoGuerreiro(),
	                g.getForca(), g.getDefesa(), g.getAlcance());
		}
	}
	
	private static void imprimirRanking(List<Guerreiro> lista) {
	    if (lista == null || lista.isEmpty()) {
	        System.out.println("(Nenhum lutador para listar)");
	        return;
	    }
	    for (int i = 0; i < lista.size(); i++) {
	        Guerreiro g = lista.get(i);
	        System.out.printf("%d) ID=%s | tipo=%s | rating=%.2f%n",
	                i + 1, g.getId(), g.getTipoGuerreiro(), g.rating());
	    }
	}
}
