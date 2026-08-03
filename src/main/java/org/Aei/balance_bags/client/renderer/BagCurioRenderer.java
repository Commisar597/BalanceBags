package org.Aei.balance_bags.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.Aei.balance_bags.BalanceBags;
import org.Aei.balance_bags.client.model.PouchModel;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class BagCurioRenderer implements ICurioRenderer {

    private final EntityModel<LivingEntity> model;
    private final ResourceLocation texture;

    @SuppressWarnings("unchecked")
    public <M extends EntityModel<? extends LivingEntity>> BagCurioRenderer(M model, ResourceLocation texture) {
        this.model = (EntityModel<LivingEntity>) model;
        this.texture = texture;
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        LivingEntity entity = slotContext.entity();

        poseStack.pushPose();

        // 1. Привязка координат
        if (renderLayerParent.getModel() instanceof HumanoidModel<?> playerModel) {
            if (this.model instanceof PouchModel<?>) {
                playerModel.rightLeg.translateAndRotate(poseStack);
            } else {
                playerModel.body.translateAndRotate(poseStack);
            }
        }

        // 2. Отзеркаливание Regular Bag при надетом Duffel Bag
        boolean isRegularBag = stack.is(BalanceBags.REGULAR_BAG.get());
        boolean hasDuffelBag = isRegularBag && CuriosApi.getCuriosHelper()
                .findFirstCurio(entity, BalanceBags.DUFFEL_BAG.get())
                .isPresent();

        VertexConsumer consumer = renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(this.texture));

        if (hasDuffelBag) {
            poseStack.scale(-1.0F, 1.0F, 1.0F);
            // 💡 Оборачиваем consumer в FlatLightVertexConsumer, чтобы полностью убрать тени
            consumer = new FlatLightVertexConsumer(consumer);
        }

        // 3. Отрисовка
        this.model.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.model.renderToBuffer(poseStack, consumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }

    /**
     * Обертка для VertexConsumer, подменяющая вектор нормали на (0, 1, 0).
     * Отключает направленное затемнение граней (diffuse shading) у отзеркаленной модели.
     */
    private static class FlatLightVertexConsumer implements VertexConsumer {
        private final VertexConsumer parent;

        public FlatLightVertexConsumer(VertexConsumer parent) {
            this.parent = parent;
        }

        @Override
        public VertexConsumer vertex(double x, double y, double z) {
            return parent.vertex(x, y, z);
        }

        @Override
        public VertexConsumer color(int red, int green, int blue, int alpha) {
            return parent.color(red, green, blue, alpha);
        }

        @Override
        public VertexConsumer uv(float u, float v) {
            return parent.uv(u, v);
        }

        @Override
        public VertexConsumer overlayCoords(int u, int v) {
            return parent.overlayCoords(u, v);
        }

        @Override
        public VertexConsumer uv2(int u, int v) {
            return parent.uv2(u, v);
        }

        @Override
        public VertexConsumer normal(float x, float y, float z) {
            // Принудительно направляем нормаль вверх, благодаря чему все грани освещаются одинаково без теней
            return parent.normal(0.0F, 1.0F, 0.0F);
        }

        @Override
        public void endVertex() {
            parent.endVertex();
        }

        @Override
        public void defaultColor(int defaultR, int defaultG, int defaultB, int defaultA) {
            parent.defaultColor(defaultR, defaultG, defaultB, defaultA);
        }

        @Override
        public void unsetDefaultColor() {
            parent.unsetDefaultColor();
        }
    }
}