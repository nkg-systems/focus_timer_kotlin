package com.example.gh0st_focus

import android.app.Application
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

data class TimerState(
    val totalMillis: Long = 25.minutes.inWholeMilliseconds,
    val remainingMillis: Long = 25.minutes.inWholeMilliseconds,
    val isRunning: Boolean = false,
    val autoRepeat: Boolean = false,
    val completedSessions: Int = 0
)

class TimerViewModel(app: Application) : AndroidViewModel(app) {

    private val _state = MutableStateFlow(TimerState())
    val state = _state.asStateFlow()

    private var timer: CountDownTimer? = null

    fun setMinutes(minutes: Int) {
        val ms = max(1, minutes).minutes.inWholeMilliseconds
        val wasRunning = _state.value.isRunning
        stopTimerInternal(keepProgress = false)
        _state.value = _state.value.copy(
            totalMillis = ms,
            remainingMillis = ms,
            isRunning = false
        )
        if (wasRunning) start()
    }

    fun toggleAutoRepeat() {
        _state.value = _state.value.copy(autoRepeat = !_state.value.autoRepeat)
    }

    fun start() {
        if (_state.value.isRunning) return
        if (_state.value.remainingMillis <= 0L) {
            _state.value = _state.value.copy(remainingMillis = _state.value.totalMillis)
        }
        startTimerInternal()
    }

    fun pause() {
        stopTimerInternal(keepProgress = true)
    }

    fun reset() {
        stopTimerInternal(keepProgress = false)
        _state.value = _state.value.copy(remainingMillis = _state.value.totalMillis, isRunning = false)
    }

    private fun startTimerInternal() {
        val duration = _state.value.remainingMillis
        timer?.cancel()
        timer = object : CountDownTimer(duration, 250L) {
            override fun onTick(millisUntilFinished: Long) {
                _state.value = _state.value.copy(
                    remainingMillis = millisUntilFinished,
                    isRunning = true
                )
            }

            override fun onFinish() {
                _state.value = _state.value.copy(
                    remainingMillis = 0L,
                    isRunning = false,
                    completedSessions = _state.value.completedSessions + 1
                )
                notifyComplete()

                if (_state.value.autoRepeat) {
                    _state.value = _state.value.copy(remainingMillis = _state.value.totalMillis)
                    startTimerInternal()
                }
            }
        }.start()
        _state.value = _state.value.copy(isRunning = true)
    }

    private fun stopTimerInternal(keepProgress: Boolean) {
        timer?.cancel()
        timer = null
        _state.value = _state.value.copy(isRunning = false).let {
            if (keepProgress) it else it.copy(remainingMillis = it.totalMillis)
        }
    }

    private fun notifyComplete() {
        viewModelScope.launch {
            val vib = getApplication<Application>().getSystemService(Vibrator::class.java)
            vib?.let {
                try {
                    it.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
                } catch (_: Throwable) { }
            }
            Notifications.showFinished(getApplication())
        }
    }

    companion object {
        fun format(ms: Long): String {
            val totalSeconds = (ms / 1000.0).toInt()
            val m = totalSeconds / 60
            val s = totalSeconds % 60
            return "%02d:%02d".format(m, s)
        }
        fun toMinutes(ms: Long): Int = (ms / 1000 / 60).toInt()
        fun asDuration(ms: Long): Duration = ms.seconds
    }
}
