package com.impat.green_bill.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_activity")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivity extends BaseEntity {
    @Id
    private String id;
    private String phoneNumber;
    private String status;
}
