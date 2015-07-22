package com.pvo.prototype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pvo.activity.R;

public abstract class PVOFragment extends Fragment implements PVOAction {

	private View rootView;

	int contentView;

	//Popup window for Select Multiple area
	public PopupWindow pwindo;

	private EditText search;

	private ScrollView scroll;

	private ListView searchListView;

	private LinearLayout selectedItemLayout;

	private ArrayList<String> notSelectedItemList = new ArrayList<String>();

	public ArrayList<String> selectedItemList = new ArrayList<String>();

	private ArrayList<String> masterList = new ArrayList<String>();

	// private ArrayList<String> arraysort = new ArrayList<String>();

	private ArrayAdapter<String> adapter;

	private Button done;

	private Button cancel;

	boolean bool = false;

	public int counter = 0;
	
	public LinearLayout ll;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(getContentView(), container, false);
		this.init(savedInstanceState);
		hideKeyboard(getActivity());
		return rootView;
	}

	public void init(Bundle savedInstanceState) {}

	public int getContentView() {
		return contentView;
	}

	public void setContentView(int contentView) {
		this.contentView = contentView;
	};

	public View findViewById(int id) {

		return rootView.findViewById(id);
	}

	public void callParentMethod() {

		getActivity().onBackPressed();
	}

	/**
	 * Popup Window For Select Area
	 **/
	public void openAreaListOfCityPopupWindow(final String purpose, final EditText editText,Map<String, Integer> areaMap) {
		try {
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.activity_area_list_popup_window,(ViewGroup) findViewById(R.id.popupWindow));

			pwindo = new PopupWindow(layout, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);
			pwindo.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
			pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
			
			//pwindo.setOutsideTouchable(true);
			
			search = (EditText) layout.findViewById(R.id.search);
			scroll = (ScrollView) layout.findViewById(R.id.selectedList);
			searchListView = (ListView) layout.findViewById(R.id.searchList);
			selectedItemLayout = (LinearLayout) layout.findViewById(R.id.selectitem);
			selectedItemLayout.setOrientation(LinearLayout.VERTICAL);
			selectedItemList.clear();
			notSelectedItemList.clear();
			masterList.clear();
			selectedItemLayout.removeAllViews();

			if (purpose.equalsIgnoreCase("areaMap")) {
				notSelectedItemList = new ArrayList<String>(areaMap.keySet());
				masterList = new ArrayList<String>(areaMap.keySet());
			}

			Collections.sort(notSelectedItemList);
			Collections.sort(masterList);
			String purposeText = "";
			
			if (purpose.equalsIgnoreCase("areaMap"))
				purposeText = editText.getText().toString();
			
			if (purposeText != null && purposeText.length() > 0) {
				ArrayList<TextView> getAllTextViewbyLanguageText = new ArrayList<TextView>();
				selectedItemList = new ArrayList<String>(Arrays.asList(purposeText.split(" - ")));
				final ArrayList<TextView> getLanguageTextView = getAllLanguageTextViewbyLanguageText(purposeText,getAllTextViewbyLanguageText,selectedItemList);
				notSelectedItemList.removeAll(selectedItemList);
				counter = getLanguageTextView.size();
				for (int i = 0; i < getLanguageTextView.size(); i++) {
					addTextViewINLinearLayout(selectedItemLayout, getLanguageTextView.get(i), getActivity());
				}
			} else {
				counter = 0;
			}

			adapter = new ArrayAdapter<String>(getActivity(), R.layout.demo, R.id.listTV, notSelectedItemList);
			adapter.setNotifyOnChange(true);
			searchListView.setAdapter(adapter);

			// on click of done button fill the selected item into edit box
			done = (Button) layout.findViewById(R.id.doneButton);
			done.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					ArrayList<TextView> allTextView = new ArrayList<TextView>();
					pwindo.dismiss();
					allTextView = getAllTextViewbyParentLinearLayout(selectedItemLayout, allTextView);

					String getAllTextViewString = getAlltextViewString(allTextView);

					if (purpose.equalsIgnoreCase("areaMap"))

						editText.setText(getAllTextViewString, TextView.BufferType.EDITABLE);

				}
			});

			// close the screen
			cancel = (Button) layout.findViewById(R.id.cancelPopupButton);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pwindo.dismiss();
					counter = 0;
				}
			});

			search.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {}
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

				@Override
				public void afterTextChanged(Editable s) {
					int length = search.getText().length();
					if (search.getText().length() > 0) {
						@SuppressWarnings("unchecked")
						List<String> searchItemList = (List<String>) CollectionUtils.select(masterList,new Predicate() {
							@Override
							public boolean evaluate(Object item) {
								String text = search.getText().toString().toLowerCase();
								return !selectedItemList.contains(item)&& String.valueOf(item).toLowerCase().startsWith(text);
							}
						});

						Collections.sort(searchItemList);
						notSelectedItemList.clear();
						notSelectedItemList.addAll(searchItemList);
						adapter.notifyDataSetChanged();
					} else {
						notSelectedItemList.clear();
						notSelectedItemList.addAll(masterList);
						notSelectedItemList.removeAll(selectedItemList);
						Collections.sort(notSelectedItemList);
						adapter.notifyDataSetChanged();
					}
				}
			});

			searchListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {

					String item = (String) searchListView.getItemAtPosition(position);

					final TextView tv = new TextView(getActivity());

					tv.setText(item);

					tv.setId((int) id);

					LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

					llp.setMargins(12, 8, 0, 0); // llp.setMargins(left,
													// top,right, bottom);

					tv.setLayoutParams(llp);

					tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imageclose, 0, 0, 0);

					tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {

							clickOnTextView(tv);
						}
					});

					// set condition to select maximum 10 area
					if (counter < 10) {

						counter++;

						addTextViewINLinearLayout(selectedItemLayout, tv, getActivity());

						selectedItemList.add(notSelectedItemList.get(position));

						notSelectedItemList.remove(position);

						adapter.notifyDataSetChanged();
					}

					// scroll view down at bottom wheen add item
					scroll.post(new Runnable() {
						@Override
						public void run() {

							scroll.fullScroll(View.FOCUS_DOWN);
						}
					});
				}
			});

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * onclick of selected item text view remove from list and add into not
	 * selected item list
	 * **/
	public void clickOnTextView(TextView tv) {
		counter--;
		LinearLayout parentll = (LinearLayout) tv.getParent();
		parentll.removeView(tv);
		notSelectedItemList.add((String) tv.getText());
		selectedItemList.remove(tv.getText());
		Collections.sort(notSelectedItemList);
		adapter.notifyDataSetChanged();
	}

	/**
	 * GEt All TExtView by parent LinearLayout
	 * 
	 * @param selectedItem
	 * @param alltextView
	 * @return
	 */
	private ArrayList<TextView> getAllTextViewbyParentLinearLayout(LinearLayout selectedItem,ArrayList<TextView> alltextView) {
		if (selectedItem.getChildCount() > 0) {
			for (int i = 0; i < selectedItem.getChildCount(); i++) {
				if (selectedItem.getChildAt(i) instanceof LinearLayout) {
					LinearLayout ll = (LinearLayout) selectedItem.getChildAt(i);
					for (int j = 0; j < ll.getChildCount(); j++) {
						if (ll.getChildAt(j) instanceof TextView) {
							alltextView.add((TextView) ll.getChildAt(j));
						}
					}
					ll.removeAllViews();
				}
			}
		}
		return alltextView;
	}

	private String getAlltextViewString(ArrayList<TextView> allTextView) {
		String textViewText = "";
		for (int i = 0; i < allTextView.size(); i++) {
			if (i == (allTextView.size() - 1)) {
				textViewText += allTextView.get(i).getText();
			} else {
				textViewText += allTextView.get(i).getText() + " - ";
			}
		}
		return textViewText;
	}

	/**
	 * Add TextView IN LinearLayout
	 * 
	 * @param parentll
	 * @param tv
	 * @param mContext
	 */
	private void addTextViewINLinearLayout(LinearLayout parentll, TextView tv, Context mContext) {
		
		int widthSoFar = 0;
		int maxWidth = 0;
		tv.measure(0, 0);
		tv.setTextSize(20);
		tv.setPadding(5, 5, 5, 5);
		tv.setTextColor(Color.BLACK);
		tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.selected_location_tv_border));
		
		LinearLayout currentLL;
		ll = parentll;

		if (parentll.getChildAt(parentll.getChildCount() - 1) != null) {
			parentll.getChildAt(parentll.getChildCount() - 1).measure(0, 0);
			widthSoFar = (parentll.getChildAt(parentll.getChildCount() - 1).getMeasuredWidth()) + tv.getMeasuredWidth();
		}

		Display display = getActivity().getWindowManager().getDefaultDisplay();
		maxWidth = display.getWidth() - 100;

		// Check if parent Liner Layout has child
		if (parentll.getChildCount() == 0 || widthSoFar >= maxWidth) {

			// Create a liner layout and add
			currentLL = new LinearLayout(mContext);

			currentLL.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			// currentLL.setGravity(Gravity.LEFT);
			currentLL.setOrientation(LinearLayout.HORIZONTAL); // vertical

			parentll.addView(currentLL);
		}

		currentLL = (LinearLayout) parentll.getChildAt(parentll.getChildCount() - 1);

		currentLL.addView(tv);
	}

	/**
	 * Get Language Text View by LanguageString
	 * 
	 * @param languageText
	 * @param getAllTextViewbyLanguageText
	 * @param arraysort
	 * @return
	 */
	private ArrayList<TextView> getAllLanguageTextViewbyLanguageText(

	String languageText,

	ArrayList<TextView> getAllTextViewbyLanguageText,

	ArrayList<String> languageStringList) {

		for (int i = 0; i < languageStringList.size(); i++) {

			final TextView tv = new TextView(getActivity());

			tv.setText(languageStringList.get(i));

			tv.setTextSize(20);

			LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

			llp.setMargins(12, 8, 0, 0); // llp.setMargins(left, top,
											// right,bottom);

			tv.setLayoutParams(llp);

			tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.imageclose, 0, 0, 0);

			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					clickOnTextView(tv);
				}
			});

			getAllTextViewbyLanguageText.add(tv);
		}

		return getAllTextViewbyLanguageText;
	}

	public String getTagName() {

		return this.getClass().getSimpleName();
	}

	public static void hideKeyboard(Activity activity) {

		InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

		if (activity.getCurrentFocus() != null) {

			inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}

