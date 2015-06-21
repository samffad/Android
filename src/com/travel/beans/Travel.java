package com.travel.beans;

public class Travel {
	private String title;
	private int author_id;
	private int tid;
	private int days;
	private String date;
	private String city;
	private String backgroundPhoto;

	public Travel() {
		super();
	}
	
	public Travel(String title, int author, int tid, int days, String date, String city, String backgroundPhoto) {
		super();
		this.title = title;
		this.author_id = author;
		this.tid = tid;
		this.days = days;
		this.date = date;
		this.city = city;
		this.backgroundPhoto = backgroundPhoto;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(int author_id) {
		this.author_id = author_id;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getcity() {
		return city;
	}
	public void setcity(String city) {
		this.city = city;
	}

	public String getBackgroundPhoto() {
		return backgroundPhoto;
	}

	public void setBackgroundPhoto(String backgroundPhoto) {
		this.backgroundPhoto = backgroundPhoto;
	}
}
