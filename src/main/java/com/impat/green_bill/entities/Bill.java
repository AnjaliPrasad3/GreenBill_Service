package com.impat.green_bill.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.springframework.data.annotation.Id;

@org.springframework.data.mongodb.core.mapping.Document(collection = "bills")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bill extends BaseEntity{
    @Id
    private String id;
    private String fileName;
    private String fileUrl;
    private Document billMetadata;
    private VendorData vendorData;
    private CustomerData customerData;
    private Double totalAmount;
    private boolean isSynced;
}
