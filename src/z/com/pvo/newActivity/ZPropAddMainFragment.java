package z.com.pvo.newActivity;

import java.util.HashMap;

import z.com.pvo.util.ProjectUtility;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.pvo.activity.MainFragmentActivity;
import com.pvo.activity.R;
import com.pvo.user.session.UserSessionManager;

/*
 * This class is used to set the tab activity as per selected home menu item
 */
public class ZPropAddMainFragment extends Fragment implements TabHost.OnTabChangeListener,
		ViewPager.OnPageChangeListener, AnimationListener, OnClickListener {

	private ViewPager viewpager;
	private TabHost tabHost;
	private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, ZPropAddMainFragment.TabInfo>();
	private HorizontalScrollView tabScrollView;
	public static MenuItem menuItem;
	public static Menu menu;
	private UserSessionManager userSessionManager;

	/*
	 * Maintains extrinsic info of a tab's construct
	 */
	private class TabInfo {
		private String tag;
		private Class<?> clss;
		private Fragment fragment;

		TabInfo(String tag, Class<?> clazz) {
			this.tag = tag;
			this.clss = clazz;
		}
	}

	/*
	 * A simple factory that returns dummy views to the Tabhost
	 */
	class TabFactory implements TabContentFactory {
		private final Context mContext;

		public TabFactory(Context context) {
			mContext = context;
		}

		// @see
		// android.widget.TabHost.TabContentFactory#createTabContent(java.lang.String)
		public View createTabContent(String tag) {
			View v = new View(mContext);
			v.setMinimumWidth(0);
			v.setMinimumHeight(0);
			return v;
		}
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.z_prop_add_main_fragment, view, false);
		/*userSessionManager = ProjectUtility.intiUsersession(getActivity());
		userSessionManager.setAddPropCurrentTab("0");*/
		userSessionManager = new UserSessionManager(getActivity());
		userSessionManager.setAddPropCurrentTab("0");
		
		
		/*
		 * Clear all id array if it's contail value
		 * 1) NomineeId,AmenitiesId,GroupId,AraeId,PreferredBrokerId
		 */
		ProjectUtility.clearAllIdArrayList();
		
		/*
		 * Remove all photo if not deleted
		 */
		ProjectUtility.removeAllResComPropPhoto();
		
		MainFragmentActivity.setTitle("Add Property");
		viewpager = (ViewPager) v.findViewById(R.id.firstMenuItemTabViewpager);
		viewpager.setOffscreenPageLimit(2);
		tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
		tabScrollView = (HorizontalScrollView) v.findViewById(R.id.tabScrollView);
		
		/*
		 * initialize tabHost
		 */
		initialiseTabHost();
		
		/*
		 * set current to handle null pointer 
		 */
		setCurrentTab();

		viewpager.setAdapter(new NewTabViewPagerAdapter(getActivity().getSupportFragmentManager()));//getActivity().getSupportFragmentManager())
		// On view page change
		viewpager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// Scroll tab view on page chenge
				// http://stackoverflow.com/questions/7875939/tabhost-in-scrollview-scroll-to-tab-when-selected
				View tabView = tabHost.getCurrentTabView();
				int scrollPos = tabView.getLeft() - (tabScrollView.getWidth() - tabView.getWidth());
				tabScrollView.smoothScrollTo(scrollPos * tabView.getWidth(), tabView.getWidth());
				tabHost.setCurrentTab(position);
				//userSessionManager.setAddPropCurrentTab(String.valueOf(position));
				userSessionManager.setAddPropCurrentTab(String.valueOf(position));
			}
		});
		
		return v;
	}

	/*
	 * Initialize the Tab Host
	 */
	private void initialiseTabHost() {
		tabHost.setup();
		TabInfo tabInfo = null;
		// 1) Attribute
		ZPropAddMainFragment.AddTab(ZPropAddMainFragment.this,this.tabHost,this.tabHost.newTabSpec("Residential").setIndicator(createTabView(getActivity(),"Residential")),
		(tabInfo = new TabInfo("Residential", ZPropAddRecidentialFragment.class)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		
		ZPropAddMainFragment.AddTab(ZPropAddMainFragment.this,this.tabHost,this.tabHost.newTabSpec("Commercial").setIndicator(createTabView(getActivity(), "Commercial")),
		(tabInfo = new TabInfo("Commercial", ZPropAddCommercialFragment.class)));
		this.mapTabInfo.put(tabInfo.tag, tabInfo);
		tabHost.setOnTabChangedListener(this);

	}

	@Override
	public void onAnimationStart(Animation animation) {}

	@Override
	public void onAnimationEnd(Animation animation) {}

	@Override
	public void onAnimationRepeat(Animation animation) {}

	@Override
	public void onPageScrollStateChanged(int arg0) {}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		this.tabHost.setCurrentTab(position);
	}

	@Override
	public void onTabChanged(String tabId) {
		int activeTab = this.tabHost.getCurrentTab();
		this.viewpager.setCurrentItem(activeTab);
	}

	/*
	 * Return tab view and set tab text
	 * Set tab custom view and text color
	 */
	private static View createTabView(Context context, String tabText) {
		View view = LayoutInflater.from(context).inflate(R.layout.z_custom_tab_common, null, false);
		TextView tv = (TextView) view.findViewById(R.id.tabTitleText);
		tv.setText(tabText);
		//tabIndicator = (ImageView) view.findViewById(R.id.iv_tabIndicator);
		return view;
	}

	/*
	 * Add Tab content to the Tabhost 
	 */
	private static void AddTab(ZPropAddMainFragment activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
		tabSpec.setContent(activity.new TabFactory(activity.getActivity()));
		tabHost.addTab(tabSpec);
	}

	
	/*
	 * set the page according to category and change tab
	 */
	private class NewTabViewPagerAdapter extends FragmentStatePagerAdapter {

		public NewTabViewPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int pos) {
			/*
			 * set all page into viewer page of site data home menu
			 */
			switch (pos) {
				case 0:
					return ZPropAddRecidentialFragment.newInstance();
				case 1:
					return ZPropAddCommercialFragment.newInstance();
				default:
					return ZPropAddRecidentialFragment.newInstance();
			}
			
		}

		@Override
		public int getCount() {
			return tabHost.getTabWidget().getTabCount();
		}
	}


	@Override
	public void onClick(View v) {}


	// Set current tab when back after image editing
	private void setCurrentTab() {
		// set current page
		tabHost.setCurrentTab(0);
		viewpager.setCurrentItem(0);
		//tabIndicator.setVisibility(View.VISIBLE);
		
		View tabView = tabHost.getCurrentTabView();
		int scrollPos = tabView.getLeft() - (tabScrollView.getWidth() - tabView.getWidth()) / 2;
		tabScrollView.smoothScrollTo(scrollPos, 0);
	}
	
	
	public static void setTabColor(TabHost tabhost) {
        for(int i=0;i<tabhost.getTabWidget().getChildCount();i++) {
            tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.green); //unselected
        }
        tabhost.getTabWidget().getChildAt(tabhost.getCurrentTab()).setBackgroundResource(R.drawable.sec_tab); // selected
    }
	@Override
	public void onResume() {
		super.onResume();
		MainFragmentActivity.setTitle("Add Property");
	}
}
