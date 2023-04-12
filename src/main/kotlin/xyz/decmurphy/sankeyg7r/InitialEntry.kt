package xyz.decmurphy.sankeyg7r

import java.math.BigDecimal
import java.math.RoundingMode
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

fun List<Entry>.sankify() = this.onEach { it.sankify() }

fun List<Entry>.groupByName(): List<Entry> {
    val groupedList = mutableMapOf<String, Entry>()
    this.forEach {
        val key = "${it.sankey!!.input}-${it.sankey!!.output}"
        groupedList[key]?.let { existingEntry ->
            existingEntry.sankey!!.amount = BigDecimal(existingEntry.sankey!!.amount + it.sankey!!.amount).setScale(2, RoundingMode.HALF_UP).toDouble()
        } ?: run {
            groupedList[key] = it
        }
    }
    return groupedList.values.toList()
}

fun List<Entry>.stringify(header: String) = this.fold(header) {
        acc, cur -> acc + cur.sankey.toString() + "\n"
}