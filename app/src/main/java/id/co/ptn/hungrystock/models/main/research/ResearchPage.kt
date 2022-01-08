package id.co.ptn.hungrystock.models.main.research

class ResearchPage(
    val type: Int,
    val researchData: List<ResearchReport>,
    val stockData: List<StockData>,
    val filter: List<ResearchFilter>,
    val sorting: ResearchSorting
) {
    companion object {
        const val TYPE_SORTING = 0
        const val TYPE_FILTER = 1
        const val TYPE_LIST = 2
    }
}