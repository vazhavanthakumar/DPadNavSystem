package com.ev.dpadnavsystem.helper

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import com.ev.dpadnavsystem.utils.SpeechCallBack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DPSpeechRecognizer @Inject constructor(private val context: Context) {

    private lateinit var speechRecognizer: SpeechRecognizer

    private val _speechCallBack = MutableStateFlow(SpeechCallBack.NOTHING)
    val speechCallBack = _speechCallBack.asStateFlow()

    fun init() {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        // Initialize speech recognition listener
        speechRecognizer.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {
                Log.e("TAG", "onBeginningOfSpeech: ")
            }

            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {
                Log.e("TAG", "onEndOfSpeech: ")
            }

            override fun onError(error: Int) {
                Log.e("TAG", "onError: $error")
                Toast.makeText(context, "Please try again with Voice command", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onResults(results: Bundle?) {
                // Get the recognized speech
                val recognizedText: ArrayList<String> =
                    results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
                val data = recognizedText.joinToString { ", " }
                Log.e("TAG", "onResults:  $data")
                val command = recognizedText[0].toLowerCase()

                // Trigger method based on the voice command
                if (command.contains("open menu")) {
                    Log.e("TAG", "onResults: open fragment")
                    _speechCallBack.value = SpeechCallBack.TYPE_OPEN_MENU
                } else if (command.contains("close menu")) {
                    Log.e("TAG", "onResults: close fragment")
                    _speechCallBack.value = SpeechCallBack.TYPE_CLOSE_MENU
                } else {
                    Log.e("TAG", "onResults: command not found")
                    _speechCallBack.value = SpeechCallBack.NOTHING
                    Toast.makeText(context, "onResults: command not found", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun startRecognizer() {
        // Start listening for voice input
        if (!::speechRecognizer.isInitialized) {
            init()
            startRecognizer()
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            "Say 'open menu' to open the fragment or 'close menu' to close it."
        )
        speechRecognizer.startListening(intent)
    }

    fun destroy() {
        _speechCallBack.value = SpeechCallBack.NOTHING
    }

}