package dbl.findpro.core.domain.model.userGroupsAndProfiles

import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth

data class AvailabilitySchedule(
    var availableHours: MutableSet<WorkHourRange>,
    var occupiedHours: MutableSet<WorkHourRange>
)

object BlockColors {
    val NOT_WORKABLE = Color.Gray
    val WORKABLE = Color.Magenta
    val AVAILABLE = Color.Green
    val PARTIAL_AVAILABILITY = Color.Yellow
    val OCCUPIED = Color.Red
}

enum class DetailLevel(val parent: DetailLevel?, val durationMinutes: Long) {
    YEARS(null, -1), MONTHS(YEARS, -1), WEEKS(MONTHS, 7 * 1440L), DAYS(MONTHS, 1440L), HOURS(DAYS, 60L), DETAIL_BLOCKS(HOURS, 15L);
    fun getChild(): DetailLevel? = when (this) {
        YEARS -> MONTHS
        MONTHS -> WEEKS
        WEEKS -> DAYS
        DAYS -> HOURS
        HOURS -> DETAIL_BLOCKS
        DETAIL_BLOCKS -> null
    }
}

enum class CalendarMode { VIEW_ONLY, SET_WORK_SCHEDULE, SET_AVAILABILITY }

data class CalendarKit(
    val years: MutableList<CalendarYear>,
    val workSchedule: WorkSchedule,
    val availabilitySchedule: AvailabilitySchedule,
    val id: String,
    val updatedAt: Instant
)

data class WorkSchedule(
    var workMonths: MutableSet<YearMonth>,
    var workDays: MutableSet<LocalDate>,
    var workHours: MutableSet<WorkHourRange>
)

data class WorkHourRange(val start: LocalTime, val end: LocalTime)

data class CalendarYear(val year: Int, val months: MutableList<CalendarMonth>)
data class CalendarMonth(val yearMonth: YearMonth, val weeks: MutableList<CalendarWeek>, val days: MutableList<CalendarDay>)
data class CalendarWeek(val weekNumber: Int, val days: MutableList<CalendarDay>)
data class CalendarDay(val date: LocalDate, val blocks: MutableList<CalendarBlock>, var state: BlockState = BlockState.NOT_WORKABLE)
data class CalendarBlock(val time: LocalTime, var state: BlockState = BlockState.NOT_WORKABLE)

enum class BlockState {
    NOT_WORKABLE, WORKABLE, AVAILABLE, PARTIAL_AVAILABILITY, OCCUPIED
}
