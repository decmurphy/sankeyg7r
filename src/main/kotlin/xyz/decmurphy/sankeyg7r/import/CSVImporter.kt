package xyz.decmurphy.sankeyg7r.import

import xyz.decmurphy.sankeyg7r.Entry

interface CSVImporter {
    fun readCsvFiles(): List<Entry>
}