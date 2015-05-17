package hr.fer.seminar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Fast2 {

	/**
	 * Izraèunava skalarni produkt.
	 * 
	 * @param a
	 *            prvi vektor
	 * @param b
	 *            drugi vektor
	 * @return
	 */
	private static int dotProduct(List<Integer> a, List<Integer> b) {
		int rez = 0;
		for (int i = 0; i < a.size(); i++) {
			rez += a.get(i) * b.get(i);
		}
		return rez;
	}

	/**
	 * Za jedan uzorak raèunat æemo njegove rezultate, i pozvati ovu metodu za
	 * par njih.
	 * 
	 * @return
	 */
	private static List<Integer> finalVector(List<Integer> test,
			List<List<Integer>> matrix) {
		List<Integer> finalVector = new ArrayList<>(); // bit ce dimenzija 12
		for (List<Integer> l : matrix) {
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
		//System.out.println(mx);
		return mostSimilar;
	}

	private static List<List<Integer>> readFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("Testing1.fq"))));
		String line;
		List <List<Integer>> kon = new ArrayList<>();
		int i=0; 
		
		while ((line = br.readLine()) != null) {
			/*
			 * System.out.println(line); if (!line.startsWith("A") ||
			 * !line.startsWith("T") || !line.startsWith("C") ||
			 * !line.startsWith("G")) continue;
			 */
			if (convertStringToNumber(line, 0, 10) == -1)
				continue;
			// zasad samo 1
			// System.out.println(line);
			List<Integer> l = createArrayOfNumbersSliding(line, 10);
			kon.add(l);
			i++;
			if (i == 20) break;
		}
		return kon;

	}

	private static List<Integer> createArrayOfNumbersSliding(String s, int slide) {
		int size = (int) Math.pow(4, 10);
		List<Integer> finals = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
			finals.add(0);
		for (int i = 0; i < s.length() - slide; i++) {
			int index = convertStringToNumber(s, i, slide);
			// System.out.println(index);
			if (index == -1) break;
			finals.set(index, finals.get(index) + 1);
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

	private static List<List<Integer>> readInputMatrix() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(new FileInputStream("matrix.txt"))));
		String line;

		List<List<Integer>> input = new ArrayList<>();
		while ((line = br.readLine()) != null) {
			List<Integer> redak = new ArrayList<>();
			String[] svi = line.split(" ");
			for (int i = 0; i < svi.length; i++) {
				redak.add(Integer.parseInt(svi[i]));
			}

			input.add(redak);
		}
		br.close();
		return input;
	}

	public static void main(String args[]) throws IOException {
		List<List<Integer>> matrix = readInputMatrix();
		List<List<Integer>> sviRedovi = readFromFile();
		int ukupno = sviRedovi.size();
		final int constSize = 12;
		int [] frequencyStatistic = new int [constSize];
		for (int i=0; i<constSize; i++) frequencyStatistic[i] = 0;
		
		for (int i=0; i<ukupno; i++){
			List <Integer> red = sviRedovi.get(i);
			
			List<Integer> finalVector = finalVector(red, matrix);
			frequencyStatistic[printStatistic(finalVector)]++;
		}
		
		for (int i=0; i<constSize; i++){
			System.out.printf("Sveukupno ima %d-tog: %d\n", i ,frequencyStatistic[i]);
		}
		
		
		
		
		
		
		
		

	}
}
