package com.example.springproject.service;

import com.example.springproject.domain.PurchaseRecord;
import com.example.springproject.domain.api.PurchaseRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseRecordServiceImpl implements PurchaseRecordService{
    @Autowired
    private PurchaseRecordRepository purchaseRecordRepository;


    @Override
    public List<PurchaseRecord> findAll() {
        return purchaseRecordRepository.findAll();
    }

    @Override
    public PurchaseRecord save(PurchaseRecord purchaseRecord) {
        return purchaseRecordRepository.save(purchaseRecord);
    }

    @Override
    public void deleteById(long id) {
        purchaseRecordRepository.deleteById(id);
    }
}
