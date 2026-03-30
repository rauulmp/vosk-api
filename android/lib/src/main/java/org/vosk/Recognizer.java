package org.vosk;

import com.sun.jna.PointerType;
import java.io.IOException;

public class Recognizer extends PointerType implements AutoCloseable {
    /**
     * Creates the recognizer object.
     *
     * The recognizers process the speech and return text using shared model data
     * @param model       VoskModel containing static data for recognizer. Model can be
     *                    shared across recognizers, even running in different threads.
     * @param sampleRate The sample rate of the audio you are going to feed into the recognizer.
     *                    Make sure this rate matches the audio content, it is a common
     *                    issue causing accuracy problems.
     * @throws IOException if the recognizer could not be created
     */
    public Recognizer(Model model, float sampleRate) throws IOException {
        super(LibVosk.vosk_recognizer_new(model, sampleRate));

        if (getPointer() == null) {
            throw new IOException("Failed to create a recognizer");
        }
    }

    /**
     * Creates the recognizer object with speaker recognition.
     *
     *  With the speaker recognition mode the recognizer not just recognize
     *  text but also return speaker vectors one can use for speaker identification
     *
     *  @param model       VoskModel containing static data for recognizer. Model can be
     *                     shared across recognizers, even running in different threads.
     *  @param sampleRate The sample rate of the audio you are going to feed into the recognizer.
     *                     Make sure this rate matches the audio content, it is a common
     *                     issue causing accuracy problems.
     *  @param spkModel speaker model for speaker identification
     *  @throws IOException if the recognizer could not be created
     */
    public Recognizer(Model model, float sampleRate, SpeakerModel spkModel) throws IOException {
        super(LibVosk.vosk_recognizer_new_spk(model.getPointer(), sampleRate, spkModel.getPointer()));

        if (getPointer() == null) {
            throw new IOException("Failed to create a recognizer");
        }
    }

    /**
     * Creates the recognizer object with the phrase list.
     *
     *  Sometimes when you want to improve recognition accuracy and when you don't need
     *  to recognize large vocabulary you can specify a list of phrases to recognize. This
     *  will improve recognizer speed and accuracy but might return [unk] if user said
     *  something different.
     *
     *  Only recognizers with lookahead models support this type of quick configuration.
     *  Precompiled HCLG graph models are not supported.
     *
     *  @param model       VoskModel containing static data for recognizer. Model can be
     *                     shared across recognizers, even running in different threads.
     *  @param sampleRate The sample rate of the audio you are going to feed into the recognizer.
     *                     Make sure this rate matches the audio content, it is a common
     *                     issue causing accuracy problems.
     *  @param grammar The string with the list of phrases to recognize as JSON array of strings,
     *                 for example "["one two three four five", "[unk]"]".
     *  @throws IOException if the recognizer could not be created
     */
    public Recognizer(Model model, float sampleRate, String grammar) throws IOException {
        super(LibVosk.vosk_recognizer_new_grm(model.getPointer(), sampleRate, grammar));

        if (getPointer() == null) {
            throw new IOException("Failed to create a recognizer");
        }
    }

    /**
     * Configures recognizer to output n-best results.
     *
     * <pre>
     *   {
     *      "alternatives": [
     *          { "text": "one two three four five", "confidence": 0.97 },
     *          { "text": "one two three for five", "confidence": 0.03 },
     *      ]
     *   }
     * </pre>
     *
     * @param maxAlternatives - maximum alternatives to return from recognition results
     */
    public void setMaxAlternatives(int maxAlternatives) {
        LibVosk.vosk_recognizer_set_max_alternatives(this.getPointer(), maxAlternatives);
    }

    /** Enables words with times in the output
     *
     * <pre>
     *   "result" : [{
     *       "conf" : 1.000000,
     *       "end" : 1.110000,
     *       "start" : 0.870000,
     *       "word" : "what"
     *     }, {
     *       "conf" : 1.000000,
     *       "end" : 1.530000,
     *       "start" : 1.110000,
     *       "word" : "zero"
     *     }, {
     *       "conf" : 1.000000,
     *       "end" : 1.950000,
     *       "start" : 1.530000,
     *       "word" : "zero"
     *     }, {
     *       "conf" : 1.000000,
     *       "end" : 2.340000,
     *       "start" : 1.950000,
     *       "word" : "zero"
     *     }, {
     *       "conf" : 1.000000,
     *       "end" : 2.610000,
     *       "start" : 2.340000,
     *       "word" : "one"
     *     }],
     * </pre>
     *
     * @param words - boolean value
     */
    public void setWords(boolean words) {
        LibVosk.vosk_recognizer_set_words(this.getPointer(), words);
    }

    /** Enables words with phone level results and times in the output
     *
     * <pre>
     *   {
     *  *     "result" : [{
     *  *         "conf" : 0.998335,
     *  *         "end" : 0.450000,
     *  *         "phone_end" : [0.450000],
     *  *         "phone_label" : ["SIL"],
     *  *         "phone_start" : [0.000000],
     *  *         "start" : 0.000000,
     *  *         "word" : "<eps>"
     *  *       }, {
     *  *         "conf" : 0.998324,
     *  *         "end" : 0.600000,
     *  *         "phone_end" : [0.540000, 0.600000],
     *  *         "phone_label" : ["DH_B", "AH1_E"],
     *  *         "phone_start" : [0.450000, 0.540000],
     *  *         "start" : 0.450000,
     *  *         "word" : "THE"
     *  *       }, {
     *  *         "conf" : 0.574095,
     *  *         "end" : 1.200000,
     *  *         "phone_end" : [0.720000, 0.810000, 0.870000, 0.930000, 0.990000, 1.080000, 1.110000, 1.200000],
     *  *         "phone_label" : ["S_B", "T_I", "UW1_I", "D_I", "AH0_I", "N_I", "T_I", "S_E"],
     *  *         "phone_start" : [0.600000, 0.720000, 0.810000, 0.870000, 0.930000, 0.990000, 1.080000, 1.110000],
     *  *         "start" : 0.600000,
     *  *         "word" : "STUDENT'S"
     *  *       }, {
     *  *         "conf" : 0.923344,
     *  *         "end" : 1.260000,
     *  *         "phone_end" : [1.260111],
     *  *         "phone_label" : ["SIL"],
     *  *         "phone_start" : [1.200111],
     *  *         "start" : 1.200111,
     *  *         "word" : "<eps>"
     *  *       }, {
     *  *         "conf" : 1.000000,
     *  *         "end" : 1.800000,
     *  *         "phone_end" : [1.440000, 1.500000, 1.590000, 1.680000, 1.800000],
     *  *         "phone_label" : ["S_B", "T_I", "AH1_I", "D_I", "IY0_E"],
     *  *         "phone_start" : [1.260000, 1.440000, 1.500000, 1.590000, 1.680000],
     *  *         "start" : 1.260000,
     *  *         "word" : "STUDY"
     *  *       }, {
     *  *         "conf" : 1.000000,
     *  *         "end" : 1.860000,
     *  *         "phone_end" : [1.860000],
     *  *         "phone_label" : ["AH0_S"],
     *  *         "phone_start" : [1.800000],
     *  *         "start" : 1.800000,
     *  *         "word" : "A"
     *  *       }, {
     *  *         "conf" : 1.000000,
     *  *         "end" : 2.190000,
     *  *         "phone_end" : [1.980000, 2.100000, 2.190000],
     *  *         "phone_label" : ["L_B", "AA1_I", "T_E"],
     *  *         "phone_start" : [1.860000, 1.980000, 2.100000],
     *  *         "start" : 1.860000,
     *  *         "word" : "LOT"
     *  *       }, {
     *  *         "conf" : 1.000000,
     *  *         "end" : 2.880000,
     *  *         "phone_end" : [2.880000],
     *  *         "phone_label" : ["SIL"],
     *  *         "phone_start" : [2.190000],
     *  *         "start" : 2.190000,
     *  *         "word" : "<eps>"
     *  *       }],
     *  *     "text" : " THE STUDENT'S STUDY A LOT"
     *  *   }
     * </pre>
     *
     * @param resultOpts - String value
     */
    public void setResultOptions(String resultOpts) {
        LibVosk.vosk_recognizer_set_result_options(this.getPointer(), resultOpts);
    }

    /**
     * Like above return words and confidences in partial results.
     *
     * @param partial_words - boolean value
     */
    public void setPartialWords(boolean partial_words) {
        LibVosk.vosk_recognizer_set_partial_words(this.getPointer(), partial_words);
    }

    /**
     * Adds speaker model to already initialized recognizer.
     *
     * Can add speaker recognition model to already created recognizer.
     * Helps to initialize speaker recognition for grammar-based recognizer.
     *
     * @param spkModel Speaker recognition model
     */
    public void setSpeakerModel(SpeakerModel spkModel) {
        LibVosk.vosk_recognizer_set_spk_model(this.getPointer(), spkModel.getPointer());
    }

    /**
     * Accept and process new chunk of voice data.
     *
     *  @param data - audio data in PCM 16-bit mono format
     *  @param len - length of the audio data
     *  @return 1 if silence is occurred and you can retrieve a new utterance with result method
     *           0 if decoding continues
     *           -1 if exception occurred
     */
    public boolean acceptWaveForm(byte[] data, int len) {
        return LibVosk.vosk_recognizer_accept_waveform(this.getPointer(), data, len);
    }

    public boolean acceptWaveForm(short[] data, int len) {
        return LibVosk.vosk_recognizer_accept_waveform_s(this.getPointer(), data, len);
    }

    public boolean acceptWaveForm(float[] data, int len) {
        return LibVosk.vosk_recognizer_accept_waveform_f(this.getPointer(), data, len);
    }

    /**
     * Returns speech recognition result
     *
     * @return the result in JSON format which contains decoded line, decoded
     *          words, times in seconds and confidences. You can parse this result
     *          with any json parser
     *
     * <pre>
     *  {
     *    "text" : "what zero zero zero one"
     *  }
     * </pre>
     *
     * If alternatives enabled it returns result with alternatives, see also #setMaxAlternatives().
     *
     * If word times enabled returns word time, see also #setWordTimes().
     */
    public String getResult() {
        return LibVosk.vosk_recognizer_result(this.getPointer());
    }

    /**
     * Returns partial speech recognition.
     *
     * @return partial speech recognition text which is not yet finalized.
     *          result may change as recognizer process more data.
     *
     * <pre>
     * {
     *    "partial" : "cyril one eight zero"
     * }
     * </pre>
     */
    public String getPartialResult() {
        return LibVosk.vosk_recognizer_partial_result(this.getPointer());
    }

    /**
     * Returns speech recognition result. Same as result, but doesn't wait for silence.
     *  You usually call it in the end of the stream to get final bits of audio. It
     *  flushes the feature pipeline, so all remaining audio chunks got processed.
     *
     *  @return speech result in JSON format.
     */
    public String getFinalResult() {
        return LibVosk.vosk_recognizer_final_result(this.getPointer());
    }

    /**
     * Reconfigures recognizer to use grammar.
     *
     * @param grammar      Set of phrases in JSON array of strings or "[]" to use default model graph.
     * @see #Recognizer(Model, float, String)
     */
    public void setGrammar(String grammar) {
        LibVosk.vosk_recognizer_set_grm(this.getPointer(), grammar);
    }

    /**
     * Resets the recognizer.
     * Resets current results so the recognition can continue from scratch.
     */
    public void reset() {
        LibVosk.vosk_recognizer_reset(this.getPointer());
    }

    /**
     * Endpointer delay mode
     */
    public class EndpointerMode {
        public static final int DEFAULT = 0;
        public static final int SHORT = 1;
        public static final int LONG = 2;
        public static final int VERY_LONG = 3;
    }

    /**
     * Configures endpointer mode for recognizer
     */
    public void setEndpointerMode(int mode) {
        LibVosk.vosk_recognizer_set_endpointer_mode(this.getPointer(), mode);
    }

    /**
     * Set endpointer delays
     *
     * @param t_start_max     timeout for stopping recognition in case of initial silence (usually around 5.0)
     * @param t_end           timeout for stopping recognition in milliseconds after we recognized something (usually around 0.5 - 1.0)
     * @param t_max           timeout for forcing utterance end in milliseconds (usually around 20-30)
     **/
    public void setEndpointerDelays(float t_start_max, float t_end, float t_max) {
        LibVosk.vosk_recognizer_set_endpointer_delays(this.getPointer(), t_start_max, t_end, t_max);
    }

    /**
     * Releases recognizer object.
     * Underlying model is also unreferenced and if needed, released.
     */
    @Override
    public void close() {
        LibVosk.vosk_recognizer_free(this.getPointer());
    }
}
