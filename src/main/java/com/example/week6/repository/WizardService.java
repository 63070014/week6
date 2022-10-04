package com.example.week6.repository;

import com.example.week6.pojo.Wizard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//ตัวกลางในการติดต่อและเข้าถึงระหว่าง Business Layer กับ Database Layer ในรูปแบบ Service API
//สามารถทำ Caching Data ได้
@Service
public class WizardService {
    @Autowired
    private WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }
    public List<Wizard> retrieveWizards(){
        return wizardRepository.findAll();
    }
    public Wizard retrieveWizardById(String id){
        return wizardRepository.findWizardID();
    }
    public Wizard createWizard(Wizard wizard){
        return wizardRepository.save(wizard);
    }
    public Wizard updateWizard(Wizard wizard){
        return wizardRepository.save(wizard);
    }
    public boolean deleteWizard(Wizard wizard){
        try{wizardRepository.delete(wizard); return true;}
        catch (Exception e) {return false;}
    }
}
