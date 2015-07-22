package com.pvo.util;

import android.os.Environment;

import com.pvo.activity.R;

public class Constant {
	
	
	public static class Testing {
		public static final String[] TESTING_ARRAY = new String[]{"TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1","TESTEING-1"};
		public static final String[] TESTING_GROUP = new String[]{"GROUP-1","GROUP-2","GROUP-3","GROUP-4","GROUP-5","GROUP-6","GROUP-7","GROUP-8","GROUP-9","GROUP-10","GROUP-11","GROUP-12","GROUP-13","GROUP-14","GROUP-15"};
		public static final String[] TESTING_PREEFERED = new String[]{"PREEFERED-1","PREEFERED-2","PREEFERED-3","PREEFERED-4","PREEFERED-5","PREEFERED-6","PREEFERED-7","PREEFERED-8","PREEFERED-9","PREEFERED-10","PREEFERED-11","PREEFERED-12","PREEFERED-13","PREEFERED-14","PREEFERED-15"};
		public static final String[] TESTING_AREA = new String[]{"AREA-1","AREA-2","AREA-3","AREA-4","AREA-5","AREA-6","AREA-7","AREA-8","AREA-9","10","AREA-11","AREA-12","AREA-13","AREA-14","AREA-15"};
		public static final int[] TESTING_ICONID = new int[]{R.drawable.feature_ic1,R.drawable.feature_ic2,R.drawable.feature_ic3,R.drawable.feature_ic4};
	}
	
	public static class ZAddProperty {
		public static final int CAMERA_CAPTURE = 1;
		public static final int GALLERY_PHOTO = 2;
		public static final String RESIDENTIAL_PHOTO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PVO/ResidentialPhoto/";
		public static final String COMMERCIAL_PHOTO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PVO/CommercialPhoto/";
		public static final String CURRENT_TAB = "current_tab";
		public static final String areaUnit[]  = new String[]{"Sq.foot","Sq.yard"};
	}
	
	public static class PropertyType {
		/*
		 * Residential property type icon
		 */
		public static int[] residentialIconId = new int[]{
				R.drawable.appartment,
				R.drawable.house_villa,
				R.drawable.land,
				R.drawable.agrifarm
				/*R.drawable.builder_floor,
				R.drawable.farmhous,
				R.drawable.service_aapp,
				R.drawable.studio_ic*/
		};
		
		
		/*
		 * Residential property type title
		 */
		public static String[] residentialTitle = new String[]{
			"Apartment",
			"House/Villa",
			"plot/Land",
			"Agri./Farm Land"
			/*"Builder Floor",
			"Farm House",
			"Service Apartment",
			"Studio Apartment"*/
			};
		
		/*
		 *  Commercial property type icon
		 */
		public static int[] commercialIconId = new int[]{
				R.drawable.shop,
				R.drawable.shoroom,
				R.drawable.office,
				R.drawable.factory,
				R.drawable.wherehouse,
				R.drawable.industrialland
		};
		
		/*
		 * Commercial property type title
		 */
		public static String[] commercialTitle = new String[]{
			"Shop",
			"Showroom",
			"Office/Space",
			"Factory",
			"Warehouse",
			"Industrial Land"
			};
		
	}
	
	/*
	 * Set the from value constatn
	 */
	public static class StateCityLoation {
		public static final String FROM_STATE = "STATE";
		public static final String FROM_CITY = "CITY";
		public static final String FROM_LOCATION = "LOCATION";
	}
	
	public static class Residential {
		public static final String FROM_RESIDENTIAL = "RESIDENTIAL";
		public static final String RESIDENTIAL = "Residential";
		
	}
	
	public static class Commercial {
		public static final String FROM_COMMERCIAL = "COMMERCIAL";
		public static final String COMMERCIAL = "Commercial";
	}
	
	
	public static final String TREMS[] = { "Navi Sharat", "Juni Sharat", "Kheti Juni","PRA.SHA.P", "Dispute", "Shree Sarkar"};
	

	public static class PropertyDetail {
		public static final String FROM_PROPERTY_DETAIL = "Property_Detail";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String LANGUAGE[] = { "English", "hindi", "Gujarati",
			"Marathi", "Punjabi", "Tamil", "Kannad", "telugu",
			"Bengali", "malyalam" };

	public static final String PROPERTY_DEALS[] = { "Flat", "Shop", "Shades",
			"Factory", "Twin", "Plot", "Office", "Tenament" };
	
	/**
	 * Search Property 
	 * */
	public class SearchProperty{
		public static final String URL                   = "http://www.propertyviaonline.com/ws/searchproperty.php?token=@6sm@9re";
		
		/**  Flat For Rent **/
		public static final String PROPERTY_TYPE         = "propertytype";
		public static final String CMB_OPTION            = "cmboptions";
		public static final String CMB_PROPERTY_TYPE     = "cmbpropertytype";
		public static final String CMB_AREA1             = "cmbarea1";
		public static final String PROPERTY_SUB_TYPE     = "propertysubtype";
		public static final String CMB_AREA2             = "cmbarea2";
		public static final String CMB_AREA3             = "cmbarea3";
		public static final String CMB_LANDMARK1         = "cmblandmark1";
		public static final String CMB_LANDMARK2         = "cmblandmark2";
		public static final String KEYWORD               = "keyword";
		public static final String MIN_AREA              = "minarea";
		public static final String MAX_AREA              = "maxarea";
		public static final String CHK_RENT              = "chkrent";
		public static final String TXT_MIN_RENT          = "txtminrent";
		public static final String TXT_MAX_RENT          = "txtmaxrent";
		public static final String CMB_MIN_BED           = "cmbminbed";
		public static final String CMB_MAX_BED           = "cmbmaxbed";
		public static final String CMB_MIN_FLOOR         = "cmbminfloor";
		public static final String CMB_MAX_FLOOR         = "cmbmaxfloor";
		public static final String RD_RISE               = "rdrise";
		public static final String CMB_PURPOSE           = "cmbpurpose";
		public static final String FURNISH_STATUS        = "furnishstatus";
		public static final String LOGO_ENCODED          = "logoencoded";
		public static final String PHOTO_ENCODED         = "photoencoded";
		public static final String TOTAL_PRICE           = "totalprice";
		/** flat for Sale **/
		public static final String CHK_SELL              = "chksell";
		public static final String TXT_MAX_PRICE         = "txtmaxprice";
		public static final String TXT_MIN_PRICE         = "txtminprice";
		/** Bunglow for Sale **/
		public static final String MIN_PLOT_AREA         = "minplotarea";
		public static final String MAX_PLOT_AREA         = "maxplotarea";
		public static final String MIN_CONSTRUCTION_AREA = "minconstructionarea";
		public static final String MAX_CONSTRUCTION_AREA = "maxconstructionarea";
		//bunglow - rent
		/** plot for Sale **/
		public static final String CMB_TP_SCHEME          = "cmbtpscheme";
		public static final String CHK_ZONE               = "chkzone";
		public static final String NA_STATUS              = "nastatus";
		public static final String NAVI_SHARAT            = "navisharat";
		public static final String JUNI_SHARAT            = "junisharat";
		public static final String KHETI                  = "kheti";
		public static final String PRASSAP                = "prassap";
		public static final String DISPUTE                = "dispute";
		public static final String SHREE_SARKAR           = "shreesarkar";
		public static final String TXT_MIN_PLOT_AREA      = "txtminplotarea";
		public static final String TXT_MAX_PLOT_AREA      = "txtmaxplotarea";
		public static final String TXT_MIN_CONSTRUCTION   = "txtminconstr";
		public static final String TXT_MAX_CONSTRUCTION   = "txtmaxconstr";
		// Plot for Rent
		/** Shop Rent **/
		public static final String TXT_MIN_CONSTRUCTION_AREA = "txtminconstrarea";
		public static final String TXT_MAX_CONSTRUCTION_AREA = "txtmaxconstrarea";
		public static final String TXT_MAX_AREA           = "txtmaxarea";
		public static final String TXT_MIN_AREA           = "txtminarea";
		public static final String PROPERTY_ID            = "propertyid";
		public static final String STR_OPTIONS            = "stroptions";
		public static final String PURPOSE                = "purpose";
		public static final String FURNISH                = "furnish";
		public static final String MIN_FLOOR              = "minfloor";
		public static final String MAX_FLOOR              = "maxfloor";
		public static final String MIN_SQFOOT             = "minsqfoot";
		public static final String MAX_SQFOOT             = "maxsqfoot";
		public static final String MIN_BED                = "minbed";
		public static final String MAX_BED                = "maxbed";
		public static final String ST_PURPOSE             = "stpurpose";
		public static final String MIN_PRICE              = "minprice";
		public static final String MAX_PRICE              = "maxprice";
		public static final String MIN_RENT               = "minrent";
		public static final String MAX_RENT               = "maxrent";
		public static final String LOCATION1              = "Location1";
		public static final String LOCATION2              = "Location2";
		public static final String DT_ADDED               = "dtadded";
		public static final String DT_UPDATE              = "dtupdated";
		public static final String AREA_UNIT              = "areaunit";
		public static final String BED                    = "bed";
		public static final String FLOOR                  = "floor";
		public static final String LANDMARK1              = "landmark1";
		public static final String RISE                   = "rise";
		public static final String RENT                   = "rent";
		public static final String ADDRESS                = "address";
		public static final String AREA1                  = "area1";
		public static final String PLOT_AREA_UNIT         = "plotareaunit";
		public static final String AREA_NAME              = "areaname";
		public static final String LANDMARK1_NAME         = "landmark1name";
		public static final String LANDMARK2_NAME         = "landmark2name";
		public static final String FIRSTNAME              = "firstname";
		public static final String LASTNAME               = "lastname";
		public static final String PHONEM                 = "phonem";
		public static final String NO_RECORD              = "no_record";
		public static final String PAGE                   = "page";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * Search Requirement 
	 **/
	
	public class SearchRequirement{
		public static final String URL                    = "http://www.propertyviaonline.com/ws/searchrequirement.php?token=@6sm@9re";
		//Flat-sale
		public static final String CMB_PROPERTY_TYPE      = "cmbpropertytype";
		public static final String CMB_OPTION             = "cmboptions";
		public static final String CMB_AREA1              = "cmbarea1";
		public static final String BED                    = "bed";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String FLOOR                  = "floor";
		public static final String RISE                   = "rise";
		public static final String ST_PURPOSE             = "stpurpose";
		public static final String AREA_SQFOOT            = "areasqfoot";
		public static final String TXT_MIN_PRICE          = "txtminprice";
		public static final String TXT_MAX_PRICE          = "txtmaxprice";
		public static final String KEYWORD                = "keyword";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String PHOTO_ENCODE           = "photoencoded";
		public static final String PHONE_M                = "phonem";
		//Flat-Rent
		public static final String TXT_MIN_RENT           = "txtminrent";
		public static final String TXT_MAX_RENT           = "txtmaxrent";
		//Bunglow-rent
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String PROPERTY_SUBTYPE       = "propertysubtype";
		public static final String PLOT_AREA              = "plotarea";
		public static final String AREA_UNIT              = "areaunit";
		public static final String AREA_UNIT_BUNGLOW      = "areaunit";
		public static final String CONSTR_AREA            = "constrarea";
		public static final String ALL_LOCATION_NAME      = "alllocationsname";
		//Bunglow-Sale
		//Plot-Rent
		public static final String CMB_TP_SCHEME          = "cmbtpscheme";
		public static final String CMB_TP_SCHEME2         = "cmbtpscheme2";
		public static final String CMB_TP_SCHEME3         = "cmbtpscheme3";
		public static final String CHK_ZONE               = "chkzone";
		//Plot -sale
		//Shop-rent
		public static final String PLOT_AREA_UNIT         = "plotareaunit";
		public static final String CONSTR_AREA_UNIT       = "constrareaunit";
		//Shop-sale
		//
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String COMPANY_NAME           = "companyname";
		public static final String PURPOSE                = "purpose";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String MAX_CONST_AREA         = "maxconstrarea";
		public static final String MIN_CONST_AREA         = "minconstrarea";
		public static final String MIN_RENT               = "minrent";
		public static final String MAX_RENT               = "maxrent";
		public static final String REQUIREMENT_ID         = "requirementid";
		public static final String LOCATION1              = "location1";
		public static final String LOCATION2              = "location2";
		public static final String LOCATION3              = "location3";
		public static final String DT_ADDED               = "dtadded";
		public static final String DT_UPDATED             = "dtupdated";
		public static final String MIN_BED                = "minbed";
		public static final String MAX_BED                = "maxbed";
		public static final String FURNISH                = "furnish";
		public static final String MIN_FLOOR              = "minfloor";
		public static final String MAX_FLOOR              = "maxfloor";
		public static final String MIN_SQFOOT             = "minsqfoot";
		public static final String MAX_SQFOOT             = "maxsqfoot";
		public static final String MIN_PRICE              = "minprice";
		public static final String MAX_PRICE              = "maxprice";
		public static final String NO_RECORD              = "no_record";
		public static final String PAGE                   = "page";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String ST_STATUS              = "ststatus";
		public static final String LOCATION1_NAME         = "location1name";
		public static final String LOCATION2_NAME         = "location2name";
		public static final String LOCATION3_NAME         = "location3name";
		public static final String BROKER_DETAIL          = "brokerdetail";
	}

	
	/**
	 * Find Agent
	 * */
	public class FindAgent {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/findagent.php?token=@6sm@9re";
		public static final String SEARCH_STRING          = "search_string";
		public static final String BROKER_ID              = "brokerid";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String COMPANY_NAME           = "companyname";
		public static final String ADDRESS                = "address";
		public static final String PHONEM                 = "phonem";
		public static final String PHONEO                 = "phoneo";
		public static final String WEB_SITE               = "website";
		public static final String EMAIL                  = "email";
		public static final String ST_STATUS              = "ststatus";
		public static final String DT_JOIN                = "dtjoin";
		public static final String MAX_NOMINEE            = "maxnominee";
		public static final String LOGO                   = "logo";
		public static final String PHOTO                  = "photo";
		public static final String EXPIRY_DATE            = "expirydate";
		public static final String MAX_PROPERTY           = "maxproperty";
		public static final String BLN_SMS                = "blnsms";
		public static final String SMS_CREDIT             = "smscredit";
		public static final String PROPERTY_DEALS_IN      = "propertytypedealin";
		public static final String AREA_DEALS_IN          = "areadealsin";
		public static final String POST_CODE              = "postcode";
		public static final String FACEBOOK               = "facebook";
		public static final String TWITTER                = "twitter";
		public static final String LINKEDIN               = "linkedin";
		public static final String BUSINESS_PAGE          = "businessPage";
		public static final String AFFILIATED_WITH        =  "affiliatedWith";
		public static final String BUSINNESS_SCINCE       = "businessScince";
		public static final String LANGUAGE_KNOWN         = "languageKnown";
		public static final String CMBFIELDS              = "cmbfields";
		public static final String AGENT_ID               = "agentid";
		public static final String PAGE		              = "page";
		public static final String ACTION	              = "action";
		public static final String PREF_BROKER_ID         = "prefbrokerid";
		public static final String LOGO_LINK              = "logolink";
		public static final String PHOTO_LINK             = "photolink";
		public static final String TOTAL_PROPERTY         = "totalproperty";
		public static final String TOTAL_REQUIREMENT      = "totalrequirement";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Find Property By Id
	 * */
	public class FindByPropertyId {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/findproperty.php?token=@6sm@9re";
		public static final String PROPERTY_ID            = "property_id";
		public static final String USER_ID                = "userid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String MORE_DETAIL            = "moredetail";
		public static final String PROPERTY_SUB_TYPE      = "propertysubtype";
	}
	
	
	/**
	 * Account Info
	 * */
	
	public static class MyAccount {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myaccount.php?token=@6sm@9re";
		public static final String USERID                 = "user_id";
		public static final String FIRSTNAME              = "first_name";
		public static final String LASTNAME               = "last_name";
		public static final String COMPANYNAME            = "company_name";
		public static final String WEBSITE                = "website";
		public static final String ADDRESS                = "address";
		public static final String PHONEOFFICE            = "phone_office";
		public static final String PHONEOFFICE1           = "phone_office1";
		public static final String PHONEMOBILE1           = "phone_mobile1";
		public static final String PHONEMOBILE2           = "phone_mobile2";
		public static final String EMAIL                  = "email";
		public static final String PHOTO                  = "photo";
		public static final String LOGO                   = "logo";
		public static final String STATE                  = "state";
		public static final String DISTINCT               = "district";
		public static final String CITY                   = "city";
		public static final String AREA                   = "area";
		public static final String PROPERTYDEALS          = "property_deals";
		public static final String POSTCODE               = "post_code";
		public static final String FACEBOOKLINK           = "facebook_link";
		public static final String TWITTERLINK            = "twitter_link";
		public static final String LINKEDINLINK           = "linkedin_link";
		public static final String BUSINESSPAGELINK       = "business_page_link";
		public static final String AFFIATEDWITH           = "affiated_with";
		public static final String BUSINESSSCIENCE        = "business_science";
		public static final String LANGUAGEKNOW           = "language_know";
		public static final String LETTITUDE              = "latitude";
		public static final String LONGITUDE              = "longitude";
		public static final String API_MESSAGE            = "message";
		public static final String API_STATUS             = "status";
	}
	
	/**
	 * Nominee
	 * */
	public class NomineeList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/nominee.php?action=list&token=@6sm@9re";
		public static final String USER_ID                = "user_id";
		public static final String TITLE                  = "title";
		public static final String NOMINEE_ID             = "nomineeid";
		public static final String MOBILE_NO              = "mobile_no";
		public static final String EMAIL_ID               = "email_id";
		public static final String PWD		              = "pwd";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	/**
	 * Nominee Add
	 * */
	public class AddNominee {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/nominee.php?action=add&token=@6sm@9re";
		public static final String USER_ID                = "user_id";
		public static final String NAME                   = "name";
		public static final String LANLINE_NO             = "landline_no";
		public static final String MOBILE_NO              = "mobile_no";
		public static final String EMAIL                  = "email_id";
		public static final String PWD	                  = "pwd";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Edit Nominee
	 * */
	public class EditNominee   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/nominee.php?action=edit&token=@6sm@9re";
		public static final String USER_ID                = "user_id";
		public static final String NOMINEE_ID             = "nominee_id";
		public static final String NAME                   = "name";
		public static final String LANLINE_NO             = "landline_no";
		public static final String MOBILE_NO              = "mobile_no";
		public static final String EMAIL                  = "email_id";
		public static final String PWD                    = "pwd";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Delete Nominee
	 * */
	public class DeleteNominee {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/nominee.php?action=delete&token=@6sm@9re";
		public static final String NOMINEE_ID             = "nominee_id";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	/**
	 * Change Password
	 * */
	public class ChangePassword    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/changepassword.php?token=@6sm@9re";
		public static final String USER_ID                = "user_id";
		public static final String CURRENT_PWD            = "current_pwd";
		public static final String NEW_PWD                = "new_pwd";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * View Profile
	 * */
	public static class ViewProfile    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/findagentbyid.php?token=@6sm@9re";
		public static final String BROKER_ID              = "brokerid";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String COMPANY_NAME           = "companyname";
		public static final String ADDRESS                = "address";
		public static final String PHONE_M                = "phonem";
		public static final String PHONE_O                = "phoneo";
		public static final String WEBSITE                = "website";
		public static final String POST_CODE              = "postcode";
		public static final String EMAIL                  = "email";
		public static final String DT_JOIN                = "dtjoin";
		public static final String SCINCE                 = "businessScince";
		public static final String LANGUAGE_KNOWN         = "languageKnown";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String TOTAL_PROPERTY         = "maxproperty";
		public static final String SMS_BALANCE            = "blnsms";
		public static final String SMS_CREDIT             = "smscredit";
		public static final String FACEBOOK               = "facebook";
		public static final String TWITTER                = "twitter";
		public static final String LINKEDIN               = "linkedin";
		public static final String BUSINESS_PAGE          = "businessPage";
		public static final String AFFILIATED_WITH        = "affiliatedWith";
		public static final String AREA_DEALS_IN          = "areadealsin";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String PHOTO_ENCODED          = "photoencoded";
		public static final String AREA_DEALS_IN_TEXT     = "areadealsintext";
		public static final String PROPERTY_TYPE_DEALIN   = "propertytypedealin";
		public static final String STATENAME              = "statename";
		public static final String CITYNAME               = "cityname";
		public static final String DISTRICTNAME           = "districtname";
		public static final String LATITUDE		          = "lattide";
		public static final String LONGITUDE              = "longitude";
		public static final String PHOTO	              = "photo";
		public static final String LOGO		              = "logo";
	}
	
	/**
	 * My Property
	 * */
	public class MyProperty    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=list";//"http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=list&page=1";
		public static final String USER_ID                = "user_id";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String PAGE                   = "page";
		public static final String PROPERTY_ID            = "propertyid";
		public static final String IMAGE1_LINK            = "image1link";
		public static final String POSTCODE               = "postcode";
		public static final String AREA1                  = "area1";
		public static final String LANDMARK1              = "landmark1";
		public static final String LANDMARK2              = "landmark2";
		public static final String NO_RECORD              = "no_record";
		public static final String PHOTO_ENCODED          = "photoencoded";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String AREA_NAME              = "areaname";
		public static final String LANDMARK1_NAME         = "landmark1name";
		public static final String LANDMARK2_NAME         = "landmark2name";
		public static final String MORE_DETAILS           = "moredetails";
		public static final String WHOM_TO_LET            = "whomtolet";
		public static final String WHOM_TO_LET_OTHER      = "whomtoletother";
		public static final String PLOT_TYPE              = "plottype";
		public static final String BUNGLOW_TYPE           = "bunglowtype";
		public static final String SHOP_TYPE              = "shoptype";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String INQUIRYCOUNT           = "inquirycount";
		public static final String STATE_NAME             = "statename";
		public static final String CITY_NAME              = "cityname";
		public static final String PURPOSE  			  = "purpose";
		public static final String COMPANY_NAME			  = "companyname";
	}
	
	/**
	 * Add My Property
	 * */
	public class AddProperty   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=add";
		public static final String USER_ID                = "user_id";
		public static final String BROKER_ID              = "brokerid";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String ADDRESS                = "address";
		public static final String POST_CODE              = "postcode";
		public static final String CMB_AREA1              = "cmbarea1";
		public static final String COUNTRY_ID             = "countryid";
		public static final String STATE_ID               = "stateid";
		public static final String CITY_ID                = "cityid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String STR_OPTIONS            = "stroptions";
		public static final String LOCATION			      = "location";
		public static final String LANDMARK1              = "landmark1";
		public static final String LANDMARK1NAME          = "landmark1name";
		public static final String LANDMARK2              = "landmark2";
		public static final String LANDMARK2NAME          = "landmark2name";
		public static final String LANDMARK1_OTHER        = "landmark1other";
		public static final String LANDMARK2_OTHER        = "landmark2other";
		public static final String LONGITUDE              = "longval";
		public static final String LATITUDE               = "latval";
		public static final String OCCUPACY               = "occupacy";
		public static final String OCCUPANCY_NAME         = "occupacyName";
		public static final String OCCUPANCY_DETAIL       = "occupacyDetail";
		public static final String OCCUPANCY_DATE         = "occupacyDate";
		public static final String PRICE                  = "price";
		public static final String TOTAL_PRICE            = "totalprice";
		public static final String RENT                   = "rent";
		public static final String DASTAWAGE              = "dastawage";
		public static final String RENT_DEPOSIT           = "rentdeposit";
		public static final String AREA_UNIT              = "areaunit";
		public static final String CMB_PURPOSE            = "cmbpurpose";
		public static final String MIN_AREA               = "minarea";
		public static final String MAX_AREA               = "maxarea";
		public static final String YEAR_BUILD_UP          = "yearbuiltup";
		public static final String COMMENTS               = "comments";
		public static final String HINT                   = "hint";
		public static final String BED                    = "bed";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String FURNISH_COMMENT        = "furnishcomment";
		public static final String FLOOR                  = "floor";
		public static final String RISE                   = "rise";
		public static final String WHOM_TO_LET            = "whomtolet";
		public static final String WHOM_TO_LET_OTHER      = "whomtoletother";
		public static final String PARKING                = "parking";
		public static final String FRONT_HEIGHT           = "frontheight";
		public static final String ATTACH_COMMON          = "attachcommon";
		public static final String CONSTRUCTION_AREA      = "constructionarea";
		public static final String BUNGLOW_TYPE           = "bunglowtype";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String PLOT_AREA              = "plotarea";
		public static final String PLOT_AREA_UNIT         = "plotareaunit";
		public static final String PLOT_TYPE              = "plottype";
		public static final String NA_STATUS              = "nastatus";
		public static final String KHETI                  = "kheti";
		public static final String NAVISHARAT             = "navisharat";
		public static final String JUNISHARAT             = "junisharat";
		public static final String PRASSAP                = "prassap";
		public static final String DIS_PUTE               = "dispute";
		public static final String TITLE_CLEAR            = "titleclear";
		public static final String SHREE_SARKAR           = "shreesarkar";
		public static final String ON_ROAD                = "onroad";
		public static final String PREF_OPT               = "prefopt";
		public static final String MAINTENANCE            = "maintenance";
		public static final String TRANSFER_FEES          = "transferfees";
		public static final String AEC_AUDA               = "aecauda";
		public static final String CMBTP_SCHEME           = "cmbtpscheme";
		public static final String CHK_ZONE               = "chkzone";
		public static final String	CHK_MESSAGE           = "chkmessage";
		public static final String	CHK_MAIL              = "chkmail";
		public static final String	CHK                   = "chk";
		public static final String	CHK_FACILITY          = "chkfacility";
		public static final String	CHK_BROKER            = "chkbroker";
		public static final String	CHK_GROUP             = "chkgroup";
		public static final String	CHK_AREA              = "chkarea";
		public static final String ST_STATUS              = "ststatus";
		public static final String PURPOSE                = "purpose";
		public static final String SMS_STATUS             = "smsstatus";
		public static final String EMAIL_STATUS           = "emailstatus";
		public static final String ZONE                   = "zone";
		public static final String CNT                    = "cnt";
		public static final String AREA1                  = "area1";
		public static final String AREANAME               = "areaname";
		public static final String SMS_TO_BROKER          = "smstobrokers";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String DATE_ADDED             = "dtadded";
		public static final String DATE_UPDTE             = "dtupdated";
		public static final String DETAIL_COUNT           = "detailcount";
		public static final String SUMMARY_COUNT          = "summarycount";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String IMAGE_1                = "image0";
		public static final String IMAGE_2                = "image1";
		public static final String IMAGE_3                = "image2";
		public static final String IMAGE_4                = "image3";
		public static final String IMAGE_5                = "image4";
		public static final String IMAGES                 = "images";
		public static final String FACILITIES             = "facilities";
		public static final String REQ_STATUS             = "reqstatus";
		public static final String EXTRA_IMG		      = "extra_img";
		public static final String BATH_ROOM			  = "bath_room";
		public static final String AREA_SQ_FIT			  = "area_sq_fit";
		public static final String AREA_YARD			  = "area_sq_yard";
		
		
	}
	
	
	/**
	 * Edit Property
	 * */
	public class EditProperty  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=edit";
		public static final String USER_ID                = "user_id";
		public static final String ID                     = "id";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String ADDRESS                = "address";
		public static final String POST_CODE              = "postcode";
		public static final String CMB_AREA1              = "cmbarea1";
		public static final String COUNTRY_ID             = "countryid";
		public static final String STATE_ID               = "stateid";
		public static final String STATE_NAME             = "statename";
		public static final String CITY_NAME              = "cityname";
		public static final String AREA_NAME              = "areaname";
		public static final String CITY_ID                = "cityid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String STR_OPTIONS            = "stroptions";
		public static final String LANDMARK1              = "landmark1";
		public static final String LANDMARK2              = "landmark2";
		public static final String LANDMARK1_OTHER        = "landmark1other";
		public static final String LANDMARK2_OTHER        = "landmark2other";
		public static final String LONGITUDE              = "longval";
		public static final String LATITUDE               = "latval";
		public static final String OCCUPANCY              = "occupacy";//"occupancy";
		public static final String OCCUPANCY_NAME         = "occupacyName";//occupacyName
		public static final String OCCUPANCY_DETAIL       = "occupacyDetail";
		public static final String OCCUPANCY_DATE         = "occupacyDate";
		public static final String PRICE                  = "price";
		public static final String TOTAL_PRICE            = "totalprice";
		public static final String RENT                   = "rent";
		public static final String DASTAWAGE              = "dastawage";
		public static final String RENT_DEPOSIT           = "rentdeposit";
		public static final String AREA_UNIT              = "areaunit";
		public static final String CMB_PURPOSE            = "cmbpurpose";
		public static final String MIN_AREA               = "minarea";
		public static final String MAX_AREA               = "maxarea";
		public static final String YEAR_BUILD_UP          = "yearbuiltup";
		public static final String COMMENTS               = "comments";
		public static final String HINT                   = "hint";
		public static final String BED                    = "bed";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String FURNISH_COMMENT        = "furnishcomment";
		public static final String FLOOR                  = "floor";
		public static final String RISE                   = "rise";
		public static final String WHOM_TO_LET            = "whomtolet";
		public static final String WHOM_TO_LET_OTHER      = "whomtoletother";
		public static final String PARKING                = "parking";
		public static final String FRONT_HEIGHT           = "frontheight";
		public static final String ATTACH_COMMON          = "attachcommon";
		public static final String CONSTRUCTION_AREA      = "constructionarea";
		public static final String BUNGLOW_TYPE           = "bunglowtype";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String PLOT_AREA              = "plotarea";
		public static final String PLOT_AREA_UNIT         = "plotareaunit";
		public static final String PLOT_TYPE              = "plottype";
		public static final String NA_STATUS              = "nastatus";
		public static final String KHETI                  = "kheti";
		public static final String NAVISHARAT             = "navisharat";
		public static final String JUNISHARAT             = "junisharat";
		public static final String PRASSAP                = "prassap";
		public static final String DIS_PUTE               = "dispute";
		public static final String TITLE_CLEAR            = "titleclear";
		public static final String SHREE_SARKAR           = "shreesarkar";
		public static final String ON_ROAD                = "onroad";
		public static final String PREF_OPT               = "prefopt";
		public static final String MAINTENANCE            = "maintenance";
		public static final String TRANSFER_FEES          = "transferfees";
		public static final String AEC_AUDA               = "aecauda";
		public static final String CMBTP_SCHEME           = "tpid";//"cmbtpscheme";
		public static final String CHK_ZONE               = "zone";//"chkzone";
		public static final String	CHK_MESSAGE           = "chkmessage";
		public static final String	CHK_MAIL              = "chkmail";
		public static final String	CHK                   = "chk";
		public static final String	CHK_FACILITY          = "chkfacility";
		public static final String	CHK_BROKER            = "chkbroker";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		
		public static final String IMAGE1_LINK            = "image1link";
		public static final String IMAGE2_LINK            = "image2link";
		public static final String IMAGE3_LINK            = "image3link";
		public static final String IMAGE4_LINK            = "image4link";
	
	}
	
	/**
	 * Delete Property
	 * */
	public class DeleteMyProperty  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=delete";
		public static final String	USER_ID               = "user_id";
		public static final String INT_ID                 = "intId";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Active Selected Property from My Property List 
	 * */
	public class ActiveMyProperty  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=active";
		public static final String	USER_ID               = "user_id";
		public static final String INT_ID                 = "intId";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	/**
	 * Inactive Selected Property from My Property List 
	 * */
	public class InActiveMyProperty    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=inactivate";
		public static final String	USER_ID               = "user_id";
		public static final String INT_ID                 = "intId";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 *  My Property View Receive Inquiry 
	 **/
	public class MyPropertyViewReceiveInquiry  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/propertyinquiry.php?token=@6sm@9re&type=1";
		public static final String PROPERTY_ID            = "property_id";
		public static final String PAGE                   = "page";
		public static final String INQUIRY_ID             = "inquiryid";
		public static final String NAME                   = "name";
		public static final String PHONE                  = "phone";
		public static final String EMAIL                  = "email";
		public static final String MESSAGE                = "message";
		public static final String DATE                   = "date";
		public static final String API_STATUS             = "status"; 
		public static final String API_MESSAGE            = "message";
		public static final String BROKER_NAME            = "brokername";
		public static final String COMPANY_NAME           = "companyname";
	}
	
	
	/**
	 * Facility
	 * */
	public class Facility  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/amenities.php?token=@6sm@9re";
		public static final String FACILITY_ID            = "facilityid";
		public static final String TITLE                  = "title";
		public static final String IMAGE_PATH             = "imagepath";
	}
	
	/**
	 * My Requirement List
	 * */
	public class MyRequirement {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=list";
		public static final String USER_ID                = "user_id";
		public static final String PAGE                   = "page";
		public static final String REQUIREMENT_ID         = "requirementid";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String PROPERTY_SUB_TYPE      = "propertysubtype";
		public static final String PURPOSE                = "purpose";
		public static final String MIN_FLOOR              = "minfloor";
		public static final String MAX_FLOOR              = "maxfloor";
		public static final String RISE                   = "rise";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String MIN_CONSTRUCTION_AREA  = "minconstrarea";
		public static final String MAX_CONSTRUCTION_AREA  = "maxconstrarea";
		public static final String MIN_SQFOOT             = "minsqfoot";
		public static final String MAX_SQFOOT             = "maxsqfoot";
		public static final String ST_PURPOSE             = "stpurpose";
		public static final String LOCATION1              = "location1";
		public static final String LOCATION2              = "location2";
		public static final String LOCATION3              = "location3";
		public static final String DT_ADDED               = "dtadded";
		public static final String DT_UPDATE              = "dtupdated";
		public static final String MIN_BED                = "minbed";
		public static final String MAX_BED                = "maxbed";
		public static final String FURNISH                = "furnish";
		public static final String MIN_PRICE              = "minprice";
		public static final String MAX_PRICE              = "maxprice";
		public static final String MIN_RENT               = "minrent";
		public static final String MAX_RENT               = "maxrent";
		public static final String LOCATION1_NAME         = "location1name";
		public static final String LOCATION2_NAME         = "location2name";
		public static final String LOCATION3_NAME         = "location3name";
		public static final String PHOTO_ENCODED          = "photoencoded";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String STATE_ID               = "stateid";
		public static final String CITY_ID                = "cityid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String TP                     = "tp";
		public static final String KEYWORD                = "keyword";
		public static final String HINT                   = "hint";
		public static final String ZONE                   = "zone";
		public static final String ALL_LOCATION_NAME      = "alllocationsname";
		public static final String INQUIRY_COUNT	      = "inquirycount";
		public static final String ST_STATUS              = "ststatus";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * My Requirement Detail 
	 * */
	public class MyRequirementDetail   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/requirementbyid.php?token=@6sm@9re";
		public static final String USER_ID                = "user_id";
		public static final String REQUIREMENT_ID         = "requirementid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String LOCATION1_NAME         = "location1name";
		public static final String LOCATION2_NAME         = "location2name";
		public static final String LOCATION3_NAME         = "location3name";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String PHOTO_ENCODED          = "photoencoded";
		public static final String ALL_LOCATIONNAME       = "alllocationsname";
		public static final String ALL_LOCATIONS          = "alllocations";
	}
	
	/**
	 * Active My Requirement  
	 * */
	public class ActiveMyRequirement   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=active";
		public static final String USER_ID                = "user_id";
		public static final String INT_ID                 = "intId";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * De-Active My Requirement  
	 * */
	public class InActiveMyRequirement {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=inactive";
		public static final String USER_ID                = "user_id";
		public static final String INT_ID                 = "intId";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Delete My Requirement  
	 * */
	public class DeleteMyRequirement   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=delete";
		public static final String ID                     = "id";
		public static final String USER_ID                = "user_id";
		public static final String INT_ID                 = "intId";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * Edit My Requirement  
	 * */
	public class EditMyRequirement {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=edit";
		public static final String USER_ID                = "user_id";
		public static final String ID                     = "id";
		public static final String CMB_PROP_TYPE          = "cmbproptype";
		public static final String PROPERTY_SUB_TYPE      = "propertysubtype";
		public static final String COUNTRY_ID             = "countryid";
		public static final String STATE_ID               = "stateid";
		public static final String CITY_ID                = "cityid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String LOCATION               = "location";
		public static final String HINT                   = "hint";
		public static final String KEYWORd                = "keyword";
		public static final String STR_OPTION             = "stroptions";
		public static final String TXT_MIN_PRICE          = "txtminprice";
		public static final String TXT_MAX_PRICE          = "txtmaxprice";
		public static final String TXT_MIN_RENT           = "txtminrent";
		public static final String TXT_MAX_RENT           = "txtmaxrent";
		public static final String MIN_BED                = "minbed";
		public static final String MAX_BED                = "maxbed";
		public static final String MIN_FLOOR              = "minfloor";
		public static final String MAX_FLOOR              = "maxfloor";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String RISE                   = "rise";
		public static final String MIN_SQ_FOOT            = "minsqfoot";
		public static final String MAX_SQ_FOOT            = "maxsqfoot";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String MIN_CONSTRUCTION_AREA  = "minconstrarea";
		public static final String MAX_CONSTRUCTION_AREA  = "maxconstrarea";
		public static final String CMB_TP_SCHEME          = "cmbtpscheme";
		public static final String ST_PURPOSE             = "stpurpose";
		public static final String CHK_ZONE               = "chkzone";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Add My Requirement  
	 * */
	public class AddMyRequirement  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=add";
		public static final String USER_ID                = "user_id";
		public static final String ID                     = "id";
		public static final String CMB_PROP_TYPE          = "cmbproptype";
		public static final String PROPERTY_SUB_TYPE      = "propertysubtype";
		public static final String COUNTRY_ID             = "countryid";
		public static final String STATE_ID               = "stateid";
		public static final String CITY_ID                = "cityid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String LOCATION               = "location";
		public static final String HINT                   = "hint";
		public static final String KEYWORd                = "keyword";
		public static final String STR_OPTION             = "stroptions";
		public static final String TXT_MIN_PRICE          = "txtminprice";
		public static final String TXT_MAX_PRICE          = "txtmaxprice";
		public static final String TXT_MIN_RENT           = "txtminrent";
		public static final String TXT_MAX_RENT           = "txtmaxrent";
		public static final String MIN_BED                = "minbed";
		public static final String MAX_BED                = "maxbed";
		public static final String MIN_FLOOR              = "minfloor";
		public static final String MAX_FLOOR              = "maxfloor";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String RISE                   = "rise";
		public static final String MIN_SQ_FOOT            = "minsqfoot";
		public static final String MAX_SQ_FOOT            = "maxsqfoot";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String MIN_CONSTRUCTION_AREA  = "minconstrarea";
		public static final String MAX_CONSTRUCTION_AREA  = "maxconstrarea";
		public static final String CMB_TP_SCHEME          = "cmbtpscheme";
		public static final String ST_PURPOSE             = "stpurpose";
		public static final String CHK_ZONE               = "chkzone";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 *  My Requirement View Receive Inquiry 
	 **/
	public class MyRequirementViewReceiveInquiry   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/propertyinquiry.php?token=@6sm@9re&page=1&type=2";
		public static final String PROPERTY_ID            = "property_id";
		public static final String BROKER_ID              = "brokerid";
		public static final String NAME                   = "name";
		public static final String PHONE                  = "phone";
		public static final String EMAIL                  = "email"; 
		public static final String MESSAGE                = "message";
		public static final String DATE                   = "date";
		public static final String PAGE                   = "page";
		public static final String TYPE                   = "type";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * TPSchemes Listing
	 * */
	public class TPSchemesListing  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/tpscheme.php?token=@6sm@9re";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String TITLE                  = "title";
		public static final String TP_ID                  = "tpid";
		public static final String CITY_ID                = "cityid";
		public static final String MAP		              = "map";
		
	}
	
	
	/**
	 * Preferred Broker
	 * */
	public class PreferredBrokers  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/findagent.php?token=@6sm@9re";
		public static final String AGENT_ID               = "agentid";
		public static final String DATA_REQUEST           = "datarequest";
		public static final String PAGE                   = "page";
		public static final String NO_RECORD              = "no_record";
		public static final String BROKER_ID              = "brokerid";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String COMPANY_NAME           = "companyname";
		public static final String ADDRESS                = "address";
		public static final String PHONEM                 = "phonem";
		public static final String PHONEO                 = "phoneo";
		public static final String WEB_SITE               = "website";
		public static final String EMAIL                  = "email";
		public static final String ST_STATUS              = "ststatus";
		public static final String DT_JOIN                = "dtjoin";
		public static final String POSTCODE               = "postcode";
		public static final String FACEBOOK               = "facebook";
		public static final String TWITTER                = "twitter";
		public static final String LINKEDIN               = "linkedin";
		public static final String AFFILIATE_WITH         = "affiliatedWith";
		public static final String BUSINESS_SCINCE        = "businessScince";
		public static final String LANGUAGE_KNOWN         = "languageKnown";
		public static final String BUSINESS_PAGE          = "businessPage";
		public static final String PREF_ID                = "prefid";
		public static final String PHOTO_LINK             = "photolink";
		public static final String LOGO_LINK              = "logolink";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * Public Broker
	 * */
	public class PublicBrokers {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/publicbroker.php?token=@6sm@9re&public=1";
		public static final String AGENT_ID               = "agentid";
		public static final String PAGE                   = "page";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String BROKER_ID              = "brokerid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * All Broker
	 * */
	public class AllBrokers {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/findagent.php?token=@6sm@9re&datarequest=All";
		public static final String PAGE                   = "page";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String BROKER_ID              = "brokerid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	
	
	/**
	 * Public Property
	 * */
	public class PublicProperty    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/publicproperty.php?token=@6sm@9re";
		public static final String PAGE                   = "page";
		public static final String PROPERTY_ID            = "propertyid";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String PROPERTY_SUBTYPE       = "propertysubtype";
		public static final String STR_OPTION             = "stroptions";
		public static final String ADDRESS                = "address";
		public static final String AREA1                  = "area1";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String DT_ADDED               = "dtadded";
		public static final String DT_UPDATE              = "dtupdated";
		public static final String MIN_AREA               = "minarea";
		public static final String MAX_AREA               = "maxarea";
		public static final String AREA_UNIT              = "areaunit";
		public static final String LANDMARK1              = "landmark1";
		public static final String LANDMARK2              = "landmark2";
		public static final String DETAIL_COUNT           = "detailcount";
		public static final String POSTCODE               = "postcode";
		public static final String RISE                   = "rise";
		public static final String BED                    = "bed";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String DASTAWAGE              = "dastawage";
		public static final String FLOOR                  = "floor";
		public static final String YEAR_BUILD_UP          = "yearbuiltup";
		public static final String RENT_DEPOSITE          = "rentdeposit";
		public static final String RENT                   = "rent";
		public static final String MAINTENANCE            = "maintenance";
		public static final String TOTAL_PRICE            = "totalprice";
		public static final String LAT_VAL                = "latval";
		public static final String LONG_VAL               = "longval";
		public static final String PLOT_AREA              = "plotarea";
		public static final String PLOT_AREA_UNIT         = "plotareaunit";
		public static final String CONSTRUCTION_AREA      = "constructionarea";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String AREA_NAME              = "areaname";
		public static final String LANDMARK1_NAME         = "landmark1name";
		public static final String LANDMARK2_NAME         = "landmark2name";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String PHOTO_ENCODED          = "photoencoded";
		public static final String PHONE_M                = "phonem";
		public static final String NOMINEE_NAME           = "nominee_name";
		public static final String NOMINEE_MOBILE_NO      = "nominee_mobile_no";
		public static final String NOMINEE			      = "nominee";
		
	}
	
	/**
	 * Public Requirement
	 * */
	public class PublicRequirement {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/publicrequirement.php?token=@6sm@9re";
		public static final String PAGE                   = "page";
		public static final String REQUIREMENT_ID         = "requirementid";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String PROPERTY_SUB_TYPE      = "propertysubtype";
		public static final String LOCATION1              = "location1";
		public static final String LOCATION2              = "location2";
		public static final String LOCATION3              = "location3";
		public static final String PURPOSE                = "purpose";
		public static final String AREA1                  = "area1";
		public static final String MIN_RENT               = "minrent";
		public static final String MAX_RENT               = "maxrent";
		public static final String MIN_PRICE              = "minprice";
		public static final String MAX_PRICE              = "maxprice";
		public static final String MIN_BED                = "minbed";
		public static final String MAX_BED                = "maxbed";
		public static final String MIN_FLOOR              = "minfloor";
		public static final String MAX_FLOOR              = "maxfloor";
		public static final String FURNISH                = "furnish";
		public static final String RISE                   = "rise";
		public static final String MIN_SQFOOT             = "minsqfoot";
		public static final String MAX_SQFOOT             = "maxsqfoot";
		public static final String DT_ADDED               = "dtadded";
		public static final String DT_UPDATE              = "dtupdated";
		public static final String ST_STATUS              = "ststatus";
		public static final String KEYWORD                = "keyword";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String MIN_CONSTRUCTION_AREA  = "minconstrarea";
		public static final String MAX_CONSTRUCTION_AREA  = "maxconstrarea";
		public static final String HINT                   = "hint";
		public static final String ST_PURPOSE             = "stpurpose";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String LOCATION1_NAME         = "location1name";
		public static final String LOCATION2_NAME         = "location2name";
		public static final String LOCATION3_NAME         = "location3name";
		public static final String LOGO_ENCODED           = "logoencoded";
		public static final String PHOTO_ENCODED          = "photoencoded";
		public static final String FIRST_NAEE             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String PHONE_M                = "phonem";
		public static final String ALL_LOCATION_NAME      = "alllocationsname";
		
	}
	
	/**
	 * Login
	 * */
	public class Login {
		public static final String URL                    ="http://www.propertyviaonline.com/ws/login.php?token=@6sm@9re";// "http://propertyviaonline.com/ws/login.php?token=@6sm@9re";
		public static final String MOBILE_NO              = "mobile_no";
		public static final String PASSWORD               = "password";
		public static final String SMS_CODE               = "sms_code";
		public static final String USER_ID                = "user_id";
		public static final String LOGIN_FIREST           = "login_first";
		public static final String FIRST_NAME             = "First Name";
		public static final String LAST_NAME              = "Last Name";
		public static final String COMPANY_NAME           = "Company Name";
		public static final String PHONE_NUMBER           = "Phone Number";
		public static final String EMAIL                  = "Email";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String STATE_ID               = "stateid";
		public static final String CITY_ID                = "cityid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String AREA_DEALS_IN_TEXT     = "areadealsintext";
		public static final String AREA_DEALS_IN          = "areadealsin";
		public static final String COUNTRY_NAME           = "countryname";
		public static final String STATE_NAME             = "statename";
		public static final String CITY_NAME              = "cityname";
		public static final String DISTRICT_NAME          = "districtname";
		
	}
	
	/**
	 * Register
	 * */
	public class Registration {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/registration.php?token=@6sm@9re";
		public static final String MOBILE_NO              = "mobile_no";
		public static final String AGENT_CODE   		  = "agent_code";
		public static final String GSM_ID			      = "gsmid";
		public static final String FIRST_NAME		      = "firstname";
		public static final String COUNTRY_ID		      = "countryid";
		public static final String STATE_ID			      = "stateid";
		public static final String CITY_ID			      = "cityid";
		public static final String DISTRICT_ID		      = "districtid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Forgot Password
	 * */
	public class ForgotPassword    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/forgotpassword.php?token=@6sm@9re";
		public static final String MOBILE_NO              = "mobile_no";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Sms verification
	 * */
	public class SmsVerificationCode   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/sms-login.php?token=@6sm@9re";//"http://propertyviaonline.com/ws/sms-login.php?token=@6sm@9re";
		public static final String BROKER_ID              = "brokerid";
		public static final String SMS_CODE               = "smscode";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * State
	 * */
	public static class State  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/state.php?token=@6sm@9re";
		public static final String STATENAME              = "statename";
		public static final String STATE_ID               = "state_id";
		public static final String STATEID                = "stateid";
		public static final String COUNTRY_ID             = "countryid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * City
	 * */
	public static class City   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/city.php?token=@6sm@9re";
		public static final String CITY_NAME              = "cityname";
		public static final String CITY_ID                = "cityid";
		public static final String STATE_ID               = "state_id";
		public static final String COUNTRY_ID             = "countryid";
		public static final String DISTRICT_ID             = "district_id";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		//public static String[] CITY_NAME_ARRAY;
		//public static String[] CITY_VALUE;
	}
	
	
	/**
	 * District
	 * */
	public static class District   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/district.php?token=@6sm@9re";
		public static final String STATE_ID               = "state_id";
		public static final String STATEID                = "stateid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String DISTRICT_NAME          = "districtname";
		public static final String COUNTRY_ID             = "country_id";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	/**
	 * Area Of city
	 * */
	public static class Area   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/area.php?token=@6sm@9re";
		public static final String CITY_ID                = "city_id";
		public static final String STATE_ID               = "state_id";
		public static final String AREA_ID                = "areaid";
		public static final String AREA_NAME              = "areaname";
		public static final String DISTRICT_ID            = "districtid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * Landmark
	 * */
	public static class Landmark   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/landmark.php?token=@6sm@9re";
		public static final String LANDMARK_ID            = "landmarkid";
		public static final String LANDMARK_NAME          = "landmarkname";
		public static final String AREA_ID                = "areaid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Landmark
	 * */
	public static class AddNewLandmark   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/landmark.php?token=@6sm@9re&&action=add";
		public static final String AREA_ID                = "areaid";
		public static final String DISTRICT_ID            = "districtid";
		public static final String CITY_ID                = "cityid";
		public static final String STATE_ID               = "stateid";
		public static final String LANDMARK_NAME          = "landmarkname";
		public static final String BROKER_NAME_PHONE      = "brokernamephone";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		
	}
	
	
	/**
	 * Email/SMS 
	 * */
	public static class EmailSms   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/email-sms.php?token=@6sm@9re";
		public static final String PROPERTY_ID            = "propertyid";
		public static final String LOGIN_ID               = "loginbrokerid";
		public static final String TXT_NAME               = "txtname";
		public static final String MSG                    = "msg";
		public static final String TXT_PHONE              = "txtphone";
		public static final String EMAIL                  = "email";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}	
	
	
	/**
	 * Admin Broadcst Message
	 * */
	public static class BroadCastMessage   {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/broadcastmsg.php?token=@6sm@9re";
		public static final String MSG                    = "msg";
		public static final String ST_STATUS              = "ststatus";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}	
	
	/**
	 * Notification 
	 * */
	public static class AllNotification    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/notification.php?token=@6sm@9re&action=list";//"http://www.propertyviaonline.com/ws/notification.php?token=@6sm@9re";//"http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=list";
		public static final String LOGIN_USER_ID          = "loginuserid";
		public static final String PROPERTY_ID            = "propertyid";
		public static final String REQUIREMETN_ID         = "requirementid";
		public static final  String PROPERTY_TYPE         = "propertytype";
		public static final String PROPERTY_SUB_TYPE      = "propertysubtype";
		public static final String DATE_POSTED_TIME       = "datepostedtime";
		public static final String LOCATION1              = "location1";
		public static final String LOCATIN2               = "location2";
		public static final String AREA                   = "area"; 
		public static final String MIN_AMT                = "minamt";
		public static final String MAX_AMT                = "maxamt";
		public static final String RENT                   = "rent";
		public static final String TOTAL_PRICE            = "totalprice";
		public static final String ST_STATUS              = "ststatus";
		public static final String ADDRESS                = "address";
		public static final String POST_CODE              = "postcode";
		public static final String FLOOR                  = "floor";
		public static final String BED                    = "bed";
		public static final String RISE                   = "rise";
		public static final String FURNISH_STATUS         = "furnishstatus";
		public static final String FURNISH_COMMENT        = "furnishcomment";
		public static final String PRICE                  = "price";
		public static final String RENT_DEPOSIT           = "rentdeposit";
		public static final String MAINTENANCE            = "maintenance";
		public static final String PLOT_AREA_UNIT         = "plotareaunit";
		public static final String PLOT_AREA              = "plotarea";
		public static final String AREA_UNIT              = "areaunit";
		public static final String CONSTRUCTION_AREA      = "constructionarea";
		public static final String MIN_SQFOOT             = "minsqfoot";
		public static final String MAX_SQFOOT             = "maxsqfoot";
		public static final String MIN_PLOT_AREA          = "minplotarea";
		public static final String MAX_PLOT_AREA          = "maxplotarea";
		public static final String YEAR_BUILDUP           = "yearbuiltup";
		public static final String PURPOSE                = "purpose";
		public static final String DT_ADDED               = "dtadded";
		public static final String DT_UPDATE              = "dtupdated";
		public static final String DETAIL_COUNT           = "detailcount";
		public static final String FIRST_NAME             = "firstname";
		public static final String LAST_NAME              = "lastname";
		public static final String PHONE_M                = "phonem";
		public static final String PHONE_O                = "phoneo";
		public static final String V_TYPE                 = "vtype";
		public static final String PUROPSE			      = "purpose";
		public static final String COMPANY_NAME           = "companyname";
		public static final String AREA1                  = "area1";
		public static final String AREA_NAME              = "areaname";
		public static final String LOCATION1_NAME         = "location1name";
		public static final String LOCATION2_NAME         = "location2name";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		public static final String PAGE					  = "page";
		public static final String TOTAL				  = "total";
		public static final String UNREAD				  = "unread";
		public static final String NO_RECORD_TODAY		  = "no_record_today";
		public static final String MIN_BED				  = "minbed";
		public static final String MAX_BED				  = "maxbed";
		public static final String MIN_RENT				  = "minrent";
		public static final String MAX_RENT				  = "maxrent";
		public static final String MIN_PRICE			  = "minprice";
		public static final String MAX_PRICE			  = "maxprice";
		public static final String PHOTO_LINK			  = "photolink";
		public static final String LOGO_LINK			  = "logolink";
	}	
	
	
	/**
	 * Admin Broadcst Message
	 * */
	public static class ReadNotifaction {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/notification.php?token=@6sm@9re&action=read";
		public static final String NOTIFICATION_ID        = "notificationid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}	
	
	/** Ads Listing **/
	public static class AdsListing {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/ads.php?token=@6sm@9re";
		public static final String PROJECT_ID             = "projectid";
		public static final String VIDEO                  = "video";
		public static final String TITLE                  = "title";
		public static final String LOCATION_ID            = "locationid";
		public static final String ADDRESS                = "address";
		public static final String AREA_NAME              = "areaname";
		public static final String CITY_NAME              = "cityname";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String DETAIL                 = "detail";
		public static final String BUILDER_NAME           = "buildername";
		public static final String BUILDER_ADDRESS        = "builderaddress";
		public static final String CONTACT_NO1            = "contactno1";
		public static final String CONTACT_NO2            = "contactno2";
		public static final String CONTACT_NO3            = "contactno3";
		public static final String CONTACT_PERSON         = "contactperson";
		public static final String CONTACT_NUMBER         = "contactnumber";
		public static final String START_PRICE            = "startprice";
		public static final String END_PRICE              = "endprice";
		public static final String STSTATUS               = "ststatus";
		public static final String HOME_PAGE_STATUS       = "homepagestatus";
		public static final String FEATURED               = "featured";
		public static final String AREA_INFO              = "areainfo";
		public static final String SHORT_INFO             = "shortinfo";
		public static final String BUILDER_ID             = "builderid";
		public static final String TOTAL_VIEW             = "totalview";
		public static final String SPECIFICATION          = "specification";
		public static final String LONGI                  = "longi";
		public static final String LAT                    = "lat";
		public static final String PROPERTY_HEADER_IMAGE  = "propertyheaderimage";
		public static final String PROPERTY_LOGO          = "propertylogo";
		public static final String BUILDER_LOGO           = "builderlogo";
		public static final String PRICE_PDF              = "pricepdf";
		public static final String FLOOR_PLAN             = "floorplan";
		public static final String FACILITIES             = "facilities";
		public static final String EMAIL	              = "email";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}	
	
	/** Filter  Property **/
	public static class FilterList {
		public static final String URL_PUBLIC_PROPERTY    = "http://www.propertyviaonline.com/ws/publicproperty.php?token=@6sm@9re";	
		public static final String URL_PUBLIC_REQUIREMENT = "http://www.propertyviaonline.com/ws/publicrequirement.php?token=@6sm@9re";
		public static final String URL_ALL_NOTIFICATION   = "http://www.propertyviaonline.com/ws/notification.php?token=@6sm@9re";
		public static final String URL_SEARCH_PROPERTY    = "http://www.propertyviaonline.com/ws/searchproperty.php?token=@6sm@9re";
		public static final String URL_SEARCH_REQUIREMENT = "http://www.propertyviaonline.com/ws/searchrequirement.php?token=@6sm@9re";
		public static final String URL_FIND_AGENT         = "http://www.propertyviaonline.com/ws/findagent.php?token=@6sm@9re";
		public static final String URL_MY_PROPERTY        = "http://www.propertyviaonline.com/ws/myproperty.php?token=@6sm@9re&action=list";
		public static final String URL_MY_REQUIREMENT     = "http://www.propertyviaonline.com/ws/myrequirement.php?token=@6sm@9re&action=list";
		public static final String PAGE                   = "page";
		public static final String PROPERTY_TYPE          = "propertytype";
		public static final String PURPOSE                = "purpose";
		public static final String LOCATION               = "location";
		public static final String TXT_KEYWORD            = "txtkeyword";
		public static final String USER_ID                = "user_id";
		public static final String ALL_LOCATION           = "alllocations";
	}
	
	
	/** Register GSM **/
	public static class RegisterGSM    {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/registration-gcm.php?token=@6sm@9re&action=insert";	
		public static final String LOGGIN_ID              = "loggingid";
		public static final String GSM_ID                 = "gsmid";
		public static final String API_STATUS             ="status";
		public static final String API_MESSAGE            ="message";
	}
	
	/** Delete Register GSM ID **/
	public static class DeleteRegisterGSM  {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/registration-gcm.php?token=@6sm@9re&action=delete";	
		public static final String LOGGIN_ID              = "loggingid";
		public static final String GSM_ID                 = "gsmid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Ads Detail 
	 * */
	public static class AdsDetail {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/adsdetailpage.php?token=@6sm@9re";
		public static final String PROJECT_ID             = "projectid";
		public static final String BROKER_ID              = "brokerid";
		public static final String IMAGES                 = "images";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		
	}
	
	/**
	 * Filter Ads Listing 
	 * */
	public class FilterAdsListing {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/ads.php?token=@6sm@9re";
		public static final String PRICE_RANGE            = "pricerange";
		public static final String TXT_KEYWORD            = "txtkeyword";
		public static final String CMB_AREA1              = "cmbarea1";
		public static final String BUILDER_ID             = "builderid";
		public static final String BUILDER_NAME           = "buildername";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		
	}
	
	/**
	 * Delete Prefer Broker 
	 * */
	public class DeletePrefereBroker {
		public static final String URL                    = "http://propertyviaonline.com/ws/findagent.php?token=@6sm@9re&action=delete";
		public static final String AGENT_ID  	          = "agentid";
		public static final String PREFBROKER_ID          = "prefbrokerid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
		
	}
	
	/**
	 * Group list
	 * */
	public class GroupList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=list";
		public static final String AGENT_ID               = "agentid";
		public static final String PAGE		              = "page";
		public static final String PGID		              = "pgid";
		public static final String GROUP_NAME             = "groupname";
		public static final String OWNER_BROKER_ID        = "ownerbrokerid";
		public static final String MEMBER_COUNT           = "membercount";
		public static final String FIRST_NAME	          = "firstname";
		public static final String BROKER_ID	          = "brokerid";
		public static final String LAST_NAME	          = "lastname";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * Add group
	 * */
	public class AddGroup {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=add";
		public static final String AGENT_ID               = "agentid";
		public static final String GROUP_NAME             = "groupname";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Add group member service
	 * */
	public class AddGroupMember {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=addprefbroker";
		public static final String AGENT_ID		          = "agentid";
		public static final String PREF_GROUP_ID          = "prefgroupid";
		public static final String PREF_BROKER_ID         = "prefbrokerid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}

	
	/**
	 * Delete group
	 * */
	public class DeleteGroup {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=delete";
		public static final String AGENT_ID               = "agentid";
		public static final String PGID			          = "pgid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	
	/**
	 * Edit group
	 * */
	public class EditGroup {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=edit";
		public static final String AGENT_ID               = "agentid";
		public static final String PGID			          = "pgid";
		public static final String GROUP_NAME	          = "groupname";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * List of all broker in a group
	 * */
	public class BrokerListInGroup {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=listbroker";
		public static final String AGENT_ID               = "agentid";
		public static final String PGID			          = "pgid";
		public static final String BROKER_ID	          = "brokerid";
		public static final String FIRST_NAME	          = "firstname";
		public static final String LAST_NAME	          = "lastname";
		public static final String COMPANY_NAME           = "companyname";
		public static final String LOGO_LINK	          = "logolink";
		public static final String PHOTO_LINK	          = "photolink";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Delete a broker from group
	 * */
	public class DeleteBrokerFromGroup {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/pgall.php?token=@6sm@9re&action=deletebroker";
		public static final String AGENT_ID               = "agentid";
		public static final String PREF_BROKER_ID         = "prefbrokerid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Taluka list webservice
	 * */
	public class TalukaList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/taluko.php?token=@6sm@9re";
		public static final String TALUKA_ID              = "talukoid";
		public static final String TITLE			      = "title";
		public static final String DISTRICT_ID 		      = "districtid";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Dp map list webservice
	 * */
	public class DpMapList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/dp.php?token=@6sm@9re";
		public static final String TALUKA_ID              = "talukoid";
		public static final String DP_ID			      = "dpid";
		public static final String TITLE			      = "title";
		public static final String DP_FILE_PATH		      = "dpfilepath";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Gdcr list webservice
	 * */
	public class GdcrList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/gdcr.php?token=@6sm@9re";
		public static final String GDCR_ID	              = "gdcrid";
		public static final String GDCR_TITLE		      = "gdcrtitle";
		public static final String GDCR_PATH		      = "gdcrpath";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Mojegam list webservice
	 * */
	public class MojegamList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/mojegam.php?token=@6sm@9re";
		public static final String TALUKA_ID              = "talukoid";
		public static final String MOJEGAM_ID		      = "mojegamid";
		public static final String TITLE			      = "title";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Village list webservice
	 * */
	public class VillageList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/village.php?token=@6sm@9re";
		public static final String MOJEGAM_ID		      = "mojegamid";
		public static final String TITLE			      = "title";
		public static final String VILLAGE_FILE_PATH	  = "villagefilepath";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * Jantri list webservice
	 * */
	public class Jantri {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/jantri.php?token=@6sm@9re&";
		public static final String VILLAGE_ID		      = "villageid";
		public static final String MOJEGAM_ID		      = "mojegamid";
		public static final String TITLE			      = "title";
		public static final String JANTRI_FILE_PATH	      = "jantrifilepath";
		public static final String API_STATUS             = "status";
		public static final String API_MESSAGE            = "message";
	}
	
	/**
	 * URl for loan calculator 
	 * @author hirenk
	 *
	 */
	public class LoanCalculator {
		public static final String URL                    = "http://www.propertyviaonline.com/loan-calc.php"; 
	}
	
	 /** URl for area unit calculator 
	 * @author hirenk
	 *
	 */
	public class AreaUnitCalculator {
		public static final String URL                    = "http://www.propertyviaonline.com/includes/new/area-calc.php"; 
	}
	
	/** 
	 * URl for revenue record 
	 * @author hirenk
	 *
	 */
	public class RevenueRecord {
		public static final String URL                    = "https://anyror.gujarat.gov.in/"; 
	}
	
	/** 
	 * URl for revenue record 
	 * @author hirenk
	 *
	 */
	public class GovtCirculars {
		public static final String URL                    = "http://revenuedepartment.gujarat.gov.in/circulars-and-notifictation"; 
	}
	
	
	 
	
	/** 
	 * @author hirenk
	 *
	 */
	public class ShortlistedPropertyList {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty_short.php?token=@6sm@9re&action=list";
		public static final String USER_ID 		          = "user_id";
		public static final String PAGE 		          = "page";
		public static final String SHORT_LISTED           = "ShortListed";
	}
	/** 
	 * @author hirenk
	 *
	 */
	public class ShortlistedPropertyAdd {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty_short.php?token=@6sm@9re&action=add";
		public static final String PROPERTY_ID			  = "propertyid";
		public static final String USER_ID 		          = "user_id";
		public static final String PAGE 		          = "page";
	}
	
	/** 
	 * @author hirenk
	 *
	 */
	public class ShortlistedPropertyDelete {
		public static final String URL                    = "http://www.propertyviaonline.com/ws/myproperty_short.php?token=@6sm@9re&action=delete";
		public static final String SHORTLISTED_ID		  = "shortlistid";
		public static final String USER_ID 		          = "user_id";
	}
	
	
}
