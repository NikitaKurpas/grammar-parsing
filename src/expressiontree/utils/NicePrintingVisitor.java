/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expressiontree.utils;

import expressiontree.ir.*;

import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Program printing with indentation (two spaces) before each block of code.
 * 
 * @author beh01
 */
public class NicePrintingVisitor implements IRVisitor {

    private Stack<String> data = new Stack<>();

    @Override
    public String toString() {
        return data.pop();
    }

    @Override
    public void visit(AssignmentStatement st) {
        String expr = data.pop();
        String variable = data.pop();
        data.push(variable + " = " + expr + ";");
    }

    @Override
    public void visit(BinaryExpression exp) {
        String e2 = data.pop();
        String e1 = data.pop();
        data.push(e1 + exp.getOp() + e2);
    }

    @Override
    public void visit(BlockOfStatements st) {
        StringBuilder sb = new StringBuilder("}");
        for (int i = 0; i < st.getStatements().size(); i++) {
            String all = data.pop();
            StringBuilder lineBuilder = new StringBuilder();
            StringTokenizer lines = new StringTokenizer(all, "\n");
            while(lines.hasMoreTokens()) {
                String line=lines.nextToken();
                lineBuilder.append("  ").append(line).append("\n");
            }
            sb.insert(0, lineBuilder.toString());
        }
        sb.insert(0, "{\n");
        data.push(sb.toString());
    }

    @Override
    public void visit(TernaryExpression exp) {
        String e2 = data.pop();
        String e1 = data.pop();
        String condition = data.pop();
        data.push("(" + condition + ")?" + e1 + ":" + e2);
    }

    @Override
    public void visit(IfStatement st) {
        String elsePart = "";
        if (st.getElsePart() != null) {
            elsePart = data.pop();
        }
        String thenPart = data.pop();
        String condition = data.pop();

        String result = "if (" + condition + ") " + thenPart;
        if (st.getElsePart() != null) {
            result += " else " + elsePart;
        }
        data.push(result);
    }

    @Override
    public void visit(Constant exp) {
        data.push(exp.getValue().toString());
    }

    @Override
    public void visit(PrintStatement st) {
        StringBuilder sb = new StringBuilder(";");
        for (int i = 1; i < st.getExpressions().size(); i++) {
            sb.insert(0, "," + data.pop());
        }
        sb.insert(0, "print " + data.pop());
        data.push(sb.toString());
    }

    @Override
    public void visit(ReadStatement st) {
        StringBuilder sb = new StringBuilder(";");
        for (int i = 1; i < st.getVariables().size(); i++) {
            sb.insert(0, "," + data.pop());
        }
        sb.insert(0, "read " + data.pop());
        data.push(sb.toString());
    }

    @Override
    public void visit(UnaryExpression exp) {
        data.push(exp.getOperator() + data.pop());
    }

    @Override
    public void visit(Variable exp) {
        data.push(exp.getName());
    }

    @Override
    public void visit(WhileStatement st) {

        String body = data.pop();
        String condition = data.pop();

        String result = "while (" + condition + ") " + body;
        data.push(result);
    }
}
