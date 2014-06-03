package com.example.imagecarte;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;

public class CreationImages {

	static final int SCALE = 10;
	/*
	 * POSITIONS DES CAPTEURS LIFI 
	 * origine en haut à gauche 
	 * Y axe horizontal (croissant -> droite)
	 * X axe vertical (croissant -> bas) 
	 */
	
	static final int X1 = 7; 
	static final int Y1 = 9;
	static final int X2 = 20;
	static final int Y2 = 9;
	static final int X3 = 30;
	static final int Y3 = 9;
	static final int NUMBEROFPAINT = 5; // nombre de types de cases dans la
										// matrice, sans compter la position

	public static void creationImage(int[][] matrice) throws Throwable {

		int dim1 = matrice.length;
		int dim2 = matrice[0].length;

		Bitmap bitmap = Bitmap.createBitmap(dim2 * SCALE, dim1 * SCALE,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);

		Paint tabPaint[] = new Paint[NUMBEROFPAINT];

		Paint paintCouloir = new Paint();
		paintCouloir.setStyle(Paint.Style.FILL);
		paintCouloir.setColor(Color.WHITE);
		tabPaint[0] = paintCouloir;

		Paint paintCaisse = new Paint();
		paintCaisse.setStyle(Paint.Style.FILL);
		paintCaisse.setARGB(255,37,151,248);
		tabPaint[1] = paintCaisse;

		Paint paintRayon = new Paint();
		paintRayon.setStyle(Paint.Style.FILL);
		paintRayon.setARGB(255,109,185,0);
		tabPaint[2] = paintRayon;

		Paint paintItineraire = new Paint();
		paintItineraire.setStyle(Paint.Style.FILL);
		paintItineraire.setARGB(255,248,176,37);
		tabPaint[3] = paintItineraire;
		
		Paint paintArticle = new Paint();
		paintArticle.setStyle(Paint.Style.FILL);
		paintArticle.setARGB(255,248,197,37);
		tabPaint[4] = paintArticle;

		Paint paintPos = new Paint();
		paintPos.setStyle(Paint.Style.FILL);
		paintPos.setColor(Color.RED);

		for (int i = 0; i < dim1; i++)
			for (int j = 0; j < dim2; j++) {

				int type = matrice[i][j];
				if (type < NUMBEROFPAINT)
					canvas.drawRect(j * SCALE, i * SCALE, j * SCALE + SCALE, i
							* SCALE + SCALE, tabPaint[matrice[i][j]]);
				else
					canvas.drawRect(j * SCALE, i * SCALE, j * SCALE + SCALE, i
							* SCALE + SCALE, paintCouloir);
			}

		canvas.drawRect(Y1 * SCALE, X1 * SCALE, Y1 * SCALE + SCALE, X1 * SCALE
				+ SCALE, paintPos);

		// Définition fichier
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath());
		dir.mkdirs();
		File file1 = new File(dir, "/oledcomm/image/1.jpg");

		// Ecriture dans le fichier
		try {
			OutputStream stream = new FileOutputStream(file1, false);
			try {
				if (bitmap.compress(CompressFormat.JPEG, 100, stream)) {

				} else {
					throw new Exception("Failed to save the image as a JPEG");
				}
			} finally {
				stream.close();
			}
		} catch (Throwable t) {

			throw t;
		}

		canvas.drawRect(Y1 * SCALE, X1 * SCALE, Y1 * SCALE + SCALE, X1 * SCALE
				+ SCALE, tabPaint[matrice[X1][Y1]]);
		canvas.drawRect(Y2 * SCALE, X2 * SCALE, Y2 * SCALE + SCALE, X2 * SCALE
				+ SCALE, paintPos);

		File file2 = new File(dir, "/oledcomm/image/2.jpg");
		try {
			OutputStream stream = new FileOutputStream(file2, false);
			try {
				if (bitmap.compress(CompressFormat.JPEG, 100, stream)) {

				} else {
					throw new Exception("Failed to save the image as a JPEG");
				}
			} finally {
				stream.close();
			}
		} catch (Throwable t) {

			throw t;
		}

		canvas.drawRect(Y2 * SCALE, X2 * SCALE, Y2 * SCALE + SCALE, X2 * SCALE
				+ SCALE, tabPaint[matrice[X2][Y2]]);
		canvas.drawRect(Y3 * SCALE, X3 * SCALE, Y3 * SCALE + SCALE, X3 * SCALE
				+ SCALE, paintPos);

		File file3 = new File(dir, "/oledcomm/image/3.jpg");
		try {
			OutputStream stream = new FileOutputStream(file3, false);
			try {
				if (bitmap.compress(CompressFormat.JPEG, 100, stream)) {

				} else {
					throw new Exception("Failed to save the image as a JPEG");
				}
			} finally {
				stream.close();
			}
		} catch (Throwable t) {

			throw t;
		}
	}

}
