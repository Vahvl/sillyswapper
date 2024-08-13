package silly.yet.swapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import silly.yet.swapping.config.SillyConfig;

public class SillySwapping {

    //private static final long MIN_DELAY_MS = 10;  // Minimum delay in milliseconds
    //private static final long MAX_DELAY_MS = 30;  // Maximum delay in milliseconds
    private int originalSlot = -1; // Store the original slot
    private int targetSlot = -1; // Store the slot with Etherwarp Conduit
    private boolean wasSneaking = false;
    private boolean active = false;

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (!SillyConfig.sillySwapper) return;
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (player == null) return;
        ItemStack heldItem = player.inventory.getCurrentItem();
        if (!player.worldObj.isRemote) return;

        boolean keyHeld = SillyConfig.sillyBind.isActive();
        if (keyHeld == wasSneaking) return;
        wasSneaking = keyHeld;

        int currentSlot = player.inventory.currentItem;
        if (keyHeld) {
            if (SillyConfig.AOTVMode && heldItem != null && heldItem.getDisplayName().contains("Aspect Of The Void")) {
                if (Minecraft.getMinecraft().currentScreen != null) return;
                targetSlot = findHotbarSlotWithItem(player, "Etherwarp Conduit");
                if (targetSlot == -1) return;
                originalSlot = currentSlot;
                player.inventory.currentItem = targetSlot;
                active = true;
                player.sendQueue.addToSendQueue(new C09PacketHeldItemChange(targetSlot));
            } else if (!SillyConfig.AOTVMode){
                if (Minecraft.getMinecraft().currentScreen != null) return;
                targetSlot = findHotbarSlotWithItem(player, "Etherwarp Conduit");
                if (targetSlot == -1) return;
                originalSlot = currentSlot;
                player.inventory.currentItem = targetSlot;
                active = true;
                player.sendQueue.addToSendQueue(new C09PacketHeldItemChange(targetSlot));
            }
        } else {
            boolean toggle = active;
            active = false;
            if (toggle && heldItem.getDisplayName().contains("Etherwarp Conduit")) {
                player.inventory.currentItem = originalSlot;
                player.sendQueue.addToSendQueue(new C09PacketHeldItemChange(originalSlot));
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

    private long getRandomDelay(long min, long max) {
        return min + (long) (Math.random() * (max - min));
    }
}
