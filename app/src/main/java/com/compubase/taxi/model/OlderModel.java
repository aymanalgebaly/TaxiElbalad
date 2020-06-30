
package com.compubase.taxi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OlderModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("id_user")
    @Expose
    private Integer idUser;
    @SerializedName("number_up_five")
    @Expose
    private Integer numberUpFive;
    @SerializedName("number_down_five")
    @Expose
    private Integer numberDownFive;
    @SerializedName("station_arrive")
    @Expose
    private String stationArrive;
    @SerializedName("date_arrive")
    @Expose
    private String dateArrive;
    @SerializedName("time_arrive")
    @Expose
    private String timeArrive;
    @SerializedName("typr_ticket")
    @Expose
    private String typrTicket;
    @SerializedName("datee")
    @Expose
    private String datee;
    @SerializedName("price")
    @Expose
    private Object price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getNumberUpFive() {
        return numberUpFive;
    }

    public void setNumberUpFive(Integer numberUpFive) {
        this.numberUpFive = numberUpFive;
    }

    public Integer getNumberDownFive() {
        return numberDownFive;
    }

    public void setNumberDownFive(Integer numberDownFive) {
        this.numberDownFive = numberDownFive;
    }

    public String getStationArrive() {
        return stationArrive;
    }

    public void setStationArrive(String stationArrive) {
        this.stationArrive = stationArrive;
    }

    public String getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(String dateArrive) {
        this.dateArrive = dateArrive;
    }

    public String getTimeArrive() {
        return timeArrive;
    }

    public void setTimeArrive(String timeArrive) {
        this.timeArrive = timeArrive;
    }

    public String getTyprTicket() {
        return typrTicket;
    }

    public void setTyprTicket(String typrTicket) {
        this.typrTicket = typrTicket;
    }

    public String getDatee() {
        return datee;
    }

    public void setDatee(String datee) {
        this.datee = datee;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

}
