package io.standardcore.http;

import java.util.Objects;

public class MarshallerInvoker {
    public static ApiRequest invoke(MarshallInfo marshallInfo){
        if(Objects.isNull(marshallInfo)) throw new IllegalArgumentException("marshallInfo");
        if(Objects.isNull(marshallInfo.getMarshaller())) throw new IllegalArgumentException("getMarshaller");
        if(!Objects.isNull(marshallInfo.getMarshallWares()) && !marshallInfo.getMarshallWares().isEmpty()){
            for(MarshallWare marshallWare : marshallInfo.getMarshallWares()){
                marshallWare.apply(marshallInfo);
            }
        }
        return marshallInfo.marshall();
    }
}
