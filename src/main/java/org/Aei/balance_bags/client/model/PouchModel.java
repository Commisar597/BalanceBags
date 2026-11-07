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

public class PouchModel<T extends LivingEntity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation(BalanceBags.MOD_ID, "pouch"), "main");

    private final ModelPart rightLeg;

    public PouchModel(ModelPart root) {
        this.rightLeg = root.getChild("rightLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rightLeg = partdefinition.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));

        rightLeg.addOrReplaceChild("pouch", CubeListBuilder.create()
                        .texOffs(0, 0).addBox(-4.5F, -10.0F, -2.5F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 7).addBox(-6.0F, -11.0F, -1.5F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 7).addBox(-6.5F, -11.5F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(15, 17).addBox(-6.7F, -10.3F, -0.3F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 25).addBox(-6.7F, -10.3F, -1.7F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 29).addBox(-6.7F, -9.8F, -0.3F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 16).addBox(-6.7F, -9.8F, -1.7F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(6, 23).addBox(-6.4F, -10.3F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
