package uk.co.jcox.farmingri.common.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.entity.ThreshingTableBlockEntity;

import java.util.List;

public class ThreshingTableBlock extends Block implements EntityBlock {


    public static final String TOOLTIP = "tooltip." + FarmingRI.MODID + ".threshing";
    public static final String TOOLTIP_INFO = "tooltip." + FarmingRI.MODID + ".threshing_info";

    public ThreshingTableBlock(Properties blockBehaviour) {
        super(blockBehaviour);
    }



    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter getter, @NotNull List<Component> comps, @NotNull TooltipFlag flag) {
        comps.add(Component.translatable(TOOLTIP));
        if (Screen.hasShiftDown()) {
            comps.add(Component.translatable(TOOLTIP_INFO, Component.literal(Integer.toString(ThreshingTableBlockEntity.ENERGY_CAPACITY)), Component.literal(Integer.toString(ThreshingTableBlockEntity.ENERGY_REQUIRED))));
        }
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new ThreshingTableBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }

        return (level1, pos, state1, entity) -> {
            if (entity instanceof ThreshingTableBlockEntity threshingTable) {
                threshingTable.tickServer();
            }
        };
    }


    @SuppressWarnings("deprecated")
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_60519_) {
        if (level.getBlockEntity(pos) instanceof ThreshingTableBlockEntity thresher) {
            thresher.destroyed();
        }

        super.onRemove(state,level,pos,newState,p_60519_);
    }

    @SuppressWarnings("deprecated")
    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos , Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(blockPos);

        if (be instanceof ThreshingTableBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) be, be.getBlockPos());
        } else {
            throw new IllegalStateException("Threshing container is missing!");
        }

        return InteractionResult.SUCCESS;
    }
}
