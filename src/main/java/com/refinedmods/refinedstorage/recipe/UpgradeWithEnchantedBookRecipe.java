package com.refinedmods.refinedstorage.recipe;

import com.refinedmods.refinedstorage.RSItems;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class UpgradeWithEnchantedBookRecipe extends ShapedRecipe {
    private EnchantmentData enchant;
    private ItemStack result;

    public UpgradeWithEnchantedBookRecipe(ResourceLocation recipeId, Enchantment enchantment, int enchantmentLevel, ItemStack result) {
        super(recipeId, "", 3, 3, NonNullList.from(Ingredient.EMPTY,
            Ingredient.fromStacks(new ItemStack(RSItems.QUARTZ_ENRICHED_IRON)),
            Ingredient.fromStacks(EnchantedBookItem.getEnchantedItemStack(new EnchantmentData(enchantment, enchantmentLevel))),
            Ingredient.fromStacks(new ItemStack(RSItems.QUARTZ_ENRICHED_IRON)),
            Ingredient.fromStacks(new ItemStack(Blocks.BOOKSHELF)),
            Ingredient.fromStacks(new ItemStack(RSItems.UPGRADE)),
            Ingredient.fromStacks(new ItemStack(Blocks.BOOKSHELF)),
            Ingredient.fromStacks(new ItemStack(RSItems.QUARTZ_ENRICHED_IRON)),
            Ingredient.fromStacks(new ItemStack(RSItems.QUARTZ_ENRICHED_IRON)),
            Ingredient.fromStacks(new ItemStack(RSItems.QUARTZ_ENRICHED_IRON))
        ), result);

        this.enchant = new EnchantmentData(enchantment, enchantmentLevel);
        this.result = result;
    }

    public EnchantmentData getEnchant() {
        return enchant;
    }

    public ItemStack getResult() {
        return result;
    }

    @Override
    public boolean matches(CraftingInventory inv, World world) {
        if (super.matches(inv, world)) {
            ListNBT enchantments = EnchantedBookItem.getEnchantments(inv.getStackInSlot(1));

            for (int i = 0; i < enchantments.size(); ++i) {
                CompoundNBT enchantmentNbt = enchantments.getCompound(i);

                // @Volatile: NBT tags from ItemEnchantedBook
                if (ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(enchantmentNbt.getString("id"))) == enchant.enchantment && enchantmentNbt.getShort("lvl") == enchant.enchantmentLevel) {
                    return true;
                }
            }
        }

        return false;
    }
}
