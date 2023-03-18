//package id.co.ptn.hungrystock.bases.pagination
//
//import android.content.Context
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import id.co.ptn.hungrystock.models.Links
//import id.co.ptn.hungrystock.ui.main.learning.adapters.LearningPaginationAdapter
//
//class BasePagination(val context: Context, val paginationAdapter: LearningPaginationAdapter) {
//    fun setPagination(recyclerView: RecyclerView, items: MutableList<Links>?) {
//        val paginationAdapter = LearningPaginationAdapter(items ?: mutableListOf(), object : LearningPaginationAdapter.LearningListener{
//            override fun itemClicked(page: Links, position: Int) {
//                if (viewModel?.requesting == false){
//                    // for inactive page button
//                    items?.forEachIndexed { index, links ->
//                        if (links.active == true){
//                            links.active = false
//                            paginationAdapter.notifyItemChanged(index)
//                            return@forEachIndexed
//                        }
//                    }
//
//                    val lastPage = viewModel?.lastPage?.toInt() ?: 0
//                    val currentPage = viewModel?.currentPage ?: "1"
//                    if (page.label?.lowercase()?.contains(Links.previous) == true) {
//                        var prevPage = Links.previousPage(currentPage).toInt()
//                        if (prevPage < 1){
//                            prevPage = 1
//                        }
//
//                        viewModel?.getLinks()?.get(prevPage)?.active = true
//                        paginationAdapter.notifyItemChanged(prevPage)
//
//                        viewModel?.setNextPage(prevPage.toString())
//                    } else if (page.label?.lowercase()?.contains(Links.next) == true) {
//                        var nextPage = Links.nextPage(currentPage).toInt()
//                        if (nextPage > lastPage) {
//                            nextPage = lastPage
//                        }
//
//                        viewModel?.getLinks()?.get(nextPage)?.active = true
//                        paginationAdapter?.notifyItemChanged(nextPage)
//
//                        viewModel?.setNextPage(nextPage.toString())
//                    } else {
//                        viewModel?.getLinks()?.get(position)?.active = true
//                        paginationAdapter?.notifyItemChanged(position)
//                        viewModel?.setNextPage(page.label ?: "0")
//                    }
//                    viewModel?.currentPage = viewModel?.getNextPage() ?: "1"
//                    apiGetNextLearnings()
//                }
//            }
//        })
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//            adapter = paginationAdapter
//        }
//    }
//    interface Listener {
//
//    }
//}