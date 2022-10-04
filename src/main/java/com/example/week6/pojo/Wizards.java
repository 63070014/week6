package com.example.week6.pojo;

import java.util.ArrayList;
import java.util.List;
//แมป Domain Model เข้ากับ Data Transfer Object (DTO) กล่าวคือ นำมาช่วยในการแปลงข้อมูล
public class Wizards {
    private List<Wizard> model;
    public Wizards(){
        model = new ArrayList<>();
    }
    public Wizards(List<Wizard> model) {
        this.model = model;
    }
    public List<Wizard> getModel() {
        return model;
    }
    public void setModel(List<Wizard> model) {
        this.model = model;
    }
}
