package me.elsiff.morefish.fishing.competition

import me.elsiff.morefish.util.NumberUtils
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Sign
import org.bukkit.block.Skull
import org.bukkit.entity.ItemFrame
import org.bukkit.plugin.Plugin
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*




/**
 * Created by elsiff on 2019-01-20.
 */
class Prize(
    private val commands: List<String>
) {
    fun giveTo(record: Record, rankNumber: Int, plugin: Plugin) {
        if (!record.fisher.isOnline) {
            val ordinal = NumberUtils.ordinalOf(rankNumber)
            plugin.logger.warning("$ordinal fisher ${record.fisher.name} isn't online! Contest prizes may not be sent.")
        }

        val server = plugin.server
        for (command in commands) {
            server.dispatchCommand(server.consoleSender, command.replace("@p", record.fisher.name!!))
        }

        if (rankNumber == 1 ) {
            setVictoryStand(record)
        }

    }

    //もりぱの表彰台設置
    fun setVictoryStand(record: Record) {
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

        //看板の書き換え
        val signLocation = Location(Bukkit.getWorld("world"),-557.0,75.0, dayOfWeek.let {
            when(it) {
                Calendar.FRIDAY -> -89.0
                Calendar.SATURDAY -> -90.0
                else -> -91.0
            }
        })
        val sign = signLocation.block.state as Sign
        sign.lines[0] = record.fisher.name
        sign.lines[1] = record.fish.type.displayName
        sign.lines[2] = "${record.fish.length} cm"
        sign.lines[3] = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))


        //プレイヤーヘッドの書き換え
        val headLocation = signLocation.clone()
        headLocation.y = 73.0
        val head = headLocation.block.state as Skull
        head.setOwningPlayer(record.fisher)
        head.update()


        //額縁の書き換え
        val itemFrameLocation = Location(Bukkit.getWorld("world"),-556.97,74.50, dayOfWeek.let {
            when(it) {
                Calendar.FRIDAY -> -88.50
                Calendar.SATURDAY -> -89.50
                else -> -90.50
            }
        })
        val itemFrameList = Bukkit.getWorld("world")?.getNearbyEntitiesByType(
            ItemFrame::class.java,itemFrameLocation,0.1
        )
        for (itemFrame in itemFrameList!!) {
            itemFrame.setItem(record.fish.type.icon)
        }

    }
}
