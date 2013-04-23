package com.fizz.attenda.datamodels;

public class Subject {
	 private long id;
	  private String name;
	  private int credits;
	  private boolean lab;

	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	
	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return name;
	  }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public boolean isLab() {
		return lab;
	}

	public void setLab(boolean lab) {
		this.lab = lab;
	}
}
