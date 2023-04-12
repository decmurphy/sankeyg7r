package xyz.decmurphy.sankeyg7r

import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import xyz.decmurphy.sankeyg7r.categories.AppCategoriser
import xyz.decmurphy.sankeyg7r.importer.BOIImporter
import xyz.decmurphy.sankeyg7r.importer.RevolutImporter

private val logger = KotlinLogging.logger {}

@SpringBootApplication
@ConfigurationPropertiesScan
class Sankeyg7rApplication(
	val boiImporter: BOIImporter,
	val revolutImporter: RevolutImporter,
	val categoriser: AppCategoriser
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
		val (categorised, uncategorised) = (boiTransactions + revolutTransactions).categoriseWith(categoriser)

		/**
		 * Print uncategorised entries
		 */
		uncategorised
			.groupBy { it.details }
			.let { map -> map.keys.map { Pair(it, map[it]!!.fold(0.0) { acc, cur -> acc + (cur.debit ?: cur.credit!!) }) } }
			.sortedByDescending { it.second }
			.joinToString("", prefix = "\n\nUncategorized Entries:\n") { pair -> "${pair.first} (x${pair.second})\n" }
			.apply { logger.info { this } }

		/**
		 * Sankify, Sort and Group by Period. Then group categorised entries by name, sort by amount, and print
		 */
		categorised
			.filter { it.category?.name?.lowercase() != "ignore" }
			.resolveSankeyInfo()
			.sortedBy { it.date }
			.groupBy { "${it.date.year}" } // group by year
			.sankify()
			.joinToString("")
			.apply { logger.info { this } }

	}
}


fun main(args: Array<String>) {
	runApplication<Sankeyg7rApplication>(*args)
}