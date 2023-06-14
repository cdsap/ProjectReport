package io.github.cdsap.projectreport


import io.github.cdsap.geapi.client.model.Filter
import io.github.cdsap.geapi.client.network.GEClient
import io.github.cdsap.geapi.client.repository.impl.GradleRepositoryImpl

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.ajalt.clikt.parameters.types.int
import io.github.cdsap.geapi.client.network.ClientConf
import io.github.cdsap.projectreport.report.ProjectReport

import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    ProjectReport().main(args)
}

class ProjectReport : CliktCommand() {
    private val apiKey: String by option().required()
    private val url by option().required()
    private val maxBuilds by option().int().default(1000).check("max builds to process 100000") { it <= 100000 }
    private val project: String? by option()
    private val tags: List<String> by option().multiple(default = emptyList())
    private val concurrentCalls by option().int().default(150)
    private val user: String? by option()
    private val fileJsonOutput: Boolean by option().flag(default = false)

    override fun run() {
        val filter = Filter(
            url = url,
            maxBuilds = maxBuilds,
            project = project,
            tags = tags,
            initFilter = System.currentTimeMillis(),
            user = user,
            concurrentCalls = concurrentCalls,
            concurrentCallsConservative = 0
        )
        val repository = GradleRepositoryImpl(
            GEClient(
                apiKey, url, ClientConf(
                    maxRetries = 300,
                    exponentialBase = 1.0,
                    exponentialMaxDelay = 5000
                )
            )
        )

        runBlocking {
            ProjectReport(filter, repository,fileJsonOutput).process()
        }
    }
}
