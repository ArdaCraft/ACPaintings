package me.ardacraft.paintings.item;

import me.ardacraft.paintings.entity.PaintingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author dags <dags@dags.me>
 */
public interface PaintingCreator
{

    PaintingBase createEntity(World worldIn, BlockPos pos, EnumFacing clickedSide);

}
