package com.rajutechies.contactusapp.interceptors;


import com.rajutechies.contactusapp.exceptions.RajuTechiesException;
import com.rajutechies.contactusapp.models.enums.StatusCode;
import com.rajutechies.contactusapp.models.response.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RequestInterceptor implements HandlerInterceptor {


    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final String WHITE_LIST_PATH = "/actuator/health";

    public RequestInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {

        Boolean blflg = Boolean.TRUE;

        Boolean appLabel = Boolean.TRUE;

        Boolean corId = Boolean.TRUE;

        if (WHITE_LIST_PATH.equals(request.getServletPath())) {
            return true;
        }
        List<ErrorDetails> errorDetailsList = new ArrayList<>();

        Enumeration<String> headerNames= request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
           String headerName = headerNames.nextElement();
           if (headerName.equalsIgnoreCase("applicationlabel")) {
               if (!request.getHeader(headerName).equals("")) {
                    appLabel = Boolean.FALSE;
               }
           }

           if (headerName.equalsIgnoreCase("correlationid")) {
               if (!request.getHeader(headerName).equals("")) {
                   corId = Boolean.FALSE;
               }
           }
        }

        if (appLabel) {
           errorDetailsList.add(populateErrorDetails(StatusCode.APPLICATION_LABEL_MISSING));
        }

        if (corId) {
            errorDetailsList.add(populateErrorDetails(StatusCode.CORRELATION_ID_MISSING));
        }

        if (errorDetailsList.size() > 0 ) {
            throw new RajuTechiesException(errorDetailsList);
        }

        return blflg;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    private ErrorDetails populateErrorDetails(StatusCode statusCode) {
        return new ErrorDetails(
                statusCode.getCode(),
                statusCode.getAppCode(),
                statusCode.getStatusMessage()
        );
    }
}
