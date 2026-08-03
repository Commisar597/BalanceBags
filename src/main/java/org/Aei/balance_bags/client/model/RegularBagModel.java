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

public class RegularBagModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(BalanceBags.MOD_ID, "regular_bag"), "main");

    private final ModelPart body;

    public RegularBagModel(ModelPart root) {
        this.body = root.getChild("body");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(4, 26).addBox(-0.5F, -0.5F, 0.1F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -12.0F, -4.5F, 0.0F, 0.0F, -0.0873F));
        body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(7, 26).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -12.0F, -4.5F, -0.0832F, 0.0262F, 0.3043F));

        PartDefinition bag = body.addOrReplaceChild("bag", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition belt = bag.addOrReplaceChild("belt", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        belt.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -11.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-1.0F, -11.0F, -6.5F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -15.0F, 3.0F, 0.0F, 0.0F, -0.4363F));
        belt.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(16, 4).addBox(-0.7F, 0.17F, -2.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -25.0F, 1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition rbag = bag.addOrReplaceChild("rbag", CubeListBuilder.create().texOffs(8, 15).addBox(-2.1F, -1.0F, -4.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.1F, -1.0F, 2.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        rbag.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -2.0F, -1.1F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 1.0F, 1.0F, 0.0F, 1.5708F, 0.0F));
        rbag.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(16, 9).addBox(1.0F, -1.0F, -1.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 3.0F, 0.0F, 1.5708F, 0.0F));

        bag.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(14, 26).addBox(-2.2F, -1.6F, 1.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -12.5F, -5.5F, 0.1745F, 0.0F, 0.2618F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}