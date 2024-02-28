package osu.cse2123;

import java.util.HashMap;
import java.util.Map;

public class CCounty implements County {
	
	private String name;
	private int year;
	private int pop;
	
	Map<Integer, Integer> year_pop = new HashMap<>();
	
	//Constructor
	public CCounty() {
		this.name = "";
		this.year = 0;
		this.pop = 0;
	}
	
	@Override
	public void setName(String name) {
		//sets name
		this.name = name;
	}

	@Override
	public String getName() {
		//gets name
		return this.name;
	}

	@Override
	public void addPopulation(int year, int pop) {
		this.year = year;
		this.pop = pop;
		//a map with year and correlating population
		year_pop.put(this.year, this.pop);
	}

	@Override
	public int getPopulation(int year) {
		return year_pop.get(year);
	}
	
	@Override
	public String toString() {
		return String.format("%s %s", this.name, this.pop);
	}
}
