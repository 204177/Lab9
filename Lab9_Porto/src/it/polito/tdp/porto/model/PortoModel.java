package it.polito.tdp.porto.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.Multigraph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.porto.controller.PortoController;
import it.polito.tdp.porto.db.PortoDAO;

public class PortoModel {
	
	PortoDAO p = new PortoDAO();
	
	public UndirectedGraph<Autore, DefaultEdge> grafo =
			new SimpleGraph<Autore, DefaultEdge>(DefaultEdge.class);
	
	List<Autore> autori = new LinkedList<Autore>(p.getAutori());
	public List<Autore> visitati = new LinkedList<Autore>(p.getAutori());
	List<Connessione> connessioni = new LinkedList<Connessione>(p.getConnessioni());
	List<Articolo> articoli = new LinkedList<Articolo>(p.getArticoli());
	List<Articolo> artTemp = new LinkedList<Articolo>();
	List<Autore> cammino = new LinkedList<Autore>();
	public List<List<Autore>> cluster = new LinkedList<List<Autore>>();
	Articolo elimina = null;
	public int cont = 0;
	int contatore = 0;
	int aut = 0;
	
	
	
	public void creaGrafo(){
		
		for(Autore a : autori)
			grafo.addVertex(a);
		
		for(Connessione c : connessioni){
			Autore a = this.trovaAutore(c.getIdAutore());
			Articolo r = this.trovaArticolo(c.getIdArticolo());
			a.articoli.add(r);
			r.autori.add(a);
		}
		
		for(Articolo r : articoli){
			for(Autore uno : r.autori)
				for(Autore due : r.autori){
					if(uno.getId()!=due.getId())
						grafo.addEdge(uno, due);
				}
		}
		
	}
	
	public Autore trovaAutore(int id){
		for(Autore a : autori)
			if(a.getId()==id)
				return a;
		return null;
	}
	
	public Articolo trovaArticolo(long id){
		for(Articolo a : articoli)
			if(a.getId()==id)
				return a;
		return null;
	}

	public List<Autore> Coautori(String value) {
		
		List<Autore> coautori = new LinkedList<Autore>();
		
		Autore a = trovaAutorePerNome(value);
		coautori.addAll(Graphs.neighborListOf(grafo, a));
		
		return coautori;
	}

	public Autore trovaAutorePerNome(String value) {
		for(Autore a : autori)
			if(a.getCognome().compareTo(value)==0)
				return a;
		return null;
	}
	
	public void cluster(List<Autore> visitati){
		
		List<Autore> temp = new LinkedList<Autore>();
		
		if(aut==autori.size())
			return;
		
		GraphIterator<Autore, DefaultEdge> iterator = 
				new DepthFirstIterator<Autore, DefaultEdge>(grafo, visitati.get(0));
		
		temp.clear();
		
		while(iterator.hasNext()){
			Autore a = iterator.next();
			temp.add(a);
			visitati.remove(a);
			aut++;
		}
		
		cont++;
		
		cluster.add(temp);
		
		this.cluster(visitati);
		
	}
	
	public void camminoMinimo(String partenza, String arrivo){
		
		Autore uno = this.trovaAutorePerNome(partenza);
		Autore due = this.trovaAutorePerNome(arrivo);
		
		DijkstraShortestPath<Autore, DefaultEdge> dijkstra = new DijkstraShortestPath<Autore, DefaultEdge>(grafo, uno, due);
		GraphPath<Autore, DefaultEdge> path = dijkstra.getPath();
		if(path==null)
			return;
		cammino.addAll(Graphs.getPathVertexList(dijkstra.getPath()));
	}
	
	public List<Articolo> articoliConnessi(){
		//cammino è la lista di autori in cui ho salvato il camminoMinimo secondo dijkstra
		
		//Condizione di uscita : il mio contatore ha un valore uguale alla lunghezza della lista.
		//Significa che ho esplorato la mia intera lista e sono riuscito a collegare i due autori
		if(contatore == cammino.size())
			return artTemp;
		
		//Esploro la lista cammino andando a leggere il mio elemento, preso dall' indice
		//(dettato dal valore del contatore)della lista cammino e l'elemento successivo(grazie all'indice successivo)
		if(contatore<(cammino.size()-2)){
			for(Articolo uno : cammino.get(contatore).articoli){
				int successivo = contatore + 1;
				for(Articolo due : cammino.get(successivo).articoli){
					//Ciclo le due liste in modo da trovare gli articoli in comune
					if(uno.getId()==due.getId()){
						if(!artTemp.contains(uno)){
							//Salvo "elimina"(rappresenta l'articolo uguale) nel caso in cui la ricorsione mi porta al nulla
							elimina = uno;
							contatore++;
							//Aggiungo l'articolo nella lista nel caso in cui riesco ad arrivare alla fine
							artTemp.add(uno);
							//Richiamo il metodo in modo da andare al livello successivo
							articoliConnessi();
						}
					}
				}
			}
		}
		//Caso in cui il cammino è costuito da solo due autori
		if(cammino.size()==2){
			for(Articolo uno : cammino.get(contatore).articoli){
				int successivo = contatore + 1;
				for(Articolo due : cammino.get(successivo).articoli){
					if(uno.getId()==due.getId()){
						if(!artTemp.contains(uno)){
							elimina = uno;
							contatore++;
							artTemp.add(uno);
							return artTemp;
						}
					}
			}
}
			
		}
		// Se sono in questa zona significa che non sono riuscito a trovare
		// la lista di articoli che collega i due autori.
		// Effettuo il backtracking (Elimino articolo dalla lista, decremento contatore)
		
		this.contatore= contatore - 1;
		artTemp.remove(elimina);
		
		
		return null;
	}
	
	
}
