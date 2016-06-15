package me.ardacraft.paintings.item;

import me.ardacraft.paintings.entity.Art;
import me.ardacraft.paintings.entity.PaintingBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingItem extends ItemHangingEntity
{
    private static final CreativeTabs tab = new PaintingsTab();

    private final Class<? extends PaintingBase> entityClass;
    private final PaintingCreator paintingCreator;

    public PaintingItem(Class<? extends PaintingBase> entityClass, PaintingCreator paintingCreator)
    {
        super(entityClass);
        this.entityClass = entityClass;
        this.paintingCreator = paintingCreator;
        this.setUnlocalizedName(getSimpleName());
        this.setRegistryName(getSimpleName());
        this.setCreativeTab(tab);
    }

    public Class<? extends PaintingBase> getEntityClass()
    {
        return entityClass;
    }

    public String getSimpleName()
    {
        return entityClass.getSimpleName().toLowerCase();
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack)
    {
        String base = super.getItemStackDisplayName(itemStack);
        int id = itemStack.getItemDamage();
        if (id > 0 && id < Art.values().length)
        {
            return base + ":" + id + " " + Art.values()[id].shape;
        }
        return base;
    }

    @Override
    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if (side == EnumFacing.DOWN)
        {
            return false;
        }
        else if (side == EnumFacing.UP)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = pos.offset(side);

            if (!playerIn.canPlayerEdit(blockpos, side, stack))
            {
                return false;
            }
            else
            {
                PaintingBase painting = paintingCreator.createEntity(worldIn, blockpos, side);
                painting.setArtFromStack(stack);

                if (painting.onValidSurface() && !worldIn.isRemote)
                {
                    worldIn.spawnEntityInWorld(painting);
                }

                return true;
            }
        }
    }
}
