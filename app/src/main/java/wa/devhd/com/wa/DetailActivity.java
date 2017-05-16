package wa.devhd.com.wa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DetailActivity extends AppCompatActivity {
    private ImageView ivDetail;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent in = this.getIntent();
        url = in.getStringExtra("url");

        ivDetail = (ImageView) findViewById(R.id.iVDetal);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
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


    }

}
