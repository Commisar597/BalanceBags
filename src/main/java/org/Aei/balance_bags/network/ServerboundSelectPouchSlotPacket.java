package org.Aei.balance_bags.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import org.Aei.balance_bags.inventory.PouchLinkManager;

import java.util.function.Supplier;

public class ServerboundSelectPouchSlotPacket {
    private final int slotIndex;

    public ServerboundSelectPouchSlotPacket(int slotIndex) {
        this.slotIndex = slotIndex;
    }

    public ServerboundSelectPouchSlotPacket(FriendlyByteBuf buf) {
        this.slotIndex = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(this.slotIndex);
    }

    public static void handle(ServerboundSelectPouchSlotPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                // Передаем игрока, индекс выбранного слота в подсумке и текущий слот хотбара игрока
                int hotbarIndex = player.getInventory().selected;
                PouchData.setActiveSlot(player, msg.slotIndex, hotbarIndex);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}