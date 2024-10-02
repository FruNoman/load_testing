package com.frunoman.load.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;

public abstract class BaseJson {
    @JsonIgnore
    public static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JodaModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);



    @JsonIgnore
    private PrettyPrinter prettyPrinter() {
        return new DefaultPrettyPrinter()
                .withSpacesInObjectEntries()
                .withArrayIndenter(new DefaultIndenter("  ", DefaultIndenter.SYS_LF))
                .createInstance();
    }

    @JsonIgnore
    public String objectToJson(boolean pretty) {
        try {
            return pretty ? objectMapper().writer(prettyPrinter()).writeValueAsString(this)
                    : objectMapper().writer().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    @JsonIgnore
    public <T> T jsonToObject(String json) {
        if (json == null) {
            throw new UnsupportedOperationException("");
        }
        try {
            return objectMapper().reader().forType(this.getClass()).readValue(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @JsonIgnore
    public abstract ObjectMapper objectMapper();
}
