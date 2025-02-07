package com.example.catlib_0612.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catlib_0612.R;
import com.example.catlib_0612.data.CatBody;
import com.example.catlib_0612.data.CatBreed;

import java.util.List;

public class CatBodyAdapter extends BaseAdapter {
    private Context context;
    private List<CatBody> list;

    public CatBodyAdapter(List<CatBody> _list,Context _context){
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
                    R.layout.cat_body_item, null);
            holder = new ViewHolder();
            holder.txt_catBodyItem_posture = (TextView) convertView.findViewById(R.id.txt_catBodyItem_posture);
            holder.txt_catBodyItem_explain = (TextView) convertView.findViewById(R.id.txt_catBodyItem_explain);
            // 打标签
            convertView.setTag(holder);

        } else {
            // 从标签中获取数据
            holder = (ViewHolder) convertView.getTag();
        }

        // 根据key值设置不同数据内容
        /*holder.imageView.setImageResource((Integer) list.get(position).get(
                "image"));*/
        holder.txt_catBodyItem_posture.setText(list.get(position).getPosture());
        holder.txt_catBodyItem_explain.setText(list.get(position).getExplain());

        return convertView;
    }

    class ViewHolder {
        TextView txt_catBodyItem_posture,txt_catBodyItem_explain;
    }
}
