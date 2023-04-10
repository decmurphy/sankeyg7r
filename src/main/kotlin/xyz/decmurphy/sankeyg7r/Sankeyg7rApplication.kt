package xyz.decmurphy.sankeyg7r

import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import xyz.decmurphy.sankeyg7r.import.BOIImporter
import xyz.decmurphy.sankeyg7r.import.RevolutImporter
import java.math.BigDecimal
import java.math.RoundingMode

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@ConfigurationPropertiesScan
class Sankeyg7rApplication(
	val boiImporter: BOIImporter,
	val revolutImporter: RevolutImporter,
	val balanceCalculator: BalanceCalculator,
	val categoriser: Categoriser
) : CommandLineRunner {

	val uncategorisedEntries = mutableListOf<Entry>()

	override fun run(vararg args: String?) {

		/**
		 * Import all transactions
		 */
		val boiTransactions = boiImporter.readCsvFiles().reversed().populateBalances()
		val revolutTransactions = revolutImporter.readCsvFiles()

		/**
		 * Categorise, Sankify, Sort and Group by Month
		 */
		val transactionsByMonth = (boiTransactions + revolutTransactions)
			.categorise()
			.sankify()
			.sortedBy { it.date }
			.groupBy { "${it.date.year}-${it.date.month}" }

		/**
		 * Print uncategorised entries
		 */
		uncategorisedEntries
			.sortedByDescending { it.sankey!!.amount }
			.fold("\nUncategorised Entries:\n") { acc, cur -> acc + cur.details + "\n" }
			.let { logger.info { it } }

		/**
		 * Group categorised entries by name, sort by amount, and print
		 */
		transactionsByMonth.keys
			.map { month ->
				transactionsByMonth[month]!!
					.groupByName()
					.sortedByDescending { it.sankey!!.amount }
					.stringify("\n// $month\n")
			}
			.reduce { acc, cur -> acc + cur.toString() + "\n" }
			.let { logger.info { it } }

	}

	fun List<Entry>.populateBalances() = this.mapIndexed {
			idx, el -> balanceCalculator.process(el, if (idx == 0) null else this[idx-1])
	}

	fun List<Entry>.categorise() = this.map {
		it.category = categoriser.process(it.details, it.credit != null)
		if (it.category == null) {
			uncategorisedEntries.add(it)
		}
		it
	}.filter { it.category?.name?.lowercase() != "ignore" }

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

	fun List<Entry>.stringify(header: String?) = this.fold(header) {
			acc, cur -> acc + cur.sankey.toString() + "\n"
	}
}


fun main(args: Array<String>) {
	runApplication<Sankeyg7rApplication>(*args)
}