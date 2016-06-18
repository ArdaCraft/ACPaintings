package me.ardacraft.paintings;

import me.ardacraft.paintings.entity.*;
import me.ardacraft.paintings.item.PaintingItem;
import me.ardacraft.paintings.render.PaintingRenderFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dags <dags@dags.me>
 */
@Mod(name = "ACPaintings", modid = ACPainting.MOD_ID, version = "1.0")
public class ACPainting
{
    public static final String MOD_ID = "acpaintings";
    static final Map<String, PaintingItem> items = new HashMap<>();

    private final PaintingItem paint0 = new PaintingItem(Painting0.class, Painting0::new);
    private final PaintingItem paint1 = new PaintingItem(Painting1.class, Painting1::new);
    private final PaintingItem paint2 = new PaintingItem(Painting2.class, Painting2::new);
    private final PaintingItem paint3 = new PaintingItem(Painting3.class, Painting3::new);
    private final PaintingItem paint4 = new PaintingItem(Painting4.class, Painting4::new);

    /**
     * Each painting entity must have it's own class 'Painting#' and have it's PaintingItem registered for it as below
     * Each painting holds 71 tiles of varying sizes
     * Each painting must have an item model, item texture, and painting texture under assets
     *
     * To add more paintings:
     * 1. A new Painting# class that extends PaintingBase
     * 2. A PaintingItem constructed with step 1's Painting# class, and a PaintingCreator function that creates a new
     *    instance of type Painting#
     * 3. An item model and textures sharing the name 'painting#'
     * 4. An entry in the lang file for something human readable
     * 5. The PaintingItem must be registered as below - seems that the entity must be registered during PreInit, and
     *    the model during init (or later?)
     *
     * meh.
     *
     * ~ dags
     */

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

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new PaintingCommand());
    }

    private static void register(Object plugin, Side side, PaintingItem item, int id)
    {
        items.put(item.getIdName(), item);
        GameRegistry.registerItem(item);
        EntityRegistry.registerModEntity(item.getEntityClass(), item.getIdName(), id, plugin, 24, 1, false);
        if (side == Side.CLIENT)
        {
            RenderingRegistry.registerEntityRenderingHandler(item.getEntityClass(), new PaintingRenderFactory(item.getIdName()));
        }
    }

    private static void model(Side side, PaintingItem item)
    {
        if (side == Side.CLIENT)
        {
            ModelResourceLocation location = new ModelResourceLocation(MOD_ID + ":" + item.getIdName(), "inventory");
            for (int meta = 0; meta < Art.values().length; meta++)
            {
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, meta, location);
            }
        }
    }
}
