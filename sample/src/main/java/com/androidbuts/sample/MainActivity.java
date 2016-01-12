package com.androidbuts.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinner;
import com.androidbuts.multispinnerfilter.MultiSpinner.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch.MultiSpinnerSearchListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Getting array of String to Bind in Spinner
         */
        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        TreeMap<String, Boolean> items = new TreeMap<>();
        for(String item : list) {
            items.put(item, Boolean.FALSE);
        }

        /**
         * Simple MultiSelection Spinner (Without Search/Filter Functionlity)
         *
         *  Using MultiSpinner class
         */
        MultiSpinner simpleSpinner = (MultiSpinner) findViewById(R.id.simpleMultiSpinner);

        simpleSpinner.setItems(items, new MultiSpinnerListener() {

            @Override
            public void onItemsSelected(boolean[] selected) {

                // your operation with code...
                for (int i = 0; i < selected.length; i++) {
                    if (selected[i]) {
                        Log.i("TAG", i + " : " + list.get(i));
                    }
                }
            }
        });

        /**
         * Search MultiSelection Spinner (With Search/Filter Functionality)
         *
         *  Using MultiSpinnerSearch class
         */
        MultiSpinnerSearch searchSpinner = (MultiSpinnerSearch) findViewById(R.id.searchMultiSpinner);
        final List<KeyPairBoolData> listArray = new ArrayList<KeyPairBoolData>();

        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray.add(h);
        }

        /***
         * -1 is no by default selection
         * 0 to length will select corresponding values
         */
        searchSpinner.setItems(listArray, "Multi Selection With Filter", -1, new MultiSpinnerSearchListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
    }
}
