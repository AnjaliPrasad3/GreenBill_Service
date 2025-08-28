package com.impat.green_bill.repository;

import com.impat.green_bill.entities.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface BillRepository extends MongoRepository<Bill, String> {
    List<Bill> findByCustomerData_PhoneNumberAndIsSyncedFalse(String phoneNumber);
}
