package com.example.catlib_0612.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.Feed;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FeedAdapter extends BaseAdapter {
    private String TAG = "FeedAdapter";
    private Context context;
    private List<Feed> list;
    private StorageReference storageReference,pic_storage;
    public FeedAdapter(Context _context, List<Feed> _list){
        this.context = _context;
        this.list = _list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //@SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        storageReference = FirebaseStorage.getInstance().getReference();

        if (convertView == null) {
            // 第一次加载创建View，其余复用 View
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.feed_item, null);
            holder = new ViewHolder();
            holder.img_kittenFemaleCatGeneralNoGrain_picture = (ImageView) convertView.findViewById(R.id.img_kittenFemaleCatGeneralNoGrain_picture);
            holder.txt_kittenFemaleCatGeneralNoGrain_brand = (TextView) convertView.findViewById(R.id.txt_kittenFemaleCatGeneralNoGrain_brand);
            holder.txt_kittenFemaleCatGeneralNoGrain_name = (TextView) convertView.findViewById(R.id.txt_kittenFemaleCatGeneralNoGrain_name);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        pic_storage = storageReference.child(list.get(position).getPicture());
        final File file;
        try {
            file = File.createTempFile("images","png");
            ViewHolder finalHolder = holder;
            pic_storage.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Log.d(TAG, "onSuccess: file = "+file);
                    finalHolder.img_kittenFemaleCatGeneralNoGrain_picture.setImageURI(Uri.fromFile(file));
                    Log.d(TAG, "onSuccess: ");
                    //progressDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: ");
                }
            });
        }catch (IOException e){
            e.printStackTrace();
        }

        //holder.img_kittenFemaleCatGeneralNoGrain_picture.setImageDrawable(context.getDrawable(list.get(position).getPicture()));
        holder.txt_kittenFemaleCatGeneralNoGrain_brand.setText("品牌:"+list.get(position).getBrand());
        holder.txt_kittenFemaleCatGeneralNoGrain_name.setText("名稱: "+list.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        ImageView img_kittenFemaleCatGeneralNoGrain_picture;
        TextView txt_kittenFemaleCatGeneralNoGrain_brand;
        TextView txt_kittenFemaleCatGeneralNoGrain_name;
    }
}
