/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressiontree.ir;

import expressiontree.utils.IRVisitor;

/**
 *
 * @author beh01
 */
public abstract class Node {

    private int line, column;

    public Node() {
    }

    public Node(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public void makeError(String message) {
        // TODO: make Error class and send the message, line and column there
    }

    public abstract void accept(IRVisitor visitor);
   
    @Override
    public abstract String toString();

}
