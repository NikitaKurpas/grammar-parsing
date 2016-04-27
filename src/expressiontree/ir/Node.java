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
        throw new RuntimeException("Line " + line + ", column " + column + ": " + message);
    }

    public abstract void accept(IRVisitor visitor);
   
    @Override
    public abstract String toString();

}
