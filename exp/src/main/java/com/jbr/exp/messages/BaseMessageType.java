package com.jbr.exp.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbr.exp.messages.incoming.BaseResponse;
import com.jbr.exp.messages.incoming.LoginResponse;
import com.jbr.exp.messages.incoming.NetworkResponse;
import com.jbr.exp.messages.incoming.SystemInfoResponse;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BaseMessageType {
    @JsonIgnore
    private final short messageType;

    protected BaseMessageType(short messageType) {
        this.messageType = messageType;
    }

    public short getMessageType() {
        return messageType;
    }

    private static Map<Short,Class<?>> typeToClassMap = new HashMap<>();

    private static Class<?> getClassForMessageType(short messageType) {
        // Find the class that supports this message type.
        try {
            Reflections reflections = new Reflections("com.jbr.exp.messages.incoming");

            Set<Class<? extends BaseResponse>> classes = reflections.getSubTypesOf(com.jbr.exp.messages.incoming.BaseResponse.class);
            for (Class<? extends BaseResponse> aClass : classes) {
                Class<?> clazz = Class.forName(aClass.getName());
                Constructor<?> ctor = clazz.getConstructor();
                BaseResponse response = (BaseResponse)ctor.newInstance();

                short type = response.getMessageType();
                if(type == messageType) {
                    return clazz;
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to find the right class",e);
        }

        throw new IllegalStateException("Unexpected message type received " + messageType);
    }

    public static BaseMessageType getMessageObject(byte[] incomingData, short messageType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Class<?> responseClass = null;

        // Is this a known message type?
        if(typeToClassMap.containsKey(messageType)) {
            responseClass = typeToClassMap.get(messageType);
        } else {
            responseClass = getClassForMessageType(messageType);
        }

        if(responseClass != null) {
            return (BaseMessageType) objectMapper.readValue(new String(incomingData, StandardCharsets.UTF_8), responseClass);
        }

        throw new IllegalStateException("Unexpected message type received " + messageType);
    }
}
