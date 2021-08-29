package to.chip.dogsgallery.view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import to.chip.dogsgallery.R
import java.util.*

class CDGLetterImageView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    private var mLetter = 0.toChar()
    private var mTextPaint: Paint? = null
    private var mBackgroundPaint: Paint? = null
    private var mTextColor: Int = Color.WHITE
    private var isOval = false

    init {
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mTextPaint!!.color = mTextColor
        mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mBackgroundPaint!!.style = Paint.Style.FILL
    }

    fun getLetter(): Char {
        return mLetter
    }

    fun setLetter(letter: Char) {
        mLetter = letter
        invalidate()
    }

    fun setBackgroundColorNumber(number: Int) {
        val colorsArr = resources.getStringArray(R.array.colors)
        mBackgroundPaint!!.color = Color.parseColor(colorsArr[number])
    }

    fun getTextColor(): Int {
        return mTextColor
    }

    fun setTextColor(textColor: Int) {
        mTextColor = textColor
        invalidate()
    }

    fun setOval(oval: Boolean) {
        isOval = oval
    }

    fun isOval(): Boolean {
        return isOval
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (drawable == null) {
            mTextPaint?.textSize = height - getTextPadding() * 2
            if (isOval()) {
                canvas.drawCircle(
                    canvas.getWidth() / 2f,
                    canvas.getHeight() / 2f,
                    Math.min(canvas.getWidth(), canvas.getHeight()) / 2f,
                    mBackgroundPaint!!
                )
            } else {
                mBackgroundPaint?.let {
                    canvas.drawRect(0f, 0f,
                        width.toFloat(), height.toFloat(), it
                    )
                }
            }

            val textBounds = Rect()
            mTextPaint?.getTextBounds(mLetter.toString(), 0, 1, textBounds)
            val textWidth: Float = mTextPaint!!.measureText(mLetter.toString())
            val textHeight: Int = textBounds.height()

            canvas.drawText(
                mLetter.toString(), getWidth() / 2f - textWidth / 2f,
                getHeight() / 2f + textHeight / 2f, mTextPaint!!
            )
        }
    }

    private fun getTextPadding(): Float {
        return 8 * resources.displayMetrics.density
    }

    private fun randomColor(): Int {
        val random = Random()
        val colorsArr = resources.getStringArray(R.array.colors)
        return Color.parseColor(colorsArr[random.nextInt(colorsArr.size)])
    }
}