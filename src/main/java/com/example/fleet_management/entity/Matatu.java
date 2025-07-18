package com.example.fleet_management.entity;

import jakarta.persistence.*;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;


@Entity
public class Matatu {
    @Id
    private String plateNumber;
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    private String status;
    private String route;
    private String departure;
    private String destination;


    @OneToOne
    @JoinColumn(name = "driver_id")
    private Employee driver;

    public Matatu(){} //default constructor
    public Matatu(String route, String status){
        //this.plateNumber=plateNumber;
        this.route=route;
        this.status=status;
    } //constructor with all fields except id

    public String getPlateNumber(){
        return plateNumber;
    }
    public void setPlateNumber(String plateNumber){
        this.plateNumber= plateNumber;
    }

    public String getRoute(){
        return route;
    }
    public void setRoute(String route){
        this.route=route;
    }

    public String getStatus(){
        return status;}
    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeparture(){return departure;}
    public void  setDeparture(String departure){
        this.departure = departure;
    }

    public String getDestination(){return destination;}
    public void  setDestination(String destination){
        this.destination = destination;
    }

    public Employee getDriver(){
        return driver;
    }
    public void setDriver(Employee driver){
        this.driver=driver;
    }

}
