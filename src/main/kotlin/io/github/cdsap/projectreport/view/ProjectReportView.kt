package io.github.cdsap.projectreport.view

import com.jakewharton.picnic.*
import io.github.cdsap.geapi.client.model.Filter
import io.github.cdsap.geapi.client.model.ScanWithAttributes
import org.nield.kotlinstatistics.percentile

import kotlin.time.DurationUnit
import kotlin.time.toDuration

class ProjectReportView(private val builds: Array<ScanWithAttributes>) {

    fun print(filter: Filter) {
        val buildsGrouped = groupBuildsByUser()

        println(
            table {
                cellStyle {
                    border = true
                    alignment = TextAlignment.MiddleLeft
                    paddingLeft = 1
                    paddingRight = 1
                }
                body {
                    printBuilds(buildsGrouped, this, this@table, filter, "user")
                }
            })

        val buildsGrouped2 = groupBuildsByProjectName()

        println(
            table {
                cellStyle {
                    border = true
                    alignment = TextAlignment.MiddleLeft
                    paddingLeft = 1
                    paddingRight = 1
                }
                body {
                    printBuilds(buildsGrouped2, this, this@table, filter, "project")

                }
            })

        val buildsGrouped3 = groupBuildsByTasks()

        println(
            table {
                cellStyle {
                    border = true
                    alignment = TextAlignment.MiddleLeft
                    paddingLeft = 1
                    paddingRight = 1
                }
                body {
                    printBuilds(buildsGrouped3, this, this@table, filter, "task")

                }
            })

    }

    private fun groupBuildsByProjectName() = builds
        .groupBy {
            it.projectName
        }.map { it.key to it.value }

    private fun groupBuildsByUser() = builds
        .groupBy {
            it.environment.username
        }.map { it.key to it.value }

    private fun groupBuildsByTasks() = builds
        .groupBy {
            it.requestedTasksGoals.joinToString(" ")
        }.map { it.key to it.value }

    private fun printProjects(
        sorted: List<Pair<String, List<ScanWithAttributes>>>,
        tableSectionDsl: TableSectionDsl,
        tableDsl: TableDsl
    ) {
        sorted.toList().sortedBy { (_, value) -> value.size }.toMap()
            .forEach {
                tableSectionDsl.header("Project ${it.key}")
                tableSectionDsl.headerTable("Requested task")
                it.value.groupBy { it.requestedTasksGoals.joinToString(" ") }.forEach {
                    tableDsl.entry(it)
                }
            }
    }

    private fun printBuilds(
        sorted: List<Pair<String, List<ScanWithAttributes>>>,
        tableSectionDsl: TableSectionDsl,
        tableDsl: TableDsl,
        filter: Filter,
        type: String
    ) {
        tableSectionDsl.header("Builds by $type in ${filter.url}")
        tableSectionDsl.headerTable("Project")
        sorted.toList().sortedBy { (_, value) -> value.size }.toMap()
            .forEach {
                tableDsl.entry(it)
            }
    }

    private fun TableSectionDsl.header(title: String) {
        row {
            cell(title) {
                columnSpan = 10
                alignment = TextAlignment.MiddleCenter

            }
        }
    }

    private fun TableDsl.entry(
        buildsMap: Map.Entry<String, List<ScanWithAttributes>>
    ) {

        if (buildsMap.key != null) {
            row {
                val mean = buildsMap.value.sumOf { it.buildDuration } / buildsMap.value.size
                cell(buildsMap.key.take(50))
                cell(buildsMap.value.size) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.value.filter { it.tags.map { it.uppercase() }.contains("CI") }.count()) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.value.filter { it.tags.map { it.uppercase() }.contains("LOCAL") }.count()) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(mean.toDuration(DurationUnit.MILLISECONDS).toString()) {
                    alignment = TextAlignment.MiddleRight
                }
                percentiles(buildsMap)
            }
        }
    }

    private fun RowDsl.percentiles(it: Map.Entry<String, List<ScanWithAttributes>>) {
        cell(
            it.value.map { it.buildDuration }.percentile(25.0).toInt().toDuration(DurationUnit.MILLISECONDS).toString()
        ) {
            alignment = TextAlignment.MiddleRight
        }
        cell(
            it.value.map { it.buildDuration }.percentile(50.0).toInt().toDuration(DurationUnit.MILLISECONDS).toString()
        ) {
            alignment = TextAlignment.MiddleRight
        }
        cell(
            it.value.map { it.buildDuration }.percentile(75.0).toInt().toDuration(DurationUnit.MILLISECONDS).toString()
        ) {
            alignment = TextAlignment.MiddleRight
        }
        cell(
            it.value.map { it.buildDuration }.percentile(90.0).toInt().toDuration(DurationUnit.MILLISECONDS).toString()
        ) {
            alignment = TextAlignment.MiddleRight
        }
        cell(
            it.value.map { it.buildDuration }.percentile(99.0).toInt().toDuration(DurationUnit.MILLISECONDS).toString()
        ) {
            alignment = TextAlignment.MiddleRight
        }
    }


    private fun TableSectionDsl.headerTable(title: String) = row {
        cell(title)
        cell("Builds") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("CI") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("Local") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("Mean") {
            alignment = TextAlignment.MiddleCenter
        }
        //  cell("Standard deviation") {
        //     alignment = TextAlignment.MiddleCenter
        // }
        cell("P25") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("P50") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("P75") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("P90") {
            alignment = TextAlignment.MiddleCenter
        }
        cell("P99") {
            alignment = TextAlignment.MiddleCenter
        }
    }
}



