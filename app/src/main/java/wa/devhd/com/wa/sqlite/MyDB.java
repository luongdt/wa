package wa.devhd.com.wa.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;

import java.util.ArrayList;

import wa.devhd.com.wa.Utilities;
import wa.devhd.com.wa.object.DataObject;


public class MyDB {

	private MyDatabaseHelper dbHelper;

	private SQLiteDatabase database;

	public final static String EMP_TABLE = "Mydt"; // name of table

	public final static String EMP_ID = "_id"; // id value for employee
	public final static String EMP_THUMBS = "thumbs"; // name of employee
	public final static String EMP_LIST = "list";
	public final static String EMP_EDIT = "edit";
	public final static String EMP_STATUS = "status";
	public final static String EMP_TOTAL = "total";
	public final static String EMP_NAME = "name";
	public final static String EMP_FAVOURITE = "favourite";
	public final static String EMP_LISTTHUMBS = "listthumbs";

	/**
	 * 
	 * @param context
	 */
	public MyDB(Context context) {
		dbHelper = new MyDatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
	}

	public int getSize() {
		database = dbHelper.getReadableDatabase();
		Cursor c = database.rawQuery("SELECT _id FROM Mydt", null);

		c.moveToFirst();
		int total = c.getCount();
		c.close();

		return total;
	}
	public int getSize2() {
		database = dbHelper.getReadableDatabase();
		Cursor c = database.rawQuery("SELECT _id FROM Mydt", null);

		c.moveToFirst();
		int total = c.getCount();

		return total;
	}

	public long createRecordsInit(DataObject object) {
		ContentValues values = new ContentValues();
		values.put(EMP_ID, object.getId());
		values.put(EMP_THUMBS, object.getThumbs());
		values.put(EMP_EDIT, object.getEdit());
		values.put(EMP_STATUS, object.getStatus());
		values.put(EMP_TOTAL, object.getTotal());
		values.put(EMP_NAME, object.getName());
		values.put(EMP_FAVOURITE,
				Utilities.convertArrayToStringFav(object.getFavourite()));
		values.put(EMP_LIST, Utilities.convertArrayToString(object.getList()));
		values.put(EMP_LISTTHUMBS, Utilities.convertArrayToString(object.getListthumbs()));

		return database.insert(EMP_TABLE, null, values);
	}

	// public long createRecords(MoreItem item) {
	// ContentValues values = new ContentValues();
	// // values.put(EMP_ID, id);
	// values.put(EMP_THUMBS, item.getThumbs());
	// values.put(EMP_LINK, item.getLink());
	// values.put(EMP_FAVOURITE, item.isFavourite());
	// return database.insert(EMP_TABLE, null, values);
	// }

	// public Cursor selectRecords() {
	// String[] cols = new String[] { EMP_ID, EMP_THUMBS, EMP_LINK,
	// EMP_FAVOURITE };
	// Cursor mCursor = database.query(true, EMP_TABLE, cols, null, null,
	// null, null, null, null);
	// if (mCursor != null) {
	// mCursor.moveToFirst();
	// }
	// return mCursor; // iterate to get each value.
	// }

	public void deleteRecord(String url) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.delete(EMP_TABLE, EMP_THUMBS + " = ?", new String[] { url });
		db.close();
	}

	public ArrayList<DataObject> getAllData() throws JSONException {
		ArrayList<DataObject> list = new ArrayList<DataObject>();
		String selectQuery = "SELECT  * FROM " + EMP_TABLE +" ORDER BY "+EMP_ID+" DESC";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				DataObject m = new DataObject(
						cursor.getInt(cursor.getColumnIndex(EMP_ID)),
						cursor.getString(cursor.getColumnIndex(EMP_NAME)),
						cursor.getString(cursor.getColumnIndex(EMP_THUMBS)),
						cursor.getInt(cursor.getColumnIndex(EMP_TOTAL)),
						cursor.getInt(cursor.getColumnIndex(EMP_STATUS)),
						cursor.getInt(cursor.getColumnIndex(EMP_EDIT)),
						Utilities.convertStringToArray2(cursor.getString(cursor
								.getColumnIndex(EMP_LIST))),
						Utilities.convertStringToArrayFav(cursor
								.getString(cursor.getColumnIndex(EMP_FAVOURITE))),Utilities.convertStringToArray2(cursor.getString(cursor
						.getColumnIndex(EMP_LISTTHUMBS))));

				list.add(m);
			} while (cursor.moveToNext());
		}
		db.close();

		return list;
	}

	// public MoreItem geData(int posi) {
	// MoreItem list = new MoreItem();
	// String selectQuery = "SELECT  * FROM " + EMP_TABLE + "WHERE _id =" +
	// posi;
	// SQLiteDatabase db = dbHelper.getReadableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// if (cursor.moveToFirst()) {
	// do {
	// MoreItem m = new MoreItem();
	// m.setFavourite(cursor.getInt(cursor
	// .getColumnIndex(EMP_FAVOURITE)));
	// m.setLink(cursor.getString(cursor.getColumnIndex(EMP_LINK)));
	// m.setThumbs(cursor.getString(cursor.getColumnIndex(EMP_THUMBS)));
	// list.add(m);
	// } while (cursor.moveToNext());
	// }
	// db.close();
	//
	// return list;
	// }

	// public ArrayList<MoreItem> getAllFavourite() {
	// ArrayList<MoreItem> list = new ArrayList<MoreItem>();
	// String selectQuery = "SELECT  * FROM " + EMP_TABLE;
	// SQLiteDatabase db = dbHelper.getReadableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// if (cursor.moveToFirst()) {
	// do {
	// if (cursor.getInt(cursor.getColumnIndex(EMP_FAVOURITE)) == 1) {
	// MoreItem m = new MoreItem();
	// m.setFavourite(cursor.getInt(cursor
	// .getColumnIndex(EMP_FAVOURITE)));
	// m.setLink(cursor.getString(cursor.getColumnIndex(EMP_LINK)));
	// m.setThumbs(cursor.getString(cursor
	// .getColumnIndex(EMP_THUMBS)));
	// list.add(m);
	// }
	// } while (cursor.moveToNext());
	// }
	// db.close();
	//
	// return list;
	// }

	public ArrayList<String> getAllThumbs() {
		ArrayList<String> list = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + EMP_TABLE;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(cursor.getColumnIndex(EMP_THUMBS)));

			} while (cursor.moveToNext());
		}
		db.close();

		return list;
	}

	public ArrayList<String> getAllThumbsFavou() {
		ArrayList<String> list = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM " + EMP_TABLE;
		;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				if (cursor.getInt(cursor.getColumnIndex(EMP_FAVOURITE)) == 1)
					list.add(cursor.getString(cursor.getColumnIndex(EMP_THUMBS)));
			} while (cursor.moveToNext());
		}
		db.close();

		return list;
	}

	public ArrayList<DataObject> getAllDataFavou() throws Exception {
		ArrayList<DataObject> list = new ArrayList<DataObject>();
		String selectQuery = "SELECT  * FROM " + EMP_TABLE;
		;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {

				if (!cursor.getString(cursor.getColumnIndex(EMP_FAVOURITE))
						.equals("")) {

					DataObject m = new DataObject(
							cursor.getInt(cursor.getColumnIndex(EMP_ID)),
							cursor.getString(cursor.getColumnIndex(EMP_NAME)),
							cursor.getString(cursor.getColumnIndex(EMP_THUMBS)),
							cursor.getInt(cursor.getColumnIndex(EMP_TOTAL)),
							cursor.getInt(cursor.getColumnIndex(EMP_STATUS)),
							cursor.getInt(cursor.getColumnIndex(EMP_EDIT)),
							Utilities.convertStringToArray2(cursor
									.getString(cursor.getColumnIndex(EMP_LIST))),
							Utilities.convertStringToArrayFav(cursor
									.getString(cursor
											.getColumnIndex(EMP_FAVOURITE))),Utilities.convertStringToArray2(cursor.getString(cursor
							.getColumnIndex(EMP_LISTTHUMBS))));

					list.add(m);
				}
			} while (cursor.moveToNext());
		}
		db.close();

		return list;
	}

	public ArrayList<String> getAllFavou(int id) throws JSONException {
		ArrayList<String> list = new ArrayList<String>();
		String selectQuery = "SELECT * FROM " + EMP_TABLE + " WHERE _id == "
				+ id + "";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String[] like = Utilities.convertStringToArray2(cursor
						.getString(cursor.getColumnIndex(EMP_FAVOURITE)));
				if (like.length > 0 && like[0] != "") {
					for (int i = 0; i < like.length; ++i) {
						list.add(like[i]);
					}
				}

			} while (cursor.moveToNext());
		}
		db.close();

		return list;
	}

	// public ArrayList<String> getAllLinkFavou() {
	// ArrayList<String> list = new ArrayList<String>();
	// String selectQuery = "SELECT  * FROM " + EMP_TABLE;
	// SQLiteDatabase db = dbHelper.getReadableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// if (cursor.moveToFirst()) {
	// do {
	// if (cursor.getInt(cursor.getColumnIndex(EMP_FAVOURITE)) == 1)
	// list.add(cursor.getString(cursor.getColumnIndex(EMP_LINK)));
	//
	// } while (cursor.moveToNext());
	// }
	// db.close();
	//
	// return list;
	// }
	//
	// public ArrayList<String> getAllLink() {
	// ArrayList<String> list = new ArrayList<String>();
	// String selectQuery = "SELECT  * FROM " + EMP_TABLE;
	// SQLiteDatabase db = dbHelper.getReadableDatabase();
	// Cursor cursor = db.rawQuery(selectQuery, null);
	// if (cursor.moveToFirst()) {
	// do {
	//
	// list.add(cursor.getString(cursor.getColumnIndex(EMP_LINK)));
	//
	// } while (cursor.moveToNext());
	// }
	// db.close();
	//
	// return list;
	// }

	public void updateFavourite(int id, String count) throws Exception {
		String selectQuery = "SELECT * FROM " + EMP_TABLE + " WHERE _id == "
				+ id + "";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ArrayList<String> like = Utilities
						.convertStringToArrayFav(cursor.getString(cursor
								.getColumnIndex(EMP_FAVOURITE)));
				like.add(count);
				database.execSQL("UPDATE Mydt SET favourite='"
						+ Utilities.convertArrayToStringFav(like)
						+ "' WHERE _id=" + id + "");

			} while (cursor.moveToNext());
		}

		// ContentValues cv = new ContentValues();
		// cv.put("thumbs", item.getThumbs());
		// cv.put("link", item.getLink());
		// cv.put("favourite", item.isFavourite());
		// database.update(EMP_TABLE, cv, "_id " + "=" + item.getId(), null);
	}
	public void updateFavouriteRemove(int id, String count) throws Exception {
		String selectQuery = "SELECT * FROM " + EMP_TABLE + " WHERE _id == "
				+ id + "";
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				ArrayList<String> like = Utilities
						.convertStringToArrayFav(cursor.getString(cursor
								.getColumnIndex(EMP_FAVOURITE)));
				like.remove(count);
				database.execSQL("UPDATE Mydt SET favourite='"
						+ Utilities.convertArrayToStringFav(like)
						+ "' WHERE _id=" + id + "");

			} while (cursor.moveToNext());
		}

		// ContentValues cv = new ContentValues();
		// cv.put("thumbs", item.getThumbs());
		// cv.put("link", item.getLink());
		// cv.put("favourite", item.isFavourite());
		// database.update(EMP_TABLE, cv, "_id " + "=" + item.getId(), null);
	}

	public void updateData(DataObject data) throws Exception {

		database.execSQL("UPDATE Mydt SET name='" + data.getName()
				+ "', thumbs = '" + data.getThumbs() + "', status = "
				+ data.getStatus() + ", edit = " + data.getEdit()
				+ ", list = '" + data.getList() + "', total = "
				+ data.getTotal() + "  WHERE _id=" + data.getId() + "");

		// ContentValues cv = new ContentValues();
		// cv.put("thumbs", item.getThumbs());
		// cv.put("link", item.getLink());
		// cv.put("favourite", item.isFavourite());
		// database.update(EMP_TABLE, cv, "_id " + "=" + item.getId(), null);
	}
}
