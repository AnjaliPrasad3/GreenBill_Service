package com.impat.green_bill.service;

import com.impat.green_bill.dtos.ApiPageResponseDto;
import com.impat.green_bill.dtos.BillDto;
import com.impat.green_bill.entities.Bill;
import com.impat.green_bill.entities.CustomerData;
import com.impat.green_bill.entities.VendorData;
import org.bson.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface BillService {
    public Bill saveBill(MultipartFile file,
                         Document billMetadata,
                         VendorData vendorData,
                         Double totalAmount, CustomerData customerData) throws IOException;
    public ApiPageResponseDto<List<BillDto>> getAllBills();
    Resource downloadFileFromS3(String fileUrl);
}
