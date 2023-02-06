package com.rajutechies.contactusapp.services;

import com.rajutechies.contactusapp.exceptions.RajuTechiesException;
import com.rajutechies.contactusapp.models.enums.StatusCode;
import com.rajutechies.contactusapp.models.request.ContactDetails;
import com.rajutechies.contactusapp.models.request.ContactDetailsRequest;
import com.rajutechies.contactusapp.repositories.ContactDetailsRepo;
import com.rajutechies.contactusapp.services.util.TransformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ContactService {

    private Logger logger = LoggerFactory.getLogger(ContactService.class);

    final ContactDetailsRepo contactDetailsRepo;

    final EmailService emailService;

    final TransformationService transformationService;

    public ContactService(
            ContactDetailsRepo contactDetailsRepo,
            EmailService emailService,
            TransformationService transformationService) {
        this.contactDetailsRepo = contactDetailsRepo;
        this.emailService = emailService;
        this.transformationService = transformationService;
    }

    public String saveContactDetails(ContactDetailsRequest contactDetailsRequest) {
        String returnValue = StatusCode.DATA_SAVED_SUCCESS.getStatusMessage();
        try {
            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setEmailAddress(contactDetailsRequest.getEmailAddress());
            contactDetails.setMessage(contactDetailsRequest.getMessage());
            contactDetails.setUserName(contactDetailsRequest.getUserName());
            contactDetails.setSubject(contactDetailsRequest.getSubject());

            contactDetailsRepo.save(contactDetails);

            try {
                emailService.sendEmail(contactDetailsRequest);
            } catch (Exception ex) {
                logger.error("Mail Sent error:: " + ex.getMessage());
            }
        } catch (Exception ex) {
            logger.error("Contact Info Save error:: " + ex.getMessage());
            throw new RajuTechiesException(Arrays.asList(transformationService.populateErrorDetails(StatusCode.DATA_SAVED_FAILED)));
        }
        return returnValue;
    }

    public List<ContactDetails> listAllContactDetails() {
        return contactDetailsRepo.findContactDetailsByOrderByCreatedOnDesc();
    }
}
