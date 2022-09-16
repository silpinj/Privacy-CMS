/**
 * This code is based on the mladron C++ implementation.

	MIT License
	
	Copyright (c) 2022 mladron
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
 */

package Functions;

/**
 * Class to create CMS table of dimensions specified and initialize the hashes.
 * It has also a method to update the content of the table (insert a new element).
 * 
 * @author SilviaPinilla
 *
 */

public class CMS {

	static long LONG_PRIME = 4294967311l;
	static long RAND_MAX = 0x7fffffff;
	long width;
	long depth;
	long items;
	long [][] table;
	long [][] hashes;
	
	/*
	 * Constructor
	 */
	public CMS(int width, int depth) {
		this.width = width;
		this.depth = depth;
		
		//Create table (2D-array of integer)
		table = new long[depth][width];
		
		hashes = new long[depth][2];

		for (int i = 0; i < depth; i++) {
			hashes[i] = new long[2];
			genhash(hashes, i);
		}
	}
	
	/*
	 * Method to generate the "seed" for hash functions.
	 */
	public void genhash(long[][] hashes, int i) {
		//int seed = 45687;
		long random_number = (long) (Math.random() * RAND_MAX);
		hashes[i][0] = Hash.JSHash ((int) ((random_number * LONG_PRIME) / RAND_MAX + 1));
		if(hashes[i][0] < 0) {
			hashes[i][0] = hashes[i][0] * (-1);
		}
		hashes[i][1] = Hash.RSHash((int)((random_number * LONG_PRIME) / RAND_MAX + 1));
		if(hashes[i][1] < 0) {
			hashes[i][1] = hashes[i][1] * (-1);
		}

	}
	
	/*
	 * Method to insert a element on the CMS.
	 * It calculates the hash and add 1 to the position of the table.
	 */
	public void update(long item) {
		items = items + 1;

		long hashval = 0;

		for (int i = 0; i < depth; i++) {
			//hashval = ((hashes[i][0] * item + hashes[i][1]) % LONG_PRIME) % width;
			long hash_1 = hashes[i][0] * item + hashes[i][1]*item + item;
			if(hash_1 < 0) {
				hash_1 = hash_1 * (-1);
			}
			hashval = (hash_1 % LONG_PRIME) % width;
			table[i][(int) hashval] = table[i][(int) hashval] + 1;
		}
	}

	/*
	 * Setters and Getters
	 */
	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getDepth() {
		return depth;
	}

	public void setDepth(long depth) {
		this.depth = depth;
	}

	public long[][] getTable() {
		return table;
	}

	public void setTable(long[][] table) {
		this.table = table;
	}
	
}
