package com.example.asus1.mutualfriend.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
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
public class FriednsAdapter extends BaseAdapter implements Filterable {
    LayoutInflater lInflater;
    Context context;
    ArrayList<User> objects;
    static ArrayList<User> filterObjects;
    private ItemFilter mFilter = new ItemFilter();
    public FriednsAdapter(Context context, ArrayList<User> arUsers) {
        this.context = context;
        this.objects = arUsers;
        this.filterObjects = arUsers;

        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filterObjects.size();
    }

    @Override
    public User getItem(int position) {
        return filterObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    // товар по позиции
    public static User getfilterObjects(int position) {
        return filterObjects.get(position);
    }
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

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    private static class Holder {
        public TextView tvFirstName;
        public TextView tvLastName;
        public ImageView imAvatar;

    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<User> list = objects;

            int count = list.size();
            final ArrayList<User> nlist = new ArrayList<User>(count);

            String filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = list.get(i).getFirst_name() + " " + list.get(i).getLast_name();
                if (filterableString.toLowerCase().contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterObjects = (ArrayList<User>) results.values;
            notifyDataSetChanged();
        }

    }

}
