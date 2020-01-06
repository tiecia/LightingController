package FCTest;


import FCTest.opc.*;

import java.io.IOException;

public class FCTest {
    public static void main(String[] args) throws IOException {
        OpcClient server = new OpcClient("127.0.0.1", 7890);
        OpcDevice board = server.addDevice();
        board.printList();
        PixelStrip strip = board.addPixelStrip(0,32);
        board.printList();
        board.removeStrip(strip);
        board.printList();



        System.out.println(server.getConfig());
    }
}
