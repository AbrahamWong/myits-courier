package id.ac.its.myits.courier.ui.history.fragment.dummy

import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object AllDay {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<AllDayItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, AllDayItem> = HashMap()

    private val COUNT = 20

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: AllDayItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(position: Int): AllDayItem {
        return AllDayItem(
            position.toString(),
            "Item " + position,
            "Penjemputan Internal",
            randomCode(),
            (1..9).random(),
            "Joko Sunaryo",
            "Paket diterima PIC",
            Date()
        )
    }

    private fun randomCode(): String {
        val chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var randchar = ""
        for (i in 0..8) {
            randchar += chars[Math.floor(Math.random() * chars.length).toInt()]
        }
        return randchar
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class AllDayItem(
        val id: String,
        val content: String,
        val deliveryType: String,
        val packageCode: String,
        val numOfPackage: Int,
        val receiverName: String,
        val status: String,
        val lastDate: Date
    ) {
        override fun toString(): String = content
    }
}