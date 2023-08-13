package uk.co.jcox.farmingri.common.block;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.entity.WoodPowerGenBlockEntity;

import java.util.List;

public class WoodPowerGenBlock extends Block implements EntityBlock {

    public static final String TOOLTIP = "tooltip." + FarmingRI.MODID + ".wood_power_gen";

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<State> POWERED = EnumProperty.create("genpower", State.class);

    public WoodPowerGenBlock(Properties blockBehaviour) {
        super(blockBehaviour);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection())
                .setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()) ? State.POWERED : State.OFF);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> states) {
        states.add(FACING);
        states.add(POWERED);
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos position, @NotNull BlockState blockState) {
        return new WoodPowerGenBlockEntity(position, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }

        return (Level lvl, BlockPos pos, BlockState state1, T be) -> {
            if (be instanceof WoodPowerGenBlockEntity generator) {
                generator.tickServer();
            }
        };
    }


    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter getter, @NotNull List<Component> components, @NotNull TooltipFlag flag) {

        if (Screen.hasShiftDown()) {
            int storage = WoodPowerGenBlockEntity.POWER_STORAGE;
            int gen = WoodPowerGenBlockEntity.POWER_GEN_PER_TICK;
            int send = WoodPowerGenBlockEntity.POWER_SEND_PER_TICK;
            components.add(Component.translatable(TOOLTIP, Component.literal(storage + ""), Component.literal(gen + ""), Component.literal(send + "")));
        }
    }


    @SuppressWarnings("deprecated")
    @Override
    public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos thisBlock, @NotNull Block block, @NotNull BlockPos neighborBlock, boolean p_60514_) {
        if (level.isClientSide) {
            return;
        }

        BlockState base = defaultBlockState()
                .setValue(FACING, state.getValue(FACING));

        if (level.hasNeighborSignal(thisBlock)) {
            level.setBlockAndUpdate(thisBlock, base.setValue(POWERED, State.POWERED));
        } else {
            level.setBlockAndUpdate(thisBlock, base.setValue(POWERED, State.OFF));
        }
    }

    public enum State implements StringRepresentable {
        POWERED,
        OFF,
        GENERATING;

        @Override
        public @NotNull String getSerializedName() {
            switch (this) {
                case OFF -> {
                    return "off";
                }

                case POWERED -> {
                    return "powered";
                }

                case GENERATING -> {
                    return "gen";
                }
            }
            return "off";
        }
    }


    @SuppressWarnings("deprecated")
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof WoodPowerGenBlockEntity) {
            NetworkHooks.openScreen((ServerPlayer) player, (MenuProvider) be, be.getBlockPos());
        } else {
            throw new IllegalStateException("PowerGen container is missing!");
        }

        return InteractionResult.SUCCESS;
    }


    @Deprecated
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean p_60519_) {

        if (level.getBlockEntity(pos) instanceof WoodPowerGenBlockEntity powerGen) {
            powerGen.destroyed();
        }

        super.onRemove(state, level, pos, newState, p_60519_);
    }
}
