package com.example.itineraire;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.os.Environment;

import com.example.itineraire.CreationMatriceDistance.getposition;

public class CalculCarte {

	public static int[][] calculmatriceitineraire(int[] iti, String result, int[][] matriceCarte) {

		// matrice de l'ordre des objets
		
		
		int dim1 = matriceCarte[0].length;
		int dim2 = matriceCarte.length;
		
		int[][] carte = new int[dim1][dim2];
			    
		for (int i = 0; i < dim1; i++) {
			for (int j = 0; j < dim2; j++) {
				carte[i][j] = matriceCarte[dim2-1-j][i];
			}
		}
	       
		

		String[] values = result.split("\"");

		ArrayList<String> association = new ArrayList<String>();
		for (int i = 0; i < values.length; ++i) {
			if (values[i].compareTo("Id") == 0) {
				association.add(values[i + 2]);
			}
		}
		;
		int[][] matrice = new int[association.size()][2];

		for (int i = 0; i < association.size(); ++i) { // value of id

			int j = iti[i];
			matrice[i][0] = Integer.valueOf(values[12 * j + 7]);
			matrice[i][1] = Integer.valueOf(values[12 * j + 11]);

		}
		int largeur = 76;
		carte[4][6]=8;

		for (int i = 0; i < association.size()-1; ++i) {
			int x = matrice[i][0];
			int y = matrice[i][1];
			int w = matrice[i + 1][0];
			int z = matrice[i + 1][1];
			int hauteuriti=y;
			if (carte[x][y-1]==2) {
				hauteuriti=y+1;
			}
			if (carte[x][y+1]==2) {
				hauteuriti=y-1;
			}

			if (y == z) { // si m�me hauteur de rayon
				
				if (x > w) {

					for (int a = w; a <= x; ++a) { // si x>w
						carte[a][hauteuriti] = 3;
					}
				} else {
					for (int a = x; a <= w; ++a) { // si x<w
						carte[a][hauteuriti] = 3;
					}
				}

			} else {
				if (Math.abs(y - z) == 1) { // si m�me rayon mais face oppos�s
					int endessous;
					int audessus;
					if (z>y) { //initialisation
						endessous=y-1;
						audessus=z+1;
					} else {
						endessous=z-1;
						audessus=y+1;
					}
				
					
					if ((x > largeur / 2) && (w > largeur / 2)) { // si c�t�
																	// droit											// magasin
						
						int trajet1 = Math.abs(68 - x) + 2 + Math.abs(68 - w); // droit
						int trajet2 = Math.abs(38 - x) + 2 + Math.abs(38 - w);
							if (trajet1 < trajet2) {            //montant
								for (int a = x; a <= 68; ++a) { // si x>w
									carte[a][endessous] = 3;
								}
								
								carte[68][y] = 3;
								carte[68][z] = 3;
								
								for (int a = w; a <= 68; ++a) { // si x>w
									carte[a][audessus] = 3;
								}
	
							} else {
								for (int a = 38; a <= x; ++a) { // si x<w
									carte[a][endessous] = 3;
								}
								carte[38][y] = 3;
								carte[38][z] = 3;
								for (int a = 38; a <= w; ++a) { // si x>w
									carte[a][audessus] = 3;
								}
							}
							
					} else {
						if ((x < largeur / 2) && (w < largeur / 2)) { // c�t�
																		// gauche
																		// du
																		// magasin
							int trajet1 = Math.abs(33 - x) + 2
									+ Math.abs(35 - w); // droit
							int trajet2 = Math.abs(4 - x) + 2 + Math.abs(4 - w);
							if (trajet1 < trajet2) {
								for (int a = x; a <= 33; ++a) { // si x>w
									carte[a][endessous] = 3;
								}
								carte[33][y] = 3;
								carte[33][z] = 3;
								for (int a = w; a <= 33; ++a) { // si x>w
									carte[a][audessus] = 3;
								}

							} else {
								for (int a = 5; x <= w; ++a) { // si x<w
									carte[a][endessous] = 3;
								}
								carte[4][y] = 3;
								carte[4][z] = 3;
								for (int a = 4 ; a <= w; ++a) { // si x>w
									carte[a][audessus] = 3;
								}
							}

						} else { // si du c�t� gauche au c�t� droit ou inverse
							if ((x < largeur / 2) && (w > largeur / 2)) {
								
									for (int a = x; a <= 35; ++a) { // si x>w
										carte[a][endessous] = 3;
									}
									carte[37][y] = 3;
									carte[37][z] = 3;
									for (int a = 37; a <= w; ++a) { // si x>w
										carte[a][audessus] = 3;
									}
								
							} else {
								
									for (int a = 37; a <= x; ++a) { // si x>w
										carte[a][endessous] = 3;
									}
									carte[37][y] = 3;
									carte[37][z] = 3;
									for (int a = w; a <= 37; ++a) { // si x>w
										carte[a][audessus] = 3;
									}
								
								
							}

						}

					}
				} else {
					if ((x < largeur / 2) && (w > largeur / 2)) {
						for (int a = x; a <= 37; ++a) { // go � droite
							carte[a][hauteuriti] = 3;
						}
						if (z>y) {
							for (int a = y; a < z; a++) { // go en haut
								carte[35][a] = 3;
							}
						} else 
						{
							for (int a = z; a < y; a++) { // go en haut
								carte[35][a] = 3;
							}
						}

						for (int a = 35; a <= w; ++a) { // si go � droite
							carte[a][z + 1] = 3;
						}
						// m�me
						// c�t�
						// et
						// pas
						// m�me
						// hauteur
					}
					if ((x > largeur / 2) && (w > largeur / 2)) { // si m�me
																	// c�t�
																	// magasin
																	// mais
																	// pas m�me
																	// hauteur
																	// c�t�
																	// droit
						int trajet5 = Math.abs(69 - x) + Math.abs(y - z)
								+ Math.abs(69 - w);
						int trajet6 = Math.abs(40 - x) + Math.abs(y - z)
								+ Math.abs(40 - w);
						// distance = Math.min(trajet5, trajet6);
					}
					if ((x < largeur / 2) && (w < largeur / 2)) { // m�me c�t�
																	// magasin
																	// c�t�
																	// gauche
						int trajet7 = Math.abs(35 - x) + Math.abs(y - z)
								+ Math.abs(35 - w);
						int trajet8 = Math.abs(5 - x) + Math.abs(y - z)
								+ Math.abs(5 - w);
						// distance = Math.min(trajet7, trajet8);
					}
				}
			}

		}

		return (carte);

	}

	

}
