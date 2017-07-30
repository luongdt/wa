package wa.devhd.com.wa.object;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DataObject implements Parcelable {
	private int id;
//	private String name;
	private String thumbs;
//	private int total;
//	private int status;
//	private int edit;
//	private String[] list;
//	private String[] listthumbs;
	private boolean favourite;

	public DataObject(int id, String thumbs, boolean favourite) {
		super();
		this.id = id;
	//	this.name = name;
		this.thumbs = thumbs;
	//	this.total = total;
	//	this.status = status;
	//	this.edit = edit;
	//	this.list = list;
	//	this.listthumbs = listthumbs;
		this.favourite = favourite;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//public String getName() {
	//	return name;
//	}

	//public void setName(String name) {
	//	this.name = name;
	//}

	public String getThumbs() {
		return thumbs;
	}

	public void setThumbs(String thumbs) {
		this.thumbs = thumbs;
	}

	//public int getTotal() {
	//	return total;
	//}

//	public void setTotal(int total) {
//		this.total = total;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public int getEdit() {
//		return edit;
//	}
//
//	public void setEdit(int edit) {
//		this.edit = edit;
//	}
//
//	public String[] getList() {
//		return list;
//	}
//
//	public String[] getListthumbs() {
//		return listthumbs;
//	}
//
//	public void setListthumbs(String[] listthumbs) {
//		this.listthumbs = listthumbs;
//	}
//
//	public void setList(String[] list) {
//		this.list = list;
//	}

	public boolean getFavourite() {
		return favourite;
	}

	public void setFavourite(boolean favourite) {
		this.favourite = favourite;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int id) {
		Bundle bundle = new Bundle();

		// insert the key value pairs to the bundle
	//	bundle.putString("name", name);
		bundle.putInt("_id", this.id);
	//	bundle.putInt("total", total);
	//	bundle.putInt("edit", edit);
	//	bundle.putInt("status", status);
		bundle.putString("thumbs", thumbs);
	//	bundle.putStringArray("list", list);
		bundle.putBoolean("favourite", favourite);
	//	bundle.putStringArray("listthumbs",listthumbs);

		// write the key value pairs to the parcel
		dest.writeBundle(bundle);

	}

	/**
	 * Creator required for class implementing the parcelable interface.
	 */
	public static final Parcelable.Creator<DataObject> CREATOR = new Creator<DataObject>() {

		@Override
		public DataObject createFromParcel(Parcel source) {
			// read the bundle containing key value pairs from the parcel
			Bundle bundle = source.readBundle();

			// instantiate a person using values from the bundle
			return new DataObject(bundle.getInt("_id"),
					 bundle.getString("thumbs"),
					bundle.getBoolean("favourite"));
		}

		@Override
		public DataObject[] newArray(int size) {
			return new DataObject[size];
		}

	};

}
