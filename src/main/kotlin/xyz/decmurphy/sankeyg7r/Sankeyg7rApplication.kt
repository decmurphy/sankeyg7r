package xyz.decmurphy.sankeyg7r

import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import xyz.decmurphy.sankeyg7r.import.BOIImporter
import xyz.decmurphy.sankeyg7r.import.RevolutImporter

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@ConfigurationPropertiesScan
class Sankeyg7rApplication(
	val boiImporter: BOIImporter,
	val revolutImporter: RevolutImporter,
	val categoriser: Categoriser
) : CommandLineRunner {

	override fun run(vararg args: String?) {

		/**
		 * Import all transactions
		 */
		val boiTransactions = boiImporter.readCsvFiles()
		val revolutTransactions = revolutImporter.readCsvFiles()

		/**
		 * Categorise
		 */
		val (categorised, uncategorised) = (boiTransactions + revolutTransactions).categorise()

		/**
		 * Print uncategorised entries
		 */
		uncategorised
			.groupBy { it.details }
			.let { map -> map.keys.map { Pair(it, map[it]!!.fold(0.0) { acc, cur -> acc + (cur.debit ?: cur.credit!!) }) } }
			.sortedByDescending { it.second }
			.joinToString("") { pair -> "${pair.first} (x${pair.second})\n" }
			.let { logger.info { "\nUncategorised Entries:\n$it" } }

		/**
		 * Sankify, Sort and Group by Period. Then group categorised entries by name, sort by amount, and print
		 */
		categorised
			.filter { it.category?.name?.lowercase() != "ignore" }
			.sankify()
			.sortedBy { it.date }
			.groupBy { "${it.date.year}" } // group by year
//			.groupBy { "${it.date.year}-${it.date.month}" } // group by month
			.let { transactionsPerPeriod ->
				transactionsPerPeriod.keys
					.joinToString("") { period ->
						transactionsPerPeriod[period]!!
							.groupByName()
							.sortedByDescending { it.sankey!!.amount }
							.stringify("\n// $period\n")
					}
			}
			.let { logger.info { it } }

	}

	fun List<Entry>.categorise(): Pair<List<Entry>, List<Entry>> {

		val uncategorisedEntries = mutableListOf<Entry>()

		val categorisedEntries = this
			.onEach {
				it.category = categoriser.process(it.details, it.credit != null)
				if (it.category == null) {
					uncategorisedEntries.add(it)
				}
			}

		return Pair(categorisedEntries, uncategorisedEntries)
	}
}


fun main(args: Array<String>) {
	runApplication<Sankeyg7rApplication>(*args)
}