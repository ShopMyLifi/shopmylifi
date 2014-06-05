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
	 * POSITIONS DES CAPTEURS LIFI origine en haut à gauche Y axe horizontal
	 * (croissant -> droite) X axe vertical (croissant -> bas)
	 */
	
	static final int Y_TXT_DEC = SCALE * 2;
	static final int X_INIT = 50;
	static final int X_LEG = 50;
	static final int SIZELEGENDE = 100;
	static final int STARTARTICLE = 100;
	static final int TEXTSIZE = 12;
	static final int X1 = 36;
	static final int Y1 = 3;
	static final int X2 = 36;
	static final int Y2 = 9;
	static final int X3 = 36;
	static final int Y3 = 15;
	static final int NUMBEROFPAINT = 4; // nombre de types de cases dans la
										// matrice, sans compter la position

	public static void creationImage(int[][] matrice) throws Throwable {

		int dim1 = matrice.length;
		int dim2 = matrice[0].length;

		Bitmap bitmap = Bitmap.createBitmap(dim2 * SCALE + SIZELEGENDE, dim1
				* SCALE, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);

		Paint tabPaint[] = new Paint[NUMBEROFPAINT];

		Paint paintCouloir = new Paint();
		paintCouloir.setStyle(Paint.Style.FILL);
		paintCouloir.setColor(Color.WHITE);
		tabPaint[0] = paintCouloir;

		Paint paintCaisse = new Paint();
		paintCaisse.setStyle(Paint.Style.FILL);
		paintCaisse.setARGB(255, 37, 151, 248);
		tabPaint[1] = paintCaisse;

		Paint paintRayon = new Paint();
		paintRayon.setStyle(Paint.Style.FILL);
		paintRayon.setARGB(255, 109, 185, 0);
		tabPaint[2] = paintRayon;

		Paint paintItineraire = new Paint();
		paintItineraire.setStyle(Paint.Style.FILL);
		paintItineraire.setARGB(255, 248, 176, 37);
		tabPaint[3] = paintItineraire;

		Paint paintArticle = new Paint();
		paintArticle.setStyle(Paint.Style.FILL);
		paintArticle.setARGB(255, 248, 197, 37);

		Paint paintPos = new Paint();
		paintPos.setStyle(Paint.Style.FILL);
		paintPos.setColor(Color.RED);

		Paint paintText = new Paint();
		paintText.setColor(Color.BLACK);
		paintText.setTextSize(TEXTSIZE);

		// fond blanc pour la légende
		canvas.drawRect(dim2 * SCALE, 0, dim2 * SCALE + SIZELEGENDE, dim1
				* SCALE, paintCouloir);

		int baseY = (dim2 +1 + 1) * SCALE  ;
		
		
		//création légende
		canvas.drawRect(baseY ,X_INIT,baseY + SCALE, X_INIT + SCALE, paintRayon);
		canvas.drawText("Rayon",baseY + Y_TXT_DEC, X_INIT + SCALE, paintText);
		
		canvas.drawRect(baseY ,X_INIT + 2*SCALE,baseY + SCALE, X_INIT + 3*SCALE, paintCaisse);
		canvas.drawText("Caisse",baseY + Y_TXT_DEC, X_INIT + 3*SCALE, paintText);
		
		canvas.drawRect(baseY ,X_INIT + 4*SCALE,baseY + SCALE, X_INIT + 5*SCALE, paintPos);
		canvas.drawText("Position",baseY + Y_TXT_DEC, X_INIT + 5*SCALE, paintText);
		
		canvas.drawRect(baseY ,X_INIT + 6*SCALE,baseY + SCALE, X_INIT + 7*SCALE, paintItineraire);
		canvas.drawText("Itinéraire",baseY + Y_TXT_DEC, X_INIT + 7*SCALE, paintText);
		
		canvas.drawRect(baseY ,X_INIT + 8*SCALE,baseY + SCALE, X_INIT + 9*SCALE, paintArticle);
		canvas.drawText("2", baseY-4, X_INIT + 9*SCALE, paintText);
		canvas.drawText("Article",baseY + Y_TXT_DEC, X_INIT + 9*SCALE, paintText);

		for (int i = 0; i < dim1; i++)
			for (int j = 0; j < dim2; j++) {

				int type = matrice[i][j];
				if (type < NUMBEROFPAINT) {
					canvas.drawRect(j * SCALE, i * SCALE, j * SCALE + SCALE, i
							* SCALE + SCALE, tabPaint[matrice[i][j]]);
				}

				else if (type > STARTARTICLE) {
					canvas.drawRect(j * SCALE, i * SCALE, j * SCALE + SCALE, i
							* SCALE + SCALE, paintArticle);
					canvas.drawText(Integer.toString(type - STARTARTICLE), j
							* SCALE - 4, (i + 1) * SCALE, paintText);
				}

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
