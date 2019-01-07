package io.standardcore.http;

import java.util.Objects;

public class UnmarshallInvoker {
    public static ApiResponse invoke(UnmarshallInfo unmarshallInfo){
        if(Objects.isNull(unmarshallInfo)) throw new IllegalArgumentException("unmarshallInfo");
        if(Objects.isNull(unmarshallInfo.unmarshaller())) throw new IllegalArgumentException("unmarshaller");
        if(!Objects.isNull(unmarshallInfo.unmarshallWares()) && !unmarshallInfo.unmarshallWares().isEmpty()){
            for(UnmarshallWare unmarshallWare : unmarshallInfo.unmarshallWares()){
                unmarshallWare.apply(unmarshallInfo);
            }
        }
        return unmarshallInfo.unmarshall();
    }
}
