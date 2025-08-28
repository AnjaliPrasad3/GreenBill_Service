package com.impat.green_bill.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageInfoDto {
    private int totalPages;
    private int pageNumber;
    private int pageSize;
    private long totalRecords;
}
