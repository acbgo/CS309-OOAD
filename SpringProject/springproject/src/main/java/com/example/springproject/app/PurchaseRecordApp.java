package com.example.springproject.app;

import com.example.springproject.domain.PurchaseRecord;
import com.example.springproject.service.PurchaseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exer")
public class PurchaseRecordApp {
    @Autowired
    private PurchaseRecordService purchaseRecordService;

    @GetMapping("/record")
    public List<PurchaseRecord> findAll(){
        System.out.println(purchaseRecordService.getClass().getName());
        return purchaseRecordService.findAll();
    }

    @PostMapping("/record")
    public PurchaseRecord addOne(PurchaseRecord purchaseRecord){
        return purchaseRecordService.save(purchaseRecord);
    }

    @PutMapping("/record")
    public PurchaseRecord update(@RequestParam long id, @RequestParam String username,
                                 @RequestParam String date, @RequestParam double money,
                                 @RequestParam int type,@RequestParam String description){
        PurchaseRecord purchaseRecord = new PurchaseRecord();
        purchaseRecord.setId(id);
        purchaseRecord.setUsername(username);
        purchaseRecord.setDescription(description);
        purchaseRecord.setMoney(money);
        purchaseRecord.setDate(date);
        purchaseRecord.setType(type);
        return purchaseRecordService.save(purchaseRecord);
    }

    @DeleteMapping("record/{id}")
    public void deleteOne(@PathVariable long id){
        purchaseRecordService.deleteById(id);
    }
}

/*insert into purchase_record (username, date, money, type, description) values ('Yueming','2019-09-01',50,0,'Meituan');
insert into purchase_record (username, date, money, type, description) values ('Helly','2020-10-10',20,0,'Taobao');
 insert into purchase_record (username, date, money, type, description) values ('Bob','2021-11-11',210,0,'Taobao');*/