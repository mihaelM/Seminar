package hr.fer.seminar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Implementation of fast algorithm using maps for storing sparse matrices.
 * 
 * @author Mihael Maroviæ
 *
 */
public class FastDynamicMapInput {

	/**
	 * Calculates dot product.
	 * 
	 * @param a
	 *            first vector
	 * @param b
	 *            second vector
	 * @return dot products of given sparse vectors
	 */
	private static int dotProduct(Map<Integer, Integer> a,
			Map<Integer, Integer> b) {
		int rez = 0;
		for (Entry<Integer, Integer> en : a.entrySet()) {
			if (b.containsKey(en.getKey())) {
				rez += en.getValue() * b.get(en.getKey());
			}
		}
		return rez;
	}

	/**
	 * We calculate final vector for given test and out original matrix.
	 * 
	 * @return final vector with given information
	 */
	private static List<Integer> finalVector(Map<Integer, Integer> test,
			List<Map<Integer, Integer>> matrix) {
		List<Integer> finalVector = new ArrayList<>(); // dimension will be 12
		for (Map<Integer, Integer> l : matrix) {
			finalVector.add(dotProduct(test, l));
		}
		return finalVector;
	}

	/**
	 * Finds out the biggest number in list.
	 * 
	 * @param finalVector
	 *            list with final results.
	 * @return biggest number
	 */
	private static int printStatistic(List<Integer> finalVector) {
		int i = 0, mostSimilar = -1, mx = -1;
		for (Integer x : finalVector) {
			if (x > mx) {
				mx = x;
				mostSimilar = i;
			}
			i++;
		}
		// System.out.println("Najslicniji je: " + mostSimilar);
		return mostSimilar;
	}

	/**
	 * Reads test file and does algorithm.
	 * 
	 * @throws IOException
	 *             if reading goes wrong
	 */
	private static void readFromFileAndSolve() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("Testing7.fq"))));
		String line;
		final int constSize = 13;
		int[] frequencyStatistic = new int[constSize];
		for (int j=1; j<constSize; j++) frequencyStatistic[j] = 0;
		int i = 0;
		List<Map<Integer, Integer>> matrix = readInputMatrix();

		while ((line = br.readLine()) != null) {

			if (!(line.startsWith("A") || line.startsWith("T")
					|| line.startsWith("C") || line.startsWith("G")))
				continue;

			// System.out.println(line);
			Map<Integer, Integer> l = createArrayOfNumbersSliding(line, 12);

			List<Integer> finalVector = finalVector(l, matrix);
			frequencyStatistic[printStatistic(finalVector)+1]++;

			i++;
		//	if (i == 10000)
				//break; // how many lines we want
		}
		int sum = 0;
		for (int j = 1; j < constSize; j++) {
			System.out.printf("Sveukupno ima %d-tog: %d\n", j,
					frequencyStatistic[j]);
			sum += (double) frequencyStatistic[j];
		}
		for (int j = 1; j < constSize; j++) {
			System.out.printf("Sveukupno ima %d-tog: %.2f", j,
					((double) frequencyStatistic[j] / sum) * 100);
			System.out.println("%");

		}

	}

	/**
	 * Creates k-mers.
	 * 
	 * @param s
	 *            given string
	 * @param slide
	 *            size of k-mer (k = slide)
	 * @return map with given number of how many integer is occurred on given
	 *         position
	 */
	private static Map<Integer, Integer> createArrayOfNumbersSliding(String s,
			int slide) {

		Map<Integer, Integer> finals = new HashMap<>();

		for (int i = 0; i < s.length() - slide; i++) {
			int index = convertStringToNumber(s, i, slide);
			// System.out.println(index);
			if (index == -1)
				continue;
			if (finals.containsKey(index))
				finals.put(index, finals.get(index) + 1);
			else
				finals.put(index, 1);
		}
		return finals;
	}

	/**
	 * Converts part of string to number in base 10.
	 * 
	 * @param s
	 *            given string
	 * @param offset
	 *            starting from which part of string
	 * @return integer number that represents this string (bijection)
	 */
	private static int convertStringToNumber(String s, int offset, int length) {
		int ret = 0;
		for (int i = 0; i < length; i++) {
			int br = -1;
			int pom = offset + i;
			if (s.charAt(pom) == 'A') {
				br = 0;
			} else if (s.charAt(pom) == 'C') {
				br = 1;
			} else if (s.charAt(pom) == 'T') {
				br = 2;
			} else if (s.charAt(pom) == 'G') {
				br = 3;
			} else
				return -1;
			ret += Math.pow(4, i) * br;
		}
		return ret;
	}

	/**
	 * Reads input matrix that was created before (Python script).
	 * 
	 * @return list of vectors (matrix) using map
	 * @throws IOException
	 *             when reading goes wrong
	 */
	private static List<Map<Integer, Integer>> readInputMatrix()
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("matrixNovi.txt"))));
		String line;

		List<Map<Integer, Integer>> input = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			Map<Integer, Integer> redak = new HashMap<>();
			line = line.trim();
			String[] svi = line.split("\\s+");
		
			for (int i = 0; i < svi.length; i++) {
				//System.out.println(svi[i]);

				//System.out.println(svi[i].substring(0, svi[i].indexOf('-')));
				int key = Integer.parseInt(svi[i].substring(0, svi[i].indexOf('-')));
				int value = Integer.parseInt(
						svi[i].substring(svi[i].indexOf('>')+1, svi[i].length())) ;
				
				redak.put(key, value);
				
			}

			input.add(redak);
		}
		br.close();
		return input;
	}

	/**
	 * Standard main method.
	 * 
	 * @param args
	 *            not used
	 * @throws IOException
	 *             when reading goes wrong
	 */
	public static void main(String args[]) throws IOException {

		readFromFileAndSolve();

	}
}
