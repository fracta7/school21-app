package com.fracta7.school21

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.fracta7.school21.domain.model.school.coalition.Coalition
import com.fracta7.school21.domain.model.school.participant.Campus
import com.fracta7.school21.domain.model.school.participant.Feedback
import com.fracta7.school21.domain.model.school.participant.Participant
import com.fracta7.school21.domain.model.school.participant.Points
import com.fracta7.school21.presentation.profile.ProfileView
import com.fracta7.school21.presentation.ui.theme.School21Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      School21Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          Column(modifier = Modifier.padding(innerPadding)) {
              val participant = Participant(
                campus = Campus(
                  id = "667a42af-5469-4a33-9858-677d9d20956a",
                  shortName = "21 Samarkand"
                ),
                className = "24_08_SKD",
                expToNextLevel = 246,
                expValue = 1053,
                level = 3,
                login = "rigneysa@student.21-school.ru",
                parallelName = "Core program",
                status = "ACTIVE"
              )
              val feedback = Feedback(4f, 4f, 4f, 4f)
              val points = Points(codeReviewPoints = 5, coins = 210, peerReviewPoints = 3)
              val coalition = Coalition(435, "Fossa")
              ProfileView(participant, feedback, points)
          }
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  School21Theme {
    Greeting("Android")
  }
}