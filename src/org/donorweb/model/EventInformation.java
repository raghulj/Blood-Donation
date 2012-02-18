package org.donorweb.model;

import java.text.ParseException;

import org.donorweb.utils.Utils;

public class EventInformation {
	private String kind;
	private String id;
	private String selfLink;
	private String alternateLink;
	private Boolean canEdit;
	private String title;
	private String created;
	private String updated;
	private String details;
	private String status;
	private String creator;
	private Boolean anyoneCanAddSelf;
	private Boolean guestsCanInviteOthers;
	private Boolean guestsCanModify;
	private Boolean guestsCanSeeGuests;
	private long sequence;
	private String transparency;
	private String location;
	private String startTime;
	private String endTime;

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSelfLink() {
		return selfLink;
	}

	public void setSelfLink(String selfLink) {
		this.selfLink = selfLink;
	}

	public String getAlternateLink() {
		return alternateLink;
	}

	public void setAlternateLink(String alternateLink) {
		this.alternateLink = alternateLink;
	}

	public Boolean getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Boolean getAnyoneCanAddSelf() {
		return anyoneCanAddSelf;
	}

	public void setAnyoneCanAddSelf(Boolean anyoneCanAddSelf) {
		this.anyoneCanAddSelf = anyoneCanAddSelf;
	}

	public Boolean getGuestsCanInviteOthers() {
		return guestsCanInviteOthers;
	}

	public void setGuestsCanInviteOthers(Boolean guestsCanInviteOthers) {
		this.guestsCanInviteOthers = guestsCanInviteOthers;
	}

	public Boolean getGuestsCanModify() {
		return guestsCanModify;
	}

	public void setGuestsCanModify(Boolean guestsCanModify) {
		this.guestsCanModify = guestsCanModify;
	}

	public Boolean getGuestsCanSeeGuests() {
		return guestsCanSeeGuests;
	}

	public void setGuestsCanSeeGuests(Boolean guestsCanSeeGuests) {
		this.guestsCanSeeGuests = guestsCanSeeGuests;
	}

	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public String getTransparency() {
		return transparency;
	}

	public void setTransparency(String transparency) {
		this.transparency = transparency;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setEventStartTime(String sTime) {
		startTime = sTime;
	}

	public void setEventEndTime(String eTime) {
		endTime = eTime;
	}

	public String getEventEndTime() {
		return endTime;
	}

	public String getEventStartTime() {
		return startTime;
	}

	public String toString() {

		String eventtime = "";
		try {
			eventtime = Utils.getTime(getEventStartTime()) + "-"
					+ Utils.getTime(getEventEndTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String textAddress = "<h4><b>" + getTitle() + "</b></h4><br/>"
				+ Utils.getDate(startTime) + " <br/>" + eventtime
				+ "<br/><br/>" + getLocation()
				+ "<br/><br/><br/><font size=10><font color='grey'>"
				+ getDetails() + "</font>";
		return textAddress;
	}
}
