package com.rajutechies.contactusapp.services.util;

import com.rajutechies.contactusapp.models.enums.StatusCode;
import com.rajutechies.contactusapp.models.response.ErrorDetails;
import com.rajutechies.contactusapp.models.response.ResponseMetaData;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransformationService {

    private String getRequestPath(HttpServletRequest httpServletRequest) {

        return httpServletRequest.getContextPath() + httpServletRequest.getServletPath();
    }

    public ResponseMetaData transformErrorResponseMetaData(HttpServletRequest httpServletRequest, List<ErrorDetails> errorDetailsList, StatusCode statusCode) {
        return new ResponseMetaData(
                statusCode.getCode(),
                httpServletRequest.getHeader("applicationLabel"),
                LocalDateTime.now(),
                httpServletRequest.getHeader("correlationID"),
                null,
                errorDetailsList,
                statusCode.getAppCode(),
                statusCode.getStatusMessage(),
                getRequestPath(httpServletRequest),
                httpServletRequest.getMethod()
        );

    }

    public ResponseMetaData transformResponseMetaData(HttpServletRequest httpServletRequest, Object data, StatusCode statusCode) {
        return new ResponseMetaData(
                statusCode.getCode(),
                httpServletRequest.getHeader("applicationLabel"),
                LocalDateTime.now(),
                httpServletRequest.getHeader("correlationID"),
                data,
                null,
                statusCode.getAppCode(),
                statusCode.getStatusMessage(),
                getRequestPath(httpServletRequest),
                httpServletRequest.getMethod()
        );
    }

    public ErrorDetails populateErrorDetails(StatusCode statusCode) {
        return new ErrorDetails(
                statusCode.getCode(),
                statusCode.getAppCode(),
                statusCode.getStatusMessage()
        );
    }
}
