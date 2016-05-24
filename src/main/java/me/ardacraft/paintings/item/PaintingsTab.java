package me.ardacraft.paintings.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingsTab extends CreativeTabs
{
    public PaintingsTab()
    {
        super("paintings");
    }

    @Override
    public Item getTabIconItem()
    {
        return Items.painting;
    }
}
