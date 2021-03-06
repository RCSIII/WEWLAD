package me.wewlad.Entities.ExplosiveBlocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import me.wewlad.Blocks.WEWBlocks;
import me.wewlad.WEWLAD;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BaseExplosiveBlockRenderer extends EntityRenderer<BaseExplosiveBlockEntity> {

    private BlockState expState = null;

    public BaseExplosiveBlockRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.5f;
    }

    public void render(BaseExplosiveBlockEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        switch (pEntity.getExpType()) {
            case TEST_TNT -> expState = WEWBlocks.TEST_TNT.get().defaultBlockState();
            case DOUBLE_TNT -> expState = WEWBlocks.DOUBLE_TNT.get().defaultBlockState();
        }
        pMatrixStack.pushPose();
        pMatrixStack.translate(0.0D, 0.5D, 0.0D);
        int i = pEntity.getFuse();
        if ((float)i - pPartialTicks + 1.0F < 10.0F) {
            float f = 1.0F - ((float)i - pPartialTicks + 1.0F) / 10.0F;
            f = Mth.clamp(f, 0.0F, 1.0F);
            f *= f;
            f *= f;
            float f1 = 1.0F + f * 0.3F;
            pMatrixStack.scale(f1, f1, f1);
        }

        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
        pMatrixStack.translate(-0.5D, -0.5D, 0.5D);
        pMatrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        TntMinecartRenderer.renderWhiteSolidBlock(expState, pMatrixStack, pBuffer, pPackedLight, i / 5 % 2 == 0);
        pMatrixStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(BaseExplosiveBlockEntity pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
