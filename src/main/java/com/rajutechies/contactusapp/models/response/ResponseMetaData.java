package com.rajutechies.contactusapp.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMetaData implements Serializable {

    private Integer code;

    private String applicationLabel;

    private LocalDateTime time = LocalDateTime.now();

    private String correlationId;

    private Object data;

    private List<ErrorDetails> errors;

    private Object status;

    private String message;

    private String path;

    private String method;

}
