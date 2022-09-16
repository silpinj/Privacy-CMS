/**
 *
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

 * This code is also based on:
 * https://www.partow.net/programming/hashfunctions/#top
 */

package Functions;

/**
 * Class for hash functions implementation: Robert Sedgwicks and Justin Sobel hash functions.
 * 
 * @author SilviaPinilla
 *
 */

public class Hash {

	/*
	 * JS Hash
	 */
	public static int JSHash(int value) {
		int hash = 1315423911;
		int i = 0;
		for (i = 0; i < 8; i++) {
			hash ^= ((hash << 5) + value + (hash >> 2));
		}

		return hash;
	}
	
	/*
	 * RS Hash
	 */
	public static int RSHash(int value) {
		int b = 378551;
		int a = 63689;
		int hash = 0;
		int i = 0;
		for (i = 0; i < 8; i++) {
			hash = hash * a + value;
			a = a * b;
		}

		return hash;
	}
	
}
