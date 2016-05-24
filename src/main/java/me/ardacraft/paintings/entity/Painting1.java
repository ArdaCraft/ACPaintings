package me.ardacraft.paintings.entity;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author dags <dags@dags.me>
 */
public class Painting1 extends PaintingBase
{
    public Painting1(World worldIn)
    {
        super(worldIn);
    }

    public Painting1(World worldIn, BlockPos pos, EnumFacing facing)
    {
        super(worldIn, pos, facing);
    }

    public Painting1(World worldIn, BlockPos pos, EnumFacing facing, String title)
    {
        super(worldIn, pos, facing, title);
    }
}
