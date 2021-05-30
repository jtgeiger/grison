package com.sibilantsolutions.grison.net.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;

import retrofit2.Converter;
import retrofit2.Retrofit;

public class SetTimeDtoConverterFactory extends Converter.Factory {

    @Override
    public Converter<SetTimeDto, String> stringConverter(@Nonnull Type type, @Nonnull Annotation[] annotations, @Nonnull Retrofit retrofit) {
        return setTimeDto -> String.valueOf(setTimeDto.unixEpochSeconds());
    }
}
