package net.blay09.mods.craftingcraft.addon;

import net.blay09.mods.craftingcraft.container.ContainerInventoryCrafting;
import net.blay09.mods.craftingtweaks.api.CraftingTweaksAPI;
import net.blay09.mods.craftingtweaks.api.DefaultProviderV2;
import net.blay09.mods.craftingtweaks.api.TweakProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.FMLClientHandler;

import java.util.List;

public class InventoryCraftingTweakProvider implements TweakProvider<ContainerInventoryCrafting> {

    private DefaultProviderV2 defaultProvider = CraftingTweaksAPI.createDefaultProviderV2();

    @Override
    public boolean load() {
        return true;
    }

    @Override
    public boolean requiresServerSide() {
        return false;
    }

    @Override
    public int getCraftingGridStart(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id) {
        return 0;
    }

    @Override
    public int getCraftingGridSize(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id) {
        return 9;
    }

    @Override
    public void clearGrid(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id, boolean forced) {
        int start = getCraftingGridStart(entityPlayer, container, id);
        int size = getCraftingGridSize(entityPlayer, container, id);
        for(int i = start; i < start + size; i++) {
            container.transferStackInSlot(entityPlayer, i);
            if(forced && container.inventorySlots.get(i).getHasStack()) {
                entityPlayer.dropItem(container.inventorySlots.get(i).getStack(), false);
                container.inventorySlots.get(i).putStack(ItemStack.EMPTY);
            }
        }
    }

    @Override
    public void rotateGrid(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id, boolean counterClockwise) {
        defaultProvider.rotateGrid(this, id, entityPlayer, container, counterClockwise);
    }

    @Override
    public void balanceGrid(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id) {
        defaultProvider.balanceGrid(this, id, entityPlayer, container);
    }

    @Override
    public void spreadGrid(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id) {
        defaultProvider.spreadGrid(this, id, entityPlayer, container);
    }

    @Override
    public boolean canTransferFrom(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int i, Slot slot) {
        return defaultProvider.canTransferFrom(entityPlayer, container, slot);
    }

    @Override
    public boolean transferIntoGrid(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id, Slot slot) {
        return defaultProvider.transferIntoGrid(this, id, entityPlayer, container, slot);
    }

    @Override
    public ItemStack putIntoGrid(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id, ItemStack itemStack, int index) {
        return defaultProvider.putIntoGrid(this, id, entityPlayer, container, itemStack, index);
    }

    @Override
    public IInventory getCraftMatrix(EntityPlayer entityPlayer, ContainerInventoryCrafting container, int id) {
        return container.inventorySlots.get(getCraftingGridStart(entityPlayer, container, id)).inventory;
    }

    @Override
    public void initGui(GuiContainer guiContainer, List<GuiButton> list) {
        Slot firstSlot = guiContainer.inventorySlots.getSlot(getCraftingGridStart(Minecraft.getMinecraft().player, (ContainerInventoryCrafting) guiContainer.inventorySlots, 0));
        list.add(CraftingTweaksAPI.createRotateButtonRelative(0, guiContainer, firstSlot.xPos, firstSlot.yPos - 18));
        list.add(CraftingTweaksAPI.createBalanceButtonRelative(0, guiContainer, firstSlot.xPos + 18, firstSlot.yPos - 18));
        list.add(CraftingTweaksAPI.createClearButtonRelative(0, guiContainer, firstSlot.xPos + 36, firstSlot.yPos - 18));
    }

    @Override
    public String getModId() {
        return "craftingcraft";
    }

}
