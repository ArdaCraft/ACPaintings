package me.ardacraft.paintings.entity;

import me.ardacraft.paintings.item.PaintingCreator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author dags <dags@dags.me>
 */
public class Painting2 extends PaintingBase
{
    public Painting2(World worldIn)
    {
        super(worldIn);
    }

    public Painting2(World worldIn, BlockPos pos, EnumFacing facing)
    {
        super(worldIn, pos, facing);
    }

    public Painting2(World worldIn, BlockPos pos, EnumFacing facing, String title)
    {
        super(worldIn, pos, facing, title);
    }

    @Override
    PaintingCreator paintingCreator()
    {
        return Painting2::new;
    }
}
