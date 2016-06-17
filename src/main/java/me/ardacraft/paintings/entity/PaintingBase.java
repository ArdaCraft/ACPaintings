package me.ardacraft.paintings.entity;

import io.netty.buffer.ByteBuf;
import me.ardacraft.paintings.item.PaintingCreator;
import me.ardacraft.paintings.item.PaintingItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
public abstract class PaintingBase extends EntityHanging implements IEntityAdditionalSpawnData
{
    public Art art = Art.A1x1_0;

    public PaintingBase(World worldIn)
    {
        super(worldIn);
    }

    @SideOnly(Side.CLIENT)
    public PaintingBase(World worldIn, BlockPos pos, EnumFacing facing, String title)
    {
        this(worldIn, pos, facing);

        for (Art art : Art.values())
        {
            if (art.name().equals(title))
            {
                this.art = art;
                break;
            }
        }

        this.updateFacingWithBoundingBox(facing);
    }

    public PaintingBase(World worldIn, BlockPos pos, EnumFacing facing)
    {
        super(worldIn, pos);
        this.art = Art.A1x1_0;
        this.updateFacingWithBoundingBox(facing);
    }

    @Override
    public void onBroken(Entity brokenEntity)
    {}

    @Override
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch)
    {
        BlockPos blockpos = this.hangingPosition.add(x - this.posX, y - this.posY, z - this.posZ);
        this.setPosition((double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ());
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_)
    {}

    @Override
    public boolean interactAt(EntityPlayer player, Vec3 vec3)
    {
        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof PaintingItem)
        {
            EnumFacing facing = player.getHorizontalFacing().getOpposite();
            PaintingItem.placeItem(player.getHeldItem(), player, worldObj, getHangingPosition(), facing, paintingCreator());
        }
        return true;
    }

    @Override
    public boolean onValidSurface()
    {
        return true;
    }

    @Override
    public int getWidthPixels()
    {
        return this.art.sizeX;
    }

    @Override
    public int getHeightPixels()
    {
        return this.art.sizeY;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setString("Name", this.art.name());
        super.writeEntityToNBT(tagCompound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tagCompund)
    {
        String s = tagCompund.getString("Name");

        for (Art art : Art.values())
        {
            if (art.name().equals(s))
            {
                this.art = art;
            }
        }

        if (this.art == null)
        {
            this.art = Art.A1x1_0;
        }

        super.readEntityFromNBT(tagCompund);
    }

    @Override
    public void writeSpawnData(ByteBuf buffer)
    {
        buffer.writeInt(getHorizontalFacing().getIndex());
        buffer.writeInt(art.index());
    }

    @Override
    public void readSpawnData(ByteBuf additionalData)
    {
        int facing = additionalData.readInt();
        int id = additionalData.readInt();
        setArt(Art.values()[id], facing);
    }

    public void setArtFromStack(ItemStack stack)
    {
        int id = stack.getItemDamage();
        Art[] arts = Art.values();
        if (id < arts.length)
        {
            setArt(arts[id], this.getHorizontalFacing().getIndex());
        }
    }

    private void setArt(Art art, int facing)
    {
        this.art = art;
        if (art.sizeY == 32 || art.sizeY == 64)
        {
            this.setPosition(this.posX, this.posY - 1, this.posZ);
        }
        this.updateFacingWithBoundingBox(EnumFacing.getFront(facing));
    }

    abstract PaintingCreator paintingCreator();
}
