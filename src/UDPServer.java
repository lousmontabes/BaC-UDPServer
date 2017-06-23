import java.io.*;
import java.net.*;
import java.util.ArrayList;

class UDPServer {

    static private ArrayList<Match> activeMatches = new ArrayList<>();

    private static boolean connectionActive = true;
    private static final int READING_PORT = 9801;

    static private Match activeMatch = new Match(0);

    static private int messageCode = -1;
    static private byte[] message = new byte[1024];
    static private byte[] response = new byte[1024];

    public static void main(String args[]) throws Exception {

        DatagramSocket serverSocket = new DatagramSocket(READING_PORT);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while (connectionActive) {

            System.out.println("Listening");

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            message = receivePacket.getData();
            messageCode = message[0]; // The instruction code is the first byte of the packet data.

            String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());

            System.out.println("Received code: " + messageCode);

            if (sentence.equals("EXIT")) {
                connectionActive = false;
            }

            switch (messageCode) {

                case Message.MESSAGE_ERROR:
                    break;

                case Message.MESSAGE_NONE:
                    break;

                case Message.MESSAGE_SENDING_PLAYER1READY:
                    receivedP1Ready();
                    break;

                case Message.MESSAGE_SENDING_PLAYER2READY:
                    receivedP2Ready();
                    break;

                case Message.MESSAGE_SENDING_PLAYER1SCORE:
                    receivedP1Score();
                    break;

                case Message.MESSAGE_SENDING_PLAYER2SCORE:
                    receivedP2Score();
                    break;

                case Message.MESSAGE_SENDING_PLAYER1POS:
                    receivedP1Position();
                    break;

                case Message.MESSAGE_SENDING_PLAYER2POS:
                    receivedP2Position();
                    break;

                case Message.MESSAGE_SENDING_PLAYER1ANGLE:
                    receivedP1Angle();
                    break;

                case Message.MESSAGE_SENDING_PLAYER2ANGLE:
                    receivedP2Angle();
                    break;

                case Message.MESSAGE_SENDING_PLAYER1EVENT:
                    receivedP1Event();
                    break;

                case Message.MESSAGE_SENDING_PLAYER2EVENT:
                    receivedP2Event();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER1READY:
                    requestedP1Ready();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER2READY:
                    requestedP2Ready();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER1SCORE:
                    requestedP1Score();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER2SCORE:
                    requestedP2Score();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER1POS:
                    requestedP1Position();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER2POS:
                    requestedP2Position();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER1ANGLE:
                    requestedP1Angle();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER2ANGLE:
                    requestedP2Angle();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER1EVENT:
                    requestedP1Event();
                    break;

                case Message.MESSAGE_REQUESTING_PLAYER2EVENT:
                    requestedP2Event();
                    break;

                case Message.MESSAGE_CREATED_MATCH:
                    createMatch();
                    break;

                case Message.MESSAGE_RESTORE_MATCH:
                    restoreMatch();

                default:
                    System.out.println("Invalid message");
                    break;

            }

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            sendData = response;

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);

            System.out.println();

        }

    }

    static private void createMatch() {
        Match match = new Match(1);
        activeMatches.add(match);
        generateResponse(true);
    }

    static private void receivedP1Ready() {
        activeMatch.setPlayer1Ready(true);
        generateResponse(true);
    }

    static private void receivedP2Ready() {
        activeMatch.setPlayer2Ready(true);
        generateResponse(true);
    }

    static private void receivedP1Score() {
        // P1 score and P2 score correspond to the second and third bytes in the message data, respectively.
        activeMatch.setPlayer1Score(message[1]);
        generateResponse(true);
    }

    static private void receivedP2Score() {
        // P1 score and P2 score correspond to the second and third bytes in the message data, respectively.
        activeMatch.setPlayer2Score(message[1]);
        generateResponse(true);
    }

    static private void receivedP1Position() {

        // P1 pos and P2 pos correspond to the second to fifth and third to ninth bytes in the message data, respectively.

        byte[] bytesX = {message[1], message[2], message[3], message[4]};
        byte[] bytesY = {message[5], message[6], message[7], message[8]};

        activeMatch.setPlayer1x(bytesX);
        activeMatch.setPlayer1y(bytesY);

        int x = java.nio.ByteBuffer.wrap(bytesX).getInt();
        int y = java.nio.ByteBuffer.wrap(bytesY).getInt();

        System.out.println("Position (" + x + ", " + y + ")");

        generateResponse(true);
    }

    static private void receivedP2Position() {

        // P1 pos and P2 pos correspond to the second to fifth and third to ninth bytes in the message data, respectively.

        byte[] bytesX = {message[1], message[2], message[3], message[4]};
        byte[] bytesY = {message[5], message[6], message[7], message[8]};

        activeMatch.setPlayer2x(bytesX);
        activeMatch.setPlayer2y(bytesY);

        int x = java.nio.ByteBuffer.wrap(bytesX).getInt();
        int y = java.nio.ByteBuffer.wrap(bytesY).getInt();

        System.out.println("Position (" + x + ", " + y + ")");

        generateResponse(true);
    }

    static private void receivedP1Angle() {
        // P1 angle corresponds to the second byte in the message data.
        activeMatch.setPlayer1Angle(message[1]);
    }

    static private void receivedP2Angle() {
        // P2 angle corresponds to the second byte in the message data.
        activeMatch.setPlayer2Angle(message[1]);
    }

    static private void receivedP1Event() {

        // P1 Event and EventIndex correspond to the second and fifth to ninth bytes in the message data, respectively.

        byte[] eventIndex = {message[5], message[6], message[7], message[8]};
        activeMatch.setPlayer1Event(message[1], eventIndex);
    }

    static private void receivedP2Event() {

        // P1 Event and EventIndex correspond to the second and fifth to ninth bytes in the message data, respectively.

        byte[] eventIndex = {message[5], message[6], message[7], message[8]};
        activeMatch.setPlayer2Event(message[1], eventIndex);
    }

    static private void requestedP1Ready() {
        response[0] = 1;
        response[1] = (byte) (activeMatch.getPlayer1Ready() ? 1 : 0);
        //response[2] = (byte) (activeMatch.getPlayer2Ready() ? 1 : 0);
    }

    static private void requestedP2Ready() {
        response[0] = 1;
        response[1] = (byte) (activeMatch.getPlayer2Ready() ? 1 : 0);
        //response[2] = (byte) (activeMatch.getPlayer2Ready() ? 1 : 0);
    }

    static private void requestedP1Score() {
        response[0] = 1;
        response[1] = activeMatch.getPlayer1Score();
    }

    static private void requestedP2Score() {
        response[0] = 1;
        response[1] = activeMatch.getPlayer2Score();
    }

    static private void requestedP1Position() {

        byte[] bytesX = activeMatch.getPlayer1x();
        byte[] bytesY = activeMatch.getPlayer1y();

        response[0] = 1;

        response[1] = bytesX[0];
        response[2] = bytesX[1];
        response[3] = bytesX[2];
        response[4] = bytesX[3];

        response[5] = bytesY[0];
        response[6] = bytesY[1];
        response[7] = bytesY[2];
        response[8] = bytesY[3];

        int x = java.nio.ByteBuffer.wrap(bytesX).getInt();
        int y = java.nio.ByteBuffer.wrap(bytesY).getInt();

        System.out.println("Position (" + x + ", " + y + ")");

    }

    static private void requestedP2Position() {

        byte[] bytesX = activeMatch.getPlayer2x();
        byte[] bytesY = activeMatch.getPlayer2y();

        response[0] = 1;

        response[1] = bytesX[0];
        response[2] = bytesX[1];
        response[3] = bytesX[2];
        response[4] = bytesX[3];

        response[5] = bytesY[0];
        response[6] = bytesY[1];
        response[7] = bytesY[2];
        response[8] = bytesY[3];

        int x = java.nio.ByteBuffer.wrap(bytesX).getInt();
        int y = java.nio.ByteBuffer.wrap(bytesY).getInt();

        System.out.println("Position (" + x + ", " + y + ")");
    }

    static private void requestedP1Angle() {
        response[0] = 1;
        response[1] = activeMatch.getPlayer1Angle();
    }

    static private void requestedP2Angle() {
        response[0] = 1;
        response[1] = activeMatch.getPlayer1Angle();
    }

    static private void requestedP1Event() {

        byte[] eventIndex = activeMatch.getPlayer1EventIndex();

        response[0] = 1;

        response[1] = activeMatch.getPlayer1Event();
        // Bytes 2 - 4 are reserved for a possible int in the future

        response[5] = eventIndex[0];
        response[6] = eventIndex[1];
        response[7] = eventIndex[2];
        response[8] = eventIndex[3];

    }

    static private void requestedP2Event() {

        byte[] eventIndex = activeMatch.getPlayer2EventIndex();

        response[0] = 1;

        response[1] = activeMatch.getPlayer2Event();
        // Bytes 2 - 4 are reserved for a possible int in the future

        response[5] = eventIndex[0];
        response[6] = eventIndex[1];
        response[7] = eventIndex[2];
        response[8] = eventIndex[3];

    }

    static private void restoreMatch() {
        activeMatch = new Match(0);
        generateResponse(true);
    }

    static private void generateResponse(boolean success){
        if (success) response[0] = 1;
        else response[0] = 0;
    }

}