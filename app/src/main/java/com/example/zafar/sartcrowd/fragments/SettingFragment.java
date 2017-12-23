package com.example.zafar.sartcrowd.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.Preference;
import android.view.View;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.SharedPreference.SessionManager;
import com.example.zafar.sartcrowd.activity.MainActivity;


public class SettingFragment extends PreferenceFragmentCompat {
    private OnFragmentInteractionListener mListener;
    SessionManager session;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        session = new SessionManager(getActivity());
        // Load the Preferences from the XML file
        addPreferencesFromResource(R.xml.setting_preferences);


        Preference myPref = (Preference) findPreference("rates");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                //open browser or intent here
                Intent i = new Intent (getActivity() , MainActivity.class);
                startActivity(i);

                return true;
            }
        });

        Preference signout = (Preference) findPreference("signout");
        signout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                //open browser or intent here
                session.logoutUser();
                return true;
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDivider(new ColorDrawable(Color.parseColor("#FF4081")));
        setDividerHeight(2);

    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
