package me.elsiff.morefish.fishing.catchhandler

import com.okkero.skedule.schedule
import me.elsiff.morefish.MoreFish
import me.elsiff.morefish.fishing.Fish
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.FireworkEffect
import org.bukkit.entity.Firework
import org.bukkit.entity.Player

/**
 * Created by elsiff on 2019-01-15.
 */
class CatchFireworkSpawner(val plugin: MoreFish) : CatchHandler {
    override fun handle(catcher: Player, fish: Fish) {
        val scheduler = Bukkit.getScheduler()
        scheduler.schedule(plugin) {
            val effect = FireworkEffect.builder()
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(effectConverter(fish.type.rarity.color))
                .withFade()
                .withTrail()
                .withFlicker()
                .build()

            for (i in fish.type.rarity.fireworkCount) {
                val firework = catcher.world.spawn(catcher.location, Firework::class.java)
                val meta = firework.fireworkMeta
                meta.addEffect(effect)
                meta.power = 1
                firework.fireworkMeta = meta
                waitFor(20)
            }
        }
    }

    private fun effectConverter(color: ChatColor) :Color {
        return when (color) {
            ChatColor.AQUA -> Color.AQUA
            ChatColor.BLACK -> Color.BLACK
            ChatColor.BLUE -> Color.NAVY
            ChatColor.DARK_AQUA -> Color.TEAL
            ChatColor.DARK_BLUE -> Color.BLUE
            ChatColor.DARK_GRAY -> Color.SILVER
            ChatColor.DARK_GREEN -> Color.GREEN
            ChatColor.DARK_PURPLE -> Color.FUCHSIA
            ChatColor.DARK_RED -> Color.RED
            ChatColor.GOLD -> Color.ORANGE
            ChatColor.GRAY -> Color.GRAY
            ChatColor.GREEN -> Color.LIME
            ChatColor.LIGHT_PURPLE -> Color.PURPLE
            ChatColor.RED -> Color.MAROON
            ChatColor.WHITE -> Color.WHITE
            ChatColor.YELLOW -> Color.YELLOW
            else -> Color.WHITE
        }
    }
}