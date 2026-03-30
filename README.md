# Vosk API (Phoneme-Level Alignment Fork)

This is a fork of the original [Vosk API](https://github.com/alphacep/vosk-api) that adds **phoneme-level alignment with timestamps** to speech recognition results.

In addition to standard word-level transcription, this fork can return **per-word phoneme segmentation**, including start and end times for each phoneme.

## ✨ What's new

- Phoneme-level timestamps aligned with recognized words
- Per-word phoneme segmentation (`phone_label`, `phone_start`, `phone_end`)
- Real-time compatible (no post-processing required)
- Fully backward compatible with standard Vosk models and API
<br>

## ⚙️ Enabling Phoneme-Level Output
### C/C++
`vosk_recognizer_set_result_options(recognizer, "phones");`

### Android
`recognizer.setResultOptions("phones");`

<br>

## 🔧 Example
```
{
  "conf" : 1.000000,
  "end" : 1.800000,
  "phone_end" : [1.440000, 1.500000, 1.590000, 1.680000, 1.800000],
  "phone_label" : ["S_B", "T_I", "AH1_I", "D_I", "IY0_E"],
  "phone_start" : [1.260000, 1.440000, 1.500000, 1.590000, 1.680000],
  "start" : 1.260000,
  "word" : "STUDY"
}
```

<br><br>


# Vosk Speech Recognition Toolkit

Vosk is an offline open source speech recognition toolkit. It enables
speech recognition for 20+ languages and dialects - English, Indian
English, German, French, Spanish, Portuguese, Chinese, Russian, Turkish,
Vietnamese, Italian, Dutch, Catalan, Arabic, Greek, Farsi, Filipino,
Ukrainian, Kazakh, Swedish, Japanese, Esperanto, Hindi, Czech, Polish.
More to come.

Vosk models are small (50 Mb) but provide continuous large vocabulary
transcription, zero-latency response with streaming API, reconfigurable
vocabulary and speaker identification.

Speech recognition bindings implemented for various programming languages
like Python, Java, Node.JS, C#, C++, Rust, Go and others.

Vosk supplies speech recognition for chatbots, smart home appliances,
virtual assistants. It can also create subtitles for movies,
transcription for lectures and interviews.

Vosk scales from small devices like Raspberry Pi or Android smartphone to
big clusters.

# Documentation

For installation instructions, examples and documentation visit [Vosk
Website](https://alphacephei.com/vosk).
