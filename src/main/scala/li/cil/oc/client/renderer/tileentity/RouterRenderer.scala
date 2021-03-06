package li.cil.oc.client.renderer.tileentity

import li.cil.oc.Blocks
import li.cil.oc.common.tileentity
import li.cil.oc.util.RenderState
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.texture.TextureMap
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
import net.minecraft.tileentity.TileEntity
import org.lwjgl.opengl.GL11

object RouterRenderer extends TileEntitySpecialRenderer {
  override def renderTileEntityAt(tileEntity: TileEntity, x: Double, y: Double, z: Double, f: Float) {
    val router = tileEntity.asInstanceOf[tileentity.Router]
    val activity = math.max(0, 1 - (System.currentTimeMillis() - router.lastMessage) / 1000.0)
    if (activity > 0) {
      GL11.glPushAttrib(0xFFFFFF)

      RenderState.disableLighting()
      RenderState.makeItBlend()
      RenderState.setBlendAlpha(activity.toFloat)

      GL11.glPushMatrix()

      GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5)
      GL11.glScalef(1.002f, -1.002f, 1.002f)
      GL11.glTranslatef(-0.5f, -0.5f, -0.5f)

      bindTexture(TextureMap.locationBlocksTexture)
      val t = Tessellator.instance
      t.startDrawingQuads()

      val sideActivity = Blocks.router.iconSideActivity
      t.addVertexWithUV(1, 1, 0, sideActivity.getMinU, sideActivity.getMaxV)
      t.addVertexWithUV(0, 1, 0, sideActivity.getMaxU, sideActivity.getMaxV)
      t.addVertexWithUV(0, 0, 0, sideActivity.getMaxU, sideActivity.getMinV)
      t.addVertexWithUV(1, 0, 0, sideActivity.getMinU, sideActivity.getMinV)

      t.addVertexWithUV(0, 1, 1, sideActivity.getMinU, sideActivity.getMaxV)
      t.addVertexWithUV(1, 1, 1, sideActivity.getMaxU, sideActivity.getMaxV)
      t.addVertexWithUV(1, 0, 1, sideActivity.getMaxU, sideActivity.getMinV)
      t.addVertexWithUV(0, 0, 1, sideActivity.getMinU, sideActivity.getMinV)

      t.addVertexWithUV(1, 1, 1, sideActivity.getMinU, sideActivity.getMaxV)
      t.addVertexWithUV(1, 1, 0, sideActivity.getMaxU, sideActivity.getMaxV)
      t.addVertexWithUV(1, 0, 0, sideActivity.getMaxU, sideActivity.getMinV)
      t.addVertexWithUV(1, 0, 1, sideActivity.getMinU, sideActivity.getMinV)

      t.addVertexWithUV(0, 1, 0, sideActivity.getMinU, sideActivity.getMaxV)
      t.addVertexWithUV(0, 1, 1, sideActivity.getMaxU, sideActivity.getMaxV)
      t.addVertexWithUV(0, 0, 1, sideActivity.getMaxU, sideActivity.getMinV)
      t.addVertexWithUV(0, 0, 0, sideActivity.getMinU, sideActivity.getMinV)

      t.draw()

      GL11.glPopMatrix()
      GL11.glPopAttrib()
    }
  }
}
