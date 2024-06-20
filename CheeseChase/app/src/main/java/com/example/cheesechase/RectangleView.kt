package com.example.cheesechase

import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import android.graphics.Color
import androidx.activity.ComponentDialog
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import java.security.KeyStore.TrustedCertificateEntry
import kotlin.math.abs
import kotlin.random.Random

class RectangleView(context: Context,attrs: AttributeSet?=null) : View(context,attrs) {
    private var hitCount by mutableIntStateOf(0)
    var stateCaught = 0
    var stateStart=true
    private val paint = Paint()
    var ylane1 = 0f
    var ylane3 = 0f
    var ylane2 = 0f

    private var yBoost1Lane1 = -height.toFloat()
    private var yBoost2Lane3= -height.toFloat()

    private lateinit var animator: ValueAnimator
    private var state: Int = 2
    private var yPosition = 1700f
    private var yTomPosition = 2000f
    private var rectangle1 = 0f
    private var hitSound: MediaPlayer? = null
    private var trackChangeSound: MediaPlayer? = null
    private var gameOverSound: MediaPlayer? = null
    private var isSoundPlayed = false
    var gameOver by mutableStateOf(false)
    private var speed =25000
    private var k:Int = 0
    init {
        hitSound = MediaPlayer.create(context, R.raw.hit)
        trackChangeSound = MediaPlayer.create(context, R.raw.trackchange)
        gameOverSound = MediaPlayer.create(context, R.raw.gameover)
        setOnClickListener {}

    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        movement()
    }
    private fun movement(){
        animator = ValueAnimator.ofFloat(-(height * 8 + 400).toFloat(), (height * 4.9).toFloat())
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            if (!gameOver) {
                ylane1 = animation.animatedValue as Float
                ylane2 = ylane1
                ylane3 = ylane1
                stateCaught+=1
                if (ylane1 >= height.toFloat()) {
                    ylane1 = -(height * 8 + 400).toFloat()
                }
                if(stateCaught>1000) {
                    animator.pause()
                    animator.duration=20000.toLong()
                    animator.resume()
                }
                if(stateCaught>2500){
                    animator.pause()
                    animator.duration=15000
                    animator.resume()
                }
                if(stateCaught>5000){
                    animator.duration=5000
                }
                invalidate()
            }

        }
        animator.duration=25000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val rect1 = width / 3.toFloat()
        val rect2 = rect1 * 2
        val rect3 = width.toFloat()
        val cheeseBack = 0xFFEBC063.toInt()
        val cheeseFront = 0xFFF7D68F.toInt()
        val jerry = ContextCompat.getDrawable(context, R.drawable.jerry)
        val bitmapOld = (jerry as BitmapDrawable).bitmap
        val bitmap = Bitmap.createScaledBitmap(bitmapOld, (width / 4).toInt(), (width / 4).toInt(), true)
        val tom = ContextCompat.getDrawable(context, R.drawable.tom)
        val bitTomOld = (tom as BitmapDrawable).bitmap
        val bitTom = Bitmap.createScaledBitmap(bitTomOld, (width / 3).toInt(), (width / 3).toInt(), true)
        val smile=ContextCompat.getDrawable(context,R.drawable.smile)
        val bitSmileOld = (smile as BitmapDrawable).bitmap
        val bitSmile = Bitmap.createScaledBitmap(bitSmileOld, (width / 5).toInt(), (width / 5).toInt(), true)
        val shield=ContextCompat.getDrawable(context,R.drawable.shield)
        val bitShieldOld = (shield as BitmapDrawable).bitmap
        val bitShield = Bitmap.createScaledBitmap(bitShieldOld, (width / 5).toInt(), (width / 5).toInt(), true)
        rectangle1 = rect1
        var xPosition: Float = (rect2 - 50f - (rect1 + 50f)) / 2 - width / 8
        if (state == 1) { xPosition = (rect1 + 50f) / 2 - width / 8 }
        if (state == 2) { xPosition = (rect2 - 50f + (rect1 + 50f)) / 2 - width / 8 }
        if (state == 3) { xPosition = (rect3 - 75f + (rect2 + 25f)) / 2 - width / 8 }
        val xLane1 = (rect1 + 50f) / 2 - width/10
        val xLane2 = (rect2 - 50f + (rect1 + 50f)) / 2 - width/10
        val xLane3 = (rect3 - 75f + (rect2 + 25f)) / 2 - width/10
        paint.color = cheeseBack
        canvas.drawRect(0f, 0f, rect1, height.toFloat(), paint)
        paint.color = cheeseFront
        canvas.drawRect(75f, 0f, rect1 - 25f, height.toFloat(), paint)
        paint.color = cheeseBack
        canvas.drawRect(rect1, 0f, rect2, height.toFloat(), paint)
        paint.color = cheeseFront
        canvas.drawRect(rect1 + 50f, 0f, rect2 - 50f, height.toFloat(), paint)
        paint.color = cheeseBack
        canvas.drawRect(rect2, 0f, rect3, height.toFloat(), paint)
        paint.color = cheeseFront
        canvas.drawRect(rect2 + 25f, 0f, rect3 - 75, height.toFloat(), paint)
        canvas.drawBitmap(bitmap, xPosition, yPosition, paint)
        // Images of obstacles
        val danger = ContextCompat.getDrawable(context, R.drawable.danger)
        val bitmapDangerOld = (danger as BitmapDrawable).bitmap
        val bitmapDanger=Bitmap.createScaledBitmap(bitmapDangerOld,width/5,width/5,true)
        val snake = ContextCompat.getDrawable(context, R.drawable.snake)
        val bitmapSnakeOld = (snake as BitmapDrawable).bitmap
        val bitmapSnake=Bitmap.createScaledBitmap(bitmapSnakeOld,width/5,width/5,true)
        val trap = ContextCompat.getDrawable(context, R.drawable.trap)
        val bitmapTrapOld = (trap as BitmapDrawable).bitmap
        val bitmapTrap =Bitmap.createScaledBitmap(bitmapTrapOld,width/5,width/5,true)
        val list1 = listOf(
            ylane1 - height * 1.5.toFloat(),
            ylane1 - height * 2.3.toFloat(),
            ylane1 - height * 3.97.toFloat(),
            ylane1 + height * 4.31.toFloat(),
            ylane1 + height * 5.734.toFloat(),
            ylane1 + height * 6.54.toFloat(),
            ylane1 + height * 7.6.toFloat()
        )
        val list2 = listOf(
            ylane2 - height * 0.2.toFloat(),
            ylane2 - height * 0.64.toFloat(),
            ylane2 - height * 1.07.toFloat(),
            ylane2 - height * 2.toFloat(),
            ylane2 - height * 3.4.toFloat(),
            ylane2 + height * 3.4.toFloat(),
            ylane2 - height * 5.toFloat()
        )
        val list3 = listOf(
            ylane3,
            ylane3 + height.toFloat(),
            ylane3 + height * 1.7.toFloat(),
            ylane3 + height * 3.toFloat(),
            ylane3 + height * 4.toFloat(),
            ylane3 + height * 5.4.toFloat(),
            ylane3 + height * 6.toFloat()
        )
        if(stateCaught in 0..1000){
            if ((state == 1 && list1.any { abs(it - yPosition) < 25}) ||
                (state == 2 && list2.any { abs(it - yPosition) < 25 }) ||
                (state == 3 && list3.any { abs(it - yPosition) < 25 })
            ) {
                hitCount += 1
            }
        }
        if(stateCaught in 1000..5000){
            if ((state == 1 && list1.any { abs(it - yPosition) <  55 }) ||
                (state == 2 && list2.any { abs(it - yPosition) < 55 }) ||
                (state == 3 && list3.any { abs(it - yPosition) < 55 })
            ) {
                hitCount += 1
            }
        }
        if(stateCaught>=5000){
            if ((state == 1 && list1.any { abs(it - yPosition) < 100 }) ||
                (state == 2 && list2.any { abs(it - yPosition) < 100 }) ||
                (state == 3 && list3.any { abs(it - yPosition) < 100 })
            ) {
                hitCount += 1
            }
        }
        when (hitCount) {
            0 -> yTomPosition = height.toFloat()
            1 -> {
                yTomPosition = 2000f
                if (!isSoundPlayed) {
                    hitSound?.start()
                    isSoundPlayed = true }
            }
            2 -> {
                yTomPosition = yPosition
                if (isSoundPlayed) {
                    gameOverSound?.start()
                    isSoundPlayed = false
                    gameOver = true
                    showDialog()
                }
                
            }
        }
        canvas.drawBitmap(bitmapDanger, xLane1, ylane1 - height * 1.5.toFloat(), paint)
        canvas.drawBitmap(bitmapDanger, xLane1, ylane1 - height * 2.3.toFloat(), paint)
        canvas.drawBitmap(bitmap, xLane1, ylane1 - height * 3.97.toFloat(), paint)
        canvas.drawBitmap(bitmapDanger, xLane1, ylane1 + height * 4.31.toFloat(), paint)
        canvas.drawBitmap(bitmapSnake, xLane1, ylane1 + height * 5.734.toFloat(), paint)
        canvas.drawBitmap(bitmapTrap, xLane1, ylane1 + height * 6.54.toFloat(), paint)
        canvas.drawBitmap(bitmapDanger, xLane1, ylane1 + height * 7.6.toFloat(), paint)
        // Obstacles on second lane
        canvas.drawBitmap(bitmapDanger, xLane2, ylane2 - height * 0.2.toFloat(), paint)
        canvas.drawBitmap(bitmapTrap, xLane2, ylane2 - height * 0.64.toFloat(), paint)
        canvas.drawBitmap(bitmapSnake, xLane2, ylane2 - height * 1.07.toFloat(), paint)//no problem
        canvas.drawBitmap(bitmapSnake, xLane2, ylane2 - height * 2.toFloat(), paint)
        canvas.drawBitmap(bitmapDanger, xLane2, ylane2 - height * 3.4.toFloat(), paint)
        canvas.drawBitmap(bitmapDanger, xLane2, ylane2 + height * 3.4.toFloat(), paint)
        // Obstacles on third lane
        canvas.drawBitmap(bitmapSnake, xLane3, ylane3, paint)
        canvas.drawBitmap(bitmapTrap, xLane3, ylane3 + height.toFloat(), paint)
        canvas.drawBitmap(bitmapTrap, xLane3, ylane3 + height * 1.7.toFloat(), paint)
        canvas.drawBitmap(bitmapDanger, xLane3, ylane3 + height * 3.toFloat(), paint)
        canvas.drawBitmap(bitmapSnake, xLane3, ylane3 + height * 4.toFloat(), paint)
        canvas.drawBitmap(bitmapSnake, xLane3, ylane3 + height * 5.4.toFloat(), paint)
        canvas.drawBitmap(bitmapTrap, xLane3, ylane3 + height * 6.toFloat(), paint)
        //for powerups on lanes
        canvas.drawBitmap(bitSmile,xLane1,yBoost1Lane1,paint)
        canvas.drawBitmap(bitShield,xLane3,yBoost2Lane3,paint)
        //condition for placing the obstacle
        for(i in 1..5){
            if (stateCaught in 100*(2*i-1)..1000*(2*i)){
                yBoost1Lane1=ylane1 + height * 2.9.toFloat()} else yBoost1Lane1=-height.toFloat()
            if(stateCaught in 100*((2*i))..1000*((2*i)+1)) {
                yBoost2Lane3=ylane1 + height * 4.9.toFloat() } else yBoost2Lane3=-height.toFloat()
        }
        //adding coditions for boosts
        if(abs(yPosition-yBoost1Lane1)<50) stateCaught+=500
        if(abs(yPosition-yBoost2Lane3)<50)hitCount=0
        canvas.drawBitmap(bitTom, xPosition - 30f, yTomPosition - width / 24, paint)
        paint.color= Color.BLACK
        paint.textSize=50f
        canvas.drawText("$stateCaught",800f,300f,paint)
        canvas.drawText("" +
                "$hitCount",800f,400f,paint)
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.x > 0f && event.x < rectangle1) {
                state = 1
                trackChangeSound?.start()
                invalidate()
                return true
            }
            if (event.x > rectangle1 && event.x < rectangle1 * 2) {
                state = 2
                trackChangeSound?.start()
                invalidate()
                return true
            }
            if (event.x > rectangle1 * 2) {
                state = 3
                trackChangeSound?.start()
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }
    private fun showDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("GAME OVER")
        builder.setMessage("SCORE: $stateCaught")
        builder.setPositiveButton("play again") { dialog, _ ->
            gameOver=false
            ylane1 = 0f
            ylane2 = 0f
            ylane3 = 0f
            yTomPosition = height.toFloat()
            yPosition = 1700f
            hitCount = 0
            stateCaught = 0
            movement()
            dialog.dismiss()
        }
        builder.show()
    }
}