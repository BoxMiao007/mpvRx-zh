package app.gyrolet.mpvrx.ui.player

import androidx.annotation.StringRes
import app.gyrolet.mpvrx.R
import app.gyrolet.mpvrx.preferences.DecoderPreferences
import app.gyrolet.mpvrx.preferences.preference.Preference

enum class PlayerOrientation(
  @StringRes val titleRes: Int,
) {
  Free(R.string.pref_player_orientation_free),
  Video(R.string.pref_player_orientation_video),
  Portrait(R.string.pref_player_orientation_portrait),
  ReversePortrait(R.string.pref_player_orientation_reverse_portrait),
  SensorPortrait(R.string.pref_player_orientation_sensor_portrait),
  Landscape(R.string.pref_player_orientation_landscape),
  ReverseLandscape(R.string.pref_player_orientation_reverse_landscape),
  SensorLandscape(R.string.pref_player_orientation_sensor_landscape),
}

enum class VideoAspect(
  @StringRes val titleRes: Int,
) {
  Crop(R.string.player_aspect_crop),
  Fit(R.string.player_aspect_fit),
  Stretch(R.string.player_aspect_stretch),
}

enum class SingleActionGesture(
  @StringRes val titleRes: Int,
) {
  None(R.string.pref_gesture_double_tap_none),
  Seek(R.string.pref_gesture_double_tap_seek),
  PlayPause(R.string.pref_gesture_double_tap_play),
  Custom(R.string.pref_gesture_double_tap_custom),
}

enum class CustomKeyCodes(
  val keyCode: String,
) {
  DoubleTapLeft("MBTN_LEFT_DBL"),
  DoubleTapCenter("MBTN_MID_DBL"),
  DoubleTapRight("MBTN_RIGHT_DBL"),
  MediaPrevious("PREV"),
  MediaPlay("PLAYPAUSE"),
  MediaNext("NEXT"),
}

enum class Decoder(
  @StringRes val titleRes: Int,
  val value: String,
) {
  AutoCopy(R.string.enum_auto, "auto-copy"),
  Auto(R.string.enum_auto, "auto"),
  SW(R.string.enum_sw, "no"),
  HW(R.string.enum_hw, "mediacodec-copy"),
  HWPlus(R.string.enum_hw_plus, "mediacodec"),
  ;

  companion object {
    fun getDecoderFromValue(value: String): Decoder = Decoder.entries.first { it.value == value }
  }
}

enum class Debanding(
  @StringRes val titleRes: Int,
) {
  None(R.string.player_sheets_deband_none),
  CPU(R.string.player_sheets_deband_cpu),
  GPU(R.string.player_sheets_deband_gpu),
}

enum class MPVProfile(
  @StringRes val displayNameRes: Int,
  val value: String,
) {
  Fast(R.string.enum_fast, "fast"),
  Default(R.string.player_controls_default, "default"),
  HighQuality(R.string.enum_high_quality, "high-quality"),
  GpuHQ(R.string.enum_gpu_hq, "gpu-hq"),
  LowLatency(R.string.enum_low_latency, "low-latency"),
  SwFast(R.string.enum_sw_fast, "sw-fast"),
  ;

  override fun toString(): String = value

  companion object {
    fun fromValue(value: String): MPVProfile = entries.firstOrNull { it.value == value } ?: Fast
  }
}

enum class Sheets {
  None,
  PlaybackSpeed,
  SubtitleTracks,
  OnlineSubtitleSearch,
  AudioTracks,
  Chapters,
  Decoders,
  More,
  VideoZoom,
  AspectRatios,
  Playlist,
  AmbientConfig,
  FrameNavigation,
}

enum class Panels {
  None,
  SubtitleSettings,
  SubtitleDelay,
  AudioDelay,
  VideoFilters,
  LuaScripts,
  HdrScreenOutput,
}

sealed class PlayerUpdates {
  data object None : PlayerUpdates()

  data object MultipleSpeed : PlayerUpdates()

  data class DynamicSpeedControl(
    val speed: Float,
    val showFullOverlay: Boolean = true,
  ) : PlayerUpdates()

  data object AspectRatio : PlayerUpdates()

  data object VideoZoom : PlayerUpdates()

  data class SubtitleZoom(
    val scale: Float,
  ) : PlayerUpdates()

  data class HorizontalSeek(
    val currentTime: String,
    val seekDelta: String,
  ) : PlayerUpdates()

  data class ShowText(
    val value: String,
  ) : PlayerUpdates()

  data class ProviderStatusText(
    val value: String,
  ) : PlayerUpdates()

  data class RepeatMode(
    val mode: app.gyrolet.mpvrx.ui.player.RepeatMode,
  ) : PlayerUpdates()

  data class Shuffle(
    val enabled: Boolean,
  ) : PlayerUpdates()

  data class FrameInfo(
    val currentFrame: Int,
    val totalFrames: Int,
  ) : PlayerUpdates()
}

/**
 * Filter presets for quick video color adjustments.
 * Each preset defines specific values for brightness, saturation, contrast, gamma, hue, and sharpness.
 * Sharpness uses MPV's 'sharpen' property which ranges from -5 (blur) to 5 (sharp).
 */
enum class FilterPreset(
  @StringRes val displayNameRes: Int,
  val description: String,
  val brightness: Int,
  val saturation: Int,
  val contrast: Int,
  val gamma: Int,
  val hue: Int,
  val sharpness: Int,
) {
  NONE(
    displayNameRes = R.string.player_controls_none,
    description = "Default settings with no adjustments",
    brightness = 0,
    saturation = 0,
    contrast = 0,
    gamma = 0,
    hue = 0,
    sharpness = 0,
  ),
  VIVID(
    displayNameRes = R.string.enum_vivid,
    description = "Enhanced colors with crisp details",
    brightness = 5,
    saturation = 25,
    contrast = 15,
    gamma = 0,
    hue = 0,
    sharpness = 0,
  ),
  WARM_TONE(
    displayNameRes = R.string.enum_warm_tone,
    description = "Warmer colors with golden tint",
    brightness = 5,
    saturation = 10,
    contrast = 5,
    gamma = 5,
    hue = 15,
    sharpness = 0,
  ),
  COOL_TONE(
    displayNameRes = R.string.enum_cool_tone,
    description = "Cooler colors with blue tint",
    brightness = 0,
    saturation = 5,
    contrast = 10,
    gamma = 0,
    hue = -15,
    sharpness = 0,
  ),
  SOFT_PASTEL(
    displayNameRes = R.string.enum_soft_pastel,
    description = "Soft, muted colors with gentle look",
    brightness = 10,
    saturation = -15,
    contrast = -10,
    gamma = 5,
    hue = 0,
    sharpness = 0,
  ),
  CINEMATIC(
    displayNameRes = R.string.enum_cinematic,
    description = "Film-like color grading with depth",
    brightness = -5,
    saturation = -10,
    contrast = 20,
    gamma = -5,
    hue = 5,
    sharpness = 0,
  ),
  DRAMATIC(
    displayNameRes = R.string.enum_dramatic,
    description = "High contrast dramatic look",
    brightness = -10,
    saturation = 15,
    contrast = 30,
    gamma = -10,
    hue = 0,
    sharpness = 0,
  ),
  NIGHT_MODE(
    displayNameRes = R.string.enum_night_mode,
    description = "Reduced brightness for dark environments",
    brightness = -20,
    saturation = -5,
    contrast = 5,
    gamma = -10,
    hue = 0,
    sharpness = 0,
  ),
  NOSTALGIC(
    displayNameRes = R.string.enum_nostalgic,
    description = "Vintage film look with soft focus",
    brightness = 5,
    saturation = -20,
    contrast = 10,
    gamma = 0,
    hue = 20,
    sharpness = 0,
  ),
  GHIBLI_STYLE(
    displayNameRes = R.string.enum_ghibli_style,
    description = "Soft, dreamy anime colors",
    brightness = 8,
    saturation = 15,
    contrast = -5,
    gamma = 5,
    hue = 5,
    sharpness = 0,
  ),
  NEON_POP(
    displayNameRes = R.string.enum_neon_pop,
    description = "Vibrant neon-like colors with edge",
    brightness = 5,
    saturation = 40,
    contrast = 20,
    gamma = 0,
    hue = 0,
    sharpness = 0,
  ),
  DEEP_BLACK(
    displayNameRes = R.string.enum_deep_black,
    description = "Enhanced blacks for OLED displays",
    brightness = -15,
    saturation = 5,
    contrast = 25,
    gamma = -15,
    hue = 0,
    sharpness = 0,
  ),
}

enum class VideoFilters(
  @StringRes val titleRes: Int,
  val preference: (DecoderPreferences) -> Preference<Int>,
  val mpvProperty: String,
  val min: Int = -100,
  val max: Int = 100,
) {
  BRIGHTNESS(
    R.string.player_sheets_filters_brightness,
    { it.brightnessFilter },
    "brightness",
  ),
  SATURATION(
    R.string.player_sheets_filters_Saturation,
    { it.saturationFilter },
    "saturation",
  ),
  CONTRAST(
    R.string.player_sheets_filters_contrast,
    { it.contrastFilter },
    "contrast",
  ),
  GAMMA(
    R.string.player_sheets_filters_gamma,
    { it.gammaFilter },
    "gamma",
  ),
  HUE(
    R.string.player_sheets_filters_hue,
    { it.hueFilter },
    "hue",
  ),
  SHARPNESS(
    titleRes = R.string.player_sheets_filters_sharpness,
    preference = { it.sharpnessFilter },
    mpvProperty = "sharpen",
    min = -5,
    max = 5,
  ),
}

enum class DebandSettings(
  @StringRes val titleRes: Int,
  val preference: (DecoderPreferences) -> Preference<Int>,
  val mpvProperty: String,
  val start: Int,
  val end: Int,
) {
  Iterations(
    R.string.player_sheets_deband_iterations,
    { it.debandIterations },
    "deband-iterations",
    0,
    16,
  ),
  Threshold(
    R.string.player_sheets_deband_threshold,
    { it.debandThreshold },
    "deband-threshold",
    0,
    200,
  ),
  Range(
    R.string.player_sheets_deband_range,
    { it.debandRange },
    "deband-range",
    1,
    64,
  ),
  Grain(
    R.string.player_sheets_deband_grain,
    { it.debandGrain },
    "deband-grain",
    0,
    200,
  ),
}

/** Controls whether the playback service shows a notification, and which style it uses. */
enum class NotificationStyle(val displayName: String) {
  /** Do not show any playback notification. */
  None("No Notification"),

  /** Classic MediaStyle with transport controls rendered by the system. */
  Media("Media Controls"),

  /** Progress-centric style with chapter segment indicators (Android 16+ only). */
  Progress("Progress with Chapters"),

  ;

  fun isSupportedOn(sdkInt: Int): Boolean =
    when (this) {
      Progress -> sdkInt >= 36
      None, Media -> true
    }
}

