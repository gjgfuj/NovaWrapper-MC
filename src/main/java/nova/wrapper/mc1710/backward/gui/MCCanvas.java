package nova.wrapper.mc1710.backward.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import nova.core.gui.render.Canvas;
import nova.core.render.Color;
import nova.core.render.texture.Texture;
import nova.core.util.transform.Vector2i;

import org.lwjgl.opengl.GL11;

public class MCCanvas implements Canvas {

	private final Vector2i dimension;
	private final Tessellator tessellator;
	private int zIndex;
	private Color color = Color.white;

	public MCCanvas(int width, int height, Tessellator tessellator) {
		this.dimension = new Vector2i(width, height);
		this.tessellator = tessellator;
	}

	@Override
	public Vector2i getDimension() {
		return dimension;
	}

	@Override
	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	@Override
	public int getZIndex() {
		return zIndex;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public void bindTexture(Texture texture) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(texture.getResource()));
	}

	@Override
	public void translate(double x, double y) {
		GL11.glTranslated(x, y, 0);
	}

	@Override
	public void rotate(double angle) {
		GL11.glRotated(angle, 0, 0, 1);
	}

	@Override
	public void startDrawing(boolean textured) {
		if (!textured)
			GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glColor4f(color.redf(), color.greenf(), color.bluef(), color.alphaf());
		tessellator.startDrawing(GL11.GL_POLYGON);
	}

	@Override
	public void addVertex(double x, double y) {
		tessellator.addVertex(x, y, zIndex);
	}

	@Override
	public void addVertexWithUV(double x, double y, double u, double v) {
		tessellator.addVertexWithUV(x, y, zIndex, u, v);
	}

	@Override
	public void draw() {
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	@Override
	public boolean isBuffered() {
		return false;
	}

	@Override
	public void setScissor(int x, int y, int width, int height) {
		GL11.glScissor(x, y, width, height);
	}

	@Override
	public void enableScissor() {
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}

	@Override
	public void disableScissor() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}
