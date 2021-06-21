package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> grafo;
	private FoodDao dao;
	private int N;
	private List<String> soluzioneMigliore;
	private int sommaPeso;
	private int sommaPesoBest;
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(int calorie) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, dao.vertici(calorie));
		for(Arco arco : dao.edges()) {
			if(this.grafo.containsVertex(arco.getA()) && this.grafo.containsVertex(arco.getB())) {
				Graphs.addEdgeWithVertices(grafo, arco.getA(), arco.getB(), arco.getPeso());
			}
		}
	}
	
	public Set<String> getVertexes(){
		return this.grafo.vertexSet();
	}
	
	public Set<DefaultWeightedEdge> getEdges(){
		return this.grafo.edgeSet();
	}
	
	public List<Arco> getConnessi(String vertice){
		List<Arco> list = new ArrayList<>();
		for(String adiacente : Graphs.neighborListOf(this.grafo, vertice)) {
			DefaultWeightedEdge e = grafo.getEdge(vertice, adiacente);
			list.add(new Arco(vertice, adiacente, (int) grafo.getEdgeWeight(e)));
		}
		return list;
	}
	
	public List<String> cammino(int N, String partenza){
		this.N=N;
		List<String> parziale = new LinkedList<>();
		this.soluzioneMigliore = new LinkedList<>();
		parziale.add(partenza);
		sommaPeso = 0;
		sommaPesoBest = 0;
		cerca(parziale);
		return this.soluzioneMigliore;
	}
	
	private void cerca(List<String> parziale) {
		if(parziale.size() == this.N) {
			if(this.sommaPeso > this.sommaPesoBest || this.sommaPesoBest == 0) {
				this.soluzioneMigliore = new LinkedList<>(parziale);
				this.sommaPesoBest = this.sommaPeso;
			}
			return;
		}
		
		for(String ad : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(ad)) {
				sommaPeso += grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), ad));
				parziale.add(ad);
				cerca(parziale);
				parziale.remove(parziale.size()-1);
				sommaPeso -= grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), ad));
			}
		}
	}
	
	public int getPesoTot() {
		return this.sommaPesoBest;
	}
}
