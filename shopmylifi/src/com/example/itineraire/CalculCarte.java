package com.example.itineraire;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.example.itineraire.CreationMatriceDistance.getposition;

public class CalculCarte {

	public static int[][] calculmatriceitineraire(int[] iti,String result) {
		
		//matrice de l'ordre des objets
		
			
			String[] values = result.split("\"");
			
			
			ArrayList<String> association = new ArrayList<String>();
			for (int i = 0; i < values.length; ++i) {
				if (values[i].compareTo("Id") == 0) {
					association.add(values[i + 2]);
				}
			}
			;
			int[][] matrice =new int [association.size()+1][2];
			
			for (int i = 0; i < association.size(); ++i) { // value of id

				int j=iti[i];
				matrice[i][0] = Integer.valueOf(values[12*j + 7]);
				matrice[i][1] = Integer.valueOf(values[12*j + 11]);
						
			}
			int largeur = 76;
			int[][] carte =new int [85][85];
			
			
			for (int i = 0; i < association.size() ; ++i) {
				int x = matrice[i][0];
				int y = matrice[i][1];
				int w = matrice[i+1][0];
				int z = matrice[i+1][1];
				
				if (y == z) { // si mï¿½me hauteur de rayon
					if (x>w) {
						
						for (int a = w; a<=x; ++a){ //si x>w
							carte[a][y]=1;
						}
					} else 
					{
						for (int a = x; a<=w; ++a){ //si x>w
							carte[a][y]=1;
						}
					}
					
				} else {
					if (Math.abs(y - z) == 1) { // si même rayon mais face opposés
						if ((x > largeur / 2) && (w > largeur / 2)) { // si côté droit
																		// magasin
							int trajet1 = Math.abs(69 - x) + 2 + Math.abs(69 - w); //droit
							int trajet2 = Math.abs(40 - x) + 2 + Math.abs(40 - w);
							if (trajet1<trajet2) {
								for (int a = x; a<=69; ++a){ //si x>w
									carte[a][y-1]=1;
								}
								carte[70][y]=1;
								carte[70][y+1]=1;
								for (int a = w; a<=69; ++a){ //si x>w
									carte[a][y+2]=1;
								}
								
							} else {
								for (int a = 40; x<=69; ++a){ //si x<w
									carte[a][y-1]=1;
								}	
								carte[39][y]=1;
								carte[39][y+1]=1;
								for (int a = 39; a<=w; ++a){ //si x>w
									carte[a][y+2]=1;
								}
							}
						} else {
							if ((x < largeur / 2) && (w < largeur / 2)) { // cï¿½tï¿½ gauche
																			// du
																			// magasin
								int trajet1 = Math.abs(35 - x) + 2 + Math.abs(35 - w); //droit
								int trajet2 = Math.abs(5 - x) + 2 + Math.abs(5 - w);
								if (trajet1<trajet2) {
									for (int a = x; a<=35; ++a){ //si x>w
										carte[a][y-1]=1;
									}
									carte[70][y]=1;
									carte[70][y+1]=1;
									for (int a = w; a<=35; ++a){ //si x>w
										carte[a][y+2]=1;
									}
									
								} else {
									for (int a = 5; x<=w; ++a){ //si x<w
										carte[a][y-1]=1;
									}	
									carte[39][y]=1;
									carte[39][y+1]=1;
									for (int a = 39; a<=w; ++a){ //si x>w
										carte[a][y+2]=1;
									}
								}
								
							} else { // si du cï¿½tï¿½ gauche au cï¿½tï¿½ droit ou inverse
								if ((x < largeur / 2) && (w > largeur / 2)) {
									if (z>y) {
										for (int a = x; a<=35; ++a){ //si x>w
											carte[a][y-1]=1;
										}
										carte[37][y]=1;
										carte[37][y+1]=1;
										for (int a = 37; a<=w; ++a){ //si x>w
											carte[a][z+1]=1;
										}	
									}
									else {
										for (int a = x; a<=35; ++a){ //si x>w
											carte[a][y+1]=1;
										}
										carte[37][y-1]=1;
										carte[37][y]=1;
										for (int a = 37; a<=w; ++a){ //si x>w
											carte[a][z-1]=1;
										}	
									}
								 } else {
									 if (z>y) {
											for (int a = 37; a<=x; ++a){ //si x>w
												carte[a][y-1]=1;
											}
											carte[37][y]=1;
											carte[37][y+1]=1;
											for (int a = w; a<=37; ++a){ //si x>w
												carte[a][z+1]=1;
											}	
										}
										else {
											for (int a = w; a<=37; ++a){ //si x>w
												carte[a][z-1]=1;
											}
											carte[37][y+1]=1;
											carte[37][y]=1;
											for (int a = 37; a<=x; ++a){ //si x>w
												carte[a][y+1]=1;
											}	
										}
								 }
								
							}

						}
					} else {
						if ((x < largeur / 2) && (w > largeur / 2)) {
							for (int a = x; a<=35; ++a){ //go à droite
								carte[a][y-1]=1;
							}
							for (int a = y; a <= z;a++) { //go en haut
								carte[35][y]=1;
							}
							
							for (int a = w; a<=35; ++a){ //si go à droite
								carte[a][z+1]=1;
							}
																				// mï¿½me
																				// cï¿½tï¿½
																				// et
																				// pas
																				// mï¿½me
																				// hauteur
						}
						if ((x > largeur / 2) && (w > largeur / 2)) { // si mï¿½me cï¿½tï¿½
																		// magasin mais
																		// pas mï¿½me
																		// hauteur cï¿½tï¿½
																		// droit
							int trajet5 = Math.abs(69 - x) + Math.abs(y - z)
									+ Math.abs(69 - w);
							int trajet6 = Math.abs(40 - x) + Math.abs(y - z)
									+ Math.abs(40 - w);
							//distance = Math.min(trajet5, trajet6);
						}
						if ((x < largeur / 2) && (w < largeur / 2)) { // mï¿½me cï¿½tï¿½
																		// magasin cï¿½tï¿½
																		// gauche
							int trajet7 = Math.abs(35 - x) + Math.abs(y - z)
									+ Math.abs(35 - w);
							int trajet8 = Math.abs(5 - x) + Math.abs(y - z)
									+ Math.abs(5 - w);
							//distance = Math.min(trajet7, trajet8);
						}
					}
				}
				
			}
			
			
			return (carte);
	
	}
	
}
