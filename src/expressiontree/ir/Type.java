/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package expressiontree.ir;

/**
 *
 * @author beh01
 */
public enum Type {
    INT, FLOAT, STRING, BOOLEAN, ERROR;
    
    public static Type convert(String name) {
        if (name.compareToIgnoreCase("String")==0) return STRING;
        if (name.compareToIgnoreCase("int")==0) return INT;
        if (name.compareToIgnoreCase("float")==0) return FLOAT;
        if (name.compareToIgnoreCase("boolean")==0) return BOOLEAN;
        return ERROR;
    }
}
