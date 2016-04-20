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
public class Variable extends Expression{
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
        return name;
    }

}
