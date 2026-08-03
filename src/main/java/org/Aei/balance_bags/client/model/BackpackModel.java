package org.Aei.balance_bags.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.Aei.balance_bags.BalanceBags;

public class BackpackModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(BalanceBags.MOD_ID, "backpack"), "main");

    private final ModelPart body;

    public BackpackModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -22.0F, 2.0F, 6.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(12, 24).addBox(-2.0F, -19.0F, 5.0F, 4.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 24).addBox(3.0F, -20.0F, 2.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 27).addBox(-4.0F, -20.0F, 2.0F, 1.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 21).addBox(-1.0F, -21.8F, 5.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        backpack.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 3).addBox(-1.0F, -2.5F, -1.3F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.0F, 5.5F, 0.8727F, 0.0F, 0.0F));
        backpack.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(18, 9).addBox(-0.5F, -1.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.3F, 6.2F, -0.1309F, 0.0F, 0.0F));
        backpack.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(18, 0).addBox(-7.4F, -1.5F, 2.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -16.0F, 0.0F, 3.1416F, 0.0F, 1.4835F));
        backpack.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 18).addBox(-7.4F, -1.5F, 2.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -16.0F, 0.0F, 3.1416F, 0.0F, 1.6144F));
        backpack.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(18, 3).addBox(-1.0F, -1.7F, -1.6F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -16.0F, 1.0F, 3.1416F, 0.0F, 1.5708F));
        backpack.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(28, 30).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -16.0F, 2.0F, -2.1817F, 0.0F, -1.5708F));
        backpack.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(18, 18).addBox(-1.0F, -1.7F, -1.6F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -16.0F, 1.0F, 3.1416F, 0.0F, -1.5708F));
        backpack.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(14, 30).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -16.0F, 2.0F, -2.1817F, 0.0F, 1.5708F));
        backpack.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(22, 9).addBox(-1.0F, -1.5F, 0.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(28, 24).addBox(-1.0F, -1.5F, 4.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -23.0F, -3.0F, 0.0F, 0.0F, 0.6981F));
        backpack.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 21).addBox(-1.0F, -1.5F, -2.9F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(6, 30).addBox(-1.0F, -1.5F, 1.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, -23.0F, 0.0F, 0.0F, 0.0F, -0.6981F));
        backpack.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 12).addBox(-3.5F, -1.0F, -2.0F, 7.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.0F, 4.0F, -0.6109F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}