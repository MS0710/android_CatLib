package com.example.catlib_0612.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.UserMyCat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyCatAdapter extends BaseAdapter {
    private String TAG = "MyCatAdapter";
    private Context context;
    private List<UserMyCat> list;
    private StorageReference storageReference,pic_storage;
    private String catName,catBreed,catBirthday;
    public MyCatAdapter(Context _context,List<UserMyCat> _list){
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

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;

        storageReference = FirebaseStorage.getInstance().getReference();

        if (convertView == null) {
            // 第一次加载创建View，其余复用 View
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.my_cat_item, null);
            holder = new ViewHolder();
            holder.img_myCatItem_picture = (ImageView) convertView.findViewById(R.id.img_myCatItem_picture);
            holder.img_myCatItem_birthdayIcon = (ImageView) convertView.findViewById(R.id.img_myCatItem_birthdayIcon);
            holder.txt_myCatItem_name = (TextView) convertView.findViewById(R.id.txt_myCatItem_name);
            holder.txt_myCatItem_breed = (TextView) convertView.findViewById(R.id.txt_myCatItem_breed);
            holder.txt_myCatItem_birthday = (TextView) convertView.findViewById(R.id.txt_myCatItem_birthday);
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
                    finalHolder.img_myCatItem_picture.setImageURI(Uri.fromFile(file));
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

        if(list.get(position).getFlag().equals("true")){
            holder.img_myCatItem_birthdayIcon.setImageDrawable(context.getDrawable(R.drawable.birth_red));
        }else {
            holder.img_myCatItem_birthdayIcon.setImageDrawable(context.getDrawable(R.drawable.birthdya_black));
        }
        catName = list.get(position).getName();
        catBreed = list.get(position).getBreed();
        catBirthday = list.get(position).getBirthday();

        //holder.img_animalHouseItem_picture.setImageDrawable(context.getDrawable(list.get(position).getPicture()));
        holder.txt_myCatItem_name.setText("姓名:"+catName);
        holder.txt_myCatItem_breed.setText("品種: "+catBreed);
        holder.txt_myCatItem_birthday.setText("生日: "+catBirthday);

        return convertView;
    }

    class ViewHolder {
        ImageView img_myCatItem_picture;
        ImageView img_myCatItem_birthdayIcon;
        TextView txt_myCatItem_name;
        TextView txt_myCatItem_breed;
        TextView txt_myCatItem_birthday;
    }
}
