package com.fracta7.school21.domain.model.project

data class StartCondition(
    val logicalOperator: String,
    val rulesInGroup: List<RulesInGroup>
)