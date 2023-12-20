package io.github.cdsap.projectreport.view

import io.github.cdsap.projectreport.Metric
import io.github.cdsap.projectreport.Type
import java.io.File

class ProjectReportCsvOutputView(
    private val metrics: List<Metric>
) {

    fun print() {
        writeCsv()
    }

    fun writeCsv() {
        val csv = "project-report-users-${System.currentTimeMillis()}.csv"
        val headers = "user,builds,ci,local,mean,p25,p50,p75,p90,p99\n"

        var values = ""
        metrics.filter { it.type == Type.User }.forEach {
            values += "${it.value},${it.builds},${it.ci},${it.local},${it.mean.inWholeMilliseconds},${it.p25.inWholeMilliseconds},${it.p50.inWholeMilliseconds},${it.p75.inWholeMilliseconds},${it.p90.inWholeMilliseconds},${it.p99.inWholeMilliseconds}\n"
        }
        File(csv).writeText("""$headers$values""".trimIndent())
        println("File $csv created")

        val csvProject = "project-report-projects-${System.currentTimeMillis()}.csv"
        val headersProject = "project,builds,ci,local,mean,p25,p50,p75,p90,p99\n"

        var valuesProject = ""
        metrics.filter { it.type == Type.Project }.forEach {
            valuesProject += "${it.value},${it.builds},${it.ci},${it.local},${it.mean.inWholeMilliseconds},${it.p25.inWholeMilliseconds},${it.p50.inWholeMilliseconds},${it.p75.inWholeMilliseconds},${it.p90.inWholeMilliseconds},${it.p99.inWholeMilliseconds}\n"
        }
        File(csvProject).writeText("""$headersProject$valuesProject""".trimIndent())
        println("File $csvProject created")

        val csvTasks = "project-report-tasks-${System.currentTimeMillis()}.csv"
        val headersTasks = "task,project,builds,ci,local,mean,p25,p50,p75,p90,p99\n"

        var valuesTasks = ""
        metrics.filter { it.type == Type.Task }.forEach {
            valuesTasks += "${it.value.replace(",", " ")},${it.project},${it.builds},${it.ci},${it.local},${it.mean.inWholeMilliseconds},${it.p25.inWholeMilliseconds},${it.p50.inWholeMilliseconds},${it.p75.inWholeMilliseconds},${it.p90.inWholeMilliseconds},${it.p99.inWholeMilliseconds}\n"
        }
        File(csvTasks).writeText("""$headersTasks$valuesTasks""".trimIndent())
        println("File $csvTasks created")

    }
}
