package com.sandwich.testclasses;


public class BusinessPOJOImpl implements BusinessPOJO{
	Integer noOfApples  = null;
	String colorOfOranges = null;
	public BusinessPOJOImpl(){
		noOfApples  = 5;
		colorOfOranges = "orange";
	}
	@Override
	public String getColorOfOranges() {
		return colorOfOranges;
	}
	@Override
	public Integer getNoOfApples() {
		return noOfApples;
	}
	@Override
	public void setColorOfOranges(String i) {}
	public void setNoOfApples(Integer i) {}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((colorOfOranges == null) ? 0 : colorOfOranges.hashCode());
		result = prime * result
				+ ((noOfApples == null) ? 0 : noOfApples.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessPOJOImpl other = (BusinessPOJOImpl) obj;
		if (colorOfOranges == null) {
			if (other.colorOfOranges != null)
				return false;
		} else if (!colorOfOranges.equals(other.colorOfOranges))
			return false;
		if (noOfApples == null) {
			if (other.noOfApples != null)
				return false;
		} else if (!noOfApples.equals(other.noOfApples))
			return false;
		return true;
	};
	
}
