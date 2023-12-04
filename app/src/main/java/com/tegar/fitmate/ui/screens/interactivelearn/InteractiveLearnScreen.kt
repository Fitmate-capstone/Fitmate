package com.tegar.fitmate.ui.screens.interactivelearn

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.media.MediaPlayer
import android.os.HandlerThread
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.arch.core.executor.TaskExecutor
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.tasks.TaskExecutors
import com.tegar.fitmate.ml.ModelType
import com.tegar.fitmate.ml.MoveNet
import com.tegar.fitmate.ui.model.Device
import androidx.camera.core.ImageProxy
import androidx.camera.core.impl.ImageAnalysisConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import com.tegar.fitmate.R
import com.tegar.fitmate.ml.KeyPointAnalyzer
import com.tegar.fitmate.ml.PoseDetectorProcessor
import com.tegar.fitmate.ui.model.BodyPart
import com.tegar.fitmate.ui.model.KeyPoint
import com.tegar.fitmate.ui.model.Person
import com.tegar.fitmate.ui.theme.lightblue60
import com.tegar.fitmate.ui.tracker.PoseClassifier
import com.tegar.fitmate.ui.tracker.VisualizationUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.atan2


@OptIn(ExperimentalGetImage::class)
@Composable
fun InteractiveLearnScreen(
    workoutId: Long,
    navigateBack: () -> Unit,
) {
    var lens by remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                ) {
                    CameraPreview(
                        cameraLens = lens
                    )

                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f).clickable {

                        },
                    contentAlignment = Alignment.CenterEnd,

                ) {
                    Text("Hello")
                }
            }
            ScoreBox()

        }

        else -> {
            Column {
                Text(" Please change to landscape mode")
            }
        }
    }


}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    cameraLens: Int
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    var sourceInfo by remember { mutableStateOf(SourceInfo(10, 10, false)) }
    var detectedPose by remember { mutableStateOf<Pose?>(null) }
    val previewView = remember { PreviewView(context) }
    var isThumbsUp by remember { mutableStateOf(false) }
    var rightHipAngle by remember { mutableStateOf(0.0) }
    var isHumanDetected by remember { mutableStateOf(false) }
    var borderColor by remember { mutableStateOf(Color.Green) }
    val cameraProvider = remember(sourceInfo, cameraLens) {
        ProcessCameraProvider.getInstance(context)
            .configureCamera(
                previewView, lifecycleOwner, cameraLens, context,
                setSourceInfo = { sourceInfo = it },
                onPoseDetected = { detectedPose = it }
            )
    }
    val soundEffect: MediaPlayer = MediaPlayer.create(context, R.raw.correct).apply {
        setVolume(1.0f, 1.0f)
    }
    var canPlaySound by remember { mutableStateOf(true) }

    if (rightHipAngle > 10 && canPlaySound) {
        soundEffect.start()
        canPlaySound = false


        LaunchedEffect(Unit) {
            delay(2000)
            canPlaySound = true
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        with(LocalDensity.current) {
            Box(
                modifier = Modifier
                    .size(
                        height = sourceInfo.height.toDp(),
                        width = sourceInfo.width.toDp()
                    )
                    .scale(
                        calculateScale(
                            constraints,
                            sourceInfo,
                            PreviewScaleType.CENTER_CROP
                        )
                    )
            )
            {
                CameraPreview(previewView)
                DetectedPose(pose = detectedPose, sourceInfo = sourceInfo) { angle ->
                    Log.d("angle" , angle.toString())
                    if(angle != 0.0){
                        isHumanDetected = true // Set to true when human is detected
                        rightHipAngle = angle

                    }else{
                        isHumanDetected = false
                    }
                }
                ColoredBorderBox(
                    modifier = Modifier.fillMaxSize(),
                    borderColor = if (rightHipAngle < 10) Color.Green else Color.Red
                )
            }
        }
    }
}

@Composable
private fun ColoredBorderBox(
    modifier: Modifier,
    borderColor: Color
) {
    Box(
        modifier = modifier
            .border(1.dp, borderColor) // Border color and width
    )
}

@Composable
fun DetectedPose(
    pose: Pose?,
    sourceInfo: SourceInfo,
    onRightHipAngleChange: (Double) -> Unit

) {
    var isThumbsUp by remember { mutableStateOf(false) }

    if (pose != null) {
        if(pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER) != null) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val strokeWidth = 1.dp.toPx()
                val primaryPaint = SolidColor(lightblue60)


                val needToMirror = sourceInfo.isImageFlipped
                val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
                val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
                val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
                val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
                val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
                val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
                val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
                val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
                val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
                val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
                val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
                val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

                val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
                val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
                val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
                val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
                val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
                val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
                val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
                val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
                val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
                val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)


                fun drawLine(
                    startLandmark: PoseLandmark?,
                    endLandmark: PoseLandmark?,
                    paint: Brush
                ) {
                    if (startLandmark != null && endLandmark != null) {
                        val startX =
                            if (needToMirror) size.width - startLandmark.position.x else startLandmark.position.x
                        val startY = startLandmark.position.y
                        val endX =
                            if (needToMirror) size.width - endLandmark.position.x else endLandmark.position.x
                        val endY = endLandmark.position.y
                        drawLine(
                            brush = paint,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = strokeWidth,
                        )
                    }
                }

                drawLine(leftShoulder, rightShoulder, primaryPaint)
                drawLine(leftHip, rightHip, primaryPaint)
                // Left body
                drawLine(leftShoulder, leftElbow, primaryPaint)
                drawLine(leftElbow, leftWrist, primaryPaint)
                drawLine(leftShoulder, leftHip, primaryPaint)
                drawLine(leftHip, leftKnee, primaryPaint)
                drawLine(leftKnee, leftAnkle, primaryPaint)
                drawLine(leftWrist, leftThumb, primaryPaint)
                drawLine(leftWrist, leftPinky, primaryPaint)
                drawLine(leftWrist, leftIndex, primaryPaint)
                drawLine(leftIndex, leftPinky, primaryPaint)
                drawLine(leftAnkle, leftHeel, primaryPaint)
                drawLine(leftHeel, leftFootIndex, primaryPaint)
                // Right body
                drawLine(rightShoulder, rightElbow, primaryPaint)
                drawLine(rightElbow, rightWrist, primaryPaint)
                drawLine(rightShoulder, rightHip, primaryPaint)
                drawLine(rightHip, rightKnee, primaryPaint)
                drawLine(rightKnee, rightAnkle, primaryPaint)
                drawLine(rightWrist, rightThumb, primaryPaint)
                drawLine(rightWrist, rightPinky, primaryPaint)
                drawLine(rightWrist, rightIndex, primaryPaint)
                drawLine(rightIndex, rightPinky, primaryPaint)
                drawLine(rightAnkle, rightHeel, primaryPaint)
                drawLine(rightHeel, rightFootIndex, primaryPaint)


                val rightHipAngle = getAngle(
                    pose.getPoseLandmark(PoseLandmark.NOSE),
                    pose.getPoseLandmark(PoseLandmark.LEFT_KNEE),
                    pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)
                )

                val right = getAngle(
                    pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER),
                    pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW),
                    pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
                )




                Log.d("DETECT", right.toString())

                onRightHipAngleChange(right)
            }
        }else{
            Log.d("pose","null")
        }


    }else{
        Log.d("pose","null")
    }
}

fun getAngle(
    firstPoint: PoseLandmark?,
    midPoint: PoseLandmark?,
    lastPoint: PoseLandmark?,
): Double {
    if (firstPoint == null || midPoint == null || lastPoint == null) {
        // Handle the case where any of the landmarks is null
        return 0.0 // You can choose an appropriate default value
    }

    val result = Math.toDegrees(
        (atan2(
            lastPoint.getPosition().y - midPoint.getPosition().y,
            lastPoint.getPosition().x - midPoint.getPosition().x
        )
                - atan2(
            firstPoint.getPosition().y - midPoint.getPosition().y,
            firstPoint.getPosition().x - midPoint.getPosition().x
        )).toDouble()
    )

    var correctedResult = abs(result) // Angle should never be negative

    if (correctedResult > 180) {
        correctedResult =
            360.0 - correctedResult // Always get the acute representation of the angle
    }

    return correctedResult
}

@Composable
fun ScoreBox(

) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,


        ) {
        Box(modifier = Modifier.background(Color.White)) {
            Text("100", color = Color.Black)


        }
    }
}

@Composable
fun Controls(
    onLensChange: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            onClick = onLensChange,
            modifier = Modifier.wrapContentSize()
        ) { Icon(Icons.Filled.Refresh, contentDescription = "Switch camera") }
    }
}

private fun ListenableFuture<ProcessCameraProvider>.configureCamera(
    previewView: PreviewView,
    lifecycleOwner: LifecycleOwner,
    cameraLens: Int,
    context: Context,
    setSourceInfo: (SourceInfo) -> Unit,
    onPoseDetected: (Pose) -> Unit
): ListenableFuture<ProcessCameraProvider> {
    addListener({
        val cameraSelector = CameraSelector.Builder().requireLensFacing(cameraLens).build()

        val preview = androidx.camera.core.Preview.Builder()
            .build()
            .apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

        val analysis =
            bindAnalysisUseCase(cameraLens, setSourceInfo, onPoseDetected)
        try {
            get().apply {
                unbindAll()
                bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                bindToLifecycle(lifecycleOwner, cameraSelector, analysis)
            }
        } catch (exc: Exception) {
            TODO("process errors")
        }
    }, ContextCompat.getMainExecutor(context))
    return this
}

private fun switchLens(lens: Int) = if (CameraSelector.LENS_FACING_FRONT == lens) {
    CameraSelector.LENS_FACING_BACK
} else {
    CameraSelector.LENS_FACING_FRONT
}

private fun bindAnalysisUseCase(
    lens: Int,
    setSourceInfo: (SourceInfo) -> Unit,
    onPoseDetected: (Pose) -> Unit
): ImageAnalysis? {

    val poseProcessor = try {
        PoseDetectorProcessor()
    } catch (e: Exception) {
        Log.e("CAMERA", "Can not create pose processor", e)
        return null
    }
    val builder = ImageAnalysis.Builder()
    val analysisUseCase = builder.build()

    var sourceInfoUpdated = false

    analysisUseCase.setAnalyzer(
        TaskExecutors.MAIN_THREAD,
        { imageProxy: ImageProxy ->
            if (!sourceInfoUpdated) {
                setSourceInfo(obtainSourceInfo(lens, imageProxy))
                sourceInfoUpdated = true
            }
            try {
//                faceProcessor.processImageProxy(imageProxy, onFacesDetected)
                poseProcessor.processImageProxy(imageProxy, onPoseDetected)
            } catch (e: MlKitException) {
                Log.e(
                    "CAMERA", "Failed to process image. Error: " + e.localizedMessage
                )
            }
        }
    )
    return analysisUseCase
}

private fun obtainSourceInfo(lens: Int, imageProxy: ImageProxy): SourceInfo {
    val isImageFlipped = lens == CameraSelector.LENS_FACING_FRONT
    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
    return if (rotationDegrees == 0 || rotationDegrees == 180) {
        SourceInfo(
            height = imageProxy.height, width = imageProxy.width, isImageFlipped = isImageFlipped
        )
    } else {
        SourceInfo(
            height = imageProxy.width, width = imageProxy.height, isImageFlipped = isImageFlipped
        )
    }
}


private fun calculateScale(
    constraints: Constraints,
    sourceInfo: SourceInfo,
    scaleType: PreviewScaleType
): Float {
    val heightRatio = constraints.maxHeight.toFloat() / sourceInfo.height
    val widthRatio = constraints.maxWidth.toFloat() / sourceInfo.width
    return when (scaleType) {
        PreviewScaleType.FIT_CENTER -> kotlin.math.min(heightRatio, widthRatio)
        PreviewScaleType.CENTER_CROP -> kotlin.math.max(heightRatio, widthRatio)
    }
}

@Composable
private fun CameraPreview(previewView: PreviewView) {
    Box(
        modifier = Modifier
            .fillMaxSize()
        // Background color for the entire preview area
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                previewView.apply {
                    this.scaleType = PreviewView.ScaleType.FILL_CENTER
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // Preview is incorrectly scaled in Compose on some devices without this
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                }

                previewView
            }
        )

        // Add a green bounding box around the device boundaries

    }
}

data class SourceInfo(
    val width: Int,
    val height: Int,
    val isImageFlipped: Boolean,
)

private enum class PreviewScaleType {
    FIT_CENTER,
    CENTER_CROP
}