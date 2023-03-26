package xyz.decmurphy.sankeyg7r.import

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.springframework.core.io.ResourceLoader
import org.springframework.core.io.support.ResourcePatternUtils
import org.springframework.stereotype.Service
import xyz.decmurphy.sankeyg7r.Entry
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class BOIImporter(
    val resourceLoader: ResourceLoader
) : CSVImporter {

    val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    override fun readCsvFiles(): List<Entry> {
        val entries = linkedSetOf<Entry>()
        val csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader()
        val csvFolder = "classpath:transactions/boi/*.csv"

        val resources = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(csvFolder)

        for (resource in resources) {

            val csvParser = CSVParser.parse(InputStreamReader(resource.inputStream), csvFormat)

            for (record in csvParser.records) {
                val initialEntry = Entry(
                    LocalDate.parse(record.get("Date"), dateFormatter),
                    record.get("Details"),
                    record.get("Debit").toDoubleOrNull(),
                    record.get("Credit").toDoubleOrNull(),
                    record.get("Balance").toDoubleOrNull()
                )
                entries.add(initialEntry)
            }
        }

        return entries.toList()
    }
}