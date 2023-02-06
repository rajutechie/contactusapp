package com.rajutechies.contactusapp.controllers;

import com.rajutechies.contactusapp.exceptions.RajuTechiesException;
import com.rajutechies.contactusapp.models.enums.StatusCode;
import com.rajutechies.contactusapp.models.request.ContactDetailsRequest;
import com.rajutechies.contactusapp.models.response.ErrorDetails;
import com.rajutechies.contactusapp.models.response.ResponseMetaData;
import com.rajutechies.contactusapp.services.ContactService;
import com.rajutechies.contactusapp.services.util.TransformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/contactservice")
public class ContactDetailsController {

    final ContactService contactService;

    final TransformationService transformationService;

    public ContactDetailsController(ContactService contactService, TransformationService transformationService) {
        this.contactService = contactService;
        this.transformationService = transformationService;
    }

    @PostMapping("/saveContactDetails")
    public ResponseEntity<ResponseMetaData> saveContactDetails(@RequestBody ContactDetailsRequest contactDetailsRequest,
                                                               HttpServletRequest httpServletRequest) {
        String applicationLabel = httpServletRequest.getHeader("applicationLabel");
        String correlationId = httpServletRequest.getHeader("correlationId");
        validateHeaders(applicationLabel, correlationId);
        String resultData = contactService.saveContactDetails(contactDetailsRequest);
        return new ResponseEntity<>(
                transformationService.transformResponseMetaData(httpServletRequest, resultData, StatusCode.DATA_SAVED_SUCCESS), HttpStatus.CREATED
        );
    }

    @GetMapping("/listContactDetails")
    public ResponseEntity<ResponseMetaData> listContactDetails(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(
                transformationService.transformResponseMetaData(httpServletRequest, contactService.listAllContactDetails(), StatusCode.DATA_LISTED_SUCCESS)
        , HttpStatus.OK);
    }

    private void validateHeaders(String applicationLabel, String correlationId) {
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        if (!Optional.ofNullable(applicationLabel).isPresent()) {
            errorDetailsList.add(populateErrorDetails(StatusCode.APPLICATION_LABEL_MISSING));
        }

        if (!Optional.ofNullable(correlationId).isPresent()) {
            errorDetailsList.add(populateErrorDetails(StatusCode.CORRELATION_ID_MISSING));
        }
        if (errorDetailsList.size() > 0 ) {
            throw new RajuTechiesException(errorDetailsList);
        }
    }

    private ErrorDetails populateErrorDetails(StatusCode statusCode) {
        return new ErrorDetails(
                statusCode.getCode(),
                statusCode.getAppCode(),
                statusCode.getStatusMessage()
        );
    }
}
