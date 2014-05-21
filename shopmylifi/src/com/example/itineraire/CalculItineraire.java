package com.example.itineraire;

public class CalculItineraire {

	final static int PN = 0; // Previous Neighbor
	final static int NN = 1; // Next Neighbor

	// fonction qui calcul un itineraire a partir de la matrice des distances
	public static void computeIti(int matriceD[][], int iti[]) {

		int dim = matriceD.length;

		// matrice qui indique les deux voisins d'un élément
		int matriceN[][] = new int[dim][2];

		initNNAlgo(matriceD, matriceN);

		iti[0] = 0;
		for (int i = 0; i < dim; i++) {
			iti[i + 1] = matriceN[iti[i]][NN];
		}

		/*
		 * // meilleur inineraire pour le moment int[] bestNextPlace = new
		 * int[dim];
		 * 
		 * for (int i = 0; i < dim - 1; i++) bestNextPlace[i] = i + 1;
		 * 
		 * // itineraire finit en 0 bestNextPlace[dim - 1] = 0;
		 * 
		 * int bestCost = findCost(bestNextPlace, matriceD); boolean change =
		 * true;
		 * 
		 * while (change) { int nextPlace[] = new int[dim];
		 * 
		 * for (int i = 1; i < dim - 1; i++) for (int j = i + 1; j < dim - 1;
		 * j++) {
		 * 
		 * System.arraycopy(bestNextPlace, 0, nextPlace, 0, dim); int temp =
		 * nextPlace[i];
		 * 
		 * }
		 * 
		 * }
		 */
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
			System.out.println();
			matriceN[current][NN] = closestPoint;
			matriceN[closestPoint][PN] = current;

			current = closestPoint;

			ajouterItineraire[current] = true;
			closestPoint = -1;
		}

		matriceN[0][PN] = current;
		matriceN[current][NN] = 0;
		for (int k = 0; k < dim; k++) {
			System.out.print(k + "  ");
			for (int m = 0; m < 2; m++) {
				System.out.print(matriceN[k][m] + "  ");
			}
			System.out.println();
		}

	}

	public static void main(String[] args) {
		int matrice[][] = { { 0, 8, 6, 10, 7, 16, 16, 16, 4 },
				{ 8, 0, 8, 4, 13, 8, 8, 8, 12 },
				{ 6, 8, 0, 4, 5, 10, 16, 10, 4 },
				{ 10, 4, 4, 0, 9, 6, 12, 6, 8 },
				{ 7, 13, 5, 9, 0, 15, 21, 9, 5 },
				{ 16, 8, 10, 6, 15, 0, 6, 6, 14 },
				{ 16, 8, 16, 12, 21, 6, 0, 12, 21 },
				{ 16, 8, 10, 6, 9, 6, 12, 0, 14 },
				{ 4, 12, 4, 8, 5, 14, 21, 14, 0 } };

		int dim = matrice.length;
		int[] iti = new int[dim + 1];
		computeIti(matrice, iti);

		for (int i = 0; i < iti.length; i++)
			System.out.println(iti[i]);

	}
}
