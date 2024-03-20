package com.example.hazesample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.hazesample.ui.theme.HazeSampleTheme
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var hideHaze by remember {
                mutableStateOf(true)
            }

            var sliderValue by remember {
                mutableFloatStateOf(0f)
            }

            HazeSampleTheme {

                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    HazeSample(setHazeVisible = hideHaze, blurValue = sliderValue)

                    Spacer(modifier = Modifier.padding(top = 30.dp))

                    Box(modifier = Modifier
                        .width(250.dp)
                        .height(50.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text =if (hideHaze) "Haze ON" else "Haze OFF",
                            color = Color.Red,

                        )
                    }

                    Spacer(modifier = Modifier.padding(top = 10.dp))


                    Box(modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                        .background(color = Color.Blue, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            hideHaze = !hideHaze
                        },
                        contentAlignment = Alignment.Center
                    ){
                        Text(text = "Set Haze", color = Color.White)
                    }

                    Spacer(modifier = Modifier.padding(top = 25.dp))

                    Slider(modifier = Modifier
                        .padding(start = 28.dp, end = 20.dp),
                        value = sliderValue,
                        onValueChange = {
                            sliderValue = it
                        },
                        valueRange = 0f .. 1f,
                        steps = 1 / 10,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Blue,
                            activeTrackColor = Color.Blue,
                            inactiveTrackColor = Color.Gray,
                            activeTickColor = Color.Blue,
                            inactiveTickColor = Color.Gray
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AnimatedLottie(modifier: Modifier = Modifier) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.animation)
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}

@Composable
fun HazeSample(setHazeVisible: Boolean, blurValue: Float) {
    val hazeState = remember { HazeState() }

    Box(Modifier
        .height(450.dp)) {
        Box(
            Modifier
                .wrapContentSize()
                .haze(
                    state = hazeState,
                    style = HazeDefaults.style(
                        tint = Color.Blue.copy(alpha = blurValue),
                        blurRadius = 8.dp
                    ),
                ),
            contentAlignment = Alignment.Center
        ) {
            AnimatedLottie(
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        if (setHazeVisible){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .align(Alignment.Center)
                    .hazeChild(
                        state = hazeState,
                        shape = RoundedCornerShape(16.dp),
                    ),
            )
        }
    }
}