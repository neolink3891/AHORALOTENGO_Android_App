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

public class AdapterFI extends RecyclerView.Adapter<AdapterFI.ViewHolder> {
    private ArrayList<ItemFI> objList  = new ArrayList<>();
    ItemFI item;
    OnItemClickListener mItemClickListener;
    Context mContext;

    public AdapterFI(ArrayList<ItemFI> objList, Context context){
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
    public AdapterFI.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_feature, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdapterFI.ViewHolder viewHolder, int i){
        item = objList.get(i);

        viewHolder.tv_pid.setText(item.ID);
        viewHolder.tv_tit.setText(item.Titulo);
        viewHolder.tv_pre.setText("$" + item.Precio);

        new DownloadImageTask((ImageView) viewHolder.iv_pro)
                .execute("https://images_url/" + item.ID + ".png");
    }

    @Override
    public int getItemCount() {
        return objList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_tit, tv_pid, tv_pre;
        ImageView iv_pro;

        public ViewHolder(View view) {
            super(view);

            tv_tit = (TextView)view.findViewById(R.id.textView10);
            tv_pid = (TextView)view.findViewById(R.id.textView13);
            tv_pre = (TextView)view.findViewById(R.id.textView15);
            iv_pro = (ImageView)view.findViewById(R.id.imageView9);

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



