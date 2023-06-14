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
        val fwUsers = FileWriter(File("builds_by_users.json"))
        val bwUsers = BufferedWriter(fwUsers)
        Gson().toJson(metrics.filter { it.type == Type.User }, bwUsers)
        bwUsers.close()
        val fwProjects = FileWriter(File("builds_by_project.json"))
        val bwProjects = BufferedWriter(fwProjects)
        Gson().toJson(metrics.filter { it.type == Type.Project }, bwProjects)
        bwProjects.close()
        val fwTasks = FileWriter(File("builds_by_tasks.json"))
        val bwTasks = BufferedWriter(fwTasks)
        Gson().toJson(metrics.filter { it.type == Type.Task }, bwTasks)
        bwTasks.close()
    }

}
