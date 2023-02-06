package com.rajutechies.contactusapp.models.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "contact_details")
public class ContactDetails implements Serializable {

    @Id
    private String id;

    private String userName;

    private String emailAddress;

    private String subject;

    private String message;

    private LocalDateTime createdOn = LocalDateTime.now();
}
