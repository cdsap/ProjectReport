package io.github.cdsap.projectreport

import kotlin.time.Duration

data class Metric(
    val type: Type,
    val value: String,
    val builds: Int,
    val ci: Int,
    val local: Int,
    val mean: Duration,
    val p25: Duration,
    val p50: Duration,
    val p75: Duration,
    val p90: Duration,
    val p99: Duration
)
