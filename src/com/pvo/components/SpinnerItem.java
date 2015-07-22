
package com.pvo.components;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class SpinnerItem {
	private static Map<String, String> floorList = new LinkedHashMap<String, String>();
	private static Map<String, String> buildingTypeList = new LinkedHashMap<String, String>();
	private static Map<String, Integer> publishingOptionList = new LinkedHashMap<String, Integer>();
	private static Map<String, String> propertyOccupacyList = new LinkedHashMap<String, String>();
	private static Map<String, Integer> furnishOptionList = new LinkedHashMap<String, Integer>();
	private static Map<String, String> naStatusList = new LinkedHashMap<String, String>();
	private static Map<String, String> totalPriceList = new LinkedHashMap<String, String>();
	private static Map<String, String> rentList = new LinkedHashMap<String, String>();
	private static Map<String, String> bedList = new LinkedHashMap<String, String>();
	private static Map<String, String> onRoadList = new LinkedHashMap<String, String>();
	private static Map<String, String> propertyTypeOptionList = new LinkedHashMap<String, String>();
	private static Map<String, String> propertyForSaleRentList = new LinkedHashMap<String, String>();
	private static Map<String, String> propertyPurposeList = new LinkedHashMap<String, String>();
	
	private static Map<String, String> addPropFloorList = new LinkedHashMap<String, String>();
	private static Map<String, Integer> addPropBuildingTypeList = new LinkedHashMap<String, Integer>();
	private static Map<String, String> addPropBedList = new LinkedHashMap<String, String>();
	private static Map<String, Integer> addPropFurnishOptionList = new LinkedHashMap<String, Integer>();
	private static Map<String, String> addPropZoneList = new LinkedHashMap<String, String>();
	private static Map<String, String> addPropNaStatusList = new LinkedHashMap<String, String>();
	private static Map<String, String> addPropPurposeList = new LinkedHashMap<String, String>();
	private static Map<String, String> addPropWhomToLetShopList = new LinkedHashMap<String, String>();
	private static Map<String, String> addPropWhomToLetFlatList = new LinkedHashMap<String, String>();
	
	private static Map<String, String> fiindAgentCriteria = new LinkedHashMap<String, String>();
	
	private static Map<String, String> addReqFloorList = new LinkedHashMap<String, String>();
	
	//search Property 
	private static Map<String, String> searchPropBuildingTypeList = new LinkedHashMap<String, String>();
	private static Map<String, String> searchPropPurposeList = new LinkedHashMap<String, String>();
	private static Map<String, String> searchPropFurnishStatusList = new LinkedHashMap<String, String>();
	
	//Project budge
	private static Map<String, String> projectBudget = new LinkedHashMap<String, String>();
	
	
	static {
		
		/** project budget name/value pair **/
		projectBudget.put("Between 1 lac to 10 lac", "startprice >= 100000 and endprice <= 1000000");
		projectBudget.put("Between 11 lac to 20 lac", "startprice >= 1100000 and endprice <= 2000000");
		projectBudget.put("Between 21 lac to 30 lac", "startprice >= 2100000 and endprice <= 3000000");
		projectBudget.put("Between 31 lac to 40 lac", "startprice >= 3100000 and endprice <= 4000000");
		projectBudget.put("Between 41 lac to 50 lac", "startprice >= 4100000 and endprice <= 5000000");
		projectBudget.put("Between 51 lac to 60 lac", "startprice >= 5100000 and endprice <= 6000000");
		projectBudget.put("Between 61 lac to 70 lac", "startprice >= 6100000 and endprice <= 7000000");
		projectBudget.put("Between 71 lac to 80 lac", "startprice >= 7100000 and endprice <= 8000000");
		projectBudget.put("Between 81 lac to 90 lac", "startprice >= 800000 and endprice <= 9000000");
		projectBudget.put("91 lac or above", "startprice >= 9100000");
		
		/** Search Property Furnish status name / value pair **/
		searchPropFurnishStatusList.put("Any", "Any");
		searchPropFurnishStatusList.put("Unfurnished", "0");
		searchPropFurnishStatusList.put("Semifurnished", "2");
		searchPropFurnishStatusList.put("Fully Furnished", "3");
		
		/** Search property Purpose name value pair Commercial/residential**/
		searchPropPurposeList.put("Any", "");
		searchPropPurposeList.put("Commercial", "Commercial");
		searchPropPurposeList.put("Residential", "Residential");
		
		/** Add Property Purpose  List and Add Requirement  
		 * Edit My property Purpose
		 ***/
		addPropPurposeList.put("Any", "Any");
		addPropPurposeList.put("Commercial", "Commercial");
		addPropPurposeList.put("Residential", "Residential");
		
		/** Search Property Building type name value pair  **/
		searchPropBuildingTypeList.put("Any", "Any");
		searchPropBuildingTypeList.put("Low Rise", "1");
		searchPropBuildingTypeList.put("High Rise", "0");
		
		
		/** Property Purpose List **/
		propertyPurposeList.put("Any", "Any");
		propertyPurposeList.put("Commercial", "Commercial");
		propertyPurposeList.put("Residential", "Residential");
		
		
		
		/** Property For sale Rent **/
		propertyForSaleRentList.put("Select", "");
		propertyForSaleRentList.put("Rent", "Rent");
		propertyForSaleRentList.put("Sale", "Sale");
		
		/** Find Agent Spinner Key Value **/
		fiindAgentCriteria.put("Name", "name");
		fiindAgentCriteria.put("Phone Number", "phonenumber");
		fiindAgentCriteria.put("Area Deals in", "areadealsin");
		fiindAgentCriteria.put("Property Type Deals in", "propertytypedealin");
		//fiindAgentCriteria.put("Address", "address");
		//fiindAgentCriteria.put("Website", "website");
		//fiindAgentCriteria.put("Phone (M)", "phonem");
		//fiindAgentCriteria.put("Phone (O)", "phoneo");
		//fiindAgentCriteria.put("Email", "email");
		
		/** Add Property Whom to let Spinner for Shop **/
		addPropWhomToLetShopList.put("Any", "Any");
		addPropWhomToLetShopList.put("Company Only", "Compnay Only");
		addPropWhomToLetShopList.put("Privates", "Privates");
		
		/** Add Property Whom to let Spinner for Flat 
		 * Edit Property
		 ***/
		addPropWhomToLetFlatList.put("Any", "Any");
		addPropWhomToLetFlatList.put("Company Employee only", "Company Employee only");
		addPropWhomToLetFlatList.put("Family only", "Family only");
		addPropWhomToLetFlatList.put("Company Employee/Family only", "Company Employee/Family only");
		addPropWhomToLetFlatList.put("Bachelors only", "Bachelors only");
		addPropWhomToLetFlatList.put("Other", "Other");
		
		
		/** Add Property Zone List **/
		addPropZoneList.put("R-1", "R-1");
		addPropZoneList.put("R-2", "R-2");
		addPropZoneList.put("R-3", "R-3");
		addPropZoneList.put("R-4", "R-4");
		addPropZoneList.put("R-5", "R-5");
		addPropZoneList.put("R-6", "R-6");
		addPropZoneList.put("R-7", "R-7");
		addPropZoneList.put("Agriculture", "Agriculture");
		addPropZoneList.put("Industrial", "Industrial");
		addPropZoneList.put("SEZ", "SEZ");
		addPropZoneList.put("Do Not Know", "Do Not Know");
		
		//Road Touch used in add Property 
		onRoadList.put("Do no know", "");
		onRoadList.put("10 foot", "10");
		onRoadList.put("20 foot", "20");
		onRoadList.put("30 foot", "30");
		onRoadList.put("40 foot", "40");
		onRoadList.put("50 foot", "50");
		onRoadList.put("60 foot", "60");
		onRoadList.put("70 foot", "70");
		onRoadList.put("80 foot", "80");
		onRoadList.put("90 foot", "90");
		onRoadList.put("100 foot", "100");
		onRoadList.put("120 foot", "120");
		onRoadList.put("132 foot", "132");
		onRoadList.put("200 foot", "200");
		onRoadList.put("300 foot", "300");
		
		
		//Min Max Bed Key Valu Pai For Fill the Bed Spinner
		bedList.put("Any", "");
		bedList.put("0", "0");
		bedList.put("1", "1");
		bedList.put("2", "2");
		bedList.put("3", "3");
		bedList.put("4", "4");
		bedList.put("5", "5");
		bedList.put("6", "6");
		bedList.put("7", "7");
		bedList.put("8", "8");
		bedList.put("9", "9");
		bedList.put("10", "10");
		bedList.put("11", "11");
		
		//Add Property Bed Key Value Pair For Fill the Bed Spinner and Add Requirement
		addPropBedList.put("0", "0");
		addPropBedList.put("1", "1");
		addPropBedList.put("2", "2");
		addPropBedList.put("3", "3");
		addPropBedList.put("4", "4");
		addPropBedList.put("5", "5");
		addPropBedList.put("6", "6");
		addPropBedList.put("7", "7");
		addPropBedList.put("8", "8");
		addPropBedList.put("9", "9");
		addPropBedList.put("10", "10");
		addPropBedList.put("11", "11");
		
		
		//Min/max Rent Key Value Pair For fill the Rent Spinner
		rentList.put("Select","0.00");
		rentList.put("1000","1000.00");
		rentList.put("5000","5000.00");
		rentList.put("10000","10000.00");
		rentList.put("20000","20000.00");
		rentList.put("25000","25000.00");
		rentList.put("30000","30000.00");
		rentList.put("40000","40000.00");
		rentList.put("50000","50000.00");
		rentList.put("60000","60000.00");
		rentList.put("70000","70000.00");
		rentList.put("80,000","80000.00");
		rentList.put("90,000","90000.00");
		rentList.put("1,00,000","100000.00");
		rentList.put("1,20,000","120000.00");
		rentList.put("1,40,000","140000.00");
		rentList.put("1,60,000","160000.00");
		rentList.put("1,80,000","180000.00");
		rentList.put("2,00,000","200000.00");
		rentList.put("2,25,000","225000.00");
		rentList.put("2,50,000","250000.00");
		rentList.put("2,75,000","275000.00");
		rentList.put("3,00,000","300000.00");
		rentList.put("3,50,000","350000.00");
		rentList.put("4,00,000","400000.00");
		rentList.put("4,50,000","450000.00");
		rentList.put("5,00,000","500000.00");
		rentList.put("5,00,000 and Above","5,00,000 and Above");
		
		
		//Min/Max Total Price Key Value for Fill the Spinner 
		totalPriceList.put("Select","0.00");
		totalPriceList.put("5 Lac","500000.00");
		totalPriceList.put("10 Lac","1000000.00");
		totalPriceList.put("15 Lac","1500000.00");
		totalPriceList.put("20 Lac","2000000.00");
		totalPriceList.put("25 Lac","2500000.00");
		totalPriceList.put("30 Lac","3000000.00");
		totalPriceList.put("35 Lac","3500000.00");
		totalPriceList.put("40 Lac","4000000.00");
		totalPriceList.put("50 Lac","5000000.00");
		totalPriceList.put("60 Lac","6000000.00");
		totalPriceList.put("70 Lac","7000000.00");
		totalPriceList.put("80 Lac","8000000.00");
		totalPriceList.put("90 Lac","9000000.00");
		totalPriceList.put("1 Crore","10000000.00");
		totalPriceList.put("1.2 Crore","12000000.00");
		totalPriceList.put("1.4 Crore","14000000.00");
		totalPriceList.put("1.6 Crore","16000000.00");
		totalPriceList.put("1.8 Crore","18000000.00");
		totalPriceList.put("2 Crore","20000000.00");
		totalPriceList.put("2.25 Crore","22500000.00");
		totalPriceList.put("2.50 Crore","25000000.00");
		totalPriceList.put("2.75 Crore","27500000.00");
		totalPriceList.put("3 Crore","30000000.00");
		totalPriceList.put("3.5 Crore","35000000.00");
		totalPriceList.put("4 Crore","40000000.00");
		totalPriceList.put("4.5 Crore","45000000.00");
		totalPriceList.put("5 Crore","50000000.00");
		totalPriceList.put("5 Crore and Above","5 Crore and Above");
		
		
		//Floor List array used to fill floor Spinner
		floorList.put("Any Floor","");
		floorList.put("Will tell later","-2");
		floorList.put("Basement", "-1");
		floorList.put("Ground Floor","0");
		floorList.put("1", "1");
		floorList.put("2", "2");
		floorList.put("3", "3");
		floorList.put("4", "4");
		floorList.put("5", "5");
		floorList.put("6", "6");
		floorList.put("7", "7");
		floorList.put("8", "8");
		floorList.put("9", "9");
		floorList.put("10", "10");
		floorList.put("11", "11");
		floorList.put("Pent House", "13");
		
		//Floor List array used to fill add Property floor Spinner 
		addPropFloorList.put("Will tell you later","-2");
		addPropFloorList.put("Basement", "-1");
		addPropFloorList.put("Ground Floor","0");
		addPropFloorList.put("1", "1");
		addPropFloorList.put("2", "2");
		addPropFloorList.put("3", "3");
		addPropFloorList.put("4", "4");
		addPropFloorList.put("5", "5");
		addPropFloorList.put("6", "6");
		addPropFloorList.put("7", "7");
		addPropFloorList.put("8", "8");
		addPropFloorList.put("9", "9");
		addPropFloorList.put("10", "10");
		addPropFloorList.put("11", "11");
		addPropFloorList.put("12", "12");
		addPropFloorList.put("13", "13");
		addPropFloorList.put("14", "14");
		addPropFloorList.put("15", "15");
		addPropFloorList.put("16", "16");
		addPropFloorList.put("17", "17");
		addPropFloorList.put("18", "18");
		addPropFloorList.put("19", "19");
		addPropFloorList.put("20", "20");
		addPropFloorList.put("21", "21");
		addPropFloorList.put("22", "22");
		addPropFloorList.put("23", "23");
		addPropFloorList.put("24", "24");
		addPropFloorList.put("25", "25");
		addPropFloorList.put("26", "26");
		addPropFloorList.put("27", "27");
		addPropFloorList.put("28", "28");
		addPropFloorList.put("29", "29");
		addPropFloorList.put("30", "30");
		addPropFloorList.put("31", "31");
		addPropFloorList.put("32", "32");
		addPropFloorList.put("33", "33");
		addPropFloorList.put("34", "34");
		addPropFloorList.put("35", "35");
		addPropFloorList.put("36", "36");
		addPropFloorList.put("37", "37");
		addPropFloorList.put("38", "38");
		addPropFloorList.put("39", "39");
		addPropFloorList.put("Pent House", "40");
		
		//Floor list array used to fill add requirement floor Spinner
		addReqFloorList.put("Select","");
		addReqFloorList.put("Will tell you later","-2");
		addReqFloorList.put("Basement", "-1");
		addReqFloorList.put("Ground Floor","0");
		addReqFloorList.put("1", "1");
		addReqFloorList.put("2", "2");
		addReqFloorList.put("3", "3");
		addReqFloorList.put("4", "4");
		addReqFloorList.put("5", "5");
		addReqFloorList.put("6", "6");
		addReqFloorList.put("7", "7");
		addReqFloorList.put("8", "8");
		addReqFloorList.put("9", "9");
		addReqFloorList.put("10", "10");
		addReqFloorList.put("11", "11");
		addPropFloorList.put("12", "12");
		addPropFloorList.put("13", "13");
		addPropFloorList.put("14", "14");
		addPropFloorList.put("15", "15");
		addPropFloorList.put("16", "16");
		addPropFloorList.put("17", "17");
		addPropFloorList.put("18", "18");
		addPropFloorList.put("19", "19");
		addPropFloorList.put("20", "20");
		addPropFloorList.put("21", "21");
		addReqFloorList.put("Pent House", "22");
		
		//Building Type Key/value Pair to fill Building type Spinner used in add requirement
		buildingTypeList.put("Any", "2");
		buildingTypeList.put("Low Rise", "1");
		buildingTypeList.put("High Rise", "0");
		
		
		
		
		//Add Property Building Type Key/value Pair to fill Building type Spinner
		addPropBuildingTypeList.put("Low Rise", 1);
		addPropBuildingTypeList.put("High Rise", 0);
		
		//Publishing Option Key/Value Pair to Fill Pulbishing Option Spinner used in Add My property
		publishingOptionList.put("Area",1);
		publishingOptionList.put("Preferred only",2);
		publishingOptionList.put("Group",3);
		publishingOptionList.put("All Broker",4);
		
		//Property Occupacy Key/Value Pair to Fill Property Occupacy Spinner used in (1)Add My Property  
		propertyOccupacyList.put("Select", "0");
		propertyOccupacyList.put("Yes", "1");
		propertyOccupacyList.put("No", "2");
		
		//Furnish Option Key/Value Pair to Fill Furnish Option Spinner used for Add Requirement
		furnishOptionList.put("Any", 1);
		furnishOptionList.put("Unfurnished", 0);
		furnishOptionList.put("Semifurnished", 2);
		furnishOptionList.put("Fully Furnished", 3);
		
		
		//Furnish Option Key/Value Pair to Fill Furnish Option Spinner(AddMyProperty)
		addPropFurnishOptionList.put("Unfurnished", 0);
		addPropFurnishOptionList.put("Semifurnished", 2);
		addPropFurnishOptionList.put("Fully Furnished", 3);
		
		
		/** Flat Property type Key Value Pair **/
		propertyTypeOptionList.put(" Select ","");
		propertyTypeOptionList.put(" Flat & its related ","");
		propertyTypeOptionList.put("Flat","Flat");
		
		/** Shop Property type Key Value Pair **/
		propertyTypeOptionList.put(" Shop & its related ", "");
		propertyTypeOptionList.put("All Shop", "all shop");
		propertyTypeOptionList.put("Shop", "Shop");
		propertyTypeOptionList.put("Show Rooms", "Shop");
		propertyTypeOptionList.put("Commercial Bunglow", "Shop");
		propertyTypeOptionList.put("Commercial Flat", "Shop");
		propertyTypeOptionList.put("Office", "Shop");
		propertyTypeOptionList.put("Corporate House", "Shop");
		propertyTypeOptionList.put("Godown", "Shop");
		propertyTypeOptionList.put("Shades", "Shop");
		propertyTypeOptionList.put("Factory", "Shop");
		
		/** Bunglow Property type Key Value Pair **/
		propertyTypeOptionList.put(" Bunglow & its related ", "");
		propertyTypeOptionList.put("All Bunglow", "all bunglow");
		propertyTypeOptionList.put("Bunglow", "Bunglow");
		propertyTypeOptionList.put("Twin", "Bunglow");
		propertyTypeOptionList.put("Row House", "Bunglow");
		propertyTypeOptionList.put("Tenament", "Bunglow");
		propertyTypeOptionList.put("Individual", "Bunglow");
		propertyTypeOptionList.put("Independent Bunglows", "Bunglow");
		propertyTypeOptionList.put("Farm House(Bunglow)", "Bunglow");
		
		/** Plot Property type Key Value Pair **/
		propertyTypeOptionList.put(" Plot & its related ", "");
		propertyTypeOptionList.put("All Plots", "all plots");
		propertyTypeOptionList.put("Plot", "Plot");
		propertyTypeOptionList.put("Society Plot", "Plot");
		propertyTypeOptionList.put("Society Sub Plot", "Plot");
		propertyTypeOptionList.put("F.P.Plot", "Plot");
		propertyTypeOptionList.put("Land Of T.P", "Plot");
		propertyTypeOptionList.put("Land of Agriculture", "Plot");
		propertyTypeOptionList.put("Land of industrial", "Plot");
		propertyTypeOptionList.put("Development Society", "Plot");
		propertyTypeOptionList.put("Farm House(Plot)", "Plot");
		propertyTypeOptionList.put("Individual Plot", "Plot");
		propertyTypeOptionList.put("Gam Tal Land", "Plot");
		propertyTypeOptionList.put("GIDC Plot", "Plot");
		propertyTypeOptionList.put("SEZ Land", "Plot");
		
		
		/** NA Status Key/Value Pair of the Plot for sale and Rent **/
		naStatusList.put("Any","");
		naStatusList.put("Yes", "1");
		naStatusList.put("No", "0");
		
		/** Add Property NA Status Key/Value Pair of the Plot for sale and Rent **/
		addPropNaStatusList.put("Yes", "1");
		addPropNaStatusList.put("No", "0");
	}
	
	
	//Spinner get the key/value pair For the project budget
	public static Map getProjectBudgetList() {
		return projectBudget;

	}

	//Spinner return key from value Low rise / high rise
	public static String getProjectBudgetKey(String value) {
		return (String) MapUtils.invertMap(
				SpinnerItem.getProjectBudgetList()).get(value);
	}
	
	//Spinner get the key/value pair For the search Property Furnish status type Any,unfurnished,SemiFurnished,Fully Furnished Spinner
	public static Map getSerchPropFurnishStatusTypeList() {
		return searchPropFurnishStatusList;

	}

	//Spinner return key from value Low rise / high rise
	public static String getSearchPropFurnishStatusTypeKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getSerchPropFurnishStatusTypeList()).get(value);
	}
	
	
	//Spinner get the key/value pair For the search Property purpose type commercial / Residential Spinner
	public static Map getSerchPropPurposeTypeList() {
		return searchPropPurposeList;

	}

	//Spinner return key from value Low rise / high rise
	public static String getSearchPropPurposeTypeKey(String value) {
		return (String) MapUtils.invertMap(
				SpinnerItem.getSerchPropPurposeTypeList()).get(value);
	}

	
	 //  Spinner get the key/value pair For the search Property Building type Low rise,high Rise Spinner
	public static Map getSerchPropBuildingTypeList() {
		return searchPropBuildingTypeList;

	}

		//Spinner return key from value Low rise / high rise
		public static String getSearchPropBuildingTypeKey(String value) {
			return (String) MapUtils.invertMap(SpinnerItem.getSerchPropBuildingTypeList()).get(value);
		}
	
	 //  Spinner get the key/value pair For the Purpose Spinner
	public static Map getPurposeList() {
		return propertyPurposeList;

	}

	//Spinner return key from value Commercial / residential
	public static String getPurposeKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getPurposeList()).get(value);
		
	}
	
	 // Spinner get the key/value pair For the Criteria Spinner
	public static Map getSaleRentList() {
		return propertyForSaleRentList;

	}

	// Spinner return key from value Sale / Rent
	public static String getSaleRentKey(String value) {
		return (String) MapUtils.invertMap(
				SpinnerItem.getSaleRentList()).get(value);
	}
	
	
	   // find Agent Spinner get the key/value pair For the Criteria Spinner
		public static Map getFindAgentList() {
			return fiindAgentCriteria;

		}

		// Add Property Whom To let Shop Spinner return key from value
		public static String getFindAgentKey(String value) {
			return (String) MapUtils.invertMap(
					SpinnerItem.getFindAgentList()).get(value);
		}			

	
	
	// Add Property get the key/value pair For the Whom to let Shop Spinner
	public static Map getAddPropWhomToLetShopList() {
		return addPropWhomToLetShopList;

	}

	// Add Property Whom To let Shop Spinner return key from value
	public static String getAddWhomToLetShopListKey(String value) {
		return (String) MapUtils.invertMap(
				SpinnerItem.getAddPropWhomToLetShopList()).get(value);
	}			

	// Add Property get the key/value pair For the Whom to let Flat Spinner
	@SuppressWarnings("rawtypes")
	public static Map getAddPropWhomToLetFlatList() {
		return addPropWhomToLetFlatList;

	}

	// Add Property Whom To let Flat Spinner return key from value
	public static String getAddWhomToLetFlatListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getAddPropWhomToLetFlatList()).get(value);
	}			

	
	
	
	//Add Property get the key/value pair  For  the Purpose
	@SuppressWarnings("rawtypes")
	public static Map getAddPropPurposeList(){
	 	return addPropPurposeList;
	}

	
	//Add Property get the key/value pair  For  the Zone
	@SuppressWarnings("rawtypes")
	public static Map getAddPropZoneList(){
		return addPropZoneList;
	}

	
	//get the key/value pair  For  the onRoad Touch
	@SuppressWarnings("rawtypes")
	public static Map getOnRoadList(){
		return onRoadList;
	}
	
	
	//get the key/value pair  For  the min/max bed 
	@SuppressWarnings("rawtypes")
	public static Map getBedList(){
		return bedList;
	}
		
	//Add property get the key/value pair  For  the min/max bed 
	@SuppressWarnings("rawtypes")
	public static Map getAddPropBedList(){
		return addPropBedList;
	}
	
	//get the key/value pair of the Total Price For sale Property
	public static Map getRentList(){
		return rentList;
	}
	
	//return key from value,this method is used to get the key and display into the EditMyRequirement  
	public static String getRentListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getRentList()).get(value);
	}
	
	//get the key/value pair of the Total Price For sale Property
	public static Map getTotalPriceList(){
			return totalPriceList;
	}
	
	//return key from value,this method is used to get the key and display into the EditMyRequirement  
	public static String getTotalPriceListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getTotalPriceList()).get(value);
	}
	
	//get the key/value pair of the Nastatus
	public static Map getNaStatusList(){
		return naStatusList;
				
	}
	
	// Add Property get the key/value pair of the Nastatus
		public static Map getAddPropNaStatusList(){
			return addPropNaStatusList;
					
		}
		
	//return key from value,this method is used to get the key and display into the property detail and requirement detail
	public static String getAddPropNaStatusListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getAddPropNaStatusList()).get(value);
	}
		
	
	//get the key/value List of Floor
	public static Map getFloorList() {
		return floorList;
	}
	
	//get the key/value List of  Add Property Floor
		public static Map getAddPropFloorList() {
			return addPropFloorList;
		}
	
	//get the key/value list of Building type
	public static Map getBuildingTypeList(){
		return buildingTypeList;
	}
	
	
	//get the key/value list of Building type
		public static Map getAddPropBuildingTypeList(){
			return addPropBuildingTypeList;
		}

	//get the key/value list of Publishing Option
	public static Map getPublishinOptionList(){
		return publishingOptionList;
	}
	
	//get the key/value list of Property Occupacy 
	public static Map getPropertyOccupacyList(){
		return propertyOccupacyList;
	}
	//return key from value
	public static String getPropertyOccupacyListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getPropertyOccupacyList()).get(value);
	}
	
	
	//get the key/value list of Property Occupacy 
	public static Map getFurnishOptionList(){
		return furnishOptionList;
	}
	
	
	//Add Property get the key/value list of Property Occupacy 
		public static Map getAddPropFurnishOptionList(){
			return addPropFurnishOptionList;
		}
	
/** get the key/value pair list of Property Type  **/
	
	public static Map getPropertyTypeOptionList(){
		return propertyTypeOptionList;
	}
	
	//return key from value,this method is used to get the key and display into the property detail and requirement detail
	public static String getFloorListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getFloorList()).get(value);
	}
	
	//return key from value,this method is used to get the key and display into the property detail and requirement detail
		public static String getAddPropFloorListKey(String value) {
			return (String) MapUtils.invertMap(SpinnerItem.getAddPropFloorList()).get(value);
		}
		
	
		//Add Requirement get the key/value pair  For  the Purpose
				public static Map getAddReqFloorList(){
							return addReqFloorList;
									
				}
		//return key from value Add Requirement	
		public static String getAddReqFloorListKey(String value) {
			return (String) MapUtils.invertMap(SpinnerItem.getAddReqFloorList()).get(value);
		}
		
	
	//return key from value
	public static String getBuildingTypeListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getBuildingTypeList()).get(value);
	}
	
	//Add Property Building type return key from value
		public static String getAddPropBuildingTypeListKey(Integer value) {
			return (String) MapUtils.invertMap(SpinnerItem.getAddPropBuildingTypeList()).get(value);
		}
	
	//return key from value
	public static String getFurnishOptionListKey(Integer value) {
			return (String) MapUtils.invertMap(SpinnerItem.getFurnishOptionList()).get(value);
	}
	
	//return key from value
		public static String getAddPropFurnishOptionListKey(Integer value) {
				return (String) MapUtils.invertMap(SpinnerItem.getAddPropFurnishOptionList()).get(value);
		}
	
	//return key from value
		public static String getPublishingOptionListKey(Integer value) {
				return (String) MapUtils.invertMap(SpinnerItem.getPublishinOptionList()).get(value);
		}
	
	
	
	
	
	
	
	/** Return Property type key from value **/
	public static String getPropertyTypeListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getPropertyTypeOptionList()).get(value);
  }
	
	/** Return OnRoad key from value **/
	public static String getOnRoadListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getOnRoadList()).get(value);
  }
	
	/** Return Na Status key from value **/
	public static String getNaStatusListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getNaStatusList()).get(value);
  }
	
	/** Return Zone key from value **/
	public static String getAddPropZoneListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getAddPropZoneList()).get(value);
  }
	/** Return Bed key from value **/
	public static String getBedListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getBedList()).get(value);
  }
	
	/** Return Purpose key from value **/
	public static String getAddPropPurposeListKey(String value) {
		return (String) MapUtils.invertMap(SpinnerItem.getAddPropPurposeList()).get(value);
  }
	
}
