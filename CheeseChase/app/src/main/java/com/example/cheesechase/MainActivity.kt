package com.example.cheesechase

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.cheesechase.ui.theme.CheeseChaseTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rectangles = RectangleView(this)
        setContentView(rectangles)

        // Observe the maximum LiveData and update the RectangleView when it changes
        mainViewModel.maximum.observe(this, Observer { max ->
            rectangles.updateMaximum(max)
        })

        getvalueOfMax()

        setContent {
            CheeseChaseTheme {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFC071), Color(0xFFFFEB3B))
                )
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(gradient),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "CHEESE CHASE",
                        style = TextStyle(fontSize = 55.sp),
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Button(
                        onClick = { setContentView(rectangles) },
                        modifier = Modifier
                            .padding(15.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text(
                            text = "PLAY",
                            style = TextStyle(fontSize = 35.sp),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.Yellow
                        )
                    }
                }
            }
        }
    }

    private val BASE_URL = "https://chasedeux.vercel.app"

    private fun getvalueOfMax() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(gameApiInterface::class.java)

        val retrofitData = retrofitBuilder.getObstacleLimit()

        retrofitData.enqueue(object : Callback<obstacleLimitResponse?> {
            override fun onResponse(
                call: Call<obstacleLimitResponse?>,
                response: Response<obstacleLimitResponse?>
            ) {
                val responseBody = response.body()!!
                mainViewModel.setMaximum(responseBody.obstacleLimit)
            }

            override fun onFailure(call: Call<obstacleLimitResponse?>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
