package com.example.week6.controller;

import com.example.week6.pojo.Wizard;
import com.example.week6.pojo.Wizards;
import com.example.week6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
//เก็บ Business logic และช่วยจัดการต่างๆ
@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;
    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public Wizards getWizard(){
        Wizards wizards = new Wizards(wizardService.retrieveWizards());
        return wizards;
    }
    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public Wizard addWizard(@RequestBody MultiValueMap<String, String> data){
        Map<String, String> d = data.toSingleValueMap();
        Wizard n = wizardService.createWizard(new Wizard(null,
                            d.get("sex"),
                            d.get("fullName"),
                            d.get("school"),
                            d.get("house"),
                            Double.parseDouble(d.get("dollars")),
                            d.get("position")));
        return n;
    }
    @RequestMapping(value ="/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestBody MultiValueMap<String, String> data) {
        Map<String, String> d = data.toSingleValueMap();
        Wizard n = wizardService.retrieveWizardById(d.get("id"));
        if (n != null) {
            wizardService.updateWizard(new Wizard(n.get_id(),
                    d.get("sex"),
                    d.get("fullName"),
                    d.get("school"),
                    d.get("house"),
                    Double.parseDouble(d.get("dollars")),
                    d.get("position")));
            return true;
        } else {
            return false;
        }
    }
    @RequestMapping(value ="/deleteWizard", method = RequestMethod.POST)
    public boolean deleteWizard(@RequestBody MultiValueMap<String, String> data){
        Map<String, String> d = data.toSingleValueMap();
        Wizard n = wizardService.retrieveWizardById(d.get("id"));
        try{wizardService.deleteWizard(n); return true;}
        catch (Exception e){return false;}
    }
}

