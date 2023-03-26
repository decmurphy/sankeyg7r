package xyz.decmurphy.sankeyg7r

import java.time.LocalDate

data class Entry(
    val date: LocalDate,
    val details: String,
    val debit: Double?,
    val credit: Double?,
    var balance: Double?
) {
    var category: Category? = null
    var sankey: SankeyEntry? = null

    fun sankify() {
        if (this.credit != null)
            this.sankey = SankeyEntry(this.category!!.highLevelCategory, this.credit, HighLevelCategory.BUDGET)
        else
            this.sankey = SankeyEntry(HighLevelCategory.BUDGET, this.debit!!, this.category!!.highLevelCategory)
    }
}

data class SankeyEntry(
    val input: HighLevelCategory,
    var amount: Double,
    val output: HighLevelCategory
) {
    override fun toString(): String = "${input.displayName} [$amount] ${output.displayName}"
}