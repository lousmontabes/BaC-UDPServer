import java.awt.*;

/**
 * Created by Lous on 20/06/2017.
 */
public class Match {

    private int id;
    private boolean player1Ready, player2Ready;
    private byte player1Score, player2Score;
    private byte[] player1x, player1y, player2x, player2y;
    private byte player1Angle, player2Angle;
    private byte player1Event, player2Event;
    private byte[] player1EventIndex, player2EventIndex;

    public Match(int id){
        this.id = id;

        player1Ready = false;
        player2Ready = false;

        player1Score = 0;
        player2Score = 0;

        player1Angle = 0;
        player2Angle = 0;

        player1Event = 0;
        player2Event = 0;

        player1EventIndex = new byte[4];
        player2EventIndex = new byte[4];

        player1x = new byte[4];
        player1y = new byte[4];

        player2x = new byte[4];
        player2y = new byte[4];

    }

    /** SETTERS **/

    public void setPlayer1Ready(boolean ready) {
        this.player1Ready = ready;
    }

    public void setPlayer2Ready(boolean ready) {
        this.player2Ready = ready;
    }

    public void setPlayer1Score(byte score) {
        this.player1Score = score;
    }

    public void setPlayer2Score(byte score) {
        this.player2Score = score;
    }

    public void setPlayer1x(byte[] x) {
        player1x = x;
    }

    public void setPlayer1y(byte[] y) {
        player1y = y;
    }

    public void setPlayer2x(byte[] x) {
        player2x = x;
    }

    public void setPlayer2y(byte[] y) {
        player2y = y;
    }

    public void setPlayer1Angle(byte a) {
        player1Angle = a;
    }

    public void setPlayer2Angle(byte a) {
        player2Angle = a;
    }

    public void setPlayer1Event(byte event, byte[] index) {
        player1Event = event;
        player1EventIndex = index;
    }

    public void setPlayer2Event(byte event, byte[] index) {
        player2Event = event;
        player2EventIndex = index;
    }

    /** GETTERS **/

    public byte getPlayer1Score() {
        return player1Score;
    }

    public byte getPlayer2Score() {
        return player2Score;
    }

    public boolean getPlayer1Ready() {
        return player1Ready;
    }

    public boolean getPlayer2Ready() {
        return player2Ready;
    }

    public byte[] getPlayer1x() {
        return player1x;
    }

    public byte[] getPlayer1y() {
        return player1y;
    }

    public byte[] getPlayer2x() {
        return player2x;
    }

    public byte[] getPlayer2y() {
        return player2y;
    }

    public byte getPlayer1Angle() {
        return player1Angle;
    }

    public byte getPlayer2Angle() {
        return player2Angle;
    }

    public byte getPlayer1Event() {
        return player1Event;
    }

    public byte[] getPlayer1EventIndex() {
        return player1EventIndex;
    }

    public byte getPlayer2Event() {
        return player2Event;
    }

    public byte[] getPlayer2EventIndex() {
        return player2EventIndex;
    }

}
