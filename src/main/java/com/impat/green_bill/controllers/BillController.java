package com.impat.green_bill.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.impat.green_bill.dtos.ApiPageResponseDto;
import com.impat.green_bill.dtos.BillDto;
import com.impat.green_bill.entities.CustomerData;
import com.impat.green_bill.service.BillService;
import com.impat.green_bill.entities.Bill;
import com.impat.green_bill.entities.VendorData;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.bson.Document;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {
    private final BillService billService;
    private final ObjectMapper objectMapper;

    public BillController(BillService billService, ObjectMapper objectMapper) {
        this.billService = billService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<Bill> uploadBill(
            @RequestParam("file") MultipartFile file,
            @RequestParam("billMetadata") String billMetadataJson,
            @RequestParam("vendorData") String vendorDataJson,
            @RequestParam("totalAmount") Double totalAmount,
            @RequestParam("customerData") String customerDataJson) {

        try {
            Document billMetadata = Document.parse(billMetadataJson);
            VendorData vendorData = objectMapper.readValue(vendorDataJson, VendorData.class);
            CustomerData customerData = objectMapper.readValue(customerDataJson, CustomerData.class);

            Bill savedBill = billService.saveBill(file, billMetadata, vendorData, totalAmount, customerData);
            return ResponseEntity.ok(savedBill);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<BillDto>> getAllBills() {
        ApiPageResponseDto<List<BillDto>> response = billService.getAllBills();
        return ResponseEntity.ok(response.getData());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileUrl) {
        try {
            Resource file = billService.downloadFileFromS3(fileUrl);

            // Extract filename from URL
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
