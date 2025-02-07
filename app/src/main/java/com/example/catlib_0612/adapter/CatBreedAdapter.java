package com.example.catlib_0612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.CatBreed;
import com.example.catlib_0612.data.CatPattern;

import java.util.List;

public class CatBreedAdapter extends BaseAdapter {
    private Context context;
    private List<CatBreed> list;

    public CatBreedAdapter(List<CatBreed> _list,Context _context){
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
        if (convertView == null) {
            // 第一次加载创建View，其余复用 View
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.cat_breed_item, null);
            holder = new ViewHolder();
            holder.txt_catBreedItem_picture = (ImageView) convertView.findViewById(R.id.txt_catBreedItem_picture);
            holder.txt_catBreedItem_title = (TextView) convertView.findViewById(R.id.txt_catBreedItem_title);
            holder.txt_catBreedItem_introduce1 = (TextView) convertView.findViewById(R.id.txt_catBreedItem_introduce1);
            holder.txt_catBreedItem_introduce2 = (TextView) convertView.findViewById(R.id.txt_catBreedItem_introduce2);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        /*holder.imageView.setImageResource((Integer) list.get(position).get(
                "image"));*/
        holder.txt_catBreedItem_picture.setImageDrawable(context.getDrawable(list.get(position).getPicture()));
        holder.txt_catBreedItem_title.setText(list.get(position).getTitle());
        holder.txt_catBreedItem_introduce1.setText(list.get(position).getIntroduce1());
        holder.txt_catBreedItem_introduce2.setText(list.get(position).getIntroduce2());


        return convertView;
    }

    class ViewHolder {
        ImageView txt_catBreedItem_picture;
        TextView txt_catBreedItem_title,txt_catBreedItem_introduce1,txt_catBreedItem_introduce2;
    }
}
