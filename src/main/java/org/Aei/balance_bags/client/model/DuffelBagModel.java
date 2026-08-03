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

public class DuffelBagModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(BalanceBags.MOD_ID, "duffel_bag"), "main");

    private final ModelPart body;

    public DuffelBagModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition largeBag = body.addOrReplaceChild("largeBag", CubeListBuilder.create(), PartPose.offset(-4.0F, -24.0F, 0.0F));

        largeBag.addOrReplaceChild("cube_r1", CubeListBuilder.create()
                .texOffs(16, 26).addBox(-3.5F, -3.5F, -1.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-3.5F, -3.5F, -12.0F, 7.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -13.0F, 6.0F, 6.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, 12.0F, 6.0F, 0.0F, 0.0F, -0.2618F));

        largeBag.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(32, 32).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 10.0F, 4.5F, 0.2182F, 0.0F, -0.9163F));
        largeBag.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 26).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 10.0F, -4.5F, -0.1309F, 0.0F, -0.9163F));
        largeBag.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 20).addBox(-0.1699F, -1.0981F, -0.5F, 12.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.067F, -0.9199F, 2.5F, 0.0F, -0.1309F, 1.0472F));
        largeBag.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 23).addBox(-0.1699F, -1.0981F, -0.5F, 12.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.067F, -0.9199F, -2.5F, 0.0F, 0.1309F, 1.0472F));
        largeBag.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(26, 20).addBox(-1.0F, -1.5F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0472F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}