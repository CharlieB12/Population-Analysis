package osu.cse2123;

/**
 * An interface for county population data
 * 
 * @author Jeremy Morris
 * @version 11/12/2020
 *
 */
public interface County {

	/**
	 * Sets the name of the county
	 * @param name the county name
	 * @postcond this county name is set to name
	 */
	void setName(String name);

	/**
	 * Returns the name of the county
	 * @return the name of this county
	 */
	String getName();

	/**
	 * Adds a population value for a specific year
	 * @param year the year to set the population for
	 * @param pop the population for the county in year
	 * @postcond the year/pop pair is added to this county
	 */
	void addPopulation(int year, int pop);

	/**
	 * Returns the population for a given year
	 * @param year the year to retrieve the population for
	 * @return the population for this county in the year year
	 */
	int getPopulation(int year);

}