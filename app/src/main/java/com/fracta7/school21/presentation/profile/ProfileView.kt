package com.fracta7.school21.presentation.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fracta7.school21.R
import com.fracta7.school21.domain.model.participant.Profile
import com.fracta7.school21.domain.model.school.participant.Workstation
import com.fracta7.school21.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun ProfileView(login: String) {
  val viewModel = hiltViewModel<ProfileViewModel>()
  val cardShape = ShapeDefaults.ExtraLarge
  val scope = rememberCoroutineScope()
  LaunchedEffect(Unit) {
    scope.launch {
      viewModel.getParticipantProfile(login)
      viewModel.getWorkstation(login)
    }
  }
  Column(
    modifier = Modifier.fillMaxSize()
  ) {
    AnimatedVisibility(viewModel.state.profile != null) {
      LazyColumn(
        modifier = Modifier.fillMaxSize()
      ) {
        item {
          Card(
            modifier = Modifier
              .fillMaxWidth()
              .padding(4.dp),
            shape = cardShape
          ) {
            viewModel.getLevelInfo()
            PeerProfileInfo(viewModel.state.profile!!, viewModel.state.xpPercent)
          }
        }
        item {
          PeerSection(title = "Points") {
            Row(
              modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(4.dp)
                .fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              PointChip("${viewModel.state.profile!!.expValue} XP", MaterialTheme.colorScheme.surfaceVariant)
              PointChip(
                "${viewModel.state.profile!!.peerReviewPoints} PRP",
                MaterialTheme.colorScheme.primaryContainer
              )
              PointChip(
                "${viewModel.state.profile!!.codeReviewPoints} CRP",
                MaterialTheme.colorScheme.secondaryContainer
              )
              PointChip("${viewModel.state.profile!!.coins} Coins", MaterialTheme.colorScheme.tertiaryContainer)
            }
          }
        }
        item {
          PeerSection(title = "Peer feedback") {
            FeedbackText(
              R.drawable.person_interested_24,
              "Interested:",
              viewModel.state.profile!!.interest
            )
            FeedbackText(R.drawable.person_nice_24, "Nice:", viewModel.state.profile!!.friendliness)
            FeedbackText(
              R.drawable.person_punctual_24,
              "Punctual:",
              viewModel.state.profile!!.punctuality
            )
            FeedbackText(
              R.drawable.person_rigorous_24,
              "Rigorous:",
              viewModel.state.profile!!.thoroughness
            )
          }
        }
        item {
          PeerSection(title = "Peer information") {
            PeerInfoText(R.drawable.mail_24, viewModel.state.profile!!.login)
            PeerInfoText(R.drawable.location_24, viewModel.state.profile!!.campus.shortName)
            PeerPresenceInfo(present = viewModel.state.present, workstation = viewModel.state.workstation)
          }
        }
        item {
          PeerSection(title = "Tribe") {
            Row {
              Icon(
                painter = painterResource(R.drawable.group_24),
                contentDescription = "Tribe icon",
                modifier = Modifier.padding(8.dp)
              )
              Text(
                text = "${viewModel.state.profile!!.coalitionName}, ${viewModel.state.profile!!.rank}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
              )
            }
          }
        }
      }
    }
    AnimatedVisibility(viewModel.state.profile == null) {
      CircularProgressIndicator()
    }
  }
}

@Composable
fun PeerSection(title: String, content: @Composable () -> Unit){
  Card(modifier = Modifier
    .fillMaxWidth()
    .padding(4.dp),
    shape = ShapeDefaults.ExtraLarge
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      Text(
        text = title,
        modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 4.dp),
        fontWeight = FontWeight.Bold
      )
      content()
    }
  }
}

@Composable
fun PeerProfileInfo(profile: Profile, xpPercent: Float){
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp),
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
    Text(text = profile.login, fontWeight = FontWeight.Bold, fontSize = 21.sp)
    Row {
      Text(text = profile.parallelName, modifier = Modifier.padding(4.dp))
      Text(
        text = profile.className,
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
        Text(text = "lvl ${profile.level}")
        Text(text = "${(xpPercent * 100).toInt()}%")
      }
      LinearProgressIndicator(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp), progress = { xpPercent })
      Spacer(modifier = Modifier.padding(8.dp))
    }
  }
}

@Composable
fun FeedbackText(iconID: Int, feedbackText: String, value: Float){
  Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
    Icon(
      painter = painterResource(iconID),
      contentDescription = "Feedback icon",
      modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
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
  Row(modifier = Modifier.padding(8.dp)) {
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
fun PeerPresenceInfo(present: Boolean = false, workstation: Workstation? = null){
  if (present){
    Card(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
      )
    ) {
      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Icon(painter = painterResource(R.drawable.computer_24), contentDescription = "Computer icon", modifier = Modifier.padding(8.dp))
        Text(text = "${workstation!!.clusterName} ${workstation.clusterName[0]}${workstation.clusterName[1]}-${workstation.row}${workstation.number}")
      }
    }
  } else {
    OutlinedCard(
      modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(),
      colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
    ){
      Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        Icon(painter = painterResource(R.drawable.close_24), contentDescription = "Close icon", modifier = Modifier.padding(8.dp))
        Text(text = "Out of campus")
      }
    }
  }
}

@Composable
fun PointChip(label: String, color: Color){
  AssistChip(
    onClick = {},
    label = { Text(text = label) },
    shape = ShapeDefaults.Large,
    enabled = true,
    colors = AssistChipDefaults.assistChipColors(containerColor = color),
    modifier = Modifier.padding(2.dp)
  )
}

fun getWorkstation(): Flow<Resource<Workstation>> = flow {
  val clusterNames = listOf("registan", "sherdor", "tillakori", "ulugbek")
  while (true) {
    // Randomly determine whether to emit success or error
    val isOnline = Random.nextBoolean()

    if (isOnline) {
      // Generate random workstation data
      val workstation = Workstation(
        clusterId = Random.nextInt(1000), // Random clusterId
        clusterName = clusterNames.random(), // Random clusterName from the list
        number = Random.nextInt(1, 6), // Random number between 1 and 5
        row = ('a'..'z').random().toString() // Random row (a single letter)
      )
      emit(Resource.Success(workstation))
    } else {
      // Emit error if the workstation is offline
      emit(Resource.Error("Workstation not present"))
    }

    // Wait for a random period between emissions (simulating asynchronous updates)
    delay(Random.nextLong(2000, 5000))
  }
}