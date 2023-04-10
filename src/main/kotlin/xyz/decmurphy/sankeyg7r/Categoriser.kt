package xyz.decmurphy.sankeyg7r

import org.springframework.stereotype.Service
import xyz.decmurphy.sankeyg7r.config.AppConfig

@Service
class Categoriser(
    val appConfig: AppConfig
) {

    fun process(entry: String, isIncome: Boolean): AppCategory? {

        return appConfig.categories.find { entry belongsTo it }
// //
//        if (entry.startsWith("To ")) return Category.TRANSFERS_OUT
//        if (entry.startsWith("365 Online")) return Category.TRANSFERS_OUT
//        if (entry.equals("Revolut Bank UAB") || entry.equals("Revolut Payments UAB")) {
//            return if (isIncome) Category.TRANSFERS_IN else Category.TRANSFERS_OUT
//        }
//
//        if (entry.contains("BOI CR CARD")) return Category.FC_EXPENSES
//        if (entry.contains("Google Gsuite_flightcl")) return Category.FC_EXPENSES
//        if (entry.contains("Google*gsuite Flightcl")) return Category.FC_EXPENSES
//        if (entry.contains("digitalocean.com")) return Category.FC_EXPENSES
//        if (entry.contains("Name-Cheap.com")) return Category.FC_EXPENSES
//
//        if (entry.contains("Leap Card")) return Category.TRANSPORT
//
//        if (entry.contains("Grooming Room")) return Category.MISC
//        if (entry.contains("ticketmaster")) return Category.MISC
//        if (entry.contains("Boots")) return Category.MISC
//        if (entry.contains("Cash at")) return Category.MISC
//        if (entry.contains("zalando")) return Category.MISC
//        if (entry.contains("Boots")) return Category.MISC
//        if (entry.contains("Boots")) return Category.MISC
//
//        if (entry.contains("OXFAM")) return Category.CHARITY
//
//        return if (isIncome) Category.UNCLASSIFIED_INCOME else Category.UNCLASSIFIED_OUTGOING
    }
}