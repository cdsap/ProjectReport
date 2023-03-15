package io.github.cdsap.projectreport.report

import io.github.cdsap.geapi.client.domain.impl.GetBuildScansWithQueryImpl
import io.github.cdsap.geapi.client.model.Filter
import io.github.cdsap.geapi.client.repository.impl.GradleRepositoryImpl
import io.github.cdsap.projectreport.view.ProjectReportView

class ProjectReport(
    val filter: Filter,
    val repository: GradleRepositoryImpl
) {

    suspend fun process() {
        val getBuildScans = GetBuildScansWithQueryImpl(repository).get(filter)
        ProjectReportView(getBuildScans.toTypedArray()).print(filter)

    }
}
