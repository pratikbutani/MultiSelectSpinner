package com.androidbuts.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.sample.databinding.ActivityMainDataBindingBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainDataBindingActivity extends AppCompatActivity {

    private ActivityMainDataBindingBinding binding;
    private final String TAG = "MainDataBindingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainDataBindingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.mood_array));

        final List<KeyPairBoolData> listArray0 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(false);
            listArray0.add(h);
        }

        final List<KeyPairBoolData> listArray1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            KeyPairBoolData h = new KeyPairBoolData();
            h.setId(i + 1);
            h.setName(list.get(i));
            h.setSelected(i < 5);
            listArray1.add(h);
        }


        binding.colorSeparationCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            // Pass true, If you want color separation. Otherwise false. default = false.
            binding.singleItemSelectionSpinner.setColorseparation(b);
        });

        binding.searchEditTextCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            // Pass true If you want searchView above the list. Otherwise false. default = true.
            binding.singleItemSelectionSpinner.setSearchEnabled(b);
        });

        // A text that will display in search hint.
        binding.singleItemSelectionSpinner.setSearchHint("Select your mood");

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // Pass true in setSelected of any item that you want to preselect
        // LOGICALLY, PASS Only One Item As SELECTED...
        binding.singleItemSelectionSpinner.setItems(listArray0, new SingleSpinnerListener() {
            @Override
            public void onItemsSelected(KeyPairBoolData selectedItem) {
                Log.i(TAG, "Selected Item : " + selectedItem.getName());
            }

            @Override
            public void onClear() {
                Toast.makeText(MainDataBindingActivity.this, "Cleared Selected Item", Toast.LENGTH_SHORT).show();
            }
        });

        binding.colorSeparationMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> binding.multipleItemSelectionSpinner.setColorSeparation(b));

        binding.searchEditTextMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> binding.multipleItemSelectionSpinner.setSearchEnabled(b));

        binding.limitMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
            /*
             * If you want to set limit as maximum item should be selected is 2.
             * For No limit -1 or do not call this method.
             *
             */
            /* UNCOMMENT FOLLOWING CODE IF YOU WANT TO SET LIMIT ****/
            binding.multipleItemSelectionSpinner.setLimit(b ? 5 : -1, data -> Toast.makeText(getApplicationContext(),
                    "Limit Exceed", Toast.LENGTH_LONG).show());
        });

        binding.selectAllButtonMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> binding.multipleItemSelectionSpinner.setShowSelectAllButton(b));

        // Pass true If you want searchView above the list. Otherwise false. default = true.
        binding.multipleItemSelectionSpinner.setSearchEnabled(true);

        binding.multipleItemSelectionSpinner.setHintText("Select Any");

        //A text that will display in clear text button
        binding.multipleItemSelectionSpinner.setClearText("Close & Clear");

        // A text that will display in search hint.
        binding.multipleItemSelectionSpinner.setSearchHint("Select your mood");

        // Set text that will display when search result not found...
        binding.multipleItemSelectionSpinner.setEmptyTitle("Not Data Found!");

        // If you will set the limit, this button will not display automatically.
        binding.multipleItemSelectionSpinner.setShowSelectAllButton(true);

        // Removed second parameter, position. Its not required now..
        // If you want to pass preselected items, you can do it while making listArray,
        // Pass true in setSelected of any item that you want to preselect
        binding.multipleItemSelectionSpinner.setItems(listArray1, items -> {
            //The followings are selected items.
            for (int i = 0; i < items.size(); i++) {
                Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
            }
        });

    }
}