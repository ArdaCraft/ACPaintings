package me.ardacraft.paintings.entity;

import me.ardacraft.paintings.item.PaintingCreator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author dags <dags@dags.me>
 */
public class Painting3 extends PaintingBase
{
    public Painting3(World worldIn)
    {
        super(worldIn);
    }

    public Painting3(World worldIn, BlockPos pos, EnumFacing facing)
    {
        super(worldIn, pos, facing);
    }

    @Override
    PaintingCreator paintingCreator()
    {
        return Painting3::new;
    }

    public Painting3(World worldIn, BlockPos pos, EnumFacing facing, String title)
    {
        super(worldIn, pos, facing, title);
    }
}
