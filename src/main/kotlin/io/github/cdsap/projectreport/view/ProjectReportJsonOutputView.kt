package io.github.cdsap.projectreport.view

import com.google.gson.Gson
import com.jakewharton.picnic.*
import io.github.cdsap.projectreport.Metric
import io.github.cdsap.projectreport.Type
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


class ProjectReportJsonOutputView(
    private val metrics: List<Metric>
) {

    fun print() {
        writeFilesOutput()
    }

    private fun writeFilesOutput() {
        val fwUsers = FileWriter(File("project-report-users-${System.currentTimeMillis()}.json"))
        val bwUsers = BufferedWriter(fwUsers)
        Gson().toJson(metrics.filter { it.type == Type.User }, bwUsers)
        bwUsers.close()
        val fwProjects = FileWriter(File("project-report-projects-${System.currentTimeMillis()}.json"))
        val bwProjects = BufferedWriter(fwProjects)
        Gson().toJson(metrics.filter { it.type == Type.Project }, bwProjects)
        bwProjects.close()
        val fwTasks = FileWriter(File("project-report-tasks-${System.currentTimeMillis()}.json"))
        val bwTasks = BufferedWriter(fwTasks)
        Gson().toJson(metrics.filter { it.type == Type.Task }, bwTasks)
        bwTasks.close()
    }

}
