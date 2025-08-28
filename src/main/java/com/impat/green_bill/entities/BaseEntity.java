package com.impat.green_bill.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {
    @CreatedBy
    private Long createdBy;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedBy
    private Long updatedBy;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;
}
