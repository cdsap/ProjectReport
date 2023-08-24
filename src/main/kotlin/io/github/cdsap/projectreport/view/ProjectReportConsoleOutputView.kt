package io.github.cdsap.projectreport.view

import com.jakewharton.picnic.*
import io.github.cdsap.projectreport.Metric
import io.github.cdsap.projectreport.Type


class ProjectReportConsoleOutputView(
    private val metrics: List<Metric>
) {

    fun print() {
        writeConsoleOutput()
    }

    private fun writeConsoleOutput() {
        println(
            table {
                cellStyle {
                    border = true
                    alignment = TextAlignment.MiddleLeft
                    paddingLeft = 1
                    paddingRight = 1
                }
                body {
                    printBuilds(metrics.filter { it.type == Type.User }, this, this@table, "user")
                }
            })


        println(
            table {
                cellStyle {
                    border = true
                    alignment = TextAlignment.MiddleLeft
                    paddingLeft = 1
                    paddingRight = 1
                }
                body {
                    printBuilds(metrics.filter { it.type == Type.Project }, this, this@table, "project")

                }
            })


        println(
            table {
                cellStyle {
                    border = true
                    alignment = TextAlignment.MiddleLeft
                    paddingLeft = 1
                    paddingRight = 1
                }
                body {
                    printBuildsTasks(metrics.filter { it.type == Type.Task }, this, this@table, "task")

                }
            })
    }

    private fun printBuilds(
        sorted: List<Metric>,
        tableSectionDsl: TableSectionDsl,
        tableDsl: TableDsl,
        type: String
    ) {
        tableSectionDsl.header("Top 10 - Builds by $type")
        tableSectionDsl.headerTable(sorted.first().type.name)
        sorted.sortedBy { it.builds }.takeLast(10).forEach {
            tableDsl.entry(it)
        }
    }

    private fun printBuildsTasks(
        sorted: List<Metric>,
        tableSectionDsl: TableSectionDsl,
        tableDsl: TableDsl,
        type: String
    ) {
        tableSectionDsl.headerTasks("Top 10 - Builds by $type")
        tableSectionDsl.headerTableTasks(sorted.first().type.name)
        sorted.sortedBy { it.builds }.takeLast(10).forEach {
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

    private fun TableSectionDsl.headerTasks(title: String) {
        row {
            cell(title) {
                columnSpan = 11
                alignment = TextAlignment.MiddleCenter

            }
        }
    }

    private fun TableDsl.entry(
        buildsMap: Metric
    ) {

        if (buildsMap != null) {
            row {
                cell(buildsMap.value.take(40))
                if (buildsMap.type == Type.Task) {
                    cell(buildsMap.project.take(40)) {
                        alignment = TextAlignment.MiddleLeft
                    }
                }
                cell(buildsMap.builds) {
                    alignment = TextAlignment.MiddleRight
                }

                cell(buildsMap.ci) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.local) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.mean) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.p25) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.p50) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.p75) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.p90) {
                    alignment = TextAlignment.MiddleRight
                }
                cell(buildsMap.p99) {
                    alignment = TextAlignment.MiddleRight
                }

            }
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

    private fun TableSectionDsl.headerTableTasks(title: String) = row {
        cell(title)
        cell("Project") {
            alignment = TextAlignment.MiddleCenter
        }
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
