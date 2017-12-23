package com.example.zafar.sartcrowd.other;


import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zafar.sartcrowd.R;

public class CustomListProductDetails extends ArrayAdapter<String> {

    RadioGroup single_group;
    RadioGroup [] groups;

    private final Activity context;
    private final String[] names;
    private final String[][] values;
    public CustomListProductDetails(Activity context,
                                    String[] names,
                                    String[][] values  ) {
        super(context, R.layout.list_single_product_details, names);
        this.context = context;
        this.names = names;
        this.values = values;

        groups = new RadioGroup[names.length];
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_single_product_details, null, true);

        TextView textView = (TextView) rowView.findViewById(R.id.title_group_radio);
        single_group = (RadioGroup) rowView.findViewById(R.id.radioGroup);
        textView.setText(names[position]);
        for (int j = 0; j < values[position].length; j++) {

            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(values[position][j]);
            radioButton.setFocusable(false);
            radioButton.setFocusableInTouchMode(false);


            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            single_group.addView(radioButton, params);
        }

           Log.i("group_length" , single_group.toString());
                single_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        int radioButtonID = group.getCheckedRadioButtonId();
                        View radioButton = group.findViewById(radioButtonID);
                        //find radiobutton position
                        int position = group.indexOfChild(radioButton);

                        RadioButton btn = (RadioButton) single_group.getChildAt(position);
                        String selection = (String) btn.getText();
                        Toast.makeText(getContext(), selection, Toast.LENGTH_LONG).show();
                    }
                });


        return rowView;
    }
}