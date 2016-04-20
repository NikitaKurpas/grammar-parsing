/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package expressiontree.ir;

import java.util.HashMap;

/**
 *
 * @author beh01
 */
public class SymbolTable {
    private HashMap<String,Variable> data = new HashMap<String, Variable>();
    
    public void save(Variable v) {
        data.put(v.getName(), v);
    }
    public Variable load(String name) {
        return data.get(name);
    }
}
