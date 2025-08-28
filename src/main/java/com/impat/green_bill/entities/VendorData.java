package com.impat.green_bill.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorData {
    @NotBlank(message = "Vendor name must not be empty")
    String vendorName;
    String vendorGST;
    String address;
}
