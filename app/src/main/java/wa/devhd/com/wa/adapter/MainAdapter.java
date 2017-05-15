package wa.devhd.com.wa.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import wa.devhd.com.wa.R;

/**
 * Created by Luongdt on 5/16/2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemViewHolder>{


    private ArrayList<String> contactInfoList;
    private Context mContext;

    public MainAdapter(ArrayList<String> contactList, Context context) {
        this.contactInfoList = contactList;
        mContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        String url = contactInfoList.get(position);
        Glide.with(mContext)
                .load(url)
                .into(holder.iV);


    }

    @Override
    public int getItemCount() {
        return contactInfoList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        protected ImageView iV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iV = (ImageView) itemView.findViewById(R.id.iVitem);

        }
    }
}
