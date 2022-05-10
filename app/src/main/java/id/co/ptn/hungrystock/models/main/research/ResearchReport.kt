package id.co.ptn.hungrystock.models.main.research

class ResearchReport(
    val title: String? = "",
    val researchReportData: List<ResearchReportData>
)

class ResearchReportData(
    val id: String,
    val value: String,
    val photo_url: String,
    val file_url: String,
    val extension: String
)