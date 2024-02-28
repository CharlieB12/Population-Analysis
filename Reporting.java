package osu.cse2123;
/**
 * Reports population data in a table based off of states CSV
 * 
 * @author Charlie Britt
 * @version 12/1/2023
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Reporting {
	
	
	/**
	 * Reads population file and returns map of state and state CSV
	 * 
	 * @param fname the name of the file
	 * @return a map with the state as a key and the CSV file as value
	 * @throws FileNotFoundException
	 */
	public static Map<State, String> states_csv(String fname) throws FileNotFoundException{
		//reads text file
		File textFile = new File(fname);
		Scanner scan = new Scanner(textFile);
		//map of state and CSV to be returned
		Map<State, String> state_and_csv = new HashMap<>();
		//while there are more lines in text file
		while(scan.hasNext()) {
			//grab line
			String line = scan.nextLine();
			//split it based on comma
			String[] stateAndFile = line.split(",");
			//create new state object
			State state = new CState();
			//set the state name
			state.setName(stateAndFile[0]);
			//create a key and value based on state object and CSV respectively.
			state_and_csv.put(state, stateAndFile[1]);
		}
		scan.close();
		return state_and_csv;
	}
	
	
	/**
	 * Creates a list of state objects with each one containing a list of county objects
	 * 
	 * @param map of state and csv files
	 * @return list of populated state objects based off of population files.
	 * @throws FileNotFoundException
	 */
	public static List<State> parse_csv(Map<State, String> map, String fname) throws FileNotFoundException {
		List<State> states = new ArrayList<>();
		
		//runs through every state and CSV pair
		for(Map.Entry<State, String> m : map.entrySet()) {
			File textFile = new File(m.getValue());
			Scanner scan = new Scanner(textFile);
			
			//creates an array of year values based off of CSV
			String years = scan.nextLine();
			String[] years_arr = years.split(",");
			
			//assigns a state object
			State state = m.getKey();
			
			//while there are more lines in state CSV
			while(scan.hasNext()) {
				//grab line
				String line = scan.nextLine();
				//split it based on comma
				String[] county_arr = line.split(",");
				//create county object
				County county = create_county(county_arr, years_arr);
				//add county to state
				state.addCounty(county);
				
			}
			//add state object to states list
			states.add(state);
		}
		//puts states in right order because the HashMap mixed them up above.
		states = sort_states(states, fname);
		return states;
	}
	
	
	/**
	 * This puts the states list in correct order because using a HashMap changed the ordering.
	 * Definitely better solutions for this but I couldn't come to one :/
	 * 
	 * @param states list of states
	 * @param fname file name to get original ordering in CSV
	 * @return a list of states in same order as CSV
	 * @throws FileNotFoundException
	 */
	public static List<State> sort_states(List<State> states, String fname) throws FileNotFoundException{
		//gets file
		File textFile = new File(fname);
		Scanner scan = new Scanner(textFile);
		//list for sorted states to be returned
		List<State> sorted = new ArrayList<>();
		//list for actual ordering of names
		List<String> names = new ArrayList<>();
		while(scan.hasNext()) {
			//grab line
			String line = scan.nextLine();
			//split it based on comma
			String[] stateAndFile = line.split(",");
			//add name to names list
			names.add(stateAndFile[0]);
		}
		//Puts the states object in new list based on original ordering CSV.
		for(int i = 0; i < names.size(); i++) {
			String name = names.get(i);
			for(int j = 0; j < states.size(); j++) {
				if(states.get(j).getName().equals(name)) {
					sorted.add(states.get(j));
				}
			}
		}
		scan.close();
		return sorted;
	}
	
	
	/**
	 * CSreates a county object
	 * 
	 * @param arr array of population values corresponding to each year
	 * @param years array of years
	 * @return county object
	 */
	public static County create_county(String[] arr, String[] years) {
		//creates a new county
		County county = new CCounty();
		//assigns name of county
		county.setName(arr[0]);
		//list for years
		List<Integer> year_int = new ArrayList<>();
		//assigns years from array to list
		for (int i = 1; i < years.length; i++) {
			year_int.add(Integer.parseInt(years[i]));
		}
		//goes through each county and assigns populations based on different years
		int i = 1;
		int j = 0;
		while(i < arr.length) {
			county.addPopulation(year_int.get(j), Integer.parseInt(arr[i]));
			i+=1;
			j+=1;
		}
		return county;
	}
	
	
	/**
	 * formats the final populations table
	 * 
	 * @param states list of state objects
	 * @param start_year the starting year
	 * @param end_year the ending year
	 */
	public static void format_table(List<State> states, String start_year, String end_year) {
		//start and end years as integers
		int start = Integer.parseInt(start_year);
		int end = Integer.parseInt(end_year);
		//header line
		String header = String.format("\n%s %15s %12s %12s","State/County", start_year, end_year, "Growth");
		System.out.println(header);
		String line = "--------------- ------------ ------------ ------------";
		System.out.println(line);
		//loops through each state object
		for(State state : states) {
			//counties sorted by end year population
			List<County> counties = state.getCountiesByPopulation(end);
			//State header information
			String state_name = state.getName();
			int sum_start_pop = state.getPopulation(start);
			int sum_end_pop = state.getPopulation(end);
			int sum_growth = sum_end_pop - sum_start_pop;
			System.out.println();
			System.out.println(line);
			System.out.println(String.format("%-15s %,12d %,12d %+,12d", state_name, sum_start_pop, sum_end_pop, sum_growth));
			System.out.println(line);
			//loops through each county and and gets the population for the selected start and end years
			for(County county : counties) {
				int start_pop = county.getPopulation(start);
				int end_pop = county.getPopulation(end);
				int growth = end_pop - start_pop;
				String county_name = county.getName();
				//shortens the county name if too many characters
				if(county_name.length() > 12) {
					county_name = county_name.substring(0, 12);
				}
				//prints formatted line
				System.out.println(String.format("   %-15s %,9d %,12d %+,12d", county_name, start_pop, end_pop, growth));
			}
			//prints the average and median of start and end year of specified state
			int start_ave = get_average(state, start);
			int end_ave = get_average(state, end);
			int start_median = get_median(state, start);
			int end_median = get_median(state, end);
			System.out.println(line);
			System.out.println(String.format("   %-15s %,9d %,12d", "Average pop.", start_ave, end_ave));
			System.out.println(String.format("   %-15s %,9d %,12d", "Median pop.", start_median, end_median));
		}
	}
	
	
	/**
	 * gets the average population from state and year
	 * 
	 * @param state object for state
	 * @param year 
	 * @return the average population for the state and year
	 */
	public static int get_average(State state, int year) {
		List<County> counties = state.getCountiesByPopulation(year);
		int ave = state.getPopulation(year) / counties.size();
		return ave;
	}
	
	
	/**
	 * gets the median population from state and year
	 * 
	 * @param state object for state
	 * @param year
	 * @return the median population for the state and year
	 */
	public static int get_median(State state, int year) {
		List<County> counties = state.getCountiesByPopulation(year);
		int idx = (counties.size() / 2);
		int ave = counties.get(idx).getPopulation(year);
		return ave;
	}
	
	
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter a list of population files: ");
		String fname = scan.nextLine();
		System.out.print("Enter a start year: ");
		String start_year = scan.nextLine();
		System.out.print("Enter an end year: ");
		String end_year = scan.nextLine();
		
		try {
			Map<State, String> newMap = states_csv(fname);
			List<State> states = parse_csv(newMap, fname);
			format_table(states, start_year, end_year);
		} catch (Exception e) {
			System.out.println("File or year values are invalid. Try again");
		}
	
		
	}

}
