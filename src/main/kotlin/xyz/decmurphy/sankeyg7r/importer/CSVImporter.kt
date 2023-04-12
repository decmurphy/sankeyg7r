package xyz.decmurphy.sankeyg7r.importer

import xyz.decmurphy.sankeyg7r.Record

interface CSVImporter {
    fun readCsvFiles(): List<Record>
}