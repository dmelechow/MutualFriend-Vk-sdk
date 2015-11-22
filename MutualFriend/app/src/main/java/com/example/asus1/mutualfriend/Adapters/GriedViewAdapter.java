package com.example.asus1.mutualfriend.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus1.mutualfriend.CropSquareTransformation;
import com.example.asus1.mutualfriend.Entity.User;
import com.example.asus1.mutualfriend.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Asus1 on 20.11.2015.
 */
public class GriedViewAdapter extends BaseAdapter
{
    private ArrayList<User> object;
    LayoutInflater inflator;
    Context context;
    public GriedViewAdapter(Context context, ArrayList<User> user) {
        super();
        this.object = user;
        this.context = context;
        inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return object.size();
    }

    @Override
    public User getItem(int position) {
        // TODO Auto-generated method stub
        return object.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewFlag;
        public TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if(getCount() != 0) {
            ViewHolder view;
            //LayoutInflater inflator = mainFragment.getLayoutInflater();

            if (convertView == null) {
                view = new ViewHolder();
                convertView = inflator.inflate(R.layout.gridview_row, null);

                view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
                view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

                convertView.setTag(view);
            } else {
                view = (ViewHolder) convertView.getTag();
            }

            view.txtViewTitle.setText(object.get(position).getFirst_name() + " " + object.get(position).getLast_name());
            if(object.get(position).getAvatar() != null) {
                Picasso.with(context)
                        .load(object.get(position).getAvatar())
                        .transform(new CropSquareTransformation())
                        .into(view.imgViewFlag);
            }
        }
        return convertView;
    }
}