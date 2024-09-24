package com.fracta7.school21.domain.model.project

data class Value(
    val fieldId: Int,
    val fieldName: String,
    val `operator`: String,
    val subFieldKey: String,
    val subFieldValue: String,
    val value: List<ValueX>
)