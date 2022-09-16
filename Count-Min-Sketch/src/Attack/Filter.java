package Attack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to calculate the average count for all IPv4 addresses consulted on IPv4Universe.java.
 * Based on this average value, a threshold is established.
 * Then, it creates a file with the addresses that have a count higher than the threshold.
 * 
 * IMPORTANT: the execution of this class would be needed when calculating the average value of count
 * from the attacker perspective. 
 * As commented in IPv4Universe.java, it would require a lot of time.
 * To execute this class it is needed to modify IPv4Universe.java class to save ALL values 
 * (without using a threshold), and then running this class.
 * 
 * @author SilviaPinilla
 *
 */
public class Filter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String input_file;
		String output_file;
		
		if(args.length == 2) {
			input_file = args[0];
			output_file = args[1];
		}
		else {
			System.out.println("Filtering data using a threshold\n...\n"
					+ "You must enter 2 arguments separated by an space: \n   "
					+ "- File with input data \n  - Name of the file to save filtered data \n\n"
					+ "Please, write filenames between double quotes");
			return ;
		}
			
		ArrayList<String[]> data = obtainData(input_file);
			
		// Calculate average value of count
		float sum = 0;
		for(int i = 0; i < data.size(); i++) {
			sum += Long.valueOf(data.get(i)[0]);
		}
		float mean = sum / data.size();
			
		//Define a threshold
		float threshold = 2 * mean;
			
		filterData(data, threshold, output_file);
	}
	
	
	/*
	 * Method that reads the content of a file of 2 columns and save it on an ArrayList
	 */
	public static ArrayList<String[]> obtainData(String filename) {
		
		ArrayList<String[]> data = null;
		
		try {
			// Read file with input data
			File in = new File(filename);
			BufferedReader reader = new BufferedReader(new FileReader(in));
			String line = reader.readLine(); //First line = header
			line = reader.readLine();
			
			//Save all data in an ArrayList
			data = new ArrayList<String[]>();
			while(line != null) {
				String count = line.split("\t")[0];
				String ip = line.split("\t")[1];
				data.add(new String[] {count,ip});
			}
			reader.close();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return data;
	}
	
	/*
	 * Method that save in a file values of an ArrayList that has a higher value than a threshold
	 */
	public static void filterData(ArrayList<String[]> data, float threshold, String output_file) {
		
		try {
			// Create file to save filtered data
			File out = new File(output_file);
			
			if(out.createNewFile() == false) {
				out.delete();
			}
			out.createNewFile();
			
			FileWriter writer = new FileWriter(out);
			writer.write("Count \t\t IP\n");
			
			// Save only values with a count > threshold
			for(int i = 0; i < data.size(); i++) {
				long count = Long.valueOf(data.get(i)[0]);
				String ip = data.get(i)[1];
				if(count >= threshold) {
					writer.write(String.valueOf(count) + "\t" + String.valueOf(ip) + "\n");
				}
			}
			
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
