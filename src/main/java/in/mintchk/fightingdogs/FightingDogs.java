package in.mintchk.fightingdogs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Meta.MOD_ID)
public class FightingDogs {
    private static final Logger LOGGER = LogManager.getLogger();

    public FightingDogs() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void preInit(final FMLCommonSetupEvent event) {
        LOGGER.info("Starting up...");
    }

    @SubscribeEvent
    public void handleWolfInteraction(final EntityInteractSpecific event) {
        if (!event.getWorld().isRemote && event.getHand() == Hand.MAIN_HAND && event.getTarget() instanceof WolfEntity) {
            event.getPlayer().sendMessage(new StringTextComponent("You clicked a dog!"));
            event.setCancellationResult(ActionResultType.SUCCESS);
            event.setCanceled(true);
        }
    }
}
