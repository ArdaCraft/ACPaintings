package me.ardacraft.paintings.render;

import me.ardacraft.paintings.entity.PaintingBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

/**
 * @author dags <dags@dags.me>
 */
public class PaintingRenderFactory implements IRenderFactory<PaintingBase>
{
    private final String location;

    public PaintingRenderFactory(String s)
    {
        location = s;
    }

    @Override
    public Render<? super PaintingBase> createRenderFor(RenderManager manager)
    {
        return new PaintingRender(manager, location);
    }
}
