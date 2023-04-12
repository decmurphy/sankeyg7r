package xyz.decmurphy.sankeyg7r.utilities

import org.springframework.stereotype.Service
import xyz.decmurphy.sankeyg7r.Record

@Service
class BalanceCalculator {

    fun process(initialRecord: Record, previousInitialRecord: Record?): Record {
        if (initialRecord.balance == null && previousInitialRecord != null) {
            initialRecord.balance = previousInitialRecord.balance!! - (initialRecord.debit ?: 0.0) + (initialRecord.credit ?: 0.0)
        }
        return initialRecord
    }
}