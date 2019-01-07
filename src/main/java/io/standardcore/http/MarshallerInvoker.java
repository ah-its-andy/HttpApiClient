package io.standardcore.http;

import java.util.Objects;

public class MarshallerInvoker {
    public static ApiRequest invoke(MarshallInfo marshallInfo){
        if(Objects.isNull(marshallInfo)) throw new IllegalArgumentException("marshallInfo");
        if(Objects.isNull(marshallInfo.marshaller())) throw new IllegalArgumentException("marshaller");
        if(!Objects.isNull(marshallInfo.marshallWares()) && !marshallInfo.marshallWares().isEmpty()){
            for(MarshallWare marshallWare : marshallInfo.marshallWares()){
                marshallWare.apply(marshallInfo);
            }
        }
        return marshallInfo.marshall();
    }
}
