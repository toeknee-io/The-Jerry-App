package com.loosefang.thejerryapp.javafile;

public class MainActivityList {

	String name;
	int file;
	public MainActivityList(String name,int id) {
		this.name=name;
		this.file=id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getFile() {
		return file;
	}
	public void setFile(int file) {
		this.file = file;
	}
	
}
