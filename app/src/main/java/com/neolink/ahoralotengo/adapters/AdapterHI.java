package com.neolink.ahoralotengo.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.neolink.ahoralotengo.R;
import androidx.recyclerview.widget.RecyclerView;
import java.io.InputStream;
import java.util.ArrayList;

public class AdapterHI extends RecyclerView.Adapter<AdapterHI.ViewHolder> {
    private ArrayList<ItemHI> objList  = new ArrayList<>();
    ItemHI item;
    OnItemClickListener mItemClickListener;
    Context mContext;

    public AdapterHI(ArrayList<ItemHI> objList, Context context){
        this.objList = objList;
        this.mContext = context;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public AdapterHI.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_normal_items, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterHI.ViewHolder viewHolder, int i){
        item = objList.get(i);

        viewHolder.tv_tit.setText(item.Titulo);
        viewHolder.tv_exp.setText("Ver mas...");
        viewHolder.tv_t1.setText("$" + item.T1);
        viewHolder.tv_t2.setText("$" + item.T2);
        viewHolder.tv_t3.setText("$" + item.T3);
        viewHolder.tv_t4.setText("$" + item.T4);
        viewHolder.tv_p1.setText("$" + item.P1);
        viewHolder.tv_p2.setText("$" + item.P2);
        viewHolder.tv_p3.setText("$" + item.P3);
        viewHolder.tv_p4.setText("$" + item.P4);

        new DownloadImageTask((ImageView) viewHolder.iv_p1).execute("https://images_url/" + item.ID1 + ".png");
        new DownloadImageTask((ImageView) viewHolder.iv_p2).execute("https://images_url/" + item.ID2 + ".png");
        new DownloadImageTask((ImageView) viewHolder.iv_p3).execute("https://images_url/" + item.ID3 + ".png");
        new DownloadImageTask((ImageView) viewHolder.iv_p4).execute("https://images_url/" + item.ID4 + ".png");
    }

    @Override
    public int getItemCount() {
        return objList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_tit, tv_exp, tv_t1, tv_t2, tv_t3, tv_t4, tv_p1, tv_p2, tv_p3, tv_p4;
        ImageView iv_p1, iv_p2, iv_p3, iv_p4;

        public ViewHolder(View view) {
            super(view);

            tv_tit = (TextView)view.findViewById(R.id.textView14);
            tv_exp = (TextView)view.findViewById(R.id.textView16);
            tv_t1 = (TextView)view.findViewById(R.id.textView17);
            tv_t2 = (TextView)view.findViewById(R.id.textView19);
            tv_t3 = (TextView)view.findViewById(R.id.textView21);
            tv_t4 = (TextView)view.findViewById(R.id.textView23);
            tv_p1 = (TextView)view.findViewById(R.id.textView18);
            tv_p2 = (TextView)view.findViewById(R.id.textView20);
            tv_p3 = (TextView)view.findViewById(R.id.textView22);
            tv_p4 = (TextView)view.findViewById(R.id.textView24);
            iv_p1 = (ImageView)view.findViewById(R.id.imageView10);
            iv_p2 = (ImageView)view.findViewById(R.id.imageView12);
            iv_p3 = (ImageView)view.findViewById(R.id.imageView13);
            iv_p4 = (ImageView)view.findViewById(R.id.imageView14);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(mItemClickListener != null){
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}




