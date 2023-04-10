package xyz.decmurphy.sankeyg7r

import java.time.LocalDate

data class Entry(
    val date: LocalDate,
    val details: String,
    val debit: Double?,
    val credit: Double?,
    var balance: Double?
) {
    var category: AppCategory? = null
    var sankey: SankeyEntry? = null

    fun sankify() {
        val categoryName = this.category?.name?.uppercase()
        if (this.credit != null)
            this.sankey = SankeyEntry(input = categoryName ?: "OTHER INCOME", amount = this.credit)
        else
            this.sankey = SankeyEntry(amount = this.debit!!, output = categoryName ?: "OTHER OUTGOING")
    }
}

data class SankeyEntry(
    var amount: Double,
    val input: String? = null,
    val output: String? = null
) {
    override fun toString(): String = "${input ?: "BUDGET"} [$amount] ${output ?: "BUDGET"}"
}