package com.example.zafar.sartcrowd.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.PwithC;
import com.example.zafar.sartcrowd.Model.Store;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.SharedPreference.SessionManager;
import com.example.zafar.sartcrowd.activity.OrderConfirm;
import com.example.zafar.sartcrowd.activity.ProductDetails;
import com.example.zafar.sartcrowd.other.MySingleton;
import com.example.zafar.sartcrowd.other.OnGetDataListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    ProgressDialog Dialog;
    private GoogleMap mMap;
    SessionManager session;
    private LocationManager locationManager;
    private boolean isLocationEnabled = false;
    ArrayList<Store> store = new ArrayList<Store>();
    String cat_id;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          session = new SessionManager(getActivity());

          cat_id = getArguments().getString("cat_id");
        Toast.makeText(getActivity(), "THE CAT : " + cat_id, Toast.LENGTH_SHORT).show();
     //   Toast.makeText(getActivity(),"MapIdo"+cat_id , Toast.LENGTH_SHORT).show();

        //        Wifi Enabled
        WifiManager wifi =(WifiManager)getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()){
            showWifiSettingsAlert();
        }

        //        Location Enabled
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        isLocationEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if(isLocationEnabled == false){
            showLocationSettingsAlert();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, null, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.MAP);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(Marker marker) {
                View myContentView = getActivity().getLayoutInflater().inflate(
                        R.layout.custom_info_window, null);

                TextView map_s_title = ((TextView) myContentView.findViewById(R.id.map_s_title));
                TextView map_s_review = ((TextView) myContentView.findViewById(R.id.map_s_review));
                TextView map_s_type = ((TextView) myContentView.findViewById(R.id.map_s_type));
                TextView map_s_timing = ((TextView) myContentView.findViewById(R.id.map_s_timing));
                RatingBar ratingBar = (RatingBar) myContentView.findViewById(R.id.map_myRatingBar);

                String title = marker.getTitle();
                String snippet = marker.getSnippet();

                String[] snippetParts = snippet.split(",");

                map_s_title.setText(title);
                map_s_review.setText(snippetParts[1]);
                map_s_type.setText(snippetParts[2]);
                map_s_timing.setText(snippetParts[3]);
                ratingBar.setRating(Float.parseFloat(""+snippetParts[1]));
                return myContentView;
            }
        });



        ReadDataBase(new OnGetDataListener() {
            @Override
            public void onStart() {
                Dialog = new ProgressDialog(getActivity());
                Dialog.setCanceledOnTouchOutside(false);
                Dialog.setMessage("Loading Map");
                Dialog.show();
            }

            @Override
            public void onSuccess(DataSnapshot data) {

                if(store!=null) {
                    for (int i = 0; i < store.size(); i++) {

                        String[] locationParts = store.get(i).getLocation().split(",");
                        Double lat = Double.valueOf(locationParts[0]);
                        Double lng = Double.valueOf(locationParts[1]);
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(lat, lng))
                                .title(store.get(i).getTitle())
                                .snippet(store.get(i).getStore_id()+",5 ,Convenience Store ,7:00 Am - 12:00Am")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                    }
                }
                Dialog.dismiss();
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });


//        mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(31.4834441,74.0460169))
//                .title("A & R Corner Store")
//                .snippet("5 ,Convenience Store ,7:00 Am - 12:00Am")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
//
//        mMap.addMarker(new MarkerOptions()
//                .position(new LatLng(33.5839054,73.0346243))
//                .title("Raju Super Store")
//                .snippet("3 ,General Store ,7:00 Am - 8:00 Pm")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

        String[] UserLocParts = session.getUserLocation().split(",");
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(UserLocParts[0]), Double.valueOf(UserLocParts[1])), 15));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                String[] snippetParts = arg0.getSnippet().split(",");
                Bundle arg = new Bundle();
                arg.putString("store_id" , snippetParts[0]);
                Fragment fragment = new ProductFragment();
                fragment.setArguments(arg);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();


//                Set Preference For Store
                SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("StorePref" , 0);
                SharedPreferences.Editor editor = pref.edit();

                editor.clear(); editor.commit();
                new Database(getActivity()).cleanCart();
                for(int i=0; i<store.size();i++){
                    if(snippetParts[0].equals(store.get(i).getStore_id())){
                        editor.putString("store_id", store.get(i).getStore_id());
                        editor.putString("business_user_id", store.get(i).getBusiness_user_id());
                        session.setStoreLocation(store.get(i).getLocation());
                        editor.commit();
                    }
                }
            }
        });

    }

    public void ReadDataBase(final OnGetDataListener listener){
        listener.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference StoreWithCatDataReference = databaseReference.child("store_with_cat");
        Query query = StoreWithCatDataReference.orderByChild("cat_id").equalTo(cat_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        PwithC pc = postSnapshot.getValue(PwithC.class);

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference StoreTableDataReference = databaseReference.child("store");
                        Query query = StoreTableDataReference.orderByChild("store_id").equalTo(pc.getStore_id());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    Store single_store = postSnapshot.getValue(Store.class);
                                    store.add(single_store);
                                }
                                listener.onSuccess(dataSnapshot);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }
                }else{
                    Dialog.dismiss();
                    Toast.makeText(getActivity(), "No Store Found", Toast.LENGTH_SHORT).show();
                }

            }
            @Override public void onCancelled(DatabaseError databaseError) {
                listener.onFailed(databaseError);
            }
        });
    }


    public void showLocationSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        // Setting Dialog Title
        alertDialog.setTitle("Please Enable GPS");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
                getActivity().finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public void showWifiSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Please Enable Wifi");

        // Setting Dialog Message
        alertDialog.setMessage("Wifi is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                getActivity().finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
