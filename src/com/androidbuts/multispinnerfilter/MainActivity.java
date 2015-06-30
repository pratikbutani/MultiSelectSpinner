package com.androidbuts.multispinnerfilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.androidbuts.multispinnerfilter.MultiSpinner.MultiSpinnerListener;
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch.MultiSpinnerSearchListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/**
		 * Getting array of String to Bind in Spinner
		 */
		final List<String> list = Arrays.asList(getResources().getStringArray(R.array.sports_array));
		
		/**
		 * Simple MultiSelection Spinner (Without Search/Filter Functionlity)
		 * 
		 *  Using MultiSpinner class
		 */
		MultiSpinner simpleSpinner = (MultiSpinner) findViewById(R.id.simpleMultiSpinner);
		
		simpleSpinner.setItems(list, "Multi Selection Without Filter", -1, new MultiSpinnerListener() {
			
			@Override
			public void onItemsSelected(boolean[] selected) {
				
				// your operation with code...
				for(int i=0; i<selected.length; i++) {
					if(selected[i]) {
						Log.i("TAG", i + " : "+ list.get(i));
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

for(int i=0; i<list.size(); i++) {
	KeyPairBoolData h = new KeyPairBoolData();
	h.setId(i+1);
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

		for(int i=0; i<items.size(); i++) {
			if(items.get(i).isSelected()) {
				Log.i("TAG", i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
			}
		}
	}
});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
