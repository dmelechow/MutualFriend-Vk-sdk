package com.example.asus1.mutualfriend.Activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus1.mutualfriend.CropSquareTransformation;
import com.example.asus1.mutualfriend.Entity.User;
import com.example.asus1.mutualfriend.Fragments.ChooseUserFragment;
import com.example.asus1.mutualfriend.Fragments.MainFragment;
import com.example.asus1.mutualfriend.R;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.payments.VKPaymentsCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class TestActivity extends ActionBarActivity  implements ChooseUserFragment.OnUserSelected {
	TextView tvWelcome;
	ImageView imWelcome;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		// Handle Toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		final AccountHeader headerResult = new AccountHeaderBuilder()
				.withActivity(this)
						//.withCompactStyle(true)
				.withTranslucentStatusBar(true)
						//.withHeaderBackground(new ColorDrawable(Color.parseColor("#FDFDFD")))
				.withHeightDp(120)
				.withAccountHeader(R.layout.material_drawer_compact_persistent_header)
				.withTextColor(Color.BLACK)
				.withSelectionListEnabledForSingleProfile(false)
				.withSavedInstance(savedInstanceState)
				.build();

		VKRequest request = VKApi.users().get(VKParameters.from(VKApiConst.FIELDS,
				"id,first_name,last_name,sex,bdate,city,country,photo_50,photo_100," +
						"photo_200_orig,photo_200,photo_400_orig,photo_max,photo_max_orig,online," +
						"online_mobile,lists,domain,has_mobile,contacts,connections,site,education," +
						"universities,schools,can_post,can_see_all_posts,can_see_audio,can_write_private_message," +
						"status,last_seen,common_count,relation,relatives,counters"));
		final Context context = this;
		request.executeWithListener(new VKRequest.VKRequestListener() {
			@Override
			public void onComplete(VKResponse response) {
				super.onComplete(response);

				try {
					JSONObject json = (JSONObject) new JSONTokener(response.json.toString()).nextValue();

					JSONArray json2 = json.getJSONArray("response");

					final JSONObject json3 = json2.getJSONObject(0);

					user = new User(0, json3.getString("first_name"), json3.getString("last_name"), json3.getString("photo_max_orig"));

					tvWelcome = (TextView) headerResult.getView().findViewById(R.id.tvWelcome);
					imWelcome = (ImageView) headerResult.getView().findViewById(R.id.material_drawer_account_header_background);
					tvWelcome.setText(user.getFirst_name() + " " + user.getLast_name());

					Picasso.with(context)
							.load(user.getAvatar())
							.transform(new CropSquareTransformation())
							.into(imWelcome);

				} catch (JSONException e) {
					Log.e("MyApp", "wrong");
				}
			}
		});




		//create the drawer and remember the `Drawer` result object
		Drawer result = new DrawerBuilder()
				.withActivity(this)
				.withToolbar(toolbar)
				.withTranslucentStatusBar(true)
				.withActionBarDrawerToggle(true)
				.withAccountHeader(headerResult)
				.addDrawerItems(

                        new PrimaryDrawerItem().withName(R.string.drawer_builder_main).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_builder_share).withIcon(FontAwesome.Icon.faw_share).withIdentifier(1),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.drawer_builder_log_out).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(1),
                        new DividerDrawerItem()
                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int id, IDrawerItem iDrawerItem) {
                        switch (id) {
                            case 1: {
                                FragmentManager fm = getFragmentManager();
                                for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                                    fm.popBackStack();
                                }
                                getSupportFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
                            }
                            break;
                            case 3: {

                            }break;
                            case 5: {
                                VKSdk.logout();
                                Intent intent = new Intent(TestActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            break;
                        }
                        return false;
                    }
                })
				.build();

		VKSdk.requestUserState(this, new VKPaymentsCallback() {
			@Override
			public void onUserState(final boolean userIsVk) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
//						Toast.makeText(TestActivity.this, userIsVk ? "user is vk's" : "user is not vk's", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		if (savedInstanceState == null) {

			getSupportFragmentManager().beginTransaction().add(R.id.container, new MainFragment()).commit();
		}
	}
	@Override
	public void onUserSelected(User articleUri, int numberUser) {
		MainFragment fragment = new MainFragment();
		fragment.onUserSelected(articleUri, numberUser);
	}

}