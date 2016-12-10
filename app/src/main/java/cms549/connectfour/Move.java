package cms549.connectfour;

//Data Structure to describe one move
public class Move {
    int row;
    int col;
    int player;
    int position;
    int picID;

    /**
     * Creates the move object
     * @param row
     * @param col
     * @param player
     * @param position
     * @param picID
     */
    public Move(int row, int col, int player, int position, int picID){
        this.row = row;
        this.col=col;
        this.player = player;
        this.position = position;
        this.picID = picID;
    }

}
