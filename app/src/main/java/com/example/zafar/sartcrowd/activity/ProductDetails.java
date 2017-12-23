package com.example.zafar.sartcrowd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.R;
import com.example.zafar.sartcrowd.other.CustomListProductDetails;
import com.example.zafar.sartcrowd.other.MyAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static com.example.zafar.sartcrowd.R.id.radioGroup;

public class ProductDetails extends AppCompatActivity {

    ListView list;
    TextView txt;
    RadioGroup rg;

    String[] productAttributesNames = {
            "Color",
            "Size",
            "Weight"
    } ;
    String[][] productAttributesValues = {
            {"red" , "yellow" , "green"},
            {"small" , "large"},
            {"20kg" , "30kg" , "40kg"}
    } ;
    ArrayList<String> selectedTitle = new ArrayList<String>(productAttributesNames.length);
    ArrayList<String> selectedValue = new ArrayList<String>(productAttributesNames.length);
    private static final Integer[] XMEN= {
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.apple};


    private static ViewPager mPager;
    private static int currentPage = 0;
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        CustomListProductDetails adapter = new
                CustomListProductDetails(this,productAttributesNames, productAttributesValues);
        list=(ListView) findViewById(R.id.list_product_details);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                     Toast.makeText(getApplication(), "You Clicked at Customer" + position, Toast.LENGTH_SHORT).show();

//                rg = (RadioGroup) view.findViewById(R.id.radioGroup);
//                txt = (TextView)view.findViewById(R.id.title_group_radio);
//
//                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                        //            Toast.makeText(getApplication(), txt.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                        int radioButtonID = group.getCheckedRadioButtonId();
//                        View radioButton = group.findViewById(radioButtonID);
//                        //find radiobutton position
//                        int position = group.indexOfChild(radioButton);
//                        RadioButton btn = (RadioButton) rg.getChildAt(position);
//                        String selection = (String) btn.getText();
//
//                        Toast.makeText(getApplication(), selection + rg.getParent() , Toast.LENGTH_LONG).show();
//                        selectedTitle.add(txt.getText().toString());
//                        selectedValue.add(selection);
//                    }
//                });


            }
        });


        init();
    }

    public void buy_clicked(View view){
        Toast.makeText(this, selectedTitle.get(0).toString()+" : " + selectedValue.get(0).toString()+"\n"+
                selectedTitle.get(1).toString()+" : " + selectedValue.get(1).toString()+"\n"+
                selectedTitle.get(2).toString()+" : " + selectedValue.get(2).toString()+"\n", Toast.LENGTH_SHORT).show();



    }

//        for(int i=0;i<selectedValue.size();i++){
//            Log.i("Fuck "+selectedTitle.get(i).toString() , selectedValue.get(i).toString());
//
//            selectedValue.clear();
//            selectedTitle.clear();
//        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            overridePendingTransition(0, 0);
        }

        return super.onOptionsItemSelected(item);
    }


    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(ProductDetails.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
    }

}