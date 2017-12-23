package com.example.zafar.sartcrowd.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.StringRequest;
import com.example.zafar.sartcrowd.Database.Database;
import com.example.zafar.sartcrowd.Model.Cart;
import com.example.zafar.sartcrowd.Model.Order;
import com.example.zafar.sartcrowd.Model.OrderDetail;
import com.example.zafar.sartcrowd.Model.User;
import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.SharedPreference.SessionManager;
import com.example.zafar.sartcrowd.fragments.HomeFragment;
import com.example.zafar.sartcrowd.other.GPSTracker;
import com.example.zafar.sartcrowd.other.MySingleton;
import com.example.zafar.sartcrowd.other.OnGetDataListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderConfirm extends AppCompatActivity {

    List<Cart> cartValues = new ArrayList<>();
    ArrayList<Cart> carts = new ArrayList<Cart>();
    int total=0 ,count=0 , i=0 , f_total=0;
    OkHttpClient mClient = new OkHttpClient();
    SharedPreferences pref;
    SharedPreferences.Editor editor ;
    String business_user_id , store_id , customer_id , order_location;
    JSONArray jsonArray = new JSONArray();
    User business_person , customer_person;
    SessionManager session;
    int PLACE_PICKER_REQUEST = 1;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        gps = new GPSTracker(getApplicationContext() , this);
        if(!gps.canGetLocation()){
            gps.showSettingsAlert();
        }
        session = new SessionManager(getApplicationContext());

//        Get Customer Id Through Preference
        customer_id = session.getUserDetails().get("user_id");

//        Setting Preferences
        pref = getSharedPreferences("StorePref" , 0); editor = pref.edit();
        store_id = pref.getString("store_id" , null);
        business_user_id = pref.getString("business_user_id" , null);


        cartValues = new Database(this).getCarts();
        for (Cart cart : cartValues) {
            count++;
            total += (Integer.parseInt(cart.getPrice())) * (Integer.parseInt(cart.getQuantity()));
            carts.add(cart);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Place Order");

        TextView sub_total = (TextView) findViewById(R.id.sub_total);
        TextView delivery_charges = (TextView) findViewById(R.id.delivery_charges);
        TextView number_of_products = (TextView) findViewById(R.id.products_count);

        //        Place Order Button
        final Button placeOrder = (Button) findViewById(R.id.place_order);
        placeOrder.setVisibility(View.GONE);

        TextView total_amount = (TextView) findViewById(R.id.total_amount);
        TextView payment_method = (TextView) findViewById(R.id.payment_method);

        Button get_location_btn = (Button) findViewById(R.id.get_order_location);
        get_location_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //            Getting Location For Order
                String[] storeParts = session.getUserLocation().split(",");
                LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
                        new LatLng(Double.valueOf(storeParts[0]), Double.valueOf(storeParts[1])), new LatLng(Double.valueOf(storeParts[0]), Double.valueOf(storeParts[1])));
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                builder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
                Intent intent;
                try {
                    intent = builder.build(OrderConfirm.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        final TextView delivery_time = (TextView) findViewById(R.id.delivery_time);


        if (cartValues.size() > 0) {
//        Setting Values
            sub_total.setText("Rs." + total + "/-");
            delivery_charges.setText("Rs.50/-");
            number_of_products.setText("" + count);

            total = total + 50;
            total_amount.setText("Rs." + total + "/-");
            payment_method.setText("Cash on Delivery");
            delivery_time.setText("06:00 p.m - 09:00 p.m");


            if(business_user_id != null) {
                final ProgressDialog Dialog = new ProgressDialog(this);
                Dialog.setCanceledOnTouchOutside(false);
                Dialog.setMessage("Loading Details");
                Dialog.show();

                ReadDatabase(new OnGetDataListener() {
                    @Override
                    public void onStart() {}

                    @Override
                    public void onSuccess(DataSnapshot data) {
                        int c_wallet = Integer.valueOf(customer_person.getWallet());
                        int cart_total = Integer.valueOf(total);
                        f_total = c_wallet - cart_total;

                        placeOrder.setVisibility(View.VISIBLE);
                        Dialog.dismiss();
                    }

                    @Override
                    public void onFailed(DatabaseError databaseError) {}
                });
            }

        }else {
            Toast.makeText(getApplication(), "Cart is Empty", Toast.LENGTH_SHORT).show();
            finish();
        }

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference OrderTableDataReference = databaseReference.child("order");
                String idya = OrderTableDataReference.push().getKey();
                Order order = new Order(idya, customer_id , business_user_id , "0", "0", String.valueOf(total), String.valueOf(count), "0", Calendar.getInstance().getTime().toString(), "06:00 p.m - 09:00 p.m", FirebaseInstanceId.getInstance().getToken().toString() , order_location , session.getStoreLocation());
                OrderTableDataReference.child(idya).setValue(order);

                if (cartValues.size() > 0) {
                    for (final Cart cart : cartValues) {
                        DatabaseReference OrderDetailTableDataReference = databaseReference.child("order_detail");
                        String newKey = OrderDetailTableDataReference.push().getKey();
                        OrderDetail od = new OrderDetail(newKey, idya, cart.getProductId(), cart.getQuantity());
                        OrderDetailTableDataReference.child(newKey).setValue(od);
                    }
                }

                Toast.makeText(OrderConfirm.this, "Order Placed", Toast.LENGTH_SHORT).show();
//                          Sending Push Notification
                if(jsonArray!=null){
                    sendMessage(jsonArray , "Great News","You have new Order !" , "Http:\\google.com");
                }else{
                    Toast.makeText(OrderConfirm.this, "Empty", Toast.LENGTH_SHORT).show();
                }
//                            Clearing Shared Preferences
                editor.clear(); editor.commit();
//                Changing wallet Data

                databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference UserTableDataReference = databaseReference.child("user");
                UserTableDataReference.child(customer_person.getUser_id()).child("wallet").setValue(String.valueOf(f_total));
                int b_wallet = Integer.valueOf(business_person.getWallet());
                b_wallet = b_wallet+Integer.valueOf(total);
                UserTableDataReference.child(business_person.getUser_id()).child("wallet").setValue(String.valueOf(b_wallet));

//                           Clearing the SQLiteDatabase
                new Database(OrderConfirm.this).cleanCart();
                Intent i = new Intent(OrderConfirm.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            overridePendingTransition(0, 0);
        }
        return super.onOptionsItemSelected(item);
    }


//    FCM Push Notification to Business Person
public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon) {

    new AsyncTask<String, String, String>() {
        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
                JSONObject root = new JSONObject();
                JSONObject notification = new JSONObject();
                notification.put("body", body);
                notification.put("title", title);
                notification.put("icon", icon);
                notification.put("ItemId", 2222);
                notification.put("sound" , "Default");
                notification.put("color" , "#203E78");

                JSONObject data = new JSONObject();
                data.put("message", body);
                root.put("notification", notification);
                root.put("data", data);
                root.put("registration_ids", recipients);

                result = postToFCM(root.toString());
                Log.d("Main Activity", "Result: " + result);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject resultJson = new JSONObject(result);
                int success, failure;
                success = resultJson.getInt("success");
                failure = resultJson.getInt("failure");
                Log.i("FuckingProb" , result);
                Toast.makeText(OrderConfirm.this,  "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(OrderConfirm.this, result + "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
            }
        }
    }.execute();
}

    String postToFCM(String bodyString) throws IOException {
        String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + "AAAAaJ3REbE:APA91bH_Af3v9j5G1UUW5mf0UEVPehTfXnLieaqPnBq46qteGpIMdBjdOEM4B2uE6rJ-toV9D7yssfWFTT-d2eGv1XrcBhzzr1jDyxS8fkpqJJwjaKHDvsVXfEi4e5AFObs9wcOsoN1m")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }


//Read Data From Database
    public void ReadDatabase(final OnGetDataListener listener){
        listener.onStart();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference StoreTableDataReference = databaseReference.child("user");
        Query query = StoreTableDataReference.orderByChild("user_id").equalTo(business_user_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    business_person = postSnapshot.getValue(User.class);
                    String recieverId = business_person.getToken();
                    jsonArray.put(recieverId);
                }

                //            Getiing Customer data
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference StoreTableDataReference = databaseReference.child("user");
                Query query = StoreTableDataReference.orderByChild("user_id").equalTo(customer_id);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            customer_person = postSnapshot.getValue(User.class);
                        }
                        listener.onSuccess(dataSnapshot);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        listener.onFailed(databaseError);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST) {
            if(resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data,this);
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                order_location = latitude + "," + longitude;
            }
        }
    }
}
