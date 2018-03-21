package org.itxtech.synapseapi.multiprotocol.protocol11.protocol;

import cn.nukkit.network.protocol.DataPacket;

/**
 * Created on 15-10-13.
 */
public class PlayStatusPacket extends Packet11 {

    public static final byte NETWORK_ID = ProtocolInfo.PLAY_STATUS_PACKET;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    public static final int LOGIN_SUCCESS = 0;
    public static final int LOGIN_FAILED_CLIENT = 1;
    public static final int LOGIN_FAILED_SERVER = 2;
    public static final int PLAYER_SPAWN = 3;
    public static final int LOGIN_FAILED_INVALID_TENANT = 4;
    public static final int LOGIN_FAILED_VANILLA_EDU = 5;
    public static final int LOGIN_FAILED_EDU_VANILLA = 6;

    public int status;

    public PlayStatusPacket fromDefault(DataPacket pkk) {
        cn.nukkit.network.protocol.PlayStatusPacket pk = (cn.nukkit.network.protocol.PlayStatusPacket) pkk;
        this.status = pk.status;
        return this;
    }

    public cn.nukkit.network.protocol.PlayStatusPacket toDefault() {
        cn.nukkit.network.protocol.PlayStatusPacket pk = new cn.nukkit.network.protocol.PlayStatusPacket();
        pk.status = status;
        return pk;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.reset();
        this.putInt(this.status);
    }
}
