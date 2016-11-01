package binnie.extratrees.block;

import binnie.core.BinnieCore;
import binnie.core.IInitializable;
import binnie.core.Mods;
import binnie.core.block.ItemMetadata;
import binnie.core.block.TileEntityMetadata;
import binnie.core.liquid.ILiquidType;
import binnie.extratrees.ExtraTrees;
import binnie.extratrees.block.decor.BlockFence;
import binnie.extratrees.block.decor.BlockGate;
import binnie.extratrees.block.decor.BlockMultiFence;
import binnie.extratrees.block.decor.FenceType;
import binnie.extratrees.block.decor.MultiFenceRecipeEmbedded;
import binnie.extratrees.block.decor.MultiFenceRecipeSize;
import binnie.extratrees.block.decor.MultiFenceRecipeSolid;
import binnie.extratrees.genetics.WoodAccess;
import binnie.extratrees.item.ExtraTreeLiquid;
import forestry.api.arboriculture.EnumVanillaWoodType;
import forestry.api.arboriculture.IWoodType;
import forestry.api.arboriculture.TreeManager;
import forestry.api.arboriculture.WoodBlockKind;
import forestry.api.recipes.RecipeManagers;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

import static binnie.extratrees.block.BlockETLog.woodTypes;
import static binnie.extratrees.block.BlockETPlanks.plankTypes;

public class ModuleBlocks implements IInitializable {
	// public static int hedgeRenderID;

	@Override
	public void preInit() {
		PlankType.setup();
		//ExtraTrees.blockPlanks = new BlockETPlanks();
		ExtraTrees.blockFence = new BlockFence("fence");

		for (int i = 0; i < BlockETPlanks.GROUP_COUNT ; i++) {
			int finalI = i;
			BlockETPlanks blockETPlank = new BlockETPlanks(finalI, false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {return plankTypes[finalI];}
			};

			GameRegistry.register(blockETPlank);
			Item plank = GameRegistry.register(new BlockETPlanks.PlankItemBlock<>(blockETPlank));
			WoodAccess.getInstance().registerWithVariants(blockETPlank, WoodBlockKind.PLANKS, plankTypes[finalI], false);
			ExtraTrees.proxy.setCustomStateMapper("plank", blockETPlank);


			//fireproof
			BlockETPlanks blockETPlankFireproof = new BlockETPlanks(finalI, true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return plankTypes[finalI];
				}
			};
			GameRegistry.register(blockETPlankFireproof);
			ExtraTrees.proxy.setCustomStateMapper("plank", blockETPlankFireproof);
			Item plankFireproof = GameRegistry.register(new BlockETPlanks.PlankItemBlock<>(blockETPlankFireproof));
			WoodAccess.getInstance().registerWithVariants(blockETPlankFireproof, WoodBlockKind.PLANKS, plankTypes[finalI], true);

			for (EnumExtraTreeLog l : plankTypes[finalI].getAllowedValues()) {
				BinnieCore.proxy.registermodel(plank, l.getMetadata() % BlockETPlanks.VARIANTS_PER_BLOCK, new ModelResourceLocation("extratrees:plank", "wood_type=" + l.getName()));
				BinnieCore.proxy.registermodel(plankFireproof, l.getMetadata() % BlockETPlanks.VARIANTS_PER_BLOCK, new ModelResourceLocation("extratrees:plank", "wood_type=" + l.getName()));

			}
		}

		for (int i = 0; i < BlockETLog.GROUP_COUNT; i++) {
			int finalI = i;
			BlockETLog blockETLog = new BlockETLog(finalI, false) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return woodTypes[finalI];
				}
			};

			GameRegistry.register(blockETLog);
			Item wood = GameRegistry.register(new BlockETLog.LogItemBlock<>(blockETLog));

			WoodAccess.getInstance().registerWithVariants(blockETLog, WoodBlockKind.LOG, woodTypes[finalI], false);
			ExtraTrees.proxy.setCustomStateMapper("log", blockETLog);

			//fireproof
			BlockETLog blockETLogFireproof = new BlockETLog(i, true) {
				@Override
				public PropertyEnum<EnumExtraTreeLog> getVariant() {
					return woodTypes[finalI];
				}
			};
			GameRegistry.register(blockETLogFireproof);
			ExtraTrees.proxy.setCustomStateMapper("log", blockETLogFireproof);
			Item woodFireproof = GameRegistry.register(new BlockETLog.LogItemBlock<>(blockETLogFireproof));
			for (EnumExtraTreeLog l : woodTypes[finalI].getAllowedValues()) {
				BinnieCore.proxy.registermodel(wood, l.getMetadata() % BlockETLog.VARIANTS_PER_BLOCK, new ModelResourceLocation("extratrees:log", "axis=y,wood_type=" + l.getName()));
				BinnieCore.proxy.registermodel(woodFireproof, l.getMetadata() % BlockETLog.VARIANTS_PER_BLOCK, new ModelResourceLocation("extratrees:log", "axis=y,wood_type=" + l.getName()));
			}
			WoodAccess.getInstance().registerWithVariants(blockETLogFireproof, WoodBlockKind.LOG, woodTypes[finalI], true);
		}


		ExtraTrees.blockGate = new BlockGate();
		ExtraTrees.blockDoor = new BlockETDoor();
		ExtraTrees.blockMultiFence = new BlockMultiFence();
		ExtraTrees.blockSlab = new BlockETSlab(false);
		ExtraTrees.blockDoubleSlab = new BlockETSlab(true);
//		ExtraTrees.blockStairs = new BlockETStairs(ExtraTrees.blockPlanks);
//		GameRegistry.register(ExtraTrees.blockPlanks);
//		GameRegistry.register(new ItemMetadata(ExtraTrees.blockPlanks).setRegistryName(ExtraTrees.blockPlanks.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockFence);
		GameRegistry.register(new ItemMetadata(ExtraTrees.blockFence).setRegistryName(ExtraTrees.blockFence.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockMultiFence);
		GameRegistry.register(new ItemMetadata(ExtraTrees.blockMultiFence).setRegistryName(ExtraTrees.blockMultiFence.getRegistryName()));
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockMultiFence), new ItemMetadataRenderer());

		GameRegistry.register(ExtraTrees.blockGate);
		GameRegistry.register(new ItemMetadata(ExtraTrees.blockGate).setRegistryName(ExtraTrees.blockGate.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockSlab);
		GameRegistry.register(new ItemETSlab(ExtraTrees.blockSlab).setRegistryName(ExtraTrees.blockSlab.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockDoubleSlab);
		GameRegistry.register(new ItemETSlab(ExtraTrees.blockDoubleSlab).setRegistryName(ExtraTrees.blockDoubleSlab.getRegistryName()));
		GameRegistry.register(ExtraTrees.blockDoor);
		GameRegistry.register(new ItemETDoor(ExtraTrees.blockDoor).setRegistryName(ExtraTrees.blockDoor.getRegistryName()));
//		GameRegistry.register(ExtraTrees.blockStairs);
	//	GameRegistry.register(new ItemETStairs(ExtraTrees.blockStairs).setRegistryName(ExtraTrees.blockStairs.getRegistryName()));
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockStairs), new StairItemRenderer());
		//BinnieCore.proxy.registerCustomItemRenderer(Item.getItemFromBlock(ExtraTrees.blockGate), new GateItemRenderer());
		for (final EnumExtraTreeLog plank : EnumExtraTreeLog.values()) {
			OreDictionary.registerOre("logWood", TreeManager.woodAccess.getStack(plank, WoodBlockKind.LOG, false));
		}
//		GameRegistry.addSmelting(ExtraTrees.blockLog, new ItemStack(Items.COAL, 1, 1), 0.15f);
		for (final IPlankType plank2 : PlankType.ExtraTreePlanks.values()) {
			OreDictionary.registerOre("plankWood", plank2.getStack());
		}
		//	FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:fence");
		//	FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:gate");
		// FMLInterModComms.sendMessage("Forestry", "add-alveary-slab",
		// "ExtraTrees:slab");
		//	FMLInterModComms.sendMessage("forestry", "add-fence-block", "ExtraTrees:multifence");
		//ModuleBlocks.hedgeRenderID = BinnieCore.proxy.getUniqueRenderID();
	}

	@Override
	public void init() {
//		ExtraTrees.fenceID = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new FenceRenderer());
//		ExtraTrees.stairsID = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new StairsRenderer());
//		ExtraTrees.doorRenderId = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(new DoorBlockRenderer());
//		RenderingRegistry.registerBlockHandler(new HedgeRenderer());
		RecipeSorter.register("extratrees:multifence", MultiFenceRecipeSize.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("extratrees:multifence2", MultiFenceRecipeEmbedded.class, RecipeSorter.Category.SHAPED, "");
		RecipeSorter.register("extratrees:multifence3", MultiFenceRecipeSolid.class, RecipeSorter.Category.SHAPED, "");
	}

	@Override
	public void postInit() {
		for (final PlankType.ExtraTreePlanks plank : PlankType.ExtraTreePlanks.values()) {
			final ItemStack planks = plank.getStack();
			final ItemStack slabs = TileEntityMetadata.getItemStack(ExtraTrees.blockSlab, plank.ordinal());
			//final ItemStack stairs = TileEntityMetadata.getItemStack(ExtraTrees.blockStairs, plank.ordinal());
			//stairs.stackSize = 4;
			//GameRegistry.addRecipe(stairs.copy(), "#  ", "## ", "###", '#', planks.copy());
			//slabs.stackSize = 6;
			//CraftingManager.getInstance().getRecipeList().add(0, new ShapedOreRecipe(slabs.copy(), "###", '#', planks.copy()));
		}
		GameRegistry.addRecipe(new MultiFenceRecipeSize());
		GameRegistry.addRecipe(new MultiFenceRecipeEmbedded());
		GameRegistry.addRecipe(new MultiFenceRecipeSolid());
		for (final IPlankType plank2 : WoodManager.getAllPlankTypes()) {
			final ItemStack planks2 = plank2.getStack();
			final ItemStack fenceNormal = WoodManager.getFence(plank2, new FenceType(0), 1);
			final ItemStack gate = WoodManager.getGate(plank2);
			final ItemStack doorStandard = WoodManager.getDoor(plank2, DoorType.Standard);
			final ItemStack doorSolid = WoodManager.getDoor(plank2, DoorType.Solid);
			final ItemStack doorSplit = WoodManager.getDoor(plank2, DoorType.Double);
			final ItemStack doorFull = WoodManager.getDoor(plank2, DoorType.Full);
			if (planks2 != null) {
				if (gate == null) {
					continue;
				}
				gate.stackSize = 1;
				GameRegistry.addRecipe(gate.copy(), "fpf", 'f', fenceNormal.copy(), 'p', planks2.copy());
				fenceNormal.stackSize = 4;
				GameRegistry.addRecipe(fenceNormal.copy(), "###", "# #", '#', planks2.copy());
				GameRegistry.addRecipe(doorSolid.copy(), "###", "###", "###", '#', planks2.copy());
				GameRegistry.addRecipe(doorStandard.copy(), "# #", "###", "###", '#', planks2.copy());
				GameRegistry.addRecipe(doorSplit.copy(), "# #", "###", "# #", '#', planks2.copy());
				GameRegistry.addRecipe(doorFull.copy(), "# #", "# #", "# #", '#', planks2.copy());
			}
		}
		this.addSqueezer(EnumVanillaWoodType.SPRUCE, ExtraTreeLiquid.Resin, 50);
	}

	public void addSqueezer(final IWoodType log, final ILiquidType liquid, final int amount, final float pulpChance) {
		final FluidStack liquidStack = liquid.get(amount);
		RecipeManagers.squeezerManager.addRecipe(10, new ItemStack[]{TreeManager.woodAccess.getStack(log, WoodBlockKind.LOG, false)}, liquidStack, Mods.Forestry.stack("woodPulp"), (int) (100.0f * pulpChance));
	}

	public void addSqueezer(final IWoodType log, final ILiquidType liquid, final int amount) {
		this.addSqueezer(log, liquid, amount, 0.5f);
	}
}
