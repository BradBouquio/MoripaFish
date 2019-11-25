package me.elsiff.morefish.item

import me.elsiff.morefish.fishing.Fish
import me.elsiff.morefish.fishing.FishTypeTable
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType

/**
 * Created by elsiff on 2019-01-03.
 */
class FishItemTagReader(
    private val fishTypeTable: FishTypeTable,
    private val fishTypeKey: NamespacedKey,
    private val fishLengthKey: NamespacedKey
) {
    fun canRead(itemMeta: ItemMeta): Boolean {
        return itemMeta.persistentDataContainer.let { data ->
            data.has(fishTypeKey, PersistentDataType.STRING) && data.has(fishLengthKey, PersistentDataType.DOUBLE)
        }
    }

    fun read(itemMeta: ItemMeta): Fish {
        return itemMeta.persistentDataContainer.let { data ->
            require(data.has(fishTypeKey, PersistentDataType.STRING)) { "Item meta must have fish type tag" }
            require(data.has(fishLengthKey, PersistentDataType.DOUBLE)) { "Item meta must have fish length tag" }

            val typeName = data.get(fishTypeKey, PersistentDataType.STRING)
            val type = fishTypeTable.types.find { it.name == typeName }
                ?: throw IllegalStateException("Fish type doesn't exist")
            val length = data.get(fishLengthKey, PersistentDataType.DOUBLE) ?: throw IllegalStateException("Couldn't get fish length")
            Fish(type, length)
        }
    }
}