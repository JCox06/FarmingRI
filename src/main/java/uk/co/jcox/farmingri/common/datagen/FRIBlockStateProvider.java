package uk.co.jcox.farmingri.common.datagen;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import uk.co.jcox.farmingri.FarmingRI;
import uk.co.jcox.farmingri.common.block.*;
import uk.co.jcox.farmingri.common.setup.Registration;

import java.util.function.Function;

public class FRIBlockStateProvider extends BlockStateProvider {

    public FRIBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, FarmingRI.MODID, exFileHelper);
    }


    @Override
    protected void registerStatesAndModels() {


        simpleBlock(Registration.BLOCK_THRESHING_TABLE.get());
        simpleBlock(Registration.BLOCK_STRAW_BLOCK.get());
        simpleBlock(Registration.BLOCK_HAY_BLOCK.get());
        simpleBlock(Registration.BLOCK_SILAGE_BALE.get());
        simpleBlock(Registration.BLOCK_GRASS_BALE.get());

        stairsBlock((StairBlock) Registration.BLOCK_STRAW_STAIRS.get(), modLoc("block/" + Registration.BLOCK_STRAW_BLOCK.getId().getPath()));
        ResourceLocation texture = modLoc("block/" + Registration.BLOCK_STRAW_BLOCK.getId().getPath());
        slabBlock((SlabBlock) Registration.BLOCK_STRAW_SLAB.get(), texture, texture);

        cropBlock(Registration.BLOCK_LONG_WHEAT, LongWheatBlock.AGE);
        cropBlock(Registration.BLOCK_LONG_WHEAT_UPPER, LongWheatBlockUpper.AGE);
        cropBlock(Registration.BLOCK_TALL_FESCUE, TallFescueBlock.AGE);
        cropBlock(Registration.BLOCK_TALL_FESCUE_UPPER, TallFescueBlockUpper.AGE);

        complexRot(Registration.BLOCK_WOOD_POWER_GENERATOR, (state) -> {
            final String loc = Registration.BLOCK_WOOD_POWER_GENERATOR.getId().getPath();
            return models().getBuilder(loc + "_" + state.getValue(WoodPowerGenBlock.POWERED).getSerializedName())
                    .parent(models().getExistingFile(mcLoc("block/orientable")))
                    .texture("front", modLoc("block/" + loc + "_front_" + state.getValue(WoodPowerGenBlock.POWERED).getSerializedName()))
                    .texture("side", modLoc("block/" +loc + "_side"))
                    .texture("top", modLoc("block/" +loc + "_side"));
        });

    }


    protected void cropBlock(RegistryObject<Block> blockObj, IntegerProperty getAgeProperty) {
        this.getVariantBuilder(blockObj.get())
                .forAllStates( (BlockState state) -> {
                   int age = state.getValue(getAgeProperty);
                   String modelPath = modLoc("block/" + blockObj.getId().getPath() + "_age" + age).getPath();
                   ModelFile file = models().getBuilder(modelPath)
                           .parent(models().getExistingFile(mcLoc("block/crop")))
                           .renderType(mcLoc("cutout"))
                           .texture("crop", modelPath);

                   return ConfiguredModel.builder()
                           .modelFile(file)
                           .build();
                });
    }


    protected void complexRot(RegistryObject<Block> blockObj, Function<BlockState, ModelFile> prepareModelFile) {
        this.getVariantBuilder(blockObj.get())
                .forAllStates((BlockState blockState) -> {
                    Direction facing = blockState.getValue(BlockStateProperties.HORIZONTAL_FACING);
                    ModelFile file = prepareModelFile.apply(blockState);

                    return ConfiguredModel.builder()
                            .modelFile(file)
                            .rotationY((int) facing.toYRot())
                            .build();
                });
    }
}
