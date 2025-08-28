package com.impat.green_bill.entities;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "secrets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Secrets {
    @Id
    private String id;
    private String name;
    private String value;
}
