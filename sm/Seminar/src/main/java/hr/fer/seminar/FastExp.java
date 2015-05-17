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

public class FastExp {

	/**
	 * Izraèunava skalarni produkt.
	 * 
	 * @param a
	 *            prvi vektor
	 * @param b
	 *            drugi vektor
	 * @return
	 */
	private static int dotProduct(Map<Integer, Integer> a, Map<Integer, Integer> b) {
		int rez = 0;
		for (Entry <Integer, Integer> en : a.entrySet()){
			if (b.containsKey(en.getKey())){
				rez+=en.getValue()*b.get(en.getKey());
			}
		}
		return rez;
	}

	/**
	 * Za jedan uzorak raèunat æemo njegove rezultate, i pozvati ovu metodu za
	 * par njih.
	 * 
	 * @return
	 */
	private static List<Integer> finalVector(Map <Integer, Integer> test,
			List<Map <Integer, Integer>> matrix) {
		List<Integer> finalVector = new ArrayList<>(); // bit ce dimenzija 12
		for (Map<Integer, Integer> l : matrix) {
			finalVector.add(dotProduct(test, l));
		}
		return finalVector;
	}

	/**
	 * Koji je u ternutnom dijelu sempla najslicniji.
	 * 
	 * @param finalVector
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
		//System.out.println("Najslicniji je: " + mostSimilar);
		return mostSimilar;
	}

	private static List<Map<Integer, Integer>> readFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("Testing1.fq"))));
		String line;
		List <Map<Integer, Integer>> kon = new ArrayList<>();
		int i=0; 
		
		while ((line = br.readLine()) != null) {
		
			 if (! (line.startsWith("A") ||
			 line.startsWith("T")  || line.startsWith("C") ||
			 line.startsWith("G") )) continue;
			 
		/*	if (convertStringToNumber(line, 0, 10) == -1)
				continue;
				*/
			System.out.println(line);
			//radi
			Map<Integer, Integer> l = createArrayOfNumbersSliding(line, 10);
			kon.add(l);
			i++;
			if (i == 5) break;
		}
		return kon;

	}

	private static Map<Integer, Integer> createArrayOfNumbersSliding(String s, int slide) {
	
		Map<Integer, Integer> finals = new HashMap<>();
		
		for (int i = 0; i < s.length() - slide; i++) {
			int index = convertStringToNumber(s, i, slide);
			// System.out.println(index);
			if (index == -1) break;
			if (finals.containsKey(index))
				finals.put(index, finals.get(index) + 1);
			else finals.put(index, 1);
		}
		return finals;
	}

	/**
	 * Converts part of string to number
	 * 
	 * @param s
	 * @param offset
	 * @return
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

	private static List<Map<Integer, Integer>> readInputMatrix() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("matrix.txt"))));
		String line;

		List<Map<Integer, Integer>> input = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			Map<Integer, Integer> redak = new HashMap<>();
			String[] svi = line.split(" ");
			for (int i = 0; i < svi.length; i++) {
				int num = Integer.parseInt(svi[i]);
				if (num != 0){
					redak.put(i, num);
				}
			}

			input.add(redak);
		}
		br.close();
		return input;
	}

	public static void main(String args[]) throws IOException {
		List<Map<Integer, Integer>> matrix = readInputMatrix();
		List<Map<Integer, Integer>> sviRedovi = readFromFile();
		int ukupno = sviRedovi.size();
		final int constSize = 12;
		int [] frequencyStatistic = new int [constSize];
		for (int i=0; i<constSize; i++) frequencyStatistic[i] = 0;
		
		for (int i=0; i<ukupno; i++){
			Map <Integer, Integer> red = sviRedovi.get(i);
			
			List<Integer> finalVector = finalVector(red, matrix);
			frequencyStatistic[printStatistic(finalVector)]++;
		}
		
		for (int i=0; i<constSize; i++){
			System.out.printf("Sveukupno ima %d-tog: %d\n", i ,frequencyStatistic[i]);
		}

	}
}
