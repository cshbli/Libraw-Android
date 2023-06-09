package com.deepvi.libraw

import android.graphics.Bitmap
import android.util.Log

object Libraw {
    private const val COLORSPACE_SRGB = 0
    private const val COLORSPACE_ADOBE = 1
    private const val COLORSPACE_WIDE_GAMUT = 2
    private const val COLORSPACE_PRO_PHOTO = 3
    fun decodeAsBitmap(file: String?, halfSize: Boolean): Bitmap? {
        val result = open(file)
        setOutputBps(8)
        setQuality(2)
        setHalfSize(halfSize)
        var b: Bitmap? = null
        if (result != 0) return b
        val pixels = pixels8
        Log.d("libraw", "pixels8 " + (pixels != null))
        if (pixels != null) {
            Log.d("libraw", "pixels8 size " + bitmapWidth + "x" + bitmapHeight + " " + pixels.size)
            b = Bitmap.createBitmap(pixels, bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888)
        }
        cleanup()
        return b
    }

    external fun open(file: String?): Int
    external fun cleanup()
    val bitmapWidth: Int
        external get
    val bitmapHeight: Int
        external get
    val width: Int
        external get
    val height: Int
        external get
    val orientation: Int
        external get
    val colors: Int
        external get
    val pixels8: IntArray?
        external get
    val pixels16: Long
        external get
    val daylightMultiplier: FloatArray?
        external get
    val whitebalanceMultiplier: FloatArray?
        external get
    val camRgb: FloatArray?
        external get
    val rgbCam: FloatArray?
        external get
    val camMatrix: FloatArray?
        external get

    external fun setUserMul(r: Float, g1: Float, b: Float, g2: Float)
    external fun setAutoWhitebalance(autoWhitebalance: Boolean)
    external fun setHighlightMode(highlightMode: Int)
    external fun setAutoBrightness(autoBrightness: Boolean)
    external fun setOutputColorSpace(colorSpace: Int)
    external fun setOutputBps(outputBps: Int)
    external fun setQuality(quality: Int)
    external fun setHalfSize(halfSize: Boolean)
    external fun setGamma(g1: Double, g2: Double)
    external fun getThumbnail(file: String?): ByteArray?
    external fun setUseCameraMatrix(useCameraMatrix: Int) // 0 = off, 1 = if auto whitebalance, 3 = always

    // 0 = off, 1 = if auto whitebalance, 3 = always
    val cameraList: String?
        external get

    init {
        System.loadLibrary("libraw")
    }
}