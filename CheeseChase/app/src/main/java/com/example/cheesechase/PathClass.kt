package com.example.cheesechase

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.view.MotionEvent
import androidx.compose.material3.Switch
import androidx.core.content.ContextCompat
import kotlin.random.Random

class PathClass (context: Context): View(context){
    val paint= Paint()
    var ylane1 = 0f
    val cheeseBack =0xFFEBC063.toInt()
    val cheeseFront=0xFFF7D68F.toInt()
    val jerry=ContextCompat.getDrawable(context,R.drawable.jerry)
    val bitmap=(jerry as BitmapDrawable).bitmap
    val tom=ContextCompat.getDrawable(context,R.drawable.tom)
    val bitTom=(tom as BitmapDrawable).bitmap
    // for first 50 //for second rect1+25 // for third rect2
    var state:Int=2
    var yPosition=1700f
    var rectangle1=0f
    // Images of obstacles
    val danger = ContextCompat.getDrawable(context, R.drawable.danger)
    val bitmapDanger = (danger as BitmapDrawable).bitmap
    val snake = ContextCompat.getDrawable(context, R.drawable.snake)
    val bitmapSnake = (snake as BitmapDrawable).bitmap
    val trap = ContextCompat.getDrawable(context, R.drawable.trap)
    val bitmapTrap = (trap as BitmapDrawable).bitmap
    lateinit var animator:ValueAnimator
    val list1 = listOf(
        ylane1 - height * 1.5.toFloat(),
        ylane1 - height * 2.3.toFloat(),
        ylane1 - height * 3.97.toFloat(),
        ylane1 + height * 4.31.toFloat(),
        ylane1 + height * 5.734.toFloat(),
        ylane1 + height * 6.54.toFloat(),
        ylane1 + height * 7.5.toFloat()
    )
    init { setOnClickListener {} }
    //for the obstacles in lane1

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        animator=ValueAnimator.ofFloat(0f,height.toFloat())
        animator.addUpdateListener {animation->
            ylane1=animation.animatedValue as Float
            invalidate()
        }
        animator.duration=5000
        animator.repeatCount=3
        animator.start()
    }
    //function for drawing the background
    override fun onDraw(canvas: Canvas) {
        val xLane1 = 75f
        val rect1=width/3.toFloat()
        val rect2=rect1*2
        val rect3=width.toFloat()
        rectangle1=rect1
        var xPosition:Float=rect1
        if(state==1){ xPosition=50f }
        if(state==2){ xPosition=rect1+25 }
        if(state==3){ xPosition=rect2 }
        super.onDraw(canvas)
        paint.color= cheeseBack
        canvas.drawRect(0f,0f,rect1 ,height.toFloat(),paint)
        paint.color= cheeseFront
        canvas.drawRect(75f,0f,rect1-25f ,height.toFloat(),paint)
        paint.color= cheeseBack
        canvas.drawRect(rect1,0f, rect2,height.toFloat(),paint)
        paint.color= cheeseFront
        canvas.drawRect(rect1+50f,0f,rect2-50f ,height.toFloat(),paint)
        paint.color= cheeseBack
        canvas.drawRect(rect2,0f, rect3,height.toFloat(),paint)
        paint.color=cheeseFront
        canvas.drawRect(rect2+25f,0f,rect3-75,height.toFloat(),paint)
        canvas.drawBitmap(bitmap, xPosition, yPosition, paint)
        canvas.drawBitmap(bitTom, xPosition, yPosition+300f, paint)
        //drawing the obstacles in lane1
    }
    override fun onTouchEvent(event: MotionEvent):Boolean{
        if (event.action==MotionEvent.ACTION_UP){
            if(event.x>0f && event.x<rectangle1){
                state=1
                invalidate()
                return true
            }
            if (event.x>rectangle1 && event.x<rectangle1*2){
                state=2
                invalidate()
                return true
            }
            if(event.x>rectangle1*2){state=3
                invalidate()
                return true}
        }
        return super.onTouchEvent(event)
    }
}