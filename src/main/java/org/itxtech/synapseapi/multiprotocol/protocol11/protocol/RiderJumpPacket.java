package org.itxtech.synapseapi.multiprotocol.protocol11.protocol;

public class RiderJumpPacket extends Packet11 {

    public static final byte NETWORK_ID = ProtocolInfo.RIDER_JUMP_PACKET;

    public int unknown;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {

    }

    @Override
    public void encode() {
        this.putVarInt(this.unknown);
    }
}
