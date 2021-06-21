package it.polito.tdp.food.model;

public class Arco {
	private String a;
	private String b;
	private Integer peso;
	public Arco(String a, String b, Integer peso) {
		super();
		this.a = a;
		this.b = b;
		this.peso = peso;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	public String getB() {
		return b;
	}
	public void setB(String b) {
		this.b = b;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	

}
