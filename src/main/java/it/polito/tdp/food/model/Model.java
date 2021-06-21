package it.polito.tdp.food.model;

import java.util.ArrayList;
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
}
