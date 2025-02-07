package com.example.catlib_0612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.CatMix;
import com.example.catlib_0612.data.CatPattern;

import java.util.List;

public class CatMixAdapter extends BaseAdapter {
    private Context context;
    private List<CatMix> list;

    public CatMixAdapter(List<CatMix> _list,Context _context){
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
                    R.layout.cat_mix_item, null);
            holder = new ViewHolder();
            holder.txt_catMixItem_picture = (ImageView) convertView.findViewById(R.id.txt_catMixItem_picture);
            holder.txt_catMixItem_title = (TextView) convertView.findViewById(R.id.txt_catMixItem_title);
            holder.txt_catMixItem_color = (TextView) convertView.findViewById(R.id.txt_catMixItem_color);
            holder.txt_catMixItem_introduce = (TextView) convertView.findViewById(R.id.txt_catMixItem_introduce);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        /*holder.imageView.setImageResource((Integer) list.get(position).get(
                "image"));*/
        holder.txt_catMixItem_picture.setImageDrawable(context.getDrawable(list.get(position).getPicture()));
        holder.txt_catMixItem_title.setText(list.get(position).getTitle());
        holder.txt_catMixItem_color.setText(list.get(position).getColor());
        holder.txt_catMixItem_introduce.setText(list.get(position).getIntroduce());

        return convertView;
    }

    class ViewHolder {
        ImageView txt_catMixItem_picture;
        TextView txt_catMixItem_title,txt_catMixItem_color,txt_catMixItem_introduce;
    }
}
