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

	public static void creationImage(int[][] matrice) throws Throwable {

		Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap);

		Paint paintW = new Paint();
		paintW.setStyle(Paint.Style.FILL);
		paintW.setColor(Color.WHITE);

		Paint paintP = new Paint();
		paintP.setStyle(Paint.Style.FILL);
		paintP.setColor(Color.RED);

		Paint paintB = new Paint();
		paintB.setStyle(Paint.Style.FILL);
		paintB.setColor(Color.BLUE);

		Paint paintG = new Paint();
		paintG.setStyle(Paint.Style.FILL);
		paintG.setColor(Color.GREEN);

		for (int i = 0; i < matrice.length; i++)
			for (int j = 0; j < matrice[0].length; j++) {
				switch (matrice[i][j]) {

				case 0:
					canvas.drawPoint(i, j, paintW);
					break;
				case 1:
					canvas.drawPoint(i, j, paintP);
					break;
				case 2:
					canvas.drawPoint(i, j, paintB);
					break;
				case 3:
					canvas.drawPoint(i, j, paintG);
					break;
				default:
					canvas.drawPoint(i, j, paintW);

				}

			}

		// Définition du fichier
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath());
		dir.mkdirs();
		File file = new File(dir, "/oledcomm/image/1.jpg");

		// Ecriture dans le fichier
		try {
			OutputStream stream = new FileOutputStream(file, false);
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
