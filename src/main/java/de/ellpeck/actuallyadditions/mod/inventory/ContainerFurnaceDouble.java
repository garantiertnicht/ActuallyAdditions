/*
 * This file ("ContainerFurnaceDouble.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.inventory;

import de.ellpeck.actuallyadditions.mod.tile.TileEntityBase;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityFurnaceDouble;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;


public class ContainerFurnaceDouble extends Container{

    private final TileEntityFurnaceDouble tileFurnace;

    public ContainerFurnaceDouble(InventoryPlayer inventory, TileEntityBase tile){
        this.tileFurnace = (TileEntityFurnaceDouble)tile;

        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_INPUT_1, 51, 21));
        this.addSlotToContainer(new SlotFurnaceOutput(inventory.player, this.tileFurnace, TileEntityFurnaceDouble.SLOT_OUTPUT_1, 51, 69));
        this.addSlotToContainer(new Slot(this.tileFurnace, TileEntityFurnaceDouble.SLOT_INPUT_2, 109, 21));
        this.addSlotToContainer(new SlotFurnaceOutput(inventory.player, this.tileFurnace, TileEntityFurnaceDouble.SLOT_OUTPUT_2, 108, 69));

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 9; j++){
                this.addSlotToContainer(new Slot(inventory, j+i*9+9, 8+j*18, 97+i*18));
            }
        }
        for(int i = 0; i < 9; i++){
            this.addSlotToContainer(new Slot(inventory, i, 8+i*18, 155));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot){
        int inventoryStart = 4;
        int inventoryEnd = inventoryStart+26;
        int hotbarStart = inventoryEnd+1;
        int hotbarEnd = hotbarStart+8;

        Slot theSlot = this.inventorySlots.get(slot);

        if(theSlot != null && theSlot.getHasStack()){
            ItemStack newStack = theSlot.getStack();
            ItemStack currentStack = newStack.copy();

            //Slots in Inventory to shift from
            if(slot == TileEntityFurnaceDouble.SLOT_OUTPUT_1 || slot == TileEntityFurnaceDouble.SLOT_OUTPUT_2){
                if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, true)){
                    return null;
                }
                theSlot.onSlotChange(newStack, currentStack);
            }
            //Other Slots in Inventory excluded
            else if(slot >= inventoryStart){
                //Shift from Inventory
                if(FurnaceRecipes.instance().getSmeltingResult(newStack) != null){
                    if(!this.mergeItemStack(newStack, TileEntityFurnaceDouble.SLOT_INPUT_1, TileEntityFurnaceDouble.SLOT_INPUT_1+1, false)){
                        if(!this.mergeItemStack(newStack, TileEntityFurnaceDouble.SLOT_INPUT_2, TileEntityFurnaceDouble.SLOT_INPUT_2+1, false)){
                            return null;
                        }
                    }
                }
                //

                else if(slot >= inventoryStart && slot <= inventoryEnd){
                    if(!this.mergeItemStack(newStack, hotbarStart, hotbarEnd+1, false)){
                        return null;
                    }
                }
                else if(slot >= inventoryEnd+1 && slot < hotbarEnd+1 && !this.mergeItemStack(newStack, inventoryStart, inventoryEnd+1, false)){
                    return null;
                }
            }
            else if(!this.mergeItemStack(newStack, inventoryStart, hotbarEnd+1, false)){
                return null;
            }

            if(newStack.stackSize <= 0){
                theSlot.putStack(null);
            }
            else{
                theSlot.onSlotChanged();
            }

            if(newStack.stackSize == currentStack.stackSize){
                return null;
            }
            theSlot.onPickupFromSlot(player, newStack);

            return currentStack;
        }
        return null;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player){
        return this.tileFurnace.isUseableByPlayer(player);
    }
}