package osu.cse2123;
/**
 * A Comparator for County objects in ascending order of name
 * 
 * @author Charlie Britt
 * @version 12/1/2023
 */

import java.util.Comparator;

public class CountyComparatorByName implements Comparator<County> {
	

	@Override
	public int compare(County o1, County o2) {
		int ret = 0;
		if(o1.getName().compareToIgnoreCase(o2.getName()) < 0) {
			ret = -1;
		} else if(o1.getName().compareToIgnoreCase(o2.getName()) > 0) {
			ret = 1;
		}
		return ret;
	}
	
}