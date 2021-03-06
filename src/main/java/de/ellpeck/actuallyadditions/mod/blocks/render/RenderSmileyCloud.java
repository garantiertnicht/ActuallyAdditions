/*
 * This file ("RenderSmileyCloud.java") is part of the Actually Additions mod for Minecraft.
 * It is created and owned by Ellpeck and distributed
 * under the Actually Additions License to be found at
 * http://ellpeck.de/actaddlicense
 * View the source code at https://github.com/Ellpeck/ActuallyAdditions
 *
 * © 2015-2016 Ellpeck
 */

package de.ellpeck.actuallyadditions.mod.blocks.render;

import de.ellpeck.actuallyadditions.mod.misc.cloud.ISmileyCloudEasterEgg;
import de.ellpeck.actuallyadditions.mod.misc.cloud.SmileyCloudEasterEggs;
import de.ellpeck.actuallyadditions.mod.tile.TileEntitySmileyCloud;
import de.ellpeck.actuallyadditions.mod.util.AssetUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderSmileyCloud extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float par5, int partial){
        if(tile instanceof TileEntitySmileyCloud){
            TileEntitySmileyCloud theCloud = (TileEntitySmileyCloud)tile;

            GlStateManager.pushMatrix();
            GlStateManager.translate((float)x+0.5F, (float)y-0.5F, (float)z+0.5F);
            GlStateManager.rotate(180F, 0.0F, 0.0F, 1.0F);
            GlStateManager.translate(0.0F, -2F, 0.0F);

            theCloud.setStatus(theCloud.name != null && !theCloud.name.isEmpty() && theCloud.name.equals("Pink Fluffy Unicloud"));

            if(theCloud.name != null && !theCloud.name.isEmpty()){
                easterEggs:
                for(ISmileyCloudEasterEgg cloud : SmileyCloudEasterEggs.CLOUD_STUFF){
                    for(String triggerName : cloud.getTriggerNames()){
                        if(triggerName != null && theCloud.name != null){
                            if(triggerName.equalsIgnoreCase(theCloud.name)){
                                GlStateManager.pushMatrix();
                                IBlockState state = theCloud.getWorld().getBlockState(theCloud.getPos());
                                switch(state.getBlock().getMetaFromState(state)){
                                    case 1:
                                        GlStateManager.rotate(180, 0, 1, 0);
                                        break;
                                    case 2:
                                        GlStateManager.rotate(270, 0, 1, 0);
                                        break;
                                    case 3:
                                        GlStateManager.rotate(90, 0, 1, 0);
                                        break;
                                }
                                cloud.renderExtra(0.0625F);
                                GlStateManager.popMatrix();
                                break easterEggs;
                            }
                        }
                    }
                }
            }
            GlStateManager.popMatrix();

            if(theCloud.name != null && !theCloud.name.isEmpty() && !Minecraft.getMinecraft().gameSettings.hideGUI){
                AssetUtil.renderNameTag(theCloud.name, x+0.5F, y+1.5F, z+0.66);
            }

        }
    }

}
