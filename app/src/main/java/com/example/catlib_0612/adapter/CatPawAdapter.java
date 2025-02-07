package com.example.catlib_0612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.CatPaw;

import java.util.List;

public class CatPawAdapter extends BaseAdapter {
    private Context context;
    private List<CatPaw> list;

    public CatPawAdapter(List<CatPaw> _list,Context _context){
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
                    R.layout.cat_paw_item, null);
            holder = new ViewHolder();
            holder.img_catPaw_picture = (ImageView) convertView.findViewById(R.id.img_catPaw_picture);
            holder.txt_catPaw_mainTitle = (TextView) convertView.findViewById(R.id.txt_catPaw_mainTitle);
            holder.txt_catPaw_title = (TextView) convertView.findViewById(R.id.txt_catPaw_title);
            holder.txt_catPaw_content = (TextView) convertView.findViewById(R.id.txt_catPaw_content);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        /*holder.imageView.setImageResource((Integer) list.get(position).get(
                "image"));*/
        holder.txt_catPaw_mainTitle.setText(list.get(position).getMainTitle());
        holder.txt_catPaw_title.setText(list.get(position).getTitle());
        holder.txt_catPaw_content.setText(list.get(position).getContent());
        holder.img_catPaw_picture.setImageDrawable(context.getDrawable(list.get(position).getPicture()));

        return convertView;
    }

    class ViewHolder {
        ImageView img_catPaw_picture;
        TextView txt_catPaw_mainTitle,txt_catPaw_title,txt_catPaw_content;
    }
}
