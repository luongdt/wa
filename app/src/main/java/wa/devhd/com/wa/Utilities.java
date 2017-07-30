package wa.devhd.com.wa;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import android.view.Display;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import wa.devhd.com.wa.sqlite.MyDB;


public class Utilities {
	public static final String MY_PREF = "MyPref";
	public static final String NOTIFICATIONS_ENABLED = "NOTIFICATIONS_ENABLED";
	public static final String RUN_IN_BACKGROUND = "RUN_IN_BACKGROUND";
	public static final String DONT_SHOW_AGAIN = "DONT_SHOW_AGAIN";
	public static final String DONT_SHOW_AGAIN_DOWNLOAD = "DONT_SHOW_AGAIN_WALLPAPER";
	public static final String IMAGE_NAME = "NAME";
	public static final String MINUTES = "MINUTES";
	public static final String NOT_FIRST = "Whatever";
	public static final String URLS = "urls";
	private static final String NOT_ID = "NOT_ID";

	public static final String KIND = "KIND";
	public static final int RANDOM = 0;
	public static final int TOP = 1;
	public static MyDB sMyDb;



	public static void saveImageFromUrl(String source, Context context,
                                        String name) {
		try {
			URL url = new URL(source);
			Bitmap image = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
			saveImageToExternalStorage(context, name, image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT)
					.show();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static void saveImageFromUrlPNG(String source, Context context,
                                           String name) {
		try {
			URL url = new URL(source);
			Bitmap image = BitmapFactory.decodeStream(url.openConnection()
					.getInputStream());
			saveImageToExternalStoragePng(context, name, image);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT)
					.show();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			Toast.makeText(context, "Download Failed", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public static void saveImageToExternalStorage(Context context, String name,
                                                  Bitmap finalBitmap) {
		String root = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).toString();
		File myDir = new File(root + "/"
				+ "wa");
		myDir.mkdirs();
		String fileName = name + ".jpg";
		File file = new File(myDir, fileName);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MediaScannerConnection.scanFile(context,
				new String[] { file.toString() }, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String path, Uri uri) {

					}
				});
	}

	public static void saveImageToExternalStoragePng(Context context,
                                                     String name, Bitmap finalBitmap) {
		String root = Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).toString();
		File myDir = new File(root + "/");
		myDir.mkdirs();
		String fileName = name + ".png";
		File file = new File(myDir, fileName);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);
			finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		MediaScannerConnection.scanFile(context,
				new String[] { file.toString() }, null,
				new MediaScannerConnection.OnScanCompletedListener() {
					public void onScanCompleted(String path, Uri uri) {

					}
				});
	}

	public static boolean checkInternet(Activity activity) {
		ConnectivityManager conMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
			return true;

			// notify user you are online

		} else if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			return false;
			// notify user you are not online
		}
		return true;
	}

	public static String strSeparator = "__,__";

	public static String convertArrayToString(String[] array) {
		String str = "";
		for (int i = 0; i < array.length; i++) {
			str = str + array[i];
			// Do not append comma at the end of last element
			if (i < array.length - 1) {
				str = str + strSeparator;
			}
		}
		return str;
	}

	public static String convertArrayToStringFav(ArrayList<String> array) {
		String str = "";
		for (int i = 0; i < array.size(); i++) {
			str = str + array.get(i);
			// Do not append comma at the end of last element
			if (i < array.size() - 1) {
				str = str + strSeparator;
			}
		}
		return str;
	}

	public static String[] convertStringToArray(String str)
			throws JSONException {
		// String[] arr;// = str.split(strSeparator);

		if (str != null || str != "0") {
			JSONArray jsonArray = new JSONArray(str);
			String[] strArr = new String[jsonArray.length()];

			for (int i = 0; i < jsonArray.length(); i++) {
				strArr[i] = jsonArray.getString(i);
			}
			return strArr;
		}

		return null;
	}

	public static ArrayList<String> convertStringToArrayFav(String str)
			throws JSONException {
		ArrayList<String> arr = new ArrayList<String>();// =
															// str.split(strSeparator);
		if (str != "" && str != null) {
			String[] ll = str.split(strSeparator);
			for (int j = 0; j < ll.length; ++j) {
				if (ll[j] != "")
					arr.add(ll[j]);
			}
		}

		return arr;
	}
	public static boolean convertBoolean(int i){
		if(i == 1)
			return true;
		else return false;

	}

	public static String[] convertStringToArray2(String str)
			throws JSONException {
		String[] arr = str.split(strSeparator);

		return arr;
	}

	public static int getWResulation(Activity context) {
		Display display = context.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size.x;
	}

	public static boolean isTablet(Context context) {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}

	public static boolean exists(String URLName) {
		try {
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(URLName)
					.openConnection();
			con.setRequestMethod("HEAD");
			return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
