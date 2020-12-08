package com.project.scambiolavoro.models;

import android.support.annotation.NonNull;

/**
 * Created by ravi on 16/11/17.
 */

public class Contact implements Comparable<Contact> {


    String name;
    String surname;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;
    String phone;
    String gender;
    String birthDate;
    String fromPlace;
    String work;
    String workExp;
    String mail;
    String pwd;
    String gkey;







    public Contact(String name, String image, String phone) {
        this.name = name;
        this.image = image;
        this.phone = phone;


    }
    public Contact(String name, String image, String phone, String surname, String gender,
                   String birthDate, String fromPlace, String work,
                   String workExp, String mail){
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.fromPlace = fromPlace;
        this.work = work;
        this.workExp = workExp;
        this.mail = mail;
    }



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

    public String getPhone() {
        return phone;
    }

    public void setPhone (String phone){
        this.phone = phone;
    }

    public String getGkey() {
        return gkey;
    }

    public void setGkey(String gkey) {
        this.gkey = gkey;
    }

    @Override
    public int compareTo(@NonNull Contact contact) {

        return 0;
    }
}

class myContact {

    public myContact(String name, String surname, String gender, String birthDate,
                     String fromPlace, String work, String workExp, String mail, String pwd){
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