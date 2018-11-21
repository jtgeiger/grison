package com.sibilantsolutions.grison.driver.foscam.dto;

import com.google.auto.value.AutoValue;
import com.sibilantsolutions.grison.driver.foscam.type.FosInt32;

@AutoValue
public abstract class LoginReqAudioVideoTextDto implements FoscamTextDto {

    public abstract FosInt32 dataConnectionId();

    @Override
    public final FoscamOpCode opCode() {
        return FoscamOpCode.Login_Req_AudioVideo;
    }

    @Override
    public final int encodedLength() {
        return 4;
    }

    public static Builder builder() {
        return new AutoValue_LoginReqAudioVideoTextDto.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder dataConnectionId(FosInt32 dataConnectionId);

        public abstract LoginReqAudioVideoTextDto build();
    }
}
