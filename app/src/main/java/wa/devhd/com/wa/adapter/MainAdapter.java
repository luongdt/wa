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


    private String[] contactInfoList;
    private Context mContext;
    private ItemClickListener mClickListener;

    public MainAdapter(String[] contactList, Context context) {
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
        String url = contactInfoList[position];
        Glide.with(mContext)
                .load(url)
                .into(holder.iV);


    }

    @Override
    public int getItemCount() {
        return contactInfoList.length;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        protected ImageView iV;

        public ItemViewHolder(View itemView) {
            super(itemView);
            iV = (ImageView) itemView.findViewById(R.id.iVitem);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }
    // convenience method for getting data at click position
    public String getItem(int id) {
        return contactInfoList[id];
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
