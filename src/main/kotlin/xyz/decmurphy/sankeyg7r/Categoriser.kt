package xyz.decmurphy.sankeyg7r

import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class Categoriser {

    val patreonRegex = Regex("\\d{16}Pa")
    val boiToRevolutRegex = Regex("POS\\d{2}[A-Z]{3} Revolut")

    fun process(entry: String, isIncome: Boolean): Category {

        if (entry.contains("Zalando Ireland Lt")) return Category.INCOME_JOB

        if (entry.contains("STRIPE")) return Category.INCOME_FC
        if (patreonRegex.containsMatchIn(entry)) return Category.INCOME_FC
        if (entry.contains("JUNEte amo")) return Category.INCOME_LINDA

        if (boiToRevolutRegex.containsMatchIn(entry)) return Category.IGNORE

        if (entry.startsWith("To ")) return Category.TRANSFERS_OUT
        if (entry.startsWith("365 Online")) return Category.TRANSFERS_OUT
        if (entry.equals("Revolut Bank UAB") || entry.equals("Revolut Payments UAB")) {
            return if (isIncome) Category.TRANSFERS_IN else Category.TRANSFERS_OUT
        }
        if (entry.startsWith("Claims ")) return Category.TRANSFERS_IN
        if (entry.startsWith("From ")) return Category.TRANSFERS_IN
        if (entry.startsWith("JUNE")) return Category.TRANSFERS_IN
        if (entry.contains("Refund")) return Category.TRANSFERS_IN
        if (entry.contains("1/ROSALINDA ELIZAB GP")) return Category.TRANSFERS_IN

        if (entry.contains("Lansdowne Partners")) return Category.RENT

        if (entry.contains("Heb ")) return Category.GROCERIES
        if (entry.contains("Oxxo")) return Category.GROCERIES
        if (entry.contains("Centra")) return Category.GROCERIES
        if (entry.contains("Lidl")) return Category.GROCERIES
        if (entry.contains("Tesco")) return Category.GROCERIES
        if (entry.contains("Dunnes")) return Category.GROCERIES
        if (entry.contains("Applegreen")) return Category.GROCERIES
        if (entry.contains("Circle K")) return Category.GROCERIES
        if (entry.contains("Dealz")) return Category.GROCERIES
        if (entry.contains("Eurogiant")) return Category.GROCERIES
        if (entry.contains("Power City")) return Category.GROCERIES
        if (entry.contains("Penneys")) return Category.GROCERIES
        if (entry.contains("Supervalu")) return Category.GROCERIES
        if (entry.contains("Sv ")) return Category.GROCERIES
        if (entry.contains("Ikea")) return Category.GROCERIES
        if (entry.contains("Woodies")) return Category.GROCERIES
        if (entry.contains("Home")) return Category.GROCERIES
        if (entry.contains("Oriental Pantry")) return Category.GROCERIES
        if (entry.contains("Daybreak")) return Category.GROCERIES
        if (entry.contains("Petstop")) return Category.GROCERIES
        if (entry.contains("F.x. Buckley")) return Category.GROCERIES
        if (entry.contains("spar")) return Category.GROCERIES
        if (entry.contains("Spar")) return Category.GROCERIES
        if (entry.contains("Aldi")) return Category.GROCERIES
        if (entry.equals("Nutgrove")) return Category.GROCERIES
        if (entry.contains("Mr Price")) return Category.GROCERIES
        if (entry.contains("Aldi")) return Category.GROCERIES
        if (entry.contains("O Briens Off Licence")) return Category.GROCERIES
        if (entry.contains("Woodie's")) return Category.GROCERIES
        if (entry.contains("Woodie's")) return Category.GROCERIES
        if (entry.contains("Halfords")) return Category.GROCERIES
        if (entry.contains("Harvey Norman")) return Category.GROCERIES

        if (entry.contains("Nutgrove Veterinary")) return Category.PETS


        if (entry.contains("Panda Recycl")) return Category.BILLS
        if (entry.contains("ELECTRIC IREL")) return Category.BILLS
        if (entry.contains("Electric Ireland")) return Category.BILLS
        if (entry.contains("VIRGIN MEDIA")) return Category.BILLS
        if (entry.contains("VHI")) return Category.BILLS
        if (entry.contains("Top Dublin")) return Category.BILLS
        if (entry.contains("FEE: MAINTAINING ACC")) return Category.BILLS
        if (entry.contains("3ireland")) return Category.BILLS

        if (entry.contains("AXA INSURANCE")) return Category.CAR_AND_PETROL

        if (entry.contains("DECLAN MURPHY      SO")) return Category.SAVINGS
        if (entry.contains("BOI Savings")) return Category.SAVINGS

        if (entry.contains("BOI CR CARD")) return Category.FC_EXPENSES
        if (entry.contains("Google Gsuite_flightcl")) return Category.FC_EXPENSES
        if (entry.contains("Google*gsuite Flightcl")) return Category.FC_EXPENSES
        if (entry.contains("digitalocean.com")) return Category.FC_EXPENSES
        if (entry.contains("Name-Cheap.com")) return Category.FC_EXPENSES

        if (entry.contains("Airbnb")) return Category.TRAVEL
        if (entry.contains("Hotel")) return Category.TRAVEL
        if (entry.contains("Stena Estrid")) return Category.TRAVEL
        if (entry.contains("Iberia Express")) return Category.TRAVEL
        if (entry.contains("Getyourguide")) return Category.TRAVEL
        if (entry.contains("Ryanair")) return Category.TRAVEL
        if (entry.contains("Aer Lingus")) return Category.TRAVEL

        if (entry.contains("Leap Card")) return Category.TRANSPORT

        if (entry.contains("Starbucks")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Uber* Eats Pending")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Diep")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Coffee")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Camile Thai")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("www.camile.ie")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Mcdonalds")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Kitchen")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Uber* Eats Pending")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Nandos")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("nandos")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("El Grito")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Donuts")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Musashi")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Deliveroo")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Sprout")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Food")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Insomnia")) return Category.TAKEAWAY_AND_DRINKS
        if (entry.contains("Pizza")) return Category.TAKEAWAY_AND_DRINKS

        if (entry.contains("Uber* Pending")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("FREENOW")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Buck Mulligans")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Ferryman")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Movies")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("movies")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Bar")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Lounge")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Wrights")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Mother Reillys")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Churchtown Stores")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Bottle Tower")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Stags Head")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Blackbird")) return Category.GOING_OUT_AND_TAXIS
        if (entry.contains("Stags Head")) return Category.GOING_OUT_AND_TAXIS

        if (entry.contains("Grooming Room")) return Category.MISC
        if (entry.contains("ticketmaster")) return Category.MISC
        if (entry.contains("Boots")) return Category.MISC
        if (entry.contains("Cash at")) return Category.MISC
        if (entry.contains("zalando")) return Category.MISC
        if (entry.contains("Boots")) return Category.MISC
        if (entry.contains("Boots")) return Category.MISC

        if (entry.contains("OXFAM")) return Category.CHARITY

        if (entry.contains("Spotify")) return Category.SUBSCRIPTIONS
        if (entry.contains("Sn.nord* Products")) return Category.SUBSCRIPTIONS
        if (entry.contains("nordvpn.com")) return Category.SUBSCRIPTIONS
        if (entry.contains("Microsoft*subscription")) return Category.SUBSCRIPTIONS
        if (entry.contains("Amazon Prime")) return Category.SUBSCRIPTIONS

        if (entry.contains("PRIME FITNESS")) return Category.GYM
        if (entry.contains("Gym Plus")) return Category.GYM

        return if (isIncome) Category.UNCLASSIFIED_INCOME else Category.UNCLASSIFIED_OUTGOING
    }
}