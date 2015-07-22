package com.pvo.json.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import z.com.pvo.util.ProjectUtility;
import android.content.Context;

//http://stackoverflow.com/questions/14219253/writing-json-file-and-read-that-file
public class CreateJsonArrayFileIntoCache {
	
	
	private static Boolean isPrint = true;
	private static String TAG = "CreateJsonArrayFileIntoCache";
	
	
     /**
      * Create state list json file into cache memory	
      * @param context
      */
	 public static void createAndWriteStateListFile(Context context) {
	        try {
	            JsonParser jsonParser = new JsonParser();
	        	File file = new File(context.getCacheDir(), "StateList.json");
	            try {
	                file.createNewFile();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        	FileWriter fileWriter = new FileWriter(file);
	        	fileWriter.write(jsonParser.getJSONFromUrl("http://propertyviaonline.com/ws/state.php?token=@6sm@9re"));
	        	fileWriter.flush();
	        	fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 /**
	  * Read state list json file From cache memory   
	  * @param context
	  * @return
	  */
	 public static JSONArray readStateListJsonData(Context context) {
	    	try {
	            JSONArray jsonArray = null ;;
	        	File f = new File(context.getCacheDir(), "StateList.json");
	            FileInputStream is = new FileInputStream(f);
	            int size = is.available();
	            byte[] buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            String mResponse = new String(buffer);
	            ProjectUtility.sys(isPrint, TAG, "StateList Json--> "+mResponse);
				jsonArray = new JSONArray(mResponse);
				
	            return jsonArray;
	        } catch (Exception e) {
	            e.printStackTrace();
	            ProjectUtility.sys(isPrint, TAG, "Exception in state list");
	            
	            //createAndWriteStateListFile(context);
	            //readStateListJsonData(context);
	        }
	        return null;
	    }
	 
	 
	 /**
      * Create area list json file into cache memory	
      * @param context
      */
	 public static void createAndWriteAreaListFile(Context context) {
	        try {
	            JsonParser jsonParser = new JsonParser();
	        	File file = new File(context.getCacheDir(), "AreaList.json");
	            try {
	                file.createNewFile();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        	
	        	FileWriter fileWriter = new FileWriter(file);
	        	fileWriter.write(jsonParser.getJSONFromUrl("http://www.propertyviaonline.com/ws/area.php?token=@6sm@9re"));
	        	fileWriter.flush();
	        	fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            ProjectUtility.sys(isPrint, TAG, "Exception in AreaList");
	        }
	    }
	    
	 /**
	  * Read state list json file From cache memory   
	  * @param context
	  * @return
	  */
	 public static JSONArray readAreaListJsonData(Context context) {
	    	try {
	            JSONArray jsonArray = null ;
	        	File f = new File(context.getCacheDir(), "AreaList.json");
	            FileInputStream is = new FileInputStream(f);
	            int size = is.available();
	            byte[] buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            String mResponse = new String(buffer);
				jsonArray = new JSONArray(mResponse);
	            return jsonArray;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JSONException e) {
				e.printStackTrace();
			}
	        return null;
	    }
	 
	 /**
      * Create TP scheme list json file into cache memory	
      * @param context
      */
	 public static void createAndWriteTPSchemeListFile(Context context) {
	        try {
	            JsonParser jsonParser = new JsonParser();
	        	File file = new File(context.getCacheDir(), "TPSchemeList.json");
	            try {
	                file.createNewFile();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        	
	        	FileWriter fileWriter = new FileWriter(file);
	        	fileWriter.write(jsonParser.getJSONFromUrl("http://www.propertyviaonline.com/ws/tpscheme.php?token=@6sm@9re"));
	        	fileWriter.flush();
	        	fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	 /**
	  * Read TP scheme list json file From cache memory   
	  * @param context
	  * @return
	  */
	 public static JSONArray readTPSchemeListJsonData(Context context) {
	        
	    	try {
	            JSONArray jsonArray = null ;;
	        	File f = new File(context.getCacheDir(), "TPSchemeList.json");
	            FileInputStream is = new FileInputStream(f);
	            int size = is.available();
	            byte[] buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            String mResponse = new String(buffer);
	           
	            //System.out.println("TP Scheme Response from Json file==> "+mResponse);
	            ProjectUtility.sys(isPrint, TAG, "TPSchemeList Json--> "+mResponse);
				jsonArray = new JSONArray(mResponse);
				
	            return jsonArray;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JSONException e) {
				e.printStackTrace();
			}
	        return null;
	    }
	 
	 /**
      * Create TP scheme list json file into cache memory	
      * @param context
      */
	 public static void createAndWriteFacilityListFile(Context context) {
	        try {
	            JsonParser jsonParser = new JsonParser();
	        	File file = new File(context.getCacheDir(), "FacilityList.json");
	            try {
	                file.createNewFile();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        	
	        	FileWriter fileWriter = new FileWriter(file);
	        	fileWriter.write(jsonParser.getJSONFromUrl("http://www.propertyviaonline.com/ws/amenities.php?token=@6sm@9re"));
	        	fileWriter.flush();
	        	fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	 /**
	  * Read TP scheme list json file From cache memory   
	  * @param context
	  * @return
	  */
	 public static JSONArray readFacilityListJsonData(Context context) {
	    	try {
	            JSONArray jsonArray = null ;;
	        	File f = new File(context.getCacheDir(), "FacilityList.json");
	            FileInputStream is = new FileInputStream(f);
	            int size = is.available();
	            byte[] buffer = new byte[size];
	            is.read(buffer);
	            is.close();
	            String mResponse = new String(buffer);
				jsonArray = new JSONArray(mResponse);
				
	            return jsonArray;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (JSONException e) {
				e.printStackTrace();
			}
	        return null;
	    }
}
