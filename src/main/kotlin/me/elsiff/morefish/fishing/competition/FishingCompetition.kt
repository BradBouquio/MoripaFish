package me.elsiff.morefish.fishing.competition

/**
 * Created by elsiff on 2018-12-25.
 */
class FishingCompetition {
    enum class State { ENABLED, DISABLED }

    private val records = sortedSetOf<Record>(Comparator.reverseOrder())
    var state = State.DISABLED

    fun enable() {
        checkStateDisabled()

        records.clear()
        state = State.ENABLED
    }

    fun disable() {
        checkStateEnabled()

        state = State.DISABLED
    }

    fun putRecord(record: Record) {
        checkStateEnabled()

        records.removeIf { it.fisher == record.fisher && it.fish.length > record.fish.length }
        records.add(record)
    }

    fun ranking(): List<Record> {
        return records.toList()
    }

    fun top(size: Int): List<Record> {
        return records.toList().subList(0, size)
    }

    fun clear() {
        records.clear()
    }

    private fun checkStateEnabled() {
        check(state == State.ENABLED) { "Fishing competition hasn't enabled" }
    }

    private fun checkStateDisabled() {
        check(state == State.DISABLED) { "Fishing competition hasn't disabled" }
    }
}