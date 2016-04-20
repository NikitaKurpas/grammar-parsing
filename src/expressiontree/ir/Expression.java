/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package expressiontree.ir;

/**
 *
 * @author beh01
 */
public abstract class Expression extends Node{
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
}
