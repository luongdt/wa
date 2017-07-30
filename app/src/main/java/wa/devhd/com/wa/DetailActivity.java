package wa.devhd.com.wa;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import twice.devhd.com.R;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivDetail;
    private String url;
    private ImageButton ibSave, ibShare, ibFavourite;
    private ProgressBar progressBar;
    private int SET_WALLPAPER_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent in = this.getIntent();
        url = in.getStringExtra("url");

        ivDetail = (ImageView) findViewById(R.id.iVDetal);
        ibFavourite = (ImageButton) findViewById(R.id.favourite);
        ibSave = (ImageButton) findViewById(R.id.save);
        ibShare = (ImageButton) findViewById(R.id.share);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        Glide.with(this)
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
             //   .placeholder(R.drawable.placeholder)
                .error(R.drawable.imagenotfound)
            //    .override(200, 200)
            //    .centerCrop()
                .into(ivDetail);

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ibSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new DownloadIAsyncTask().execute(url);
            }
        });
        ibFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    private class DownloadIAsyncTask extends AsyncTask<String, Void, Boolean> {
        Bitmap bmImg = null;
        Uri uriImage = null;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... name) {
            Utilities.saveImageFromUrlPNG(url,
                    DetailActivity.this, "tempp");

            // // try {
            File myDir = new File(Environment
                    .getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).toString()
                    + "/");

            if (myDir.list() != null) {
                for (String list : myDir.list()) {
                    uriImage = Uri.parse("file://" + myDir + "/" + "tempp.png");
                }
            }

            return true;
            // }
        }

        protected void onPostExecute(Boolean result) {

            progressBar.setVisibility(View.GONE);
            Intent setAs = new Intent(Intent.ACTION_ATTACH_DATA);
            setAs.setDataAndType(uriImage, "image/*");
            startActivityForResult(
                    Intent.createChooser(setAs, "Set Wallpaper"),
                    SET_WALLPAPER_REQUEST_CODE);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {

            if (resultCode == SET_WALLPAPER_REQUEST_CODE) {

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Oversmart Eh!!", Toast.LENGTH_LONG)
                        .show();
            }
        }
    }

}
