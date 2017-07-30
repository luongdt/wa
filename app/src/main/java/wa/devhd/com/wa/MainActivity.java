package wa.devhd.com.wa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdSize;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import twice.devhd.com.R;
import wa.devhd.com.wa.adapter.MainAdapter;
import wa.devhd.com.wa.backgroud.DownloadResultReceiver;
import wa.devhd.com.wa.backgroud.DownloadService;
import wa.devhd.com.wa.object.DataObject;
import wa.devhd.com.wa.sqlite.MyDB;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainAdapter.ItemClickListener,
        DownloadResultReceiver.Receiver {
    MainAdapter adapter;
    final String url = "https://twice-74db8.firebaseapp.com/main1.json";
    private DownloadResultReceiver mReceiver;
    private ArrayList<DataObject> wallpaperListNew = new ArrayList<DataObject>();
    RequestQueue rq;
    private FirebaseAnalytics mFirebaseAnalytics;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
      //  bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
      //  bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "mainscreen");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
       LinearLayout ll = (LinearLayout) findViewById(R.id.adView);
        mAdView = new AdView(this);
        ll.addView(mAdView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("6CC527FC424854AA3C7C84D42273BCC9").build();
        mAdView.setAdSize(AdSize.BANNER);
        mAdView.setAdUnitId("ca-app-pub-9684523147022787/9840062754");
        mAdView.loadAd(adRequest);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Utilities.sMyDb = new MyDB(getApplicationContext());
        initData();
        rq = Volley.newRequestQueue(this);



        ///////////////////////////load view
     //   for(int  i = 0 ; i < 10; ++i){
     //       listUrl.add(i,"http://anhnendep.net/wp-content/uploads/2016/02/vit-boi-roi-Psyduck.jpg");
     //   }
        getData();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.cardList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
         adapter = new MainAdapter(wallpaperListNew, getBaseContext());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        getData2();

        mReceiver = new DownloadResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent intent = new Intent(Intent.ACTION_SYNC, null, this,
                DownloadService.class);

		/* Send optional extras to Download IntentService */
        intent.putExtra("url", url);
        intent.putExtra("receiver", mReceiver);
        intent.putExtra("requestId", 101);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(View view, int position) {
        Log.e("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);

        Intent in = new Intent(this, DetailActivity.class);
        in.putExtra("url", wallpaperListNew.get(position).getThumbs());
        startActivity(in);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // /////////////////////////////
    public void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivity.this);
        builder.setMessage("Are you sure?")
                .setCancelable(false)
                .setTitle("Exit")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = builder.create();
        alert.setIcon(R.drawable.ic_menu_manage);
        alert.show();
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case DownloadService.STATUS_RUNNING:

                setProgressBarIndeterminateVisibility(true);
                break;
            case DownloadService.STATUS_FINISHED:
			/* Hide progress & extract result from bundle */
                setProgressBarIndeterminateVisibility(false);

                int count = Utilities.sMyDb.getSize();
                //

                try {
                    JSONArray js = new JSONArray(resultData.getString("result"));
                    for (int i = 0; i < js.length(); ++i) {
                        JSONObject jObject = new JSONObject(js.getString(i));
                        DataObject data = new DataObject(jObject.getInt("id"),
                                jObject.getString("thumbs"),
                                false);
                        try {
                       //     Utilities.sMyDb.updateData(data);
                        } catch (Exception e) {
                            Utilities.sMyDb.createRecordsInit(data);
                            e.printStackTrace();
                        }
                    }
                    // if (count == 1) {
                    // for (int i = 0; i < Integer.parseInt(js.getString(0)); i++) {
                    // Utilities.sMyDb.createRecordsInit(js.getString(1)
                    // + (i + 1) + ".jpg",
                    // js.getString(getStringScreen(width)) + (i + 1)
                    // + ".jpg");
                    // }
                    // } else {
                    // if (count < Integer.parseInt(js.getString(0))) {
                    // for (int i = count; i < Integer.parseInt(js.getString(0));
                    // i++) {
                    // Utilities.sMyDb.createRecordsInit(js.getString(1)
                    // + (i + 1) + ".jpg",
                    // js.getString(getStringScreen(width)) + (i + 1)
                    // + ".jpg");
                    // }
                    // }
                    // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
			/* Update ListView with result */
                // arrayAdapter = new ArrayAdapter(MyActivity.this,
                // android.R.layout.simple_list_item_2, results);
                // listView.setAdapter(arrayAdapter);

                break;
            case DownloadService.STATUS_ERROR:
			/* Handle the error */
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                break;
        }

    }

    void initData() {
        int count = Utilities.sMyDb.getSize2();
        if (count == 0) {
            try {
                JSONArray m_jArry = new JSONArray(loadJSONFromAsset()); // obj.getJSONArray("formules");
              //  JSONObject jsdata = js.getJSONObject("data");
              //  JSONArray m_jArry = jsdata.ge
                for (int i = 1; i < m_jArry.length(); ++i) {

                    String jObject = m_jArry.getString(i);
                    DataObject data = new DataObject(i,jObject
                           ,
                           false);
                    Utilities.sMyDb.createRecordsInit(data);
                }
            } catch (JSONException e) {

                e.printStackTrace();
            }
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("main.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        // Log.e("", "json ===" + json);
        return json;
    }

    public int getStringScreen(int width) {
        if (width < 1440) {
            if (width > 790) {
                return 3;
            } else {
                return 4;
            }
        } else {
            return 2;
        }
    }

    void getData() {
        try {
            wallpaperListNew = Utilities.sMyDb.getAllData();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // /////////////
    void getData2() {
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        int count = Utilities.sMyDb.getSize();

                        if (count < response.length()) {
                            try {
                                //	JSONArray m_jArry = new JSONArray(loadJSONFromAsset()); // obj.getJSONArray("formules");
                                for (int i = count ; i < response.length(); ++i) {
                                    JSONObject jObject = new JSONObject(response.getString(i));
                                    DataObject data = new DataObject(jObject.getInt("id"),
                                            jObject.getString("thumbs"),
                                            false);
                                    wallpaperListNew.add(0, data);;
                                    Utilities.sMyDb.createRecordsInit(data);

                                }
                         //       mSwipeRefreshLayout.setRefreshing(false);
                                //	wallpaperListNew = new ArrayList<DataObject>();
                                //	wallpaperListNew = Utilities.sMyDb.getAllData();
                           //     mWallpaperAdapterNew.notifyDataSetChanged();
                            } catch (JSONException e) {

                                e.printStackTrace();
                            }
                        }else {
                         //   mSwipeRefreshLayout.setRefreshing(false);
                        }


                        /////////////////////////////////
//						for (int i = 0; i < response.length(); i++) {
//							try {
//								Log.e("", "a========" + response.toString());
//								String enW = response.getString(i);
//
//								// wallpaperListNew.add(enW);
//
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//
//						}


                        // mSmoothProgressbar.setVisibility(View.GONE);
                        // loading = true;
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // loading = true;
                // mSmoothProgressbar.setVisibility(View.GONE);

            }
        });
        rq.add(arrayRequest);
    }

}
