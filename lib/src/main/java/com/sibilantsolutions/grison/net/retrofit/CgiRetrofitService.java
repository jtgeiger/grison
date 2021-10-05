package com.sibilantsolutions.grison.net.retrofit;

import com.sibilantsolutions.grison.driver.foscam.domain.DecoderControlE;
import io.reactivex.rxjava3.core.Single;
import retrofit2.adapter.rxjava3.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CgiRetrofitService {
    @GET("check_user.cgi")
    Single<Result<String>> checkUser();


    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.UP)
    Single<Result<String>> up();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.UP)
    Single<Result<String>> up(@Query("degree") int degree);

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.UP + "&onestep=1")
    Single<Result<String>> upOneStep();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.STOP_UP)
    Single<Result<String>> upStop();


    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.DOWN)
    Single<Result<String>> down();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.DOWN)
    Single<Result<String>> down(@Query("degree") int degree);

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.DOWN + "&onestep=1")
    Single<Result<String>> downOneStep();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.STOP_DOWN)
    Single<Result<String>> downStop();


    //Left & right are reversed from the docs.

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.RIGHT)
    Single<Result<String>> left();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.RIGHT)
    Single<Result<String>> left(@Query("degree") int degree);

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.RIGHT + "&onestep=1")
    Single<Result<String>> leftOneStep();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.STOP_RIGHT)
    Single<Result<String>> leftStop();

    //Left & right are reversed from the docs.

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.LEFT)
    Single<Result<String>> right();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.LEFT)
    Single<Result<String>> right(@Query("degree") int degree);

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.LEFT + "&onestep=1")
    Single<Result<String>> rightOneStep();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.STOP_LEFT)
    Single<Result<String>> rightStop();


    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.DISCONNECT_SWITCH_2)
    Single<Result<String>> center();


    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_1)
    Single<Result<String>> setPreset1();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_1)
    Single<Result<String>> goToPreset1();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_2)
    Single<Result<String>> setPreset2();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_2)
    Single<Result<String>> goToPreset2();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_3)
    Single<Result<String>> setPreset3();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_3)
    Single<Result<String>> goToPreset3();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_4)
    Single<Result<String>> setPreset4();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_4)
    Single<Result<String>> goToPreset4();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_5)
    Single<Result<String>> setPreset5();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_5)
    Single<Result<String>> goToPreset5();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_6)
    Single<Result<String>> setPreset6();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_6)
    Single<Result<String>> goToPreset6();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_7)
    Single<Result<String>> setPreset7();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_7)
    Single<Result<String>> goToPreset7();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_8)
    Single<Result<String>> setPreset8();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_8)
    Single<Result<String>> goToPreset8();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_9)
    Single<Result<String>> setPreset9();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_9)
    Single<Result<String>> goToPreset9();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_10)
    Single<Result<String>> setPreset10();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_10)
    Single<Result<String>> goToPreset10();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_11)
    Single<Result<String>> setPreset11();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_11)
    Single<Result<String>> goToPreset11();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_12)
    Single<Result<String>> setPreset12();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_12)
    Single<Result<String>> goToPreset12();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_13)
    Single<Result<String>> setPreset13();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_13)
    Single<Result<String>> goToPreset13();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_14)
    Single<Result<String>> setPreset14();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_14)
    Single<Result<String>> goToPreset14();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_15)
    Single<Result<String>> setPreset15();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_15)
    Single<Result<String>> goToPreset15();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_16)
    Single<Result<String>> setPreset16();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_16)
    Single<Result<String>> goToPreset16();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_17)
    Single<Result<String>> setPreset17();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_17)
    Single<Result<String>> goToPreset17();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_18)
    Single<Result<String>> setPreset18();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_18)
    Single<Result<String>> goToPreset18();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_19)
    Single<Result<String>> setPreset19();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_19)
    Single<Result<String>> goToPreset19();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_20)
    Single<Result<String>> setPreset20();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_20)
    Single<Result<String>> goToPreset20();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_21)
    Single<Result<String>> setPreset21();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_21)
    Single<Result<String>> goToPreset21();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_22)
    Single<Result<String>> setPreset22();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_22)
    Single<Result<String>> goToPreset22();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_23)
    Single<Result<String>> setPreset23();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_23)
    Single<Result<String>> goToPreset23();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_24)
    Single<Result<String>> setPreset24();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_24)
    Single<Result<String>> goToPreset24();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_25)
    Single<Result<String>> setPreset25();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_25)
    Single<Result<String>> goToPreset25();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_26)
    Single<Result<String>> setPreset26();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_26)
    Single<Result<String>> goToPreset26();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_27)
    Single<Result<String>> setPreset27();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_27)
    Single<Result<String>> goToPreset27();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_28)
    Single<Result<String>> setPreset28();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_28)
    Single<Result<String>> goToPreset28();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_29)
    Single<Result<String>> setPreset29();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_29)
    Single<Result<String>> goToPreset29();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_30)
    Single<Result<String>> setPreset30();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_30)
    Single<Result<String>> goToPreset30();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_31)
    Single<Result<String>> setPreset31();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_31)
    Single<Result<String>> goToPreset31();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.SET_PRESET_32)
    Single<Result<String>> setPreset32();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.GO_TO_PRESET_32)
    Single<Result<String>> goToPreset32();


    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.IO_OUTPUT_LOW)
    Single<Result<String>> irAuto();

    @GET("decoder_control.cgi?command=" + DecoderControlE.Values.IO_OUTPUT_HIGH)
    Single<Result<String>> irOff();

    @GET("set_datetime.cgi")
    Single<Result<String>> setTime(@Query("now") SetTimeDto setTimeDto);

    @GET("get_status.cgi")
    Single<Result<String>> getStatus();

    @GET("get_params.cgi")
    Single<Result<String>> getParams();

}
