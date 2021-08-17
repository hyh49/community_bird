package com.example.new_community_bird;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {

    public List<String> list;
    public LayoutInflater inflater;


    public ListViewAdapter() {
    }

    public ListViewAdapter(Context context, List<String> list) {
        this.list = list;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateView(List<String> nowList)
    {
        this.list.clear();
        this.list.addAll(nowList);
//        this.list = nowList;
        this.notifyDataSetChanged();//强制动态刷新数据进而调用getView方法
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if(convertView == null)
        {
            view = inflater.inflate(R.layout.array_adapter, null);
            holder = new ViewHolder();
            holder.textView = (TextView)view.findViewById(R.id.TextView);
            view.setTag(holder);//为了复用holder
        }else
        {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText(list.get(position));
        return view;
    }
    static class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }
}
