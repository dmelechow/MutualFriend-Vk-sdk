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
 * Created by Asus1 on 22.11.2015.
 */
public class FriednsAdapter extends BaseAdapter {
    LayoutInflater lInflater;
    Context context;
    ArrayList<User> objects;
    //int image;
    public FriednsAdapter(Context context, ArrayList<User> arUsers) {
        this.context = context;
        this.objects = arUsers;

        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public User getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // товар по позиции
    User getGood(int position) {
        return ((User) getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder;
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_adapter_friends, parent, false);
        }
        holder = new Holder();
        final User p = getGood(position);

        holder.tvFirstName = ((TextView) view.findViewById(R.id.tvFirstName));
        holder.tvLastName = ((TextView) view.findViewById(R.id.tvLastName));
        holder.imAvatar = (ImageView) view.findViewById(R.id.imAvatar);


        holder.tvFirstName.setText(p.getFirst_name());
        holder.tvLastName.setText(p.getLast_name());
        Picasso.with(context)
                .load(p.getAvatar())
                .transform(new CropSquareTransformation())
                //.placeholder(R.drawable.user_placeholder)
                //.error(R.drawable.user_placeholder_error)
                .into(holder.imAvatar);
        return view;
    }


    private static class Holder {
        public TextView tvFirstName;
        public TextView tvLastName;
        public ImageView imAvatar;

    }
}
