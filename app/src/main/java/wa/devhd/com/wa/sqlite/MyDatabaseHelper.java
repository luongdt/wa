package wa.devhd.com.wa.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "DBPK";

	private static final int DATABASE_VERSION = 2;

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table Mydt ( _id integer primary key, name text ,thumbs text, total int,status int, edit int ,list text,favourite text ,listthumbs text);";

	public MyDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Method is called during creation of the database
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}

	// Method is called during an upgrade of the database,
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {

		database.execSQL("DROP TABLE IF EXISTS Mydt");
		onCreate(database);
	}
}
