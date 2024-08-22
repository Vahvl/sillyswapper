package silly.yet.swapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import silly.yet.swapping.config.SillyConfig;

public class SillySwapping {

    private int originalSlot = -1; // Store the original slot
    private int targetSlot = -1; // Store the slot with Etherwarp Conduit
    private boolean keyHeld = false;
    private boolean active = false;


    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (!SillyConfig.sillySwapper) return;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        ItemStack heldItem = player.inventory.getCurrentItem();
        if (!player.worldObj.isRemote) return;

        boolean keyHeld = SillyConfig.sillyBind.isActive();
        if (keyHeld == this.keyHeld) return;
        this.keyHeld = keyHeld;

        int currentSlot = player.inventory.currentItem;
        if (keyHeld) {
            if (SillyConfig.AOTVMode && heldItem != null && heldItem.getDisplayName().contains("Aspect of the Void")) {
                if (Minecraft.getMinecraft().currentScreen != null) return;
                targetSlot = findHotbarSlotWithItem(player, "Etherwarp Conduit");
                if (targetSlot == -1) return;
                originalSlot = currentSlot;
                player.inventory.currentItem = targetSlot;
                active = true;
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = targetSlot;
            } else if (!SillyConfig.AOTVMode) {
                if (Minecraft.getMinecraft().currentScreen != null) return;
                targetSlot = findHotbarSlotWithItem(player, "Etherwarp Conduit");
                if (targetSlot == -1) return;
                originalSlot = currentSlot;
                player.inventory.currentItem = targetSlot;
                active = true;
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = targetSlot;
            }
        } else {
            boolean toggle = active;
            active = false;
            if (heldItem != null && toggle && heldItem.getDisplayName().contains("Etherwarp Conduit")) {
                player.inventory.currentItem = originalSlot;
                Minecraft.getMinecraft().thePlayer.inventory.currentItem = originalSlot;
            }
        }
    }

    private int findHotbarSlotWithItem(EntityPlayer player, String itemName) {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (stack != null && stack.hasDisplayName() && stack.getDisplayName().contains(itemName)) {
                return i;
            }
        }
        return -1;
    }
}