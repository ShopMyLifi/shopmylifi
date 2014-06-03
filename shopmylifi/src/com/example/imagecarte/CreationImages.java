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
	static final int X1 = 8;
	static final int Y1 = 5;
	static final int X2 = 8;
	static final int Y2 = 10;
	static final int X3 = 8;
	static final int Y3 = 15;
	static final int NUMBEROFPAINT = 4; //nombre de types de cases dans la matrice, sans compter la position

	public static void creationImage(int[][] matrice) throws Throwable {

		int dim1 = matrice.length;
		int dim2 = matrice[0].length;

		Bitmap bitmap = Bitmap.createBitmap(dim1 * SCALE, dim2 * SCALE,
				Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(bitmap);
		
		Paint tabPaint[] = new Paint[NUMBEROFPAINT];

		Paint paintCouloir = new Paint();
		paintCouloir.setStyle(Paint.Style.FILL);
		paintCouloir.setColor(Color.WHITE);
		tabPaint[0] = paintCouloir;

		Paint paintCaisse = new Paint();
		paintCaisse.setStyle(Paint.Style.FILL);
		paintCaisse.setColor(Color.RED);
		tabPaint[1] = paintCaisse;
		
		Paint paintRayon = new Paint();
		paintRayon.setStyle(Paint.Style.FILL);
		paintRayon.setColor(Color.BLUE);
		tabPaint[2] = paintRayon;
		
		Paint paintItineraire = new Paint();
		paintItineraire.setStyle(Paint.Style.FILL);
		paintItineraire.setColor(Color.GREEN);
		tabPaint[3] = paintItineraire;
		
		Paint paintPos = new Paint();
		paintPos.setStyle(Paint.Style.FILL);
		paintPos.setColor(Color.YELLOW);

		for (int i = 0; i < dim1; i++)
			for (int j = 0; j < dim2; j++) 
				canvas.drawRect(i * SCALE, j * SCALE, i * SCALE + SCALE, j * SCALE + SCALE, tabPaint[matrice[i][j]]);
					
		
		canvas.drawRect(X1 * SCALE, Y1 * SCALE, X1 * SCALE + SCALE, Y1
				* SCALE + SCALE, paintPos);

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
		
		canvas.drawRect(X1 * SCALE, Y1 * SCALE, X1 * SCALE + SCALE, Y1
				* SCALE + SCALE, tabPaint[matrice[X1][Y1]]);
		canvas.drawRect(X2 * SCALE, Y2 * SCALE, X2 * SCALE + SCALE, Y2
				* SCALE + SCALE, paintPos);

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
		
		canvas.drawRect(X2 * SCALE, Y2 * SCALE, X2 * SCALE + SCALE, Y2
				* SCALE + SCALE, tabPaint[matrice[X1][Y1]]);
		canvas.drawRect(X3 * SCALE, Y3 * SCALE, X3 * SCALE + SCALE, Y3
				* SCALE + SCALE, paintPos);
		

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
