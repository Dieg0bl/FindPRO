package dbl.findpro.core.domain.model.userGroupsAndProfiles

data class PerformanceIndicators(
    val performanceIndicatorsId: String,
    val deadlinesMet: Int = 100,
    val budgetCompliance: Int = 100,
    val materialQuality: Int = 100,
    val procedureQuality: Int = 100,
    val reliability: Int = 100,
    val safety: Int = 100,
    val transparency: Int = 100,
    val custom: String = ""
)