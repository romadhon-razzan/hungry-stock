package id.co.ptn.hungrystock.models.main.home
 class Event(
  val type: String,
  val upcomingEvents: List<UpcomingEvent>,
  val pastEvents: List<PastEvent>
  ) {
  companion object {
   const val TYPE_UPCOMING_EVENT = "upcoming_event"
   const val TYPE_PAST_EVENT = "past_event"
  }
 }