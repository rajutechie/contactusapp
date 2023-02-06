package com.rajutechies.contactusapp.models.enums;

public enum StatusCode {
    CORRELATION_ID_MISSING(400, "400101", "Contact Service Request Payload Validation Error. correlationId is missing"),
    APPLICATION_LABEL_MISSING(400, "400102", "Contact Service Request Payload Validation Error. applicationLabel is missing"),

    TOO_MANY_MISSING(400, "400103", "Too many header/body details are missed"),

    EMAIL_SENT_FAILED(417, "417100", "Contact Service Error. Sending EMAIL failed"),
    EMAIL_INTERNAL_ERROR(417, "417101", "Contact Service Error. Something went wrong"),
	DATA_SAVED_FAILED(417, "417102", "Unable to save your data. Please retry after sometime."),

	DATA_LISTED_SUCCESS(200, "200101", "Data Listed Successfully"),
    DATA_SAVED_SUCCESS(200, "200100", "Thank You!!! We received your request. Our team will get back to you soon.");


    private final int code;
    private final String appCode;
    private final String statusMessage;
	private StatusCode(int code, String appCode, String statusMessage) {
		this.code = code;
		this.appCode = appCode;
		this.statusMessage = statusMessage;
	}
	public int getCode() {
		return code;
	}
	public String getAppCode() {
		return appCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
}
