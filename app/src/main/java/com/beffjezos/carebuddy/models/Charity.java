package com.beffjezos.carebuddy.models;import java.io.Serializable;public class Charity implements Serializable {    String id, name, location, summery, number, stubDate, logo;    public Charity(String id, String name, String location, String summery, String number, String stubDate, String logo) {        this.id = id;        this.name = name;        this.location = location;        this.summery = summery;        this.number = number;        this.stubDate = stubDate;        this.logo = logo;    }    public Charity() {    }    public String getId() {        return id;    }    public void setId(String id) {        this.id = id;    }    public String getName() {        return name;    }    public void setName(String name) {        this.name = name;    }    public String getLocation() {        return location;    }    public void setLocation(String location) {        this.location = location;    }    public String getSummery() {        return summery;    }    public void setSummery(String summery) {        this.summery = summery;    }    public String getNumber() {        return number;    }    public void setNumber(String number) {        this.number = number;    }    public String getStubDate() {        return stubDate;    }    public void setStubDate(String stubDate) {        this.stubDate = stubDate;    }    public String getLogo() {        return logo;    }    public void setLogo(String logo) {        this.logo = logo;    }}