
package z.com.pvo.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class SpinnerItem {
	
	/*
	 * 1) Residential property type related
	 */
	private static Map<String, String> residentialPropType = new LinkedHashMap<String, String>();
	
	/*
	 * 2) commercial property type
	 */
	private static Map<String, String> commercialPropType = new LinkedHashMap<String, String>();
	
	
	static {
		
		/*
		 * 1) Residential property type name value pair
		 */
		residentialPropType.put("Apartment","Flat");
		residentialPropType.put("House/Villa","Bunglow");
		residentialPropType.put("plot/Land","Plot");
		residentialPropType.put("Agri./Farm Land","Plot");
		
		/*
		 * 2) commercial property type name value pair
		 */
		commercialPropType.put("Shop","Shop");
		commercialPropType.put("Showroom","Shop");
		commercialPropType.put("Office/Space","Shop");
		commercialPropType.put("Factory","Shop");
		commercialPropType.put("Warehouse","Shop");
		/*commercialPropType.put("Agri./Farm Land","Plot");*/
		commercialPropType.put("Industrial Land","Plot");
	}
	
	
	/*
	 * 1) get the key/value pair list of Property Type 
	 */
	public static Map getResidentialPropTypeVal(){
		return residentialPropType;
	}
	
	/*
	 * 2) get the key/value pair list of Property Type 
	 */
	public static Map getCommercialPropTypeVal(){
		return commercialPropType;
	}
	
}
