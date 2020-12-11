package com.project.scambiolavoro.models;

import android.support.annotation.NonNull;

/**
 * Created by ravi on 16/11/17.
 */

public class Contact implements Comparable<Contact> {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWorkExp() {
        return workExp;
    }

    public void setWorkExp(String workExp) {
        this.workExp = workExp;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGkey() {
        return gkey;
    }

    public void setGkey(String gkey) {
        this.gkey = gkey;
    }

    private String name;
    private String surname;
    private String gender;
    private String birthDate;
    private String fromPlace;
    private String work;
    private String workExp;
    private String workPlace;
    private String phone;
    private String mail;
    private String pwd;
    private String image;
    private String gkey;



    public Contact(String name, String image, String work) {
        this.name = name;
        this.image = image;
        this.work = work;
    }
    public Contact(String name, String surname, String gender,
                   String birthDate,  String fromPlace, String work,
                   String workExp, String workplace, String phone,
                   String mail, String image){
        this.name = name;
        this.gender = gender;
        this.image = image;
        this.phone = phone;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.fromPlace = fromPlace;
        this.work = work;
        this.workExp = workExp;
        this.workPlace = workplace;
        this.mail = mail;
    }




    // String img;


    @Override
    public int compareTo(@NonNull Contact contact) {

        return 0;
    }
}

class myContact {

    public myContact(String name, String surname, String gender, String birthDate,
                     String fromPlace, String work, String workExp,String mail, String pwd){
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.fromPlace = fromPlace;
        this.work = work;
        this.workExp = workExp;
        this.mail = mail;
        this.pwd = pwd;
    }

    String name;
    String surname;
    String gender;
    String birthDate;
    String fromPlace;
    String work;
    String workExp;
    String mail;
    String pwd;
    // String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getWorkExp() {
        return workExp;
    }

    public void setWorkExp(String workExp) {
        this.workExp = workExp;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}