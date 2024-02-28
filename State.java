package osu.cse2123;


import java.util.List;

public interface State {

	/**
	 * Sets the name of the state
	 * @param name the state name
	 * @postcond this state name is set to name
	 */
	void setName(String name);

	/**
	 * Returns the name of the state
	 * @return the name of this state
	 */
	String getName();

	/**
	 * Adds a county to this state
	 * @param county county to add to this state
	 * @postcond county is added to the state's collection of counties
	 */
	void addCounty(County county);

	/**
	 * Returns a county based on its name
	 * @param name name of the county to retrieve
	 * @precond a county by the name provided is in the state
	 * @return the county associated with name
	 */
	County getCounty(String name);

	/**
	 * Returns the total population of the state for a given year
	 * @param year year to get the population for
	 * @return total population for the state in the year year
	 */
	int getPopulation(int year);

	/**
	 * Returns a list of counties in alphabetical order by county name
	 * @return a list of counties in the state
	 */
	List<County> getCounties();

	/**
	 * Returns a list of counties in order of descending population
	 * @param year year to use to order counties by population
	 * @return list of counties in order of descending population
	 */
	List<County> getCountiesByPopulation(int year);



}