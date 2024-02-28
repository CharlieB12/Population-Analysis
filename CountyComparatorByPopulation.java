package osu.cse2123;
/**
 * A Comparator for County objects in descending order of population
 * 
 * @author YOUR NAME HERE
 * @version DATE HERE
 */

import java.util.Comparator;

public class CountyComparatorByPopulation implements Comparator<County> {
	
	private int year;
	
	public CountyComparatorByPopulation(int year) {
		this.year = year;
	}

	@Override
	public int compare(County o1, County o2) {
		int ret = 0;
		if(o1.getPopulation(this.year) < o2.getPopulation(this.year)){
			 ret = 1;
		} else if (o1.getPopulation(this.year) > o2.getPopulation(this.year)) {
			ret = -1;
		}
		return ret;
	}
	
}