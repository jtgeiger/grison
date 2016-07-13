package com.sibilantsolutions.grison.sound.adpcm;

import javax.sound.sampled.AudioFormat;

import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.BIG_ENDIAN;
import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.CHANNELS;
import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.ENCODING;
import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.FRAME_RATE;
import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.FRAME_SIZE;
import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.SAMPLE_RATE;
import static com.sibilantsolutions.grison.sound.adpcm.AdpcmDecoder.SAMPLE_SIZE_IN_BITS;

public interface AdpcmDecoderAudioFormat {

    AudioFormat decodeAudioFormat = new AudioFormat(new AudioFormat.Encoding(ENCODING), SAMPLE_RATE,
            SAMPLE_SIZE_IN_BITS, CHANNELS, FRAME_SIZE, FRAME_RATE, BIG_ENDIAN);

}
