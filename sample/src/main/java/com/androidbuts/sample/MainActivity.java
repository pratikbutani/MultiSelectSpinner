package com.androidbuts.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	SingleSpinnerSearch singleSpinnerSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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

		CheckBox colorSeparationCheckBox = findViewById(R.id.colorSeparationCheckBox);
		CheckBox searchEditTextCheckBox = findViewById(R.id.searchEditTextCheckBox);

		singleSpinnerSearch = findViewById(R.id.singleItemSelectionSpinner);

		colorSeparationCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
			// Pass true, If you want color separation. Otherwise false. default = false.
			singleSpinnerSearch.setColorseparation(b);
		});

		searchEditTextCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
			// Pass true If you want searchView above the list. Otherwise false. default = true.
			singleSpinnerSearch.setSearchEnabled(b);
		});

		// A text that will display in search hint.
		singleSpinnerSearch.setSearchHint("Select your mood");

		// Removed second parameter, position. Its not required now..
		// If you want to pass preselected items, you can do it while making listArray,
		// pass true in setSelected of any item that you want to preselect
		// LOGICALLY, PASS Only One Item As SELECTED...
		singleSpinnerSearch.setItems(listArray0, new SingleSpinnerListener() {
			@Override
			public void onItemsSelected(KeyPairBoolData selectedItem) {
				Log.i(TAG, "Selected Item : " + selectedItem.getName());
			}

			@Override
			public void onClear() {
				Toast.makeText(MainActivity.this, "Cleared Selected Item", Toast.LENGTH_SHORT).show();
			}
		});


		MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);

		CheckBox colorSeparationMultipleCheckBox = findViewById(R.id.colorSeparationMultipleCheckBox);
		CheckBox searchEditTextMultipleCheckBox = findViewById(R.id.searchEditTextMultipleCheckBox);
		CheckBox limitEditTextMultipleCheckBox = findViewById(R.id.limitMultipleCheckBox);
		CheckBox selectAllButtonMultipleCheckBox = findViewById(R.id.selectAllButtonMultipleCheckBox);

		colorSeparationMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> multiSelectSpinnerWithSearch.setColorSeparation(b));

		searchEditTextMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> multiSelectSpinnerWithSearch.setSearchEnabled(b));

		limitEditTextMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> {
			/**
			 * If you want to set limit as maximum item should be selected is 2.
			 * For No limit -1 or do not call this method.
			 *
			 */
			/**** UNCOMMENT FOLLOWING CODE IF YOU WANT TO SET LIMIT ****/
			multiSelectSpinnerWithSearch.setLimit(b ? 5 : -1, data -> Toast.makeText(getApplicationContext(),
					"Limit Exceed", Toast.LENGTH_LONG).show());
		});

		selectAllButtonMultipleCheckBox.setOnCheckedChangeListener((compoundButton, b) -> multiSelectSpinnerWithSearch.setShowSelectAllButton(b));

		// Pass true If you want searchView above the list. Otherwise false. default = true.
		multiSelectSpinnerWithSearch.setSearchEnabled(true);

		multiSelectSpinnerWithSearch.setHintText("Select Any");

		// A text that will display in search hint.
		multiSelectSpinnerWithSearch.setSearchHint("Select your mood");

		// Set text that will display when search result not found...
		multiSelectSpinnerWithSearch.setEmptyTitle("Not Data Found!");

		// If you will set the limit, this button will not display automatically.
		multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

		// Removed second parameter, position. Its not required now..
		// If you want to pass preselected items, you can do it while making listArray,
		// pass true in setSelected of any item that you want to preselect
		multiSelectSpinnerWithSearch.setItems(listArray1, items -> {
			//The followings are selected items.
			for (int i = 0; i < items.size(); i++) {
				Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
			}
		});
	}
}
