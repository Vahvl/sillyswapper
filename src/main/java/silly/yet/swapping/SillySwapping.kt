package silly.yet.swapping

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent
import silly.yet.swapping.config.SillyConfig

class SillySwapping {

    private var originalSlot = 1
    private var keyHeld = false
    private var active = false

    private val mc get() = Minecraft.getMinecraft()

    @SubscribeEvent
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
                val targetSlot = player.findHotbarSlotWithItem("Etherwarp Conduit") ?: return

                originalSlot = currentSlot
                player.setSlot(targetSlot)
                active = true
            }
        } else {
            if (heldItem != null && active && heldItem.displayName.contains("Etherwarp Conduit")) {
                player.setSlot(originalSlot)
            }
            active = false
        }
    }

    private fun EntityPlayer.findHotbarSlotWithItem(itemName: String): Int? {
        for (i in 0..8) {
            val stack = inventory.getStackInSlot(i) ?: continue
            if (stack.hasDisplayName() && stack.displayName.contains(itemName)) {
                return i
            }
        }
        return null
    }

    private fun EntityPlayer.setSlot(slot: Int) {
        if (slot in 0..8) inventory.currentItem = slot
    }
    
}