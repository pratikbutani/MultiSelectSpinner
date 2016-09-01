package com.androidbuts.multispinnerfilter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SingleSpinner extends Spinner implements OnCancelListener {
	private static final String TAG = SingleSpinner.class.getSimpleName();
	private List<KeyPairBoolData> items;
	private String defaultText = "";
	private String spinnerTitle = "";
	private SpinnerListener listener;
	MyAdapter adapter;
	public static AlertDialog.Builder builder;
	public static AlertDialog ad;

	public SingleSpinner(Context context) {
		super(context);
	}

	public SingleSpinner(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		TypedArray a = arg0.obtainStyledAttributes(arg1, R.styleable.MultiSpinnerSearch);
		final int N = a.getIndexCount();
		for (int i = 0; i < N; ++i) {
			int attr = a.getIndex(i);
			if (attr == R.styleable.MultiSpinnerSearch_hintText) {
				spinnerTitle = a.getString(attr);
				defaultText = spinnerTitle;
				break;
			}
		}
		Log.i(TAG, "spinnerTitle: "+spinnerTitle);
		a.recycle();
	}

	public SingleSpinner(Context arg0, AttributeSet arg1, int arg2) {
		super(arg0, arg1, arg2);
	}


	public List<KeyPairBoolData> getSelectedItems() {
		List<KeyPairBoolData> selectedItems = new ArrayList<>();
		for(KeyPairBoolData item : items){
			if(item.isSelected()){
				selectedItems.add(item);
			}
		}
		return selectedItems;
	}

	public List<Long> getSelectedIds() {
		List<Long> selectedItemsIds = new ArrayList<>();
		for(KeyPairBoolData item : items){
			if(item.isSelected()){
				selectedItemsIds.add(item.getId());
			}
		}
		return selectedItemsIds;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		// refresh text on spinner

		String spinnerText = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).isSelected()) {
				spinnerText = items.get(i).getName();
				break;
			}
		}
		if (spinnerText==null){
			spinnerText = defaultText;
		}

		ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_for_spinner, new String[] { spinnerText });
		setAdapter(adapterSpinner);

		if(adapter != null)
			adapter.notifyDataSetChanged();

		listener.onItemsSelected(items);
	}

	@Override
	public boolean performClick() {

		builder = new AlertDialog.Builder(getContext(), R.style.myDialog);
		builder.setTitle(spinnerTitle);

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

		View view = inflater.inflate(R.layout.alert_dialog_listview_search, null);
		builder.setView(view);

		final ListView listView = (ListView) view.findViewById(R.id.alertSearchListView);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setFastScrollEnabled(false);
		adapter = new MyAdapter(getContext(), items);

		listView.setAdapter(adapter);
		final TextView emptyText = (TextView) view.findViewById(R.id.empty);
		listView.setEmptyView(emptyText);

		EditText editText = (EditText) view.findViewById(R.id.alertSearchEditText);
		editText.setVisibility(GONE);

		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				Log.i(TAG, " ITEMS : " + items.size() );
				dialog.cancel();
			}
		});

		builder.setOnCancelListener(this);
		ad = builder.show();
		return true;
	}

	public void setItems(List<KeyPairBoolData> items, int position, SpinnerListener listener) {

		this.items = items;
		this.listener = listener;

		for (KeyPairBoolData item : items){
			if(item.isSelected()){
				defaultText = item.getName();
				break;
			}
		}

		ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getContext(), R.layout.textview_for_spinner, new String[] { defaultText });
		setAdapter(adapterSpinner);

		if(position != -1)
		{
			items.get(position).setSelected(true);
			//listener.onItemsSelected(items);
			onCancel(null);
		}
	}



	//Adapter Class
	public class MyAdapter extends BaseAdapter{

		List<KeyPairBoolData> arrayList;
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
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Log.i(TAG, "getView() enter");
			ViewHolder holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.item_listview_single,  parent, false);
			holder.textView = (TextView) convertView.findViewById(R.id.alertTextView);
			convertView.setTag(holder);

			if(position%2==0){
				convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.list_even));
			}else{
				convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.list_odd));
			}

			final KeyPairBoolData data = arrayList.get(position);

			holder.textView.setText(data.getName());
			holder.textView.setTypeface(null, Typeface.NORMAL);

			convertView.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					int len = arrayList.size();
					for (int i = 0; i < len; i++)
					{
						arrayList.get(i).setSelected(false);
						if (i == position)
						{
							arrayList.get(i).setSelected(true);
							Log.i(TAG, "On Click Selected Item : " + arrayList.get(i).getName() + " : " + arrayList.get(i).isSelected());
						}
					}
					ad.dismiss();
					SingleSpinner.this.onCancel(ad);
				}
			});

			if(data.isSelected()){
				holder.textView.setTypeface(null, Typeface.BOLD);
				holder.textView.setTextColor(Color.WHITE);
				convertView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.list_selected));
			}
			return convertView;
		}
	}
}