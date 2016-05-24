package me.ardacraft.paintings;

import me.ardacraft.paintings.entity.*;
import me.ardacraft.paintings.item.*;
import me.ardacraft.paintings.render.PaintingRenderFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

/**
 * @author dags <dags@dags.me>
 */
@Mod(name = "ACPaintings", modid = ACPainting.MOD_ID, version = "1.0")
public class ACPainting
{
    public static final String MOD_ID = "acpaintings";

    private final PaintingItem paint0 = new PaintingItem(Painting0.class, Painting0::new);
    private final PaintingItem paint1 = new PaintingItem(Painting1.class, Painting1::new);
    private final PaintingItem paint2 = new PaintingItem(Painting2.class, Painting2::new);
    private final PaintingItem paint3 = new PaintingItem(Painting3.class, Painting3::new);
    private final PaintingItem paint4 = new PaintingItem(Painting4.class, Painting4::new);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        register(this, event.getSide(), paint0, 0);
        register(this, event.getSide(), paint1, 1);
        register(this, event.getSide(), paint2, 2);
        register(this, event.getSide(), paint3, 3);
        register(this, event.getSide(), paint4, 4);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        model(event.getSide(), paint0);
        model(event.getSide(), paint1);
        model(event.getSide(), paint2);
        model(event.getSide(), paint3);
        model(event.getSide(), paint4);
    }

    private static void register(Object plugin, Side side, PaintingItem item, int id)
    {
        GameRegistry.registerItem(item);
        EntityRegistry.registerModEntity(item.getEntityClass(), item.getSimpleName(), id, plugin, 24, 1, false);
        if (side == Side.CLIENT)
        {
            RenderingRegistry.registerEntityRenderingHandler(item.getEntityClass(), new PaintingRenderFactory(item.getSimpleName()));
        }
    }

    private static void model(Side side, PaintingItem item)
    {
        if (side == Side.CLIENT)
        {
            ModelResourceLocation location = new ModelResourceLocation(MOD_ID + ":" + item.getSimpleName(), "inventory");
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, location);
        }
    }
}
