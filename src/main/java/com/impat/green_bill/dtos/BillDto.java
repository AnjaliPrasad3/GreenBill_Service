package com.impat.green_bill.dtos;

import com.impat.green_bill.entities.Bill;
import com.impat.green_bill.entities.CustomerData;
import com.impat.green_bill.entities.VendorData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
    private String id;
    private String fileName;
    private String fileUrl;
    private Document billMetadata;
    private VendorData vendorData;
    private CustomerData customerData;
    private Double totalAmount;
    private LocalDateTime createdDateTime;
    private boolean synked;

    public static BillDto fromEntity(Bill bill) {
        return new BillDto(
                bill.getId(),
                bill.getFileName(),
                bill.getFileUrl(),
                bill.getBillMetadata(),
                bill.getVendorData(),
                bill.getCustomerData(),
                bill.getTotalAmount(),
                bill.getCreatedDateTime(),
                bill.isSynced()
        );
    }

}
