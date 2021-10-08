import com.fasterxml.jackson.core.JsonProcessingException;
import com.jbr.exp.messages.CameraMessageWrapper;
import com.jbr.exp.messages.incoming.LoginResponse;
import com.jbr.exp.messages.outgoing.*;

import java.io.IOException;
import java.net.*;


public class Main {
    private static void sendMessage(Socket clientSocket, BaseOutgoingMessage specificOutgoingMessage) throws IOException {
        CameraMessageWrapper outgoingMessage = new CameraMessageWrapper(specificOutgoingMessage);
        clientSocket.getOutputStream().write(outgoingMessage.getByteBufferForMessage().array());

        CameraMessageWrapper incomingMessage = new CameraMessageWrapper(clientSocket.getInputStream());
        System.out.println(incomingMessage.getMessageData().toString());
    }

    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("192.168.178.108",34567);
            Login loginMessage = new Login("admin", "");
            CameraMessageWrapper outgoingMessage = new CameraMessageWrapper(loginMessage);

            // Send message
            clientSocket.getOutputStream().write(outgoingMessage.getByteBufferForMessage().array());

            // Receive reply.
            CameraMessageWrapper incomingMessage = new CameraMessageWrapper(clientSocket.getInputStream());
            LoginResponse loginResponse = (LoginResponse) incomingMessage.getMessageData();
            System.out.println(incomingMessage.getMessageData().toString());

            // Get system info
            sendMessage(clientSocket,new SystemInfo(loginResponse.getSessionId()));

            // Get network info
            sendMessage(clientSocket,new Network(loginResponse.getSessionId()));

            // Set info.
//            sendMessage(clientSocket,new SetIRCutSwap(loginResponse.getSessionId(), 1));

            clientSocket.close();
            System.out.println("Complete");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
