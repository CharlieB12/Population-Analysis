package osu.cse2123;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class CState implements State {
	
	private String name;
	
	 List<County> counties = new ArrayList<>();
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void addCounty(County county) {
		counties.add(county);
	}

	@Override
	public County getCounty(String name) {
		County c =  new CCounty();
		for(County county : counties) {
			if(county.getName() == name) {
				c = county;
			}
		}
		return c;
	}

	@Override
	public int getPopulation(int year) {
		int total = 0;
		for(County county : counties) {
			total += county.getPopulation(year);
		}
		return total;
	}

	@Override
	public List<County> getCounties(){
		//list to return
		List<County> counties_abc = new ArrayList<>();
		//temporary list for counties
		List<County> temp_counties = new ArrayList<County>(counties);
		//comparator for alphabetical order
		CountyComparatorByName cmp1 = new CountyComparatorByName();
		//priority queue for ordering counties
		PriorityQueue<County> alphabetical = new PriorityQueue<>(cmp1);
		//remove counties from temp and add to priority queue
		while(!temp_counties.isEmpty()) {
			alphabetical.add(temp_counties.remove(0));
		}
		//poll the priority queue and add to list.
		while(!alphabetical.isEmpty()) {
			counties_abc.add(alphabetical.poll());
		}
		return counties_abc;
	}

	@Override
	public List<County> getCountiesByPopulation(int year){
		//list to return
		List<County> counties_pop = new ArrayList<>();
		//temporary list for counties
		List<County> temp_counties2 = new ArrayList<County>(counties);
		//comparator for population order
		CountyComparatorByPopulation cmp2 = new CountyComparatorByPopulation(year);
		//priority queue for ordering counties
		PriorityQueue<County> by_pop = new PriorityQueue<>(cmp2);
		//remove counties from temp and add to priority queue
		while(!temp_counties2.isEmpty()) {
			by_pop.add(temp_counties2.remove(0));
		}
		//poll the priority queue and add to list.
		while(!by_pop.isEmpty()) {
			counties_pop.add(by_pop.poll());
		}
		return counties_pop;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
