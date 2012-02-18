package org.donorweb.model;

public class BloodGroupDetail {

	public int Count;
	public String Label;
	public float Percent;
	public int Color;

	public BloodGroupDetail() {
		Count = 0;
		Label = "";
		Percent = 0;
		Color = -1;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Count = count;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public float getPercent() {
		return Percent;
	}

	public void setPercent(float percent) {
		Percent = percent;
	}

	public int getColor() {
		return Color;
	}

	public void setColor(int color) {
		Color = color;
	}

}