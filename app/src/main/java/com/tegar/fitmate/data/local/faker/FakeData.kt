package com.tegar.fitmate.data.local.faker

import com.tegar.fitmate.R
import com.tegar.fitmate.data.model.BodyPartSegmentValue
import com.tegar.fitmate.data.model.Category
import com.tegar.fitmate.data.model.Muscle
import com.tegar.fitmate.data.model.Exercise
import com.tegar.fitmate.data.model.InteractiveExerciseSetting

object FakeData {

    val fakeMuscleData = listOf(
        Muscle(id = 1, "Chest"),
        Muscle(id = 2, "Full Body"),
        Muscle(id = 3, "Legs"),
        Muscle(id = 4, "Arms"),
        Muscle(id = 5, "Back"),

        )
    private val fakeCategory = listOf(
        Category("Strength"),
        Category("Cardio"),


        )
    val fakeExerciseData = listOf(
        Exercise(
            id = 1,
            name = "Dumbbell Curl",
            rating = 4,
            level = 1,
            calEstimation = 150,
            requiredEquiment = true,
            explain = "Target your lat muscles with this upper body exercise.",
            step = arrayOf(
                "Sit on the lat pulldown machine with your knees secured under the pads.",
                "Grasp the bar with a wide grip, palms facing forward.",
                "Pull the bar down to your chest, keeping your back straight.",
                "Slowly return the bar to the starting position and repeat."
            ),
            isSupportInteractive = true,
            interactiveSetting = InteractiveExerciseSetting(
                repetion = 12,
                set = 3,
                RestInterval = 60

            ),
            interctiveBodyPartSegmentValue = BodyPartSegmentValue(
                rightArm = 10.0,
                leftArm = 10.0,
                rightLeg = 0.0,
                leftLeg= 0.0,
            ),
            bodyPartNeeded = arrayOf("right_hand" , "left_hand" ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[4],
            photo = R.drawable.lat_pulldown,
            Gif = R.drawable.dummy_2_nobg
        ),
        Exercise(
            id = 2,

            name = "Bench Press",
            rating = 5,
            level = 1,
            calEstimation = 180,
            requiredEquiment = true,
            explain = "A classic chest exercise using a barbell or dumbbells.",
            step = arrayOf(
                "Lie on a flat bench with a barbell or dumbbells",
                "Lower the weight to your chest",
                "Push the weight back up to the starting position."
            ),
            category = fakeCategory[0],

            muscle = fakeMuscleData[0],
            photo = R.drawable.bench_press,
            Gif = R.drawable.dummy_2_nobg
        ),
        Exercise(
            id = 3,

            name = "Push-ups",
            rating = 4,
            level = 2,
            calEstimation = 100,
            requiredEquiment = false,
            explain = "Bodyweight exercise targeting the chest, shoulders, and triceps.",
            step = arrayOf(
                "Start in a plank position",
                "Lower your body by bending your elbows",
                "Push back up to the starting position."
            ),
            category = fakeCategory[0],

            muscle = fakeMuscleData[0],
            photo = R.drawable.push_up,
            Gif = R.drawable.dummy_2_nobg
        ),
        Exercise(
            id = 4,

            name = "Squat",
            rating = 4,
            level = 3,
            calEstimation = 150,
            requiredEquiment = true,
            explain = "Compound exercise targeting the legs and glutes.",
            step = arrayOf(
                "Stand with feet shoulder-width apart",
                "Lower your body by bending your knees",
                "Stand back up to the starting position."
            ),
            category = fakeCategory[0],
            muscle = fakeMuscleData[2],
            photo = R.drawable.squat,
            Gif = R.drawable.dummy_2_nobg
        ),
    )


}