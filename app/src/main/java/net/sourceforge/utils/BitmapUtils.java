package net.sourceforge.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.util.Base64;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class BitmapUtils {

	public static byte[] readImage(String path) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		InputStream inStream = conn.getInputStream();
		return readStream(inStream);
	}

	public static byte[] readStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		return outSteam.toByteArray();
	}
	public static Bitmap bmpFromFile(Bitmap tfile, int lwidth, int lheight) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// tells the BitmapFactory class to just give us the bounds of the
		// image rather than attempting to decode the image itself.
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp =tfile;

		int wratio = (int) Math
				.ceil((bmpFactoryOptions.outWidth / (float) lwidth));
		int hratio = (int) Math
				.ceil((bmpFactoryOptions.outHeight / (float) lheight));
		if (wratio > 1 && hratio > 1) {
			if (wratio > hratio) {
				// Width ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = wratio;
			} else {
				// Height ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = hratio;
			}
		}

		// Decode it for real
		bmpFactoryOptions.inJustDecodeBounds = false;
		bmp = tfile;
		return bmp;
	}
	
	public static Bitmap bmpFromFileAbso(File tfile, int lwidth, int lheight) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// tells the BitmapFactory class to just give us the bounds of the
		// image rather than attempting to decode the image itself.
		bmpFactoryOptions.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(tfile.getAbsolutePath(), bmpFactoryOptions);

		int wratio = (int) Math
				.ceil((bmpFactoryOptions.outWidth / (float) lwidth));
		int hratio = (int) Math
				.ceil((bmpFactoryOptions.outHeight / (float) lheight));
		if (wratio > 1 && hratio > 1) {
			if (wratio > hratio) {
				// Width ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = wratio;
			} else {
				// Height ratio is larger, scale according to it
				bmpFactoryOptions.inSampleSize = hratio;
			}
		}

		// Decode it for real
		bmpFactoryOptions.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(tfile.getAbsolutePath(), bmpFactoryOptions);
	}

public static String imgToBase64(String imgPath, Bitmap bitmap,
                                 String imgFormat) {
//		if (imgPath != null && imgPath.length() > 0) {
//			bitmap = readBitmap(imgPath);
//		}
		if (bitmap == null) {
			// bitmap not found!!
			return "";
		}
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

			out.flush();
			out.close();

			byte[] imgBytes = out.toByteArray();
			return Base64.encodeToString(imgBytes, Base64.DEFAULT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Bitmap bmpFromFile(String filePath, int lwidth, int lheight) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		// tells the BitmapFactory class to just give us the bounds of the
		// image rather than attempting to decode the image itself.
		bmpFactoryOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, bmpFactoryOptions);

		int scale = 1;
		int wratio = (int) Math
				.ceil((bmpFactoryOptions.outWidth / (float) lwidth));
		int hratio = (int) Math
				.ceil((bmpFactoryOptions.outHeight / (float) lheight));
		if (wratio > 1 && hratio > 1) {
			if (wratio > hratio) {
				// Width ratio is larger, scale according to it
				scale = wratio;
			} else {
				// Height ratio is larger, scale according to it
				scale = hratio;
			}
		}

		// Decode it for real
		BitmapFactory.Options o2 = new BitmapFactory.Options();
		o2.inSampleSize = scale;
		return BitmapFactory.decodeFile(filePath, o2);
	}


	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static Bitmap toturn(Bitmap img) {
		Matrix matrix = new Matrix();
		matrix.postRotate(+90);
		int width = img.getWidth();
		int height = img.getHeight();
		img = Bitmap.createBitmap(img, 0, 0, width, height, matrix, true);
		return img;
	}

	public static Bitmap loadBitmapFromFile(File tfile, int lwidth, int lheight) {
		int degree = readPictureDegree(tfile.getAbsolutePath());
		Bitmap b = bmpFromFileAbso(tfile, lwidth, lheight);
		if (degree != 0) {
			Bitmap rb = toturn(b);
			return rb;
		}
		return b;
	}

	public static void recycleBackgroundBitMap(View view) {
		if (view != null) {
			BitmapDrawable bd = (BitmapDrawable) view.getBackground();
			rceycleBitmapDrawable(bd);
		}
	}

	private static void rceycleBitmapDrawable(BitmapDrawable bitmapDrawable) {
		if (bitmapDrawable != null) {
			Bitmap bitmap = bitmapDrawable.getBitmap();
			rceycleBitmap(bitmap);
		}
		bitmapDrawable = null;
	}

	private static void rceycleBitmap(Bitmap bitmap) {
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
			bitmap = null;
		}
	}

}
