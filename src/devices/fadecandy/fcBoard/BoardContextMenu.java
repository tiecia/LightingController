package devices.fadecandy.fcBoard;

import javafx.fxml.FXML;

public class BoardContextMenu {

    private FCBoard board;

    public void initData(FCBoard board){
        this.board = board;
    }

    @FXML
    public void addLedStrip(){
        board.addLedStrip();
    }

    @FXML
    public void removeBoard(){
        board.removeThisBoard();
    }
}
