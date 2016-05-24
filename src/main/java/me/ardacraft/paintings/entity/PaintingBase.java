package me.ardacraft.paintings.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingBase extends EntityPainting implements IEntityAdditionalSpawnData
{
    public PaintingBase(World worldIn)
    {
        super(worldIn);
    }

    @SideOnly(Side.CLIENT)
    public PaintingBase(World worldIn, BlockPos pos, EnumFacing facing, String title)
    {
        super(worldIn, pos, facing, title);
    }

    public PaintingBase(World worldIn, BlockPos pos, EnumFacing facing)
    {
        super(worldIn, pos, facing);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean bool)
    {}

    @Override
    public void onBroken(Entity brokenEntity)
    {}

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        int index = this.getHorizontalFacing().getIndex();
        buffer.writeInt(index);
        EnumArt[] arts = EnumArt.values();
        for (int i = 0; i < arts.length; i++)
        {
            if (arts[i] == art)
            {
                buffer.writeInt(i);
                return;
            }
        }
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
        int index = additionalData.readInt();
        int id = additionalData.readInt();
        this.art = EnumArt.values()[id];

        EnumFacing facing = EnumFacing.getFront(index);
        this.updateFacingWithBoundingBox(facing);
    }

    public void setArtFromStack(ItemStack stack)
    {
        if (stack.getDisplayName() != null)
        {
            String name = stack.getDisplayName();
            int index = name.indexOf(":") + 1;
            if (index > 0)
            {
                String id = name.substring(index);
                try
                {
                    int i = Integer.parseInt(id);
                    if (i <= 25)
                    {
                        art = EnumArt.values()[i];
                    }
                }
                catch (NumberFormatException e)
                {}
            }
        }
    }
}
