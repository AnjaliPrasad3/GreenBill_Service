package com.impat.green_bill.service.impl;

import com.impat.green_bill.dtos.ApiPageResponseDto;
import com.impat.green_bill.dtos.BillDto;
import com.impat.green_bill.entities.CustomerData;
import com.impat.green_bill.service.BillService;
import com.impat.green_bill.entities.Bill;
import com.impat.green_bill.entities.VendorData;
import com.impat.green_bill.repository.BillRepository;
import com.impat.green_bill.service.S3Service;
import org.bson.Document;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;


import java.io.IOException;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final S3Service s3Service;


    public BillServiceImpl(BillRepository billRepository, S3Service s3Service) {
        this.billRepository = billRepository;
        this.s3Service = s3Service;
    }

    @Override
    public Bill saveBill(MultipartFile file,
                         Document billMetadata,
                         VendorData vendorData,
                         Double totalAmount, CustomerData customerData) throws IOException {

        String fileUrl = s3Service.uploadFileToS3(file);

        Bill bill = Bill.builder()
                .fileName(file.getOriginalFilename())
                .fileUrl(fileUrl)
                .billMetadata(billMetadata)
                .vendorData(vendorData)
                .totalAmount(totalAmount)
                .customerData(customerData)
                .isSynced(false)
                .build();

        return billRepository.save(bill);
    }

    @Override
    public ApiPageResponseDto<List<BillDto>> getAllBills() {

        String phoneNumber = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Bill> bills = billRepository.findByCustomerData_PhoneNumberAndIsSyncedFalse(phoneNumber);

        bills.forEach(bill -> bill.setSynced(true));
        billRepository.saveAll(bills);

        List<BillDto> billDtos = bills.stream()
                .map(BillDto::fromEntity)
                .toList();

        return ApiPageResponseDto.<List<BillDto>>builder()
                .data(billDtos)
                .build();
    }


    @Override
    public Resource downloadFileFromS3(String fileUrl) {
        return s3Service.downloadFileFromS3(fileUrl);
    }


}
