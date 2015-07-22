package z.com.pvo.newAdapter;

public class ZAminitiesItem {

	int iconID;
	String iconName;
	String imagePath;
	String amenitiesId;
	
	
	
	public String getImagePath() {
		return imagePath;
	}
	public void setFacilityIconPath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getAmenitiesId() {
		return amenitiesId;
	}
	public void setAmenitiesId(String amenitiesId) {
		this.amenitiesId = amenitiesId;
	}
	public int getIconID() {
		return iconID;
	}
	public void setFacilityID(int iconID) {
		this.iconID = iconID;
	}
	public String getIconName() {
		return iconName;
	}
	public void setTitle(String iconName) {
		this.iconName = iconName;
	}
}
