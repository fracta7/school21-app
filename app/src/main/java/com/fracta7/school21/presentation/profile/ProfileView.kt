package com.fracta7.school21.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fracta7.school21.R
import com.fracta7.school21.domain.model.school.coalition.Coalition
import com.fracta7.school21.domain.model.school.participant.Campus
import com.fracta7.school21.domain.model.school.participant.Feedback
import com.fracta7.school21.domain.model.school.participant.Participant
import com.fracta7.school21.domain.model.school.participant.Points
import com.fracta7.school21.presentation.ui.theme.School21Theme
import kotlin.math.roundToInt

@Composable
fun ProfileView(participant: Participant, feedback: Feedback, points: Points) {
  Column(
    modifier = Modifier.fillMaxSize()
  ) {
    LazyColumn(
      modifier = Modifier.fillMaxSize()
    ) {
      item {
        Card(modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp)
        ) {
          PeerProfileInfo(participant)
        }
      }
      item {
        Card(modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp)) {
          Column {
            Text(
              text = "Points",
              modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 0.dp),
              fontWeight = FontWeight.Bold
            )
            Row(
              modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              PointChip("${participant.expValue} XP", MaterialTheme.colorScheme.surfaceVariant)
              PointChip("${points.peerReviewPoints} PRP", MaterialTheme.colorScheme.primaryContainer)
              PointChip("${points.codeReviewPoints} CRP", MaterialTheme.colorScheme.secondaryContainer)
              PointChip("${points.coins} Coins", MaterialTheme.colorScheme.tertiaryContainer)
            }
          }
        }
      }
      item {
        Card(modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp)) {
          Column {
            Text(
              text = "Peer feedback",
              modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 4.dp),
              fontWeight = FontWeight.Bold
            )
            FeedbackText(R.drawable.person_interested_24, "Interested:", feedback.averageVerifierInterest)
            FeedbackText(R.drawable.person_nice_24, "Nice:",feedback.averageVerifierFriendliness)
            FeedbackText(R.drawable.person_punctual_24, "Punctual:", feedback.averageVerifierPunctuality)
            FeedbackText(R.drawable.person_rigorous_24, "Rigorous:", feedback.averageVerifierThoroughness)
          }
        }
      }
      item {
        Card(modifier = Modifier
          .fillMaxWidth()
          .padding(4.dp)) {
          Column {
            Text(
              text = "Peer information",
              modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 4.dp),
              fontWeight = FontWeight.Bold
            )
            PeerInfoText(R.drawable.mail_24, participant.login)
            PeerInfoText(R.drawable.location_24, participant.campus.shortName)
          }
        }
      }
    }
  }
}

@Composable
fun PeerProfileInfo(participant: Participant){
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(4.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    OutlinedCard(shape = ShapeDefaults.ExtraLarge, modifier = Modifier.padding(8.dp)) {
      Icon(
        painter = painterResource(R.drawable.round_person_24),
        contentDescription = "Profile icon",
        Modifier
          .size(128.dp)
          .padding(8.dp)
      )
    }
    Text(text = participant.login, fontWeight = FontWeight.Bold, fontSize = 21.sp)
    Row() {
      Text(text = participant.parallelName, modifier = Modifier.padding(4.dp))
      Text(
        text = participant.className,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(4.dp)
      )
    }
    Spacer(modifier = Modifier.padding(12.dp))
    Column {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 12.dp)
      ) {
        Text(text = "lvl ${participant.level}")
        Text(text = "17%")
      }
      LinearProgressIndicator(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp), progress = { 0.17f })
      Spacer(modifier = Modifier.padding(8.dp))
    }
  }
}

@Composable
fun FeedbackText(iconID: Int, feedbackText: String, value: Float){
  Row(modifier = Modifier.padding(4.dp)) {
    Icon(
      painter = painterResource(iconID),
      contentDescription = "Feedback icon",
      modifier = Modifier.padding(4.dp)
    )
    Row {
      Text(
        text = feedbackText,
        modifier = Modifier.padding(4.dp)
      )
      Text(text = "${value.roundToInt()}/4", fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))
    }
  }
}

@Composable
fun PeerInfoText(iconID: Int, info: String){
  Row(modifier = Modifier.padding(4.dp)) {
    Icon(
      painter = painterResource(iconID),
      contentDescription = "Info icon",
      modifier = Modifier.padding(4.dp)
    )
    Text(
      text = info,
      modifier = Modifier.padding(4.dp)
    )
  }
}

@Composable
fun PointChip(label: String, color: Color){
  AssistChip(
    onClick = {},
    label = { Text(text = label) },
    shape = ShapeDefaults.Large,
    enabled = true,
    colors = AssistChipDefaults.assistChipColors(containerColor = color)
  )
}

@Preview
@Composable
fun ProfileViewPreview() {
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
  School21Theme(darkTheme = true) {
    Surface {
      ProfileView(participant, feedback, points)
    }
  }
}