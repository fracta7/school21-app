package com.fracta7.school21.data.repository

import com.fracta7.school21.domain.model.participant.Profile
import com.fracta7.school21.domain.model.school.participant.Badge
import com.fracta7.school21.domain.model.school.participant.Campus
import com.fracta7.school21.domain.model.school.participant.ExperienceRange
import com.fracta7.school21.domain.repository.AppRepository
import com.fracta7.school21.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(): AppRepository {
  override fun getXPRange(lvl: Int): ExperienceRange {
    val levels = listOf(
      ExperienceRange(lvl = 0, start = 0, end = 499),
      ExperienceRange(lvl = 1, start = 500, end = 699),
      ExperienceRange(lvl = 2, start = 700, end = 999),
      ExperienceRange(lvl = 3, start = 1000, end = 1299),
      ExperienceRange(lvl = 4, start = 1300, end = 1899),
      ExperienceRange(lvl = 5, start = 1900, end = 2599),
      ExperienceRange(lvl = 6, start = 2600, end = 3599),
      ExperienceRange(lvl = 7, start = 3600, end = 4999),
      ExperienceRange(lvl = 8, start = 5000, end = 6999),
      ExperienceRange(lvl = 9, start = 7000, end = 9699),
      ExperienceRange(lvl = 10, start = 9700, end = 13499),
      ExperienceRange(lvl = 11, start = 13500, end = 18699),
      ExperienceRange(lvl = 12, start = 18700, end = 25999),
      ExperienceRange(lvl = 13, start = 26000, end = 36199)
    )
    val result = if (lvl >= 0 && lvl <= 13) levels.first{ it.lvl == lvl } else ExperienceRange(lvl = lvl, start = -1, end = -1)
    return result
  }

  override suspend fun getProfile(login: String): Flow<Resource<Profile>> = flow {
    emit(Resource.Loading(true))
    delay(500L)
    val profile = Profile(
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
      status = "ACTIVE",
      interest = 4f,
      friendliness = 4f,
      punctuality = 4f,
      thoroughness = 4f,
      codeReviewPoints = 5,
      coins = 210,
      peerReviewPoints = 3,
      coalitionId = 435,
      coalitionName = "Fossa",
      rank = 20,
      badges = listOf(
        Badge(iconUrl = "/public_any/28b7cafe-b4c0-4931-9ee5-5afb322a2a00?path=c8721354-1d80-4429-af26-a28723e6393b/8892c6b3-7f09-452c-b4a5-49d398cd0857/Such+a+listener.png", name = "Such a listener", receiptDateTime = "2024-08-16T14:32:08.083Z"),
        Badge(iconUrl = "/public_any/28b7cafe-b4c0-4931-9ee5-5afb322a2a00?path=92c0e92c-2814-4c97-bd2a-471797563df5/c79e3072-e183-42f0-8bdc-3ed307276911/Billionaire.png", name = "Billionaire", receiptDateTime = "2024-08-09T10:54:55.81Z"),
        Badge(iconUrl = "/public_any/28b7cafe-b4c0-4931-9ee5-5afb322a2a00?path=1e374b6f-f31d-4398-945d-bf74d921aa1b/6faf7e01-c914-4c99-b044-6899c633b33f/Welcome+on+board.png", name = "Welcome on board", receiptDateTime = "2024-08-09T10:54:55.778Z"),
        Badge(iconUrl = "/public_any/28b7cafe-b4c0-4931-9ee5-5afb322a2a00?path=2c49b26a-6907-41b9-8c57-f82c09387352/5e03daa1-4472-4e0b-b00c-dc4b063f485a/Real+programmer.png", name = "Real programmer", receiptDateTime = "2024-07-21T11:45:45.421Z"),
        Badge(iconUrl = "/public_any/28b7cafe-b4c0-4931-9ee5-5afb322a2a00?path=2c49b26a-6907-41b9-8c57-f82c09387352/5e03daa1-4472-4e0b-b00c-dc4b063f485a/Real+programmer.png", name = "Real programmer", receiptDateTime = "2024-07-12T15:03:47.607Z"),
      )
    )
    emit(Resource.Loading(false))
    emit(Resource.Success(data = profile))
  }
}