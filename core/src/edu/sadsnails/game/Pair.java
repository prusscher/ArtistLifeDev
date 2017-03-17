package edu.sadsnails.game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Pair {
	private ArrayList<String> key;
	private ArrayList<Object> value;

	/**
	 * Create a new empty Pair
	 */
	public Pair() {
		key = new ArrayList<String>();
		value = new ArrayList<Object>();
	}
	
	/**
	 * Create a Pair and initialize with the read method 
	 * @param filename The filename of the file to read
	 */
	public Pair(String filename) {
		super();
		read(filename);
	}
	
	/**
	 * Write the current Key:Value pairs to a file
	 * @param filename Name of the file to write the Key:Value pairs to
	 * @return true for successful read, false for fail
	 */
	public boolean write(String filename) {
		// Create the file writer
		BufferedWriter write = null;
		try {
			// Set the file at the filename
			write = new BufferedWriter(new FileWriter(filename));
			
			// Write all the Key:Value pairs
			for(int j = 0; j < key.size(); j++) {
				if(value.get(j).getClass().equals(String.class))
					write.write(key.get(j) + ":\"" + value.get(j).toString() + "\"\n");
				else
					write.write(key.get(j) + ":" + value.get(j).toString() + "\n");
			}
			// Close the file
			write.close();
			
			// Return true for good
			return true;
		} catch (IOException e) { e.printStackTrace(); }
		
		// Return false for bad
		return false;
	}
	
	/**
	 * Read Key:Value pairs from a file
	 * The method specifies items with chars 
	 * [0-9, -] are ints,
	 * [0-9, ., -] are floats,
	 * and all others are strings.
	 * @param filename The file to read from
	 */
	public boolean read(String filename) {
		// Create the scanner
		Scanner scan = null;
		try {
			// Get the file at the file name
			scan = new Scanner(new File(filename));
			
			// While the file still has lines
			while(scan.hasNextLine()) {
				// Split on the colon (:)
				String[] in = scan.nextLine().split(":");
				
				// Add the Key:Value Pair
				key.add(in[0]);
				
				// Parse the type
				if(!in[1].contains(".") && !checkIfString(in[1])) { // Int : Not a float or string
					value.add(new Integer(Integer.parseInt(in[1])));
				} else if(in[1].contains(".") && !checkIfString(in[1])) {
					value.add(new Float(Float.parseFloat(in[1])));
				} else {
					value.add(in[1].substring(1, in[1].length()-1));
				}
			}
			
			// Close the scanner
			scan.close();
			
			return true;
		} catch (FileNotFoundException e) { 
			return false;
		}
	}
	
	private boolean checkIfString(String in) {
		for(int j = 0; j < in.length(); j++) {
			if(in.charAt(j) != '0' && in.charAt(j) != '1' && in.charAt(j) != '2' && in.charAt(j) != '3' && in.charAt(j) != '4' && 
			   in.charAt(j) != '5' && in.charAt(j) != '6' && in.charAt(j) != '7' && in.charAt(j) != '8' && in.charAt(j) != '9' &&
			   in.charAt(j) != '.' && in.charAt(j) != '-') {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Get the number of Key:Value pairs
	 * @return Number of Pairs
	 */
	public int size() {
		return key.size();
	}
	
	/**
	 * Store an associated Key and String Value Or set the string value of an existing key
	 * @param key The key to associate with a value
	 * @param value The string value associated to a key
	 */
	public void putString(String key, String value) {
		if(this.key.indexOf(key) == -1) {
			this.key.add(key);
			this.value.add(new String(value));
		} else
			this.value.set(this.key.indexOf(key), new String(value));
	}
	
	/**
	 * Store an associated Key and int Value Or set the int value of an existing key
	 * @param key The key to associate with a value
	 * @param value The int value associated to a key
	 */
	public void putInt(String key, int value) {
		if(this.key.indexOf(key) == -1) {
			this.key.add(key);
			this.value.add(new Integer(value));
		} else
			this.value.set(this.key.indexOf(key), new Integer(value));
	}
	
	/**
	 * Store an associated Key and float Value Or set the float value of an existing key
	 * @param key The key to associate with a value
	 * @param value The float value associated to a key
	 */
	public void putFloat(String key, float value) {
		if(this.key.indexOf(key) == -1) {
			this.key.add(key);
			this.value.add(new Float(value));
		} else
			this.value.set(this.key.indexOf(key), new Float(value));
	}
	
	/**
	 * Get the string value for a specific key
	 * (returns null if the value doesn't exist or value isn't a string)
	 * 
	 * @param key The key to get the value for
	 * @return The string value of the specified key
	 */
	public String getString(String key) {
		if(this.key.indexOf(key) == -1 || value.get(this.key.indexOf(key)).getClass().getName().equals(String.class))
			return null;
		return (String) value.get(this.key.indexOf(key));
	}
	
	/**
	 * Get the int value for a specific key
	 * (returns -1 if the value doesn't exist or value isn't an int)
	 * 
	 * @param key The key to get the value for
	 * @return The int value of the specified key
	 */
	public int getInt(String key) {
		if(this.key.indexOf(key) == -1 || value.get(this.key.indexOf(key)).getClass().getName().equals(Integer.class))
			return -1;
		return ((Integer) value.get(this.key.indexOf(key))).intValue();
	}
	
	/**
	 * Get the float value for a specific key
	 * (returns null if the value doesn't exist or value isn't a float)
	 * 
	 * @param key The key to get the value for
	 * @return The float value of the specified key
	 */
	public float getFloat(String key) {
		if(this.key.indexOf(key) == -1 || value.get(this.key.indexOf(key)).getClass().getName().equals(Float.class))
			return -1.0f;
		return ((Float) value.get(this.key.indexOf(key))).floatValue();
	}
	
	public Object get(String key) {
		if(this.key.indexOf(key) == -1)
			return null;
		return value.get(this.key.indexOf(key));
	}
	
	/**
	 * Remove a specified Key:Value Pair
	 * @param key The key of the pair to remove
	 * @return The value of the removed Pair
	 */
	public Object remove(String key) {
		// Get the index of the Pair
		int o = this.key.indexOf(key);
		
		// Remove the key and remove/return the value
		this.key.remove(o);
		return value.remove(o);
	}
	
	/**
	 * Clear the Pair. Remove all keys and values
	 */
	public void clear() {
		key.clear();
		value.clear();
	}
	
	public String toString() {
		String out = "";
		out += "Pair : " + this.size() + "\n";
		for(int j = 0; j < this.size(); j++) {
			out += "[\"" + key.get(j) + "\" , " + value.get(j).toString() + "] " + value.get(j).getClass().getSimpleName() + "\n";
		}
		return out;
	}
}
