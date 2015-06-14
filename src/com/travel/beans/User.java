package com.travel.beans;

import java.io.Serializable;

//public class User implements Serializable{
//}
public class User implements Serializable{

	private java.lang.Integer id;

	private java.lang.String name;

//	private java.lang.String nickname;

	private java.lang.String gender;

	private java.lang.String photoPath;

	public User() {
		super();
//		this.id = id;
//		this.name = name;
////		this.nickname = nickname;
//		this.gender = gender;
//		this.photoPath = photoPath;
	}
	public User(Integer id, String name, String gender, String photoPath) {
		super();
		this.id = id;
		this.name = name;
//		this.nickname = nickname;
		this.gender = gender;
		this.photoPath = photoPath;
	}

	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

//	public java.lang.String getNickname() {
//		return nickname;
//	}
//
//	public void setNickname(java.lang.String nickname) {
//		this.nickname = nickname;
//	}

	public java.lang.String getGender() {
		return gender;
	}

	public void setGender(java.lang.String gender) {
		this.gender = gender;
	}

	public java.lang.String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(java.lang.String photoPath) {
		this.photoPath = photoPath;
	}
	
}