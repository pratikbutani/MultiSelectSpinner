package com.androidbuts.sample;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch;
import com.androidbuts.multispinnerfilter.SingleSpinnerListener;
import com.androidbuts.multispinnerfilter.SingleSpinnerSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Getting array of String to Bind in Spinner
		 */
		final List<String> list = Arrays.asList(getResources().getStringArray(R.array.mood_array));

		/**
		 * Making for Single Selection
		 */
		final List<KeyPairBoolData> listArray0 = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			KeyPairBoolData h = new KeyPairBoolData();
			h.setId(i + 1);
			h.setName(list.get(i));
			h.setSelected(false);
			listArray0.add(h);
		}

		/**
		 * Making for Multiple selection
		 */
		final List<KeyPairBoolData> listArray1 = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			KeyPairBoolData h = new KeyPairBoolData();
			h.setId(i + 1);
			h.setName(list.get(i));
			h.setSelected(true);
			listArray1.add(h);
		}

		/**
		 * Single Item Selection Spinner Demo
		 */
		SingleSpinnerSearch singleSpinnerSearch = findViewById(R.id.singleItemSelectionSpinner);

		// Pass true, If you want color separation. Otherwise false. default = false.
		singleSpinnerSearch.setColorseparation(true);

		// Pass true If you want searchView above the list. Otherwise false. default = true.
		singleSpinnerSearch.setSearchEnabled(true);

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


		/**
		 * Search MultiSelection Spinner (With Search/Filter Functionality)
		 *
		 *  Using MultiSpinnerSearch class
		 */
		MultiSpinnerSearch multiSelectSpinnerWithSearch = findViewById(R.id.multipleItemSelectionSpinner);

		// Pass true If you want searchView above the list. Otherwise false. default = true.
		multiSelectSpinnerWithSearch.setSearchEnabled(true);

		// A text that will display in search hint.
		multiSelectSpinnerWithSearch.setSearchHint("Select your mood");

		// Set text that will display when search result not found...
		multiSelectSpinnerWithSearch.setEmptyTitle("Not Data Found!");

		// If you will set the limit, this button will not display automatically.
		multiSelectSpinnerWithSearch.setShowSelectAllButton(true);

		// Removed second parameter, position. Its not required now..
		// If you want to pass preselected items, you can do it while making listArray,
		// pass true in setSelected of any item that you want to preselect
		multiSelectSpinnerWithSearch.setItems(listArray1, new MultiSpinnerListener() {
			@Override
			public void onItemsSelected(List<KeyPairBoolData> items) {
				//The followings are selected items.
				for (int i = 0; i < items.size(); i++) {
					Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
				}
			}
		});

		/**
		 * If you want to set limit as maximum item should be selected is 2.
		 * For No limit -1 or do not call this method.
		 *
		 */
		/**** UNCOMMENT FOLLOWING CODE IF YOU WANT TO SET LIMIT ****/
//		multiSelectSpinnerWithSearch.setLimit(2, new MultiSpinnerSearch.LimitExceedListener() {
//			@Override
//			public void onLimitListener(KeyPairBoolData data) {
//				Toast.makeText(getApplicationContext(),
//						"Limit exceed ", Toast.LENGTH_LONG).show();
//			}
//		});
	}
}
