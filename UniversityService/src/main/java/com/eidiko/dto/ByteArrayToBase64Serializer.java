package com.eidiko.dto;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.jsonwebtoken.io.IOException;

public class ByteArrayToBase64Serializer extends JsonSerializer<byte[]>{
	@Override
    public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException, java.io.IOException {
        gen.writeString(Base64.getEncoder().encodeToString(value));
    }

}
