package xyz.decmurphy.sankeyg7r

import org.springframework.stereotype.Service

@Service
class BalanceCalculator {

    fun process(initialEntry: Entry, previousInitialEntry: Entry?): Entry {
        if (initialEntry.balance == null && previousInitialEntry != null) {
            initialEntry.balance = previousInitialEntry.balance!! - (initialEntry.debit ?: 0.0) + (initialEntry.credit ?: 0.0)
        }
        return initialEntry
    }
}