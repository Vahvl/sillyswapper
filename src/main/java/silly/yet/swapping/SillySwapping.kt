package silly.yet.swapping

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent
import silly.yet.swapping.config.SillyConfig

class SillySwapping {

    private var originalSlot = 1
    private var keyHeld = false
    private var active = false

    private val mc get() = Minecraft.getMinecraft()

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    fun onTick(event: PlayerTickEvent) {
        if (!SillyConfig.sillySwapper) return

        val player = mc.thePlayer ?: return

        val heldItem = player.inventory.getCurrentItem()

        val isActive = SillyConfig.sillyBind.isActive
        if (isActive == keyHeld) return
        keyHeld = isActive

        val currentSlot = player.inventory.currentItem
        if (isActive) {
            if (!SillyConfig.AOTVMode || heldItem != null && heldItem.displayName.contains("Aspect of the Void")) {
                val targetSlot = findHotbarSlotWithItem(player, "Etherwarp Conduit") ?: return

                originalSlot = currentSlot
                setSlot(targetSlot, player)
                active = true
            }
        } else {
            if (heldItem != null && active && heldItem.displayName.contains("Etherwarp Conduit")) {
                setSlot(originalSlot, player)
            }
            active = false
        }
    }

    private fun findHotbarSlotWithItem(player: EntityPlayer, itemName: String): Int? {
        for (i in 0..8) {
            val stack = player.inventory.getStackInSlot(i) ?: continue
            if (stack.hasDisplayName() && stack.displayName.contains(itemName)) {
                return i
            }
        }
        return null
    }

    private fun setSlot(slot: Int, player: EntityPlayer) {
        if (slot in 0..8) {
            player.inventory.currentItem = slot
        }
    }
    
}