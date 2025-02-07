package com.example.catlib_0612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.AninalHouse;

import java.util.List;

public class AnimalHouseAdapter extends BaseAdapter {
    private Context context;
    private List<AninalHouse> list;
    public AnimalHouseAdapter(Context _context,List<AninalHouse> _list){
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
        AnimalHouseAdapter.ViewHolder holder = null;
        if (convertView == null) {
            // 第一次加载创建View，其余复用 View
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.animal_house_item, null);
            holder = new AnimalHouseAdapter.ViewHolder();
            holder.img_animalHouseItem_picture = (ImageView) convertView.findViewById(R.id.img_animalHouseItem_picture);
            holder.txt_animalHouseItem_title = (TextView) convertView.findViewById(R.id.txt_animalHouseItem_title);
            holder.txt_animalHouseItem_phone = (TextView) convertView.findViewById(R.id.txt_animalHouseItem_phone);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (AnimalHouseAdapter.ViewHolder) convertView.getTag();
        }

        holder.img_animalHouseItem_picture.setImageDrawable(context.getDrawable(list.get(position).getPicture()));
        holder.txt_animalHouseItem_title.setText(list.get(position).getTitle());
        holder.txt_animalHouseItem_phone.setText("電話: "+list.get(position).getPhone());

        return convertView;
    }

    class ViewHolder {
        ImageView img_animalHouseItem_picture;
        TextView txt_animalHouseItem_title;
        TextView txt_animalHouseItem_phone;
    }
}
