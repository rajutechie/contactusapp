package com.rajutechies.contactusapp.interceptors;

import com.rajutechies.contactusapp.exceptions.RajuTechiesException;
import com.rajutechies.contactusapp.models.enums.StatusCode;
import com.rajutechies.contactusapp.models.response.ResponseMetaData;
import com.rajutechies.contactusapp.services.util.TransformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class EmailExceptionHandler extends ResponseEntityExceptionHandler {

    final
    TransformationService transformationService;

    public EmailExceptionHandler(TransformationService transformationService) {
        this.transformationService = transformationService;
    }

    @ExceptionHandler(RajuTechiesException.class)
    public ResponseEntity<ResponseMetaData> handleAuthServiceException(final RajuTechiesException rajuTechiesException, final HttpServletRequest httpRequest) {
        HttpStatus httpStatus = HttpStatus.EXPECTATION_FAILED;

        StatusCode statusCode = StatusCode.DATA_SAVED_FAILED;

        if(rajuTechiesException.getErrorDetails().size()>1) {
            statusCode = StatusCode.TOO_MANY_MISSING;
        }

        return new ResponseEntity<>(transformationService.transformErrorResponseMetaData(httpRequest, rajuTechiesException.getErrorDetails(), statusCode), httpStatus);
    }
}
