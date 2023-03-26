package xyz.decmurphy.sankeyg7r

enum class HighLevelCategory(val displayName: String) {

    BUDGET("Budget")

    , ZALANDO("Zalando")
    , FC("FC")
    , OTHER_INCOME("Other Income")

    , RENT("Rent")
    , BILLS("Bills")
    , GROCERIES("Groceries")
    , SAVINGS("Savings")
    , CAR("Car")
    , PETS("Pets")
    , EATING_OUT("Eating Out")
    , GOING_OUT("Going Out")
    , TRAVEL("Travel")

    , OTHER("Other")

    , UNCLASSIFIED_INCOME("?")
    , UNCLASSIFIED_OUTGOING("??")

    , IGNORE("Ignore")

}

enum class Category(val highLevelCategory: HighLevelCategory) {

    BUDGET(HighLevelCategory.BUDGET)

    , INCOME_JOB(HighLevelCategory.ZALANDO)
    , INCOME_FC(HighLevelCategory.FC)
    , INCOME_LINDA(HighLevelCategory.OTHER_INCOME)
    , TRANSFERS_IN(HighLevelCategory.OTHER_INCOME)

    , RENT(HighLevelCategory.RENT)
    , BILLS(HighLevelCategory.BILLS)
    , GROCERIES(HighLevelCategory.GROCERIES)
    , SAVINGS(HighLevelCategory.SAVINGS)
    , CAR_AND_PETROL(HighLevelCategory.CAR)
    , PETS(HighLevelCategory.PETS)
    , TAKEAWAY_AND_DRINKS(HighLevelCategory.EATING_OUT)
    , GOING_OUT_AND_TAXIS(HighLevelCategory.GOING_OUT)
    , TRAVEL(HighLevelCategory.TRAVEL)

    , TRANSFERS_OUT(HighLevelCategory.OTHER)
    , TRANSPORT(HighLevelCategory.OTHER)
    , SUBSCRIPTIONS(HighLevelCategory.OTHER)
    , GYM(HighLevelCategory.OTHER)
    , CHARITY(HighLevelCategory.OTHER)
    , FC_EXPENSES(HighLevelCategory.OTHER)
    , MISC(HighLevelCategory.OTHER)

    , UNCLASSIFIED_INCOME(HighLevelCategory.UNCLASSIFIED_INCOME)
    , UNCLASSIFIED_OUTGOING(HighLevelCategory.UNCLASSIFIED_OUTGOING)

    , IGNORE(HighLevelCategory.IGNORE)

}