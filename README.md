[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c6b59b9ed05b45d5b2e4b2747d3cfc9a)](https://app.codacy.com/app/pratikbutani/MultiSelectSpinner?utm_source=github.com&utm_medium=referral&utm_content=pratikbutani/MultiSelectSpinner&utm_campaign=Badge_Grade_Dashboard)
[![](https://jitpack.io/v/pratikbutani/MultiSelectSpinner.svg)](https://jitpack.io/#pratikbutani/MultiSelectSpinner)

# MultiSelectSpinner
## Android Library to Select multiple items from Spinner

Click to see video of example:

[![MultiSelectSpinner](https://yt-embed.herokuapp.com/embed?v=mF4WIcQjWLE)](https://www.youtube.com/watch?v=mF4WIcQjWLE "Click here to see example")


## How to import
### Add it in your root build.gradle at the end of repositories

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Add dependency in build.gradle

    implementation 'com.github.pratikbutani:MultiSelectSpinner:1.0.1'

### Recent Changes

  * Added Material library.
  * Extracted the sample into a separate module.
  * Code cleanup.
  * `position` parameter is removed from both types of Spinner. Check comments above the code of `setItems`
  * `selectAll` button added to Select All Items in MultiSelectSpinner.
  * Code Optimised and Upgraded to latest versions of libraries/dependencies.
  * **MOST IMP CHANGE** You will get all the selected items in the listener instead of all items.

## How to use SINGLE ITEM SELECTION

### SINGLE ITEM SELECTION SPINNER (XML Code)

    <com.androidbuts.multispinnerfilter.SingleSpinnerSearch
            android:id="@+id/singleItemSelectionSpinner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="10dp"
            app:hintText="Single Selection Spinner" />

### SINGLE ITEM SELECTION SPINNER (Java Code)

        /******** MUST READ ALL COMMENTS AS DOCUMENTATION *********/
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
		// Pass true in setSelected of any item that you want to preselect
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

## How to use MULTIPLE ITEM SELECTION SPINNER
### MULTIPLE ITEM SELECTION SPINNER (XML Code)

	<com.androidbuts.multispinnerfilter.MultiSpinnerSearch
		android:id="@+id/multipleItemSelectionSpinner"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_margin="10dp"
		app:hintText="Multi Item Selection" />

### MULTIPLE ITEM SELECTION SPINNER (Java Code)

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

        	//A text that will display in clear text button
		multiSelectSpinnerWithSearch.setClearText("Close & Clear");
		
		// Removed second parameter, position. Its not required now..
		// If you want to pass preselected items, you can do it while making listArray,
		// Pass true in setSelected of any item that you want to preselect
		multiSelectSpinnerWithSearch.setItems(listArray1, new MultiSpinnerListener() {
			@Override
			public void onItemsSelected(List<KeyPairBoolData> items) {
				for (int i = 0; i < items.size(); i++) {
					if (items.get(i).isSelected()) {
						Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
					}
				}
			}
		});

		/**
		 * If you want to set limit as maximum item should be selected is 2.
		 * For No limit -1 or do not call this method.
		 *
		 */
		multiSelectSpinnerWithSearch.setLimit(2, new MultiSpinnerSearch.LimitExceedListener() {
			@Override
			public void onLimitListener(KeyPairBoolData data) {
				Toast.makeText(getApplicationContext(),
						"Limit exceed ", Toast.LENGTH_LONG).show();
			}
		});

## Awesome contributors :star_struck:
<a href="https://github.com/pratikbutani/MultiSelectSpinner/graphs/contributors">
  <img src="https://contributors-img.web.app/image?repo=pratikbutani/MultiSelectSpinner" />
</a>

Made with [contributors-img](https://contributors-img.web.app).

## Buy a cup of coffee
If you found this project helpful or you learned something from the source code and want to thank me, consider buying me a cup of ☕️ [PayPal](http://paypal.me/androidbuts)

### Do not forget to give star if its useful to you. :)
