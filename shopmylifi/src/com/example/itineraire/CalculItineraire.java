package com.example.itineraire;

import java.util.Arrays;

//import android.util.Log;

public class CalculItineraire {

	final static int PN = 0; // Previous Neighbor
	final static int NN = 1; // Next Neighbor

	// fonction qui calcul un itineraire a partir de la matrice des distances
	public static void computeIti(int matriceD[][], int iti[]) {

		int dim = matriceD.length;
		
		if(dim==0) return;

		// matrice qui indique les deux voisins d'un élément
		int matriceN[][] = new int[dim][2];

		initNNAlgo(matriceD, matriceN);

		amelioration2OptAlgo(matriceD, matriceN);

		iti[0] = 0;

		for (int i = 0; i < dim - 1; i++) {
			iti[i + 1] = matriceN[iti[i]][NN];
		}
	}

	private static void initNNAlgo(int matriceD[][], int matriceN[][]) {

		int dim = matriceD.length;

		// false si le point n'est pas dans l'itinéraire, true sinon
		boolean ajouterItineraire[] = new boolean[dim];

		ajouterItineraire[0] = true;

		for (int i = 1; i < dim; i++)
			ajouterItineraire[i] = false;

		int closestPoint = -1;
		int current = 0;

		for (int j = 0; j < dim - 1; j++) {
			for (int i = 1; i < dim; i++) {
				if (!ajouterItineraire[i]) {

					if (closestPoint == -1) {
						closestPoint = i;
					}

					else {

						if (matriceD[current][i] < matriceD[current][closestPoint]) {
							closestPoint = i;
						}
					}
				}
			}

			matriceN[current][NN] = closestPoint;
			matriceN[closestPoint][PN] = current;

			current = closestPoint;

			ajouterItineraire[current] = true;
			closestPoint = -1;
		}

		matriceN[0][PN] = current;
		matriceN[current][NN] = 0;

	}

	private static void amelioration2OptAlgo(int matriceD[][], int matriceN[][]) {

		int dim = matriceD.length;
		int actualBestCost = cost(matriceN);
		boolean change = true;

		while (change) {

			change = false;

			for (int i = 0; i < dim - 1; i++) {
				for (int j = i + 1; j < dim; j++) {

					int tempCost;
					tempCost = changeCost(matriceD, matriceN, actualBestCost,
							i, j);

					if (tempCost < actualBestCost) {

						actualBestCost = tempCost;
						apply2Opt(matriceN, i, j);
						change = true;

					}
				}
			}
		}
	}

	private static int cost(int matriceN[][]) {

		int dim = matriceN.length;
		int cost = 0;

		for (int i = 0; i < dim; i++) {
			cost += matriceN[i][NN];
		}

		return (cost);
	}

	private static int changeCost(int[][] matriceD, int[][] matriceN,
			int actualCost, int point1, int point2) {

		int nextPoint1 = matriceN[point1][NN];
		int nextPoint2 = matriceN[point2][NN];

		int changeCost = actualCost - matriceD[point1][nextPoint1]
				- matriceD[point2][nextPoint2] + matriceD[point1][point2]
				+ matriceD[nextPoint1][nextPoint2];
		return (changeCost);

	}

	private static void xchangeDirection(int[][] matriceN, int pointCurr,
			int pointStop) {

		if (pointCurr != pointStop) {

			xchangeDirection(matriceN, matriceN[pointCurr][NN], pointStop);

			int temp = matriceN[pointCurr][NN];
			matriceN[pointCurr][NN] = matriceN[pointCurr][PN];
			matriceN[pointCurr][PN] = temp;
		}
	}

	private static void apply2Opt(int[][] matriceN, int point1, int point2) {

		xchangeDirection(matriceN, matriceN[point1][NN], point2);

		int nextPoint1 = matriceN[point1][NN];
		int nextPoint2 = matriceN[point2][NN];

		matriceN[point1][NN] = point2;
		matriceN[point2][NN] = matriceN[point2][PN];
		matriceN[nextPoint1][NN] = nextPoint2;
		matriceN[point2][PN] = point1;
		matriceN[nextPoint2][PN] = nextPoint1;

	}

	/*
	 * public static void main(String[] args) { int matrice[][] = { { 0, 8, 6,
	 * 10, 7, 16, 16, 16, 4 }, { 8, 0, 8, 4, 13, 8, 8, 8, 12 }, { 6, 8, 0, 4, 5,
	 * 10, 16, 10, 4 }, { 10, 4, 4, 0, 9, 6, 12, 6, 8 }, { 7, 13, 5, 9, 0, 15,
	 * 21, 9, 5 }, { 16, 8, 10, 6, 15, 0, 6, 6, 14 }, { 16, 8, 16, 12, 21, 6, 0,
	 * 12, 21 }, { 16, 8, 10, 6, 9, 6, 12, 0, 14 }, { 4, 12, 4, 8, 5, 14, 21,
	 * 14, 0 } };
	 * 
	 * int dim = matrice.length; int[] iti = new int[dim + 1];
	 * computeIti(matrice, iti);
	 * 
	 * }
	 */
}
