package xyz.decmurphy.sankeyg7r.categories

interface Categoriser {
    fun process(entry: String): Category?
}