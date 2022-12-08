package com.jbr.exp.tfl.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Station {
    @Id
    private String id;

    @Column
    private String name;

    @Column(name="zone_1")
    private Integer zone1;

    @Column(name="zone_2")
    private Integer zone2;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getZone1() {
        return zone1;
    }

    public void setZone1(Integer zone1) {
        this.zone1 = zone1;
    }

    public Integer getZone2() {
        return zone2;
    }

    public void setZone2(Integer zone2) {
        this.zone2 = zone2;
    }
}
