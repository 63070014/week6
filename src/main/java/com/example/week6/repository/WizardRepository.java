package com.example.week6.repository;

import com.example.week6.pojo.Wizard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

//อินเตอร์เฟสสำหรับร่างเมธอดที่ใช้ตั้งคำถามว่า
// (1) Business Layer ต้องการข้อมูลอะไรจาก Data Source
// (2) ให้คืนค่าข้อมูลอะไรและอยู่ในรูปแบบใด
@Repository
public interface WizardRepository extends MongoRepository<Wizard, String> {
   @Query(value = "{_id: '?0'}")
    public Wizard findWizardID ();
}
