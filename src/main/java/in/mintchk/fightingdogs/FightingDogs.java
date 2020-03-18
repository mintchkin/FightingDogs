package in.mintchk.fightingdogs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

@Mod(Meta.MOD_ID)
public class FightingDogs {
    private static final Logger LOGGER = LogManager.getLogger();

    public FightingDogs() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void registerCapabilities(final AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof WolfEntity) {
            LOGGER.info("Adding wolf armorslot capability...");
            event.addCapability(new ResourceLocation(Meta.MOD_ID, "armorslot"), new ArmorProvider());
        }
    }

    @SubscribeEvent
    public void handleWolfInteraction(final EntityInteractSpecific event) {
        if (event.getWorld().isRemote || event.getHand() != Hand.MAIN_HAND
                || !(event.getTarget() instanceof WolfEntity)) {
            return;
        }

        WolfEntity wolf = (WolfEntity) event.getTarget();
        PlayerEntity player = event.getPlayer();

        LazyOptional<IItemHandler> capability = wolf.getCapability(ArmorProvider.ITEM_HANDLER_CAPABILITY);

        if (player.isCrouching()) {
            capability.ifPresent(cap -> {
                LOGGER.info("Removing item...");
                ItemHandlerHelper.giveItemToPlayer(player, cap.extractItem(0, Integer.MAX_VALUE, false));
            });
        } else {
            capability.ifPresent(cap -> {
                LOGGER.info("Adding item...");
                ItemStack remainder = cap.insertItem(0, player.getHeldItemMainhand(), false);
                player.setHeldItem(Hand.MAIN_HAND, remainder);
            });
        }

        event.getPlayer().sendMessage(new StringTextComponent("You clicked a dog!"));
        event.setCancellationResult(ActionResultType.SUCCESS);
        event.setCanceled(true);
    }
}
