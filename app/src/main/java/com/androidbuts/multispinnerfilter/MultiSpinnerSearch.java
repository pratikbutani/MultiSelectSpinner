package com.androidbuts.multispinnerfilter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MultiSpinnerSearch extends Spinner implements OnCancelListener {

	private List<KeyPairBoolData> items;
	//private boolean[] selected;
	private String defaultText = "";
	private MultiSpinnerSearchListener listener;
	MyAdapter adapter;
	
	public MultiSpinnerSearch(Context context) {
		super(context);
	}

	public MultiSpinnerSearch(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
	}

	public MultiSpinnerSearch(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// refresh text on spinner

		StringBuffer spinnerBuffer = new StringBuffer();

		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isSelected()) {
				spinnerBuffer.append(items.get(i).getName());
				spinnerBuffer.append(", ");
			}
		}

		String spinnerText = "";
		spinnerText = spinnerBuffer.toString();
		if (spinnerText.length() > 2)
			spinnerText = spinnerText.substring(0, spinnerText.length() - 2);
		else
			spinnerText = defaultText;

		ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
				R.layout.textview_for_spinner,
				new String[] { spinnerText });
		setAdapter(adapterSpinner);
		
		if(adapter != null)
			adapter.notifyDataSetChanged();
		
		listener.onItemsSelected(items);
	}

	@Override
	public boolean performClick() {

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle(defaultText);

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		View view = inflater.inflate(R.layout.alert_dialog_listview_search, null);
		builder.setView(view);

		final ListView listView = (ListView) view.findViewById(R.id.alertSearchListView);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listView.setFastScrollEnabled(false);
		adapter = new MyAdapter(getContext(), items);
		listView.setAdapter(adapter);
		
		EditText editText = (EditText) view.findViewById(R.id.alertSearchEditText);
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.getFilter().filter(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {	
			}
		});

		//builder.setMultiChoiceItems(items.toArray(new CharSequence[items.size()]), selected, this);
		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

//				items = (ArrayList<KeyPairBoolData>) adapter.arrayList;

				Log.i("TAG", " ITEMS : " + items.size() );
				dialog.cancel();
			}
		});

		builder.setOnCancelListener(this);
		builder.show();
		return true;
	}

	public void setItems(List<KeyPairBoolData> items, String allText, int position,
			MultiSpinnerSearchListener listener) {

		this.items = items;
		this.defaultText = allText;
		this.listener = listener;
				
		ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(),
				R.layout.textview_for_spinner,
				new String[] { defaultText });
		setAdapter(adapterSpinner);
		
		if(position != -1)
		{
			items.get(position).setSelected(true);
			//listener.onItemsSelected(items);
			onCancel(null);
		}
	}

	public interface MultiSpinnerSearchListener {
		public void onItemsSelected(List<KeyPairBoolData> items);
	}

	//	// Adapter Class            
	public class MyAdapter extends BaseAdapter implements Filterable {

		List<KeyPairBoolData> arrayList;      
		List<KeyPairBoolData> mOriginalValues; // Original Values
		LayoutInflater inflater;

		public MyAdapter(Context context, List<KeyPairBoolData> arrayList) {
			this.arrayList = arrayList;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private class ViewHolder {
			TextView textView;
			CheckBox checkBox;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;

			if (convertView == null) {

				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.alert_dialog_listview_search_subview, null);
				holder.textView = (TextView) convertView.findViewById(R.id.alertTextView);
				holder.checkBox = (CheckBox) convertView.findViewById(R.id.alertCheckbox);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			final KeyPairBoolData data = arrayList.get(position);
			
			holder.textView.setText(data.getName());
			holder.checkBox.setChecked(data.isSelected());
			
			convertView.setOnClickListener(new View.OnClickListener()
			{
				public void onClick(View v)
				{
					ViewHolder temp = (ViewHolder) v.getTag();
					temp.checkBox.setChecked(!temp.checkBox.isChecked());
					
					int len = arrayList.size();
					for (int i = 0; i < len; i++)
					{
						if (i == position)
						{
							data.setSelected(!data.isSelected());
							Log.i("TAG", "On Click Selected : " + data.getName() + " : " + data.isSelected());
							break;
						}
					}
				}
			});
			
			holder.checkBox.setTag(holder);
			
			return convertView;
		}

		@SuppressLint("DefaultLocale")
		@Override
		public Filter getFilter() {
			Filter filter = new Filter() {

				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint,FilterResults results) {

					arrayList = (List<KeyPairBoolData>) results.values; // has the filtered values
					notifyDataSetChanged();  // notifies the data with new filtered values
				}

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
					List<KeyPairBoolData> FilteredArrList = new ArrayList<KeyPairBoolData>();

					if (mOriginalValues == null) {
						mOriginalValues = new ArrayList<KeyPairBoolData>(arrayList); // saves the original data in mOriginalValues
					}

					/********
					 * 
					 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
					 *  else does the Filtering and returns FilteredArrList(Filtered)  
					 *
					 ********/
					if (constraint == null || constraint.length() == 0) {

						// set the Original result to return  
						results.count = mOriginalValues.size();
						results.values = mOriginalValues;
					} else {
						constraint = constraint.toString().toLowerCase();
						for (int i = 0; i < mOriginalValues.size(); i++) {
							Log.i("TAG", "Filter : " + mOriginalValues.get(i).getName() + " -> " + mOriginalValues.get(i).isSelected());
							String data = mOriginalValues.get(i).getName();
							if (data.toLowerCase().contains(constraint.toString())) {
								FilteredArrList.add(mOriginalValues.get(i));
							}
						}
						// set the Filtered result to return
						results.count = FilteredArrList.size();
						results.values = FilteredArrList;
					}
					return results;
				}
			};
			return filter;
		}
	}
}