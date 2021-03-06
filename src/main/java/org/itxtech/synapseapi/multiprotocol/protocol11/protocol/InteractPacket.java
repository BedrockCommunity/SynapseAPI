package org.itxtech.synapseapi.multiprotocol.protocol11.protocol;

import cn.nukkit.network.protocol.DataPacket;
import org.itxtech.synapseapi.utils.ClassUtils;

/**
 * Created on 15-10-15.
 */
public class InteractPacket extends Packet11 {

    public static final byte NETWORK_ID = ProtocolInfo.INTERACT_PACKET;

    public static final byte ACTION_RIGHT_CLICK = 1;
    public static final byte ACTION_LEFT_CLICK = 2;
    public static final byte ACTION_VEHICLE_EXIT = 3;
    public static final byte ACTION_MOUSEOVER = 4;

    public static final byte ACTION_OPEN_INVENTORY = 6;

    public byte action;
    public long target;

    public InteractPacket fromDefault(DataPacket pkk) {
        ClassUtils.requireInstance(pkk, cn.nukkit.network.protocol.InteractPacket.class);

        cn.nukkit.network.protocol.InteractPacket pk = (cn.nukkit.network.protocol.InteractPacket) pkk;
        this.action = (byte) pk.action;
        this.target = pk.target;
        return this;
    }

    public cn.nukkit.network.protocol.InteractPacket toDefault() {
        cn.nukkit.network.protocol.InteractPacket pk = new cn.nukkit.network.protocol.InteractPacket();
        pk.action = action;
        pk.target = target;
        return pk;
    }

    @Override
    public void decode() {
        this.action = (byte) this.getByte();
        this.target = getEntityRuntimeId();
    }

    @Override
    public void encode() {
        this.reset();
        this.putByte(this.action);
        this.putEntityRuntimeId(this.target);
    }

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public static Class<? extends DataPacket> getDefaultPacket() {
        return cn.nukkit.network.protocol.InteractPacket.class;
    }
}
