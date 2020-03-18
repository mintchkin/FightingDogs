package in.mintchk.fightingdogs;

import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class ArmorProvider implements ICapabilityProvider {
    @CapabilityInject(IItemHandler.class)
    public static final Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    public static final LazyOptional<IItemHandler> holder = LazyOptional.of(ItemStackHandler::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return ITEM_HANDLER_CAPABILITY.orEmpty(cap, holder);
    }
}
