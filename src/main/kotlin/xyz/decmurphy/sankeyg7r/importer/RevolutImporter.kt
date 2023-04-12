package xyz.decmurphy.sankeyg7r.importer

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.ResourcePatternUtils
import org.springframework.stereotype.Service
import xyz.decmurphy.sankeyg7r.Record
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class RevolutImporter(
    val resourceLoader: ResourceLoader
) : CSVImporter {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    override fun readCsvFiles(): List<Record> {
        val entries = linkedSetOf<Record>()
        val csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
        val csvFolder = "classpath:transactions/revolut/*.csv"

        val resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(csvFolder)

        for (resource in resources) {

            val csvParser = CSVParser.parse(InputStreamReader(resource.inputStream), csvFormat)

            for (record in csvParser.records) {

                val type = record.get("Type")
                if (type == "CASHBACK" || type == "TOPUP" || type == "EXCHANGE") {
                    continue
                }

                val currency = record.get("Currency")
                if (currency != "EUR") {
                    continue
                }

                val description = record.get("Description")
                if (description == "Balance migration to another region or legal entity") {
                    continue
                }

                val state = record.get("State")
                if (state != "COMPLETED") {
                    continue
                }

                val amount = record.get("Amount").toDouble()
                val completedDate = record.get("Completed Date")
                val initialRecord = Record(
                    LocalDate.parse(completedDate, dateFormatter),
                    description,
                    if (amount < 0.0) -amount else null,
                    if (amount > 0.0) amount else null,
                    record.get("Balance").toDoubleOrNull()
                )
                entries.add(initialRecord)

            }
        }

        return entries.toList()
    }
}