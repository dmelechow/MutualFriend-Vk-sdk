package com.example.asus1.mutualfriend.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.asus1.mutualfriend.Adapters.FriednsAdapter;
import com.example.asus1.mutualfriend.Entity.User;
import com.example.asus1.mutualfriend.R;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Asus1 on 18.11.2015.
 */
public class ChooseUserFragment extends android.support.v4.app.Fragment {
    int numberUser;
    OnUserSelected mListener;
    // Container Activity must implement this interface
    public interface OnUserSelected {
        public void onUserSelected(User articleUri, int numberUser);
    }
    // Все друзья
    ArrayList<User> arUsers;

    // Для адаптера
    private FriednsAdapter friednsAdapter;
    private ListView lvGoods;

    public ChooseUserFragment(int numberUser) {
        super();
        this.numberUser = numberUser;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_user, container, false);

        lvGoods = (ListView) view.findViewById(R.id.lvFriends);
        arUsers = new ArrayList<User>();




        // imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "contacts,id,first_name,last_name,sex,bdate,city,photo_max_orig"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                try {
                    JSONObject json = (JSONObject) new JSONTokener(response.json.toString()).nextValue();
                    Log.e("MyApp", json.toString());
                    JSONObject json2 = json.getJSONObject("response");
                    Log.e("MyApp", json2.toString());
                    final JSONArray json3 = json2.getJSONArray("items");
                    Log.e("MyApp", json3.toString());

                    for (int i = 0; i < json3.length(); i++) {
                        final JSONObject issue = json3.getJSONObject(i);
                        arUsers.add(new User(issue.getInt("id"),issue.getString("first_name"), issue.getString("last_name"), issue.getString("photo_max_orig")));
                    }
                    Collections.sort(arUsers, User.userAlphabet);
                    friednsAdapter = new FriednsAdapter(getActivity(), arUsers);
                    lvGoods.setAdapter(friednsAdapter);
                    lvGoods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mListener.onUserSelected(arUsers.get(position),numberUser);
                            getFragmentManager().popBackStack();
                        }
                    });
                } catch (JSONException e) {
                    Log.e("MyApp", "wrong");
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnUserSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}