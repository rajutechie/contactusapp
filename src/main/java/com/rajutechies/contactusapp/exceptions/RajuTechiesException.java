package com.rajutechies.contactusapp.exceptions;


import com.rajutechies.contactusapp.models.response.ErrorDetails;

import java.util.List;


public class RajuTechiesException extends RuntimeException {

    private List<ErrorDetails> errorDetails;

	public RajuTechiesException(List<ErrorDetails> errorDetails) {
		super();
		this.errorDetails = errorDetails;
	}

	public List<ErrorDetails> getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(List<ErrorDetails> errorDetails) {
		this.errorDetails = errorDetails;
	}
    
    
}
