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

	static int SCALE = 10;
	static int X1 = 8;
	static int Y1 = 5;
	static int X2 = 8;
	static int Y2 = 10;
	static int X3 = 8;
	static int Y3 = 15;

	public static void creationImage(int[][] matrice) throws Throwable {

		int dim1 = matrice.length;
		int dim2 = matrice[0].length;

		Bitmap bitmap = Bitmap.createBitmap(dim1 * SCALE, dim2 * SCALE,
				Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);

		Paint paintCouloir = new Paint();
		paintCouloir.setStyle(Paint.Style.FILL);
		paintCouloir.setColor(Color.WHITE);

		Paint paintCaisse = new Paint();
		paintCaisse.setStyle(Paint.Style.FILL);
		paintCaisse.setColor(Color.RED);

		Paint paintRayon = new Paint();
		paintRayon.setStyle(Paint.Style.FILL);
		paintRayon.setColor(Color.BLUE);

		Paint paintItineraire = new Paint();
		paintItineraire.setStyle(Paint.Style.FILL);
		paintItineraire.setColor(Color.GREEN);

		Paint paintPos = new Paint();
		paintPos.setStyle(Paint.Style.FILL);
		paintPos.setColor(Color.YELLOW);

		for (int i = 0; i < dim1; i++)
			for (int j = 0; j < dim2; j++) {
				switch (matrice[i][j]) {

				case 0:
					canvas.drawRect(i * SCALE, j * SCALE, i * SCALE + SCALE, j
							* SCALE + SCALE, paintCouloir);
					break;
				case 1:
					canvas.drawRect(i * SCALE, j * SCALE, i * SCALE + SCALE, j
							* SCALE + SCALE, paintCaisse);
					break;
				case 2:
					canvas.drawRect(i * SCALE, j * SCALE, i * SCALE + SCALE, j
							* SCALE + SCALE, paintRayon);
					break;
				case 3:
					canvas.drawRect(i * SCALE, j * SCALE, i * SCALE + SCALE, j
							* SCALE + SCALE, paintItineraire);
					break;

				default:
					canvas.drawPoint(i, j, paintCouloir);

				}

			}
		
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
				* SCALE + SCALE, paintItineraire);
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
				* SCALE + SCALE, paintItineraire);
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
