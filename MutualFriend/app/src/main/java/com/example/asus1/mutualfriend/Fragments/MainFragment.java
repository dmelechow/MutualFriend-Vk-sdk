package com.example.asus1.mutualfriend.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.asus1.mutualfriend.Adapters.GriedViewAdapter;
import com.example.asus1.mutualfriend.Entity.User;
import com.example.asus1.mutualfriend.R;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

/**
 * Created by Asus1 on 18.11.2015.
 */
public class MainFragment extends Fragment{
    static private GriedViewAdapter griedViewAdapter;

    static ArrayList<User> arExUsers;
    static View v;
    static private GridView gridView;
    public MainFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_main, container, false);
        // Set custom adapter to gridview
        gridView = (GridView) v.findViewById(R.id.gridView1);
        arExUsers = new ArrayList<User>();

        arExUsers.add(new User(0,getResources().getString(R.string.not_chosen),"",null));
        arExUsers.add(new User(0,getResources().getString(R.string.not_chosen),"",null));
        // prepared arraylist and passed it to the Adapter class
        griedViewAdapter = new GriedViewAdapter(v.getContext(), arExUsers);


        gridView.setAdapter(griedViewAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.container, new ChooseUserFragment(position));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
    public void onUserSelected(User user, int numberUser) {
        arExUsers.set(numberUser, user);
        griedViewAdapter = new GriedViewAdapter(v.getContext(), arExUsers);
        gridView.setAdapter(griedViewAdapter);
        if(arExUsers.get(0).getId() != 0 && arExUsers.get(1).getId() != 0){
            //VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "contacts,id,first_name,last_name,sex,bdate,city,photo_max_orig"));
            VKRequest request = VKApi.friends().getMutual(VKParameters.from(arExUsers.get(0).getId(),arExUsers.get(0).getId(), arExUsers.get(1).getId(),arExUsers.get(1).getId()));
                    request.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onError(VKError error) {
                            super.onError(error);
                            Log.e("Mutual", "error");
                        }

                        @Override
                        public void onComplete(VKResponse response) {
                            super.onComplete(response);
                            try {
                                JSONObject json = (JSONObject) new JSONTokener(response.json.toString()).nextValue();
                                Log.e("Mutual", json.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                }
            });
        }
    }
}