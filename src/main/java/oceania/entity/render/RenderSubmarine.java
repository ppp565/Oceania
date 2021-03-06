package oceania.entity.render;

import static org.lwjgl.opengl.GL11.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import oceania.entity.EntitySubmarine;
import oceania.entity.render.model.ModelSubmarine;
import oceania.util.OUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSubmarine extends Render
{
	private static final float X_OFFSET = -0.4f;
	private static final float Y_OFFSET = -0.75f;
	private static final float Z_OFFSET = -0.3f;
	
	private ModelSubmarine		model;
	private ResourceLocation	texLoc;
	
	public RenderSubmarine()
	{
		// model = (TechneModel) AdvancedModelLoader.loadModel("/assets/oceania/models/submarine.tcn");
		this.model = new ModelSubmarine();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void doRender(Entity entity, double x, double y, double z, float yaw, float partialTicks)
	{
		float fSpeed = (float) (entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
		glPushMatrix();
		glTranslatef((float) x, (float) y + EntitySubmarine.ENT_HEIGHT, (float) z);
		glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
		glRotatef(yaw - 90f, 0.0f, 1.0f, 0.0f);
		glTranslatef(X_OFFSET, Y_OFFSET, Z_OFFSET * (fSpeed / 0.85f));
		if (this.texLoc == null)
			this.texLoc = this.getEntityTexture(entity);
		OUtil.bindTexture(this.texLoc);
		model.render(1.0f / 16.0f);
		glPopMatrix();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return new ResourceLocation("oceania", "textures/models/submarine.png");
	}
	
}
