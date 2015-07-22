package com.pvo.custom.adapter;


public class GroupListItem {
	
	private String pgid;
	private String groupname;
	private String ownerbrokerid;
	private String membercount;
	
	public String getMembercount() {
		return membercount;
	}
	public void setMembercount(String membercount) {
		this.membercount = membercount;
	}
	public String getPgid() {
		return pgid;
	}
	public void setPgid(String pgid) {
		this.pgid = pgid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getOwnerbrokerid() {
		return ownerbrokerid;
	}
	public void setOwnerbrokerid(String ownerbrokerid) {
		this.ownerbrokerid = ownerbrokerid;
	}
}
