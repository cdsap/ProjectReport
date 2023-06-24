package io.github.cdsap.projectreport.report

import io.github.cdsap.geapi.client.domain.impl.GetBuildScansWithQueryImpl
import io.github.cdsap.geapi.client.model.Filter
import io.github.cdsap.geapi.client.model.ScanWithAttributes
import io.github.cdsap.geapi.client.repository.impl.GradleRepositoryImpl
import io.github.cdsap.projectreport.Metric
import io.github.cdsap.projectreport.Type
import io.github.cdsap.projectreport.view.ProjectReportConsoleOutputView
import io.github.cdsap.projectreport.view.ProjectReportJsonOutputView
import org.nield.kotlinstatistics.percentile
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ProjectReport(
    private val filter: Filter,
    private val repository: GradleRepositoryImpl,
    private val fileJsonOutput: Boolean
) {

    suspend fun process() {
        val getBuildScans = GetBuildScansWithQueryImpl(repository).get(filter)
        val metrics = convertBuildsToMetrics(getBuildScans)
        println("Generating output")
        ProjectReportConsoleOutputView(metrics.toList(), filter.url).print()
        if (fileJsonOutput) {
            println("Generating files output")
            ProjectReportJsonOutputView(metrics.toList()).print()
            println("builds_by_project.json created")
            println("builds_by_user.json created")
            println("builds_by_task.json created")
        }

    }

    private fun convertBuildsToMetrics(getBuildScans: List<ScanWithAttributes>): MutableList<Metric> {
        val metrics = mutableListOf<Metric>()
        getMetrics(groupBuildsByUser(getBuildScans), metrics, Type.User)
        getMetrics(groupBuildsByProjectName(getBuildScans), metrics, Type.Project)
        getMetrics(groupBuildsByTasks(getBuildScans), metrics, Type.Task)
        return metrics
    }

    private fun getMetrics(
        buildsGrouped: List<Pair<String, List<ScanWithAttributes>>>,
        listUsers: MutableList<Metric>,
        type: Type
    ) {
        buildsGrouped.toList().sortedBy { (_, value) -> value.size }.toMap().forEach {
            val mean = it.value.sumOf { it.buildDuration } / it.value.size
            listUsers.add(
                Metric(
                    type = type,
                    value = it.key,
                    builds = it.value.size,
                    ci = it.value.filter { it.tags.map { it.uppercase() }.contains("CI") }.count(),
                    local = it.value.filter { it.tags.map { it.uppercase() }.contains("LOCAL") }.count(),
                    mean = mean.toDuration(DurationUnit.MILLISECONDS),
                    p25 = it.value.map { it.buildDuration }.percentile(25.0).toInt()
                        .toDuration(DurationUnit.MILLISECONDS),
                    p50 = it.value.map { it.buildDuration }.percentile(50.0).toInt()
                        .toDuration(DurationUnit.MILLISECONDS),
                    p75 = it.value.map { it.buildDuration }.percentile(75.0).toInt()
                        .toDuration(DurationUnit.MILLISECONDS),
                    p90 = it.value.map { it.buildDuration }.percentile(90.0).toInt()
                        .toDuration(DurationUnit.MILLISECONDS),
                    p99 = it.value.map { it.buildDuration }.percentile(99.0).toInt()
                        .toDuration(DurationUnit.MILLISECONDS),
                )
            )

        }
    }

    private fun groupBuildsByProjectName(builds: List<ScanWithAttributes>) = builds
        .groupBy {
            it.projectName
        }.map { it.key to it.value }

    private fun groupBuildsByUser(builds: List<ScanWithAttributes>) = builds
        .groupBy {
            it.environment.username
        }.map { it.key to it.value }

    private fun groupBuildsByTasks(builds: List<ScanWithAttributes>) = builds
        .groupBy {
            it.requestedTasksGoals.joinToString(" ")
        }.map { it.key to it.value }


}
