package Functions;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Obtain the count of an specific IPv4 address by querying the CMS.
 * Given an IPv4 address, all hashes are calculated. Position for these hashes is consulted on the CMS table.
 * Number of hashes  = depth table = number of counts retrieved.
 * As CMS only overestimate counts, the count returned will be the minimum of the ones obtained.
 * 
 * @author silviapinilla
 *
 */

public class Query {

	/*
	 * Method that, given an IPv4 address, returns the count of the CMS.
	 */
	public static long obtainCount_element(CMS cms, String ip) {
		
		// Calculate decimal value of IP address.
		long ip_dec = ip2dec(ip);
		long hashval = 0;

		ArrayList<Long> counts = new ArrayList<Long>();
			
		// Calculate all hashes values for this IP address.
		for (int i = 0; i < cms.depth; i++) {
			// Look for this hash values on the table and obtain the count.
			//hashval = ((cms.hashes[i][0] * ip_dec + cms.hashes[i][1]) % CMS.LONG_PRIME) % cms.width;
			long hash_1 = cms.hashes[i][0] * ip_dec + cms.hashes[i][1] *ip_dec + ip_dec;
			if(hash_1 < 0) {
				hash_1 = hash_1 * (-1);
			}
			hashval = ( hash_1 % CMS.LONG_PRIME) % cms.width;
			counts.add(cms.table[i][(int) hashval]);
		}
		// Returns minimum values of the ones obtained
		long min_count = Collections.min(counts);
			
		return min_count;
	}
	
	/*
	 * Method that, given a file with IPv4 addresses in X.X.X.X format, returns another file
	 * with the count on the CMS of all these IPv4 addresses. 
	 * The returned file will have a format like: 
	 * IPv4 		count
	 * X.X.X.X 		1000
	 */
	public static void obtainCount_file(CMS cms, String filename_queries, String filename_result){
			
			if(verifyFile(filename_queries)) {

				try {
					File file_queries = new File(filename_queries);
					File file_results = new File(filename_result);
					//If file for results already exists, delete it and create it again
					if(file_results.createNewFile() == false) {
						file_results.delete();
					}
					file_results.createNewFile();
					
					BufferedReader reader = new BufferedReader(new FileReader(file_queries));
					FileWriter writer = new FileWriter(file_results);
					
					// Reads all lines of the file (all IPv4 universe)
					String line = reader.readLine();
					
					while(line != null) {
						// Calculate decimal value of IP address.
						long ip_dec = ip2dec(line);
						long hashval = 0;
						
						//long counts[] = new long[(int) cms.depth];
						ArrayList<Long> counts = new ArrayList<Long>();
						
						// Calculate all hashes values for this IP address.
						for (int i = 0; i < cms.depth; i++) {
							// Look for this hash values on the table and obtain the count.
							hashval = ((cms.hashes[i][0] * ip_dec + cms.hashes[i][1]) % CMS.LONG_PRIME) % cms.width;
							counts.add(cms.table[i][(int) hashval]);
						}
						
						long min_count = Collections.min(counts);

						// Save the IP and the count in a different file.
						saveIP(line, min_count, writer);
						line = reader.readLine();
					}
					reader.close();
					writer.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	
	/*
	 * Method to verify if the file exists and if it is readable.
	 * Reused from InsertData.java
	 */
	public static boolean verifyFile(String filename) {
		File file = new File(filename);
		
		if(file.isFile() && file.canRead()) {
			return true;
		} else {
			System.out.println("File " + filename + " does not exist");
			return false;
		}
	}
	
	/*
	 * Method to convert an IPv4 address into a decimal number.
	 * Reused from InsertData.java
	 */
	public static long ip2dec(String ip) {
		String octets[] = ip.split("\\.");
		String ip_binary = "";
		for(int i = 0; i < octets.length; i ++) {
			int part = Integer.valueOf(octets[i]);
			ip_binary += Integer.toBinaryString(part);
		}
		long ip_int = Long.parseLong(ip_binary, 2);
		
		return ip_int;
	}
	
	/*
	 * Method that saves the IPv4 address and the count on a file that is created the first time.
	 * The file has the following content:
	 * 192.168.23.24      count
	 */
	public static void saveIP(String ip, long count, FileWriter writer) {
		
		try {
			
			String spaces = "";
			if(ip.length() == 14) {
				spaces = " ";
			}
			if(ip.length() == 13) {
				spaces = "  ";
			}
			if(ip.length() == 12) {
				spaces = "   ";
			}
			if(ip.length() == 11) {
				spaces = "    ";
			}
			if(ip.length() == 10) {
				spaces = "     ";
			}
			if(ip.length() == 9) {
				spaces = "      ";
			}
			if(ip.length() == 8) {
				spaces = "       ";
			}
			if(ip.length() == 7) {
				spaces = "        ";
			}
			writer.write(String.valueOf(ip) + spaces + "\t\t" + String.valueOf(count) + "\n");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
