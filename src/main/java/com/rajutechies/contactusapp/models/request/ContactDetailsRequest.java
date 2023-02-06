package com.rajutechies.contactusapp.models.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetailsRequest implements Serializable {

    private String userName;

    private String emailAddress;

    private String subject;

    private String message;
}
