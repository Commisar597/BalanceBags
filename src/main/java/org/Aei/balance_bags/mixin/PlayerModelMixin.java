package org.Aei.balance_bags.mixin;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.Aei.balance_bags.BalanceBags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin<T extends LivingEntity> extends HumanoidModel<T> {

    @Shadow @Final private ModelPart leftSleeve;

    public PlayerModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void lockArmAndSleeveOnDuffelBag(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof Player player) {
            boolean hasDuffelBag = CuriosApi.getCuriosHelper()
                    .findFirstCurio(player, BalanceBags.DUFFEL_BAG.get())
                    .isPresent();

            if (hasDuffelBag) {
                float lockedX = -0.62F; // Наклон руки вперёд
                float lockedY = 0.12F; // Поворот кисти
                float lockedZ = -0.42F; // Отведение руки вбок от тела (~12.5°)

                // Применяем параметры к базовому слою
                this.leftArm.xRot = lockedX;
                this.leftArm.yRot = lockedY;
                this.leftArm.zRot = lockedZ;

                // Синхронизируем второй слой (рукав)
                this.leftSleeve.copyFrom(this.leftArm);

                // Коррекция наклона на Shift
                if (this.crouching) {
                    this.leftArm.xRot -= 0.2F;
                    this.leftSleeve.xRot -= 0.2F;
                }
            }
        }
    }
}