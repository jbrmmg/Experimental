package messages;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import messages.BaseMessageType;
import messages.outgoing.BaseOutgoingMessage;

public class CameraMessageWrapper {
    private final byte header;
    private final byte version;
    private final int session;
    private final int packetCount;
    private final short messageType;
    private final BaseMessageType messageData;

    public static byte HEADER_CODE = (byte)255;
    public static byte ZERO_BYTE = (byte)0;
    public static byte NEW_LINE = (byte)10;

    public CameraMessageWrapper(BaseMessageType messageData) {
        this.header = HEADER_CODE;
        this.version = 0;
        this.packetCount = 0;
        this.messageType = messageData.getMessageType();
        this.messageData = messageData;

        // Is this a base outgoing message?
        if(messageData instanceof BaseOutgoingMessage) {
            BaseOutgoingMessage baseOutgoingMessage = (BaseOutgoingMessage)messageData;
            this.session = Integer.decode(baseOutgoingMessage.getSessionId());
        } else {
            this.session = 0;
        }
    }

    public CameraMessageWrapper(InputStream inputStream) throws IOException {
        this.header = readByteFromStream(inputStream);
        this.version = readByteFromStream(inputStream);

        // next two bytes are spare
        readByteFromStream(inputStream);
        readByteFromStream(inputStream);

        this.session = readIntFromStream(inputStream);
        this.packetCount = readIntFromStream(inputStream);

        // next two bytes are spare
        readByteFromStream(inputStream);
        readByteFromStream(inputStream);

        this.messageType = readShortFromStream(inputStream);

        int dataLength = readIntFromStream(inputStream) - 2;

        ByteBuffer incoming = ByteBuffer.allocate(dataLength);
        for(int i = 0; i < dataLength; i++) {
            incoming.put(readByteFromStream(inputStream));
        }

        this.messageData = BaseMessageType.getMessageObject(incoming.array(), this.messageType);

        // next two bytes are ignored
        readByteFromStream(inputStream);
        readByteFromStream(inputStream);
    }

    private byte readByteFromStream(InputStream inputStream) throws IOException {
        return (byte)inputStream.read();
    }

    private int readIntFromStream(InputStream inputStream) throws IOException {
        byte[] incomingInt = new byte[4];
        incomingInt[3] = (byte)inputStream.read();
        incomingInt[2] = (byte)inputStream.read();
        incomingInt[1] = (byte)inputStream.read();
        incomingInt[0] = (byte)inputStream.read();

        return ByteBuffer.wrap(incomingInt).getInt();
    }

    private short readShortFromStream(InputStream inputStream) throws IOException {
        byte[] incomingShort = new byte[2];
        incomingShort[1] = (byte)inputStream.read();
        incomingShort[0] = (byte)inputStream.read();

        return ByteBuffer.wrap(incomingShort).getShort();
    }

    public ByteBuffer getByteBufferForMessage() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer();
        String messageDataJSON = ow.writeValueAsString(messageData);

        // Translate this message into a byte buffer.
        ByteBuffer byteBuffer = ByteBuffer.allocate(messageDataJSON.length() + 22);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byteBuffer.put(this.header);
        byteBuffer.put(this.version);
        byteBuffer.put(ZERO_BYTE);
        byteBuffer.put(ZERO_BYTE);
        byteBuffer.putInt(this.session); // Session
        byteBuffer.putInt(this.packetCount); // Packet count.
        byteBuffer.put(ZERO_BYTE);
        byteBuffer.put(ZERO_BYTE);
        byteBuffer.putShort(this.messageType);
        byteBuffer.putInt(messageDataJSON.length() + 2);
        byteBuffer.put(messageDataJSON.getBytes());
        byteBuffer.put(NEW_LINE);
        byteBuffer.put(ZERO_BYTE);

        return byteBuffer;
    }

    public byte getHeader() {
        return header;
    }

    public byte getVersion() {
        return version;
    }

    public int getSession() {
        return session;
    }

    public int getPacketCount() {
        return packetCount;
    }

    public short getMessageType() {
        return messageType;
    }

    public BaseMessageType getMessageData() {
        return messageData;
    }
}
