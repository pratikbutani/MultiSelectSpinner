[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c6b59b9ed05b45d5b2e4b2747d3cfc9a)](https://app.codacy.com/app/pratikbutani/MultiSelectSpinner?utm_source=github.com&utm_medium=referral&utm_content=pratikbutani/MultiSelectSpinner&utm_campaign=Badge_Grade_Dashboard)
[![](https://jitpack.io/v/pratikbutani/MultiSelectSpinner.svg)](https://jitpack.io/#pratikbutani/MultiSelectSpinner)

<a href="https://imgur.com/O9n105R"><img src="https://i.imgur.com/O9n105R.gif" title="source: imgur.com" /></a>

### How to import:
## Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

### Add dependency in build.gradle:

    implementation 'com.github.pratikbutani:MultiSelectSpinner:-SNAPSHOT'

### Changes

  * Made this an Android Studio project.
  * Added Material library.
  * Extracted the sample into a separate module.
  * Code cleanup.


### MultiSelect Spinner with Search/Filter:

![](https://lh5.googleusercontent.com/-MivlH0DxhMc/VZJ91Aa6qtI/AAAAAAAALdc/ZYfHBo_e9Vk/w325-h577-no/Screenshot_2015-06-30-16-03-59.png)

![](https://lh5.googleusercontent.com/-Slk9xZZvOw8/VZJ91NEC9UI/AAAAAAAALdk/dg9k5e_8z8Y/w325-h577-no/Screenshot_2015-06-30-16-03-31.png)      ![](https://lh5.googleusercontent.com/-oLU8ZzsxXBk/VZJ91RZcGiI/AAAAAAAALdo/r4LgvaTB5p8/w325-h577-no/Screenshot_2015-06-30-16-03-51.png)

### How to use (WITHOUT FILTER):

	/*  
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

### How to use (WITH FILTER):

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


### (Optional) Limit setting 

	/**
	 *  Using MultiSpinnerSearch object
	 *  call setLimit method
	 *  
	 */
	searchSpinner.setLimit(3, new MultiSpinnerSearch.LimitExceedListener() {
            @Override
            public void onLimitListener(KeyPairBoolData data) {
                Toast.makeText(getApplicationContext(),
                        "Limit exceed ", Toast.LENGTH_LONG).show();
            }
        });
	
	
### (Optional) Add Colorseparation to the list
	searchSpinner.setColorseparation(true);
