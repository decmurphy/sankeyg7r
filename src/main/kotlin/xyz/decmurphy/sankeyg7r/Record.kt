package xyz.decmurphy.sankeyg7r

import xyz.decmurphy.sankeyg7r.categories.Categoriser
import xyz.decmurphy.sankeyg7r.categories.Category
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

data class Record(
    val date: LocalDate,
    val details: String,
    val debit: Double?,
    val credit: Double?,
    var balance: Double?
) {
    var category: Category? = null
    var sankey: SankeyRecord? = null

    fun convertToSankeyTxs() {
        val categoryName = this.category?.name?.uppercase()
        if (this.credit != null)
            this.sankey = SankeyRecord(input = categoryName ?: "OTHER INCOME", amount = this.credit)
        else
            this.sankey = SankeyRecord(amount = this.debit!!, output = categoryName ?: "OTHER OUTGOING")
    }
}

data class SankeyRecord(
    var amount: Double,
    val input: String? = null,
    val output: String? = null
) {
    override fun toString(): String = "${input ?: "BUDGET"} [$amount] ${output ?: "BUDGET"}"
}

fun List<Record>.categoriseWith(categoriser: Categoriser): Pair<List<Record>, List<Record>> {

    val uncategorisedEntries = mutableListOf<Record>()

    val categorisedEntries = this
        .onEach {
            it.category = categoriser.process(it.details)
            if (it.category == null) {
                uncategorisedEntries.add(it)
            }
        }

    return Pair(categorisedEntries, uncategorisedEntries)
}

fun List<Record>.resolveSankeyInfo() = this.onEach { it.convertToSankeyTxs() }

fun List<Record>.groupByName(): List<Record> {
    val groupedList = mutableMapOf<String, Record>()
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

fun List<Record>.stringify(header: String) = this.fold(header) {
        acc, cur -> acc + cur.sankey.toString() + "\n"
}

fun Map<String, List<Record>>.sankify(): List<String> {
    return this.map { entry ->
        entry.value.groupByName()
            .sortedByDescending { it.sankey!!.amount }
            .stringify("\n// ${entry.key}\n")
    }
}