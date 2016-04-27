package expressiontree.vm;

import expressiontree.ir.SymbolTable;
import expressiontree.ir.Type;
import expressiontree.ir.Variable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by nikit on 27-Apr-16
 */
public class VirtualMachine {

    private final BufferedReader reader;

    public static void main(String[] args) throws IOException {
        VirtualMachine vm;
        if (args.length == 0) {
            System.out.println("VM Version 1.0:  Reading from standard input . . .");
            vm = new VirtualMachine(System.in);
        } else if (args.length == 1) {
            System.out.println("VM Version 1.0:  Reading from file " + args[0] + " . . .");
            try {
                vm = new VirtualMachine(new java.io.FileInputStream(args[0]));
            } catch (java.io.FileNotFoundException e) {
                System.out.println("VM Version 1.0:  File " + args[0] + " not found.");
                return;
            }
        } else {
            System.out.println("VM Version 1.0:  Usage is one of:");
            System.out.println("         java VirtualMachine < inputfile");
            System.out.println("OR");
            System.out.println("         java VirtualMachine inputfile");
            return;
        }
    }

    private Stack<Object> values = new Stack<>();
    private Map<String, Object> variables = new HashMap<>();

    public VirtualMachine(InputStream in) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    public void run(SymbolTable table) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("push")) {
                String valueString = line.substring(5);
                String type = valueString.substring(0, 1);
                Object value = null;
                switch (type) {
                    case "S":
                        value = valueString.substring(2, valueString.length() - 1);
                        break;
                    case "I":
                        value = Integer.parseInt(valueString.substring(1));
                        break;
                    case "F":
                        value = Float.parseFloat(valueString.substring(1));
                        break;
                    case "B":
                        value = Boolean.parseBoolean(valueString.substring(1));
                        break;
                }
                values.push(value);
            } else if (line.startsWith("print")) {
                int count = Integer.parseInt(line.substring(6));
                while (count > 0) {
                    System.out.print(values.pop());
                    count--;
                }
                System.out.println("");
            } else if (line.startsWith("add")) {
                Object val1 = values.pop();
                Object val2 = values.pop();

                if (val1 instanceof Float && val2 instanceof Float) {
                    values.push((Float) val1 + (Float) val2);
                } else if (val1 instanceof Float && val2 instanceof Integer) {
                    values.push((Float) val1 + (Integer) val2);
                } else if (val1 instanceof Integer && val2 instanceof Float) {
                    values.push((Integer) val1 + (Float) val2);
                } else if (val1 instanceof Integer && val2 instanceof Integer) {
                    values.push((Integer) val1 + (Integer) val2);
                }
            } else if (line.startsWith("sub")) {
                Object val1 = values.pop();
                Object val2 = values.pop();

                if (val1 instanceof Float && val2 instanceof Float) {
                    values.push((Float) val1 - (Float) val2);
                } else if (val1 instanceof Float && val2 instanceof Integer) {
                    values.push((Float) val1 - (Integer) val2);
                } else if (val1 instanceof Integer && val2 instanceof Float) {
                    values.push((Integer) val1 - (Float) val2);
                } else if (val1 instanceof Integer && val2 instanceof Integer) {
                    values.push((Integer) val1 - (Integer) val2);
                }
            } else if (line.startsWith("mul")) {
                Object val1 = values.pop();
                Object val2 = values.pop();

                if (val1 instanceof Float && val2 instanceof Float) {
                    values.push((Float) val1 * (Float) val2);
                } else if (val1 instanceof Float && val2 instanceof Integer) {
                    values.push((Float) val1 * (Integer) val2);
                } else if (val1 instanceof Integer && val2 instanceof Float) {
                    values.push((Integer) val1 * (Float) val2);
                } else if (val1 instanceof Integer && val2 instanceof Integer) {
                    values.push((Integer) val1 * (Integer) val2);
                }
            } else if (line.startsWith("div")) {
                Object val1 = values.pop();
                Object val2 = values.pop();

                if (val1 instanceof Float && val2 instanceof Float) {
                    values.push((Float) val1 / (Float) val2);
                } else if (val1 instanceof Float && val2 instanceof Integer) {
                    values.push((Float) val1 / (Integer) val2);
                } else if (val1 instanceof Integer && val2 instanceof Float) {
                    values.push((Integer) val1 / (Float) val2);
                } else if (val1 instanceof Integer && val2 instanceof Integer) {
                    values.push((Integer) val1 / (Integer) val2);
                }
            } else if (line.startsWith("mod")) {
                Object val1 = values.pop();
                Object val2 = values.pop();

                if (val1 instanceof Float && val2 instanceof Float) {
                    values.push((Float) val1 % (Float) val2);
                } else if (val1 instanceof Float && val2 instanceof Integer) {
                    values.push((Float) val1 % (Integer) val2);
                } else if (val1 instanceof Integer && val2 instanceof Float) {
                    values.push((Integer) val1 % (Float) val2);
                } else if (val1 instanceof Integer && val2 instanceof Integer) {
                    values.push((Integer) val1 % (Integer) val2);
                }
            } else if (line.startsWith("uminus")) {
                Object val = values.pop();

                if (val instanceof Integer) {
                    values.push(-(Integer) val);
                } else if (val instanceof Float) {
                    values.push(-(Float) val);
                }
            } else if (line.startsWith("concat")) {
                Object val1 = values.pop();
                Object val2 = values.pop();

                values.push((String) val1 + (String) val2);
            } else if (line.startsWith("load")) {
                Variable var = table.load(line.substring(5));
                if (variables.get(var.getName()) ==  null) {
                    if (var.getType().equals(Type.STRING)) {
                        variables.put(var.getName(), "");
                    } else if (var.getType().equals(Type.BOOLEAN)) {
                        variables.put(var.getName(), false);
                    } else if (var.getType().equals(Type.INT)) {
                        variables.put(var.getName(), 0);
                    } else if (var.getType().equals(Type.FLOAT)) {
                        variables.put(var.getName(),  0.0f);
                    }
                }
                values.push(variables.get(var.getName()));
            } else if (line.startsWith("save")) {
                Variable var = table.load(line.substring(5));
                Object val = values.pop();

                if (var.getType().equals(Type.FLOAT) && val instanceof Integer) {
                    variables.put(var.getName(), ((Number) val).floatValue());
                } else {
                    variables.put(var.getName(), val);
                }
            } else if (line.startsWith("read")) {
                String type = line.substring(5);
                Scanner scanner = new Scanner(System.in);

                switch (type) {
                    case "I":
                        System.out.print("Type an integer: ");
                        values.push(scanner.nextInt());
                        break;
                    case "F":
                        System.out.print("Type a float: ");
                        values.push(scanner.nextFloat());
                        break;
                    case "S":
                        System.out.print("Type a string: ");
                        values.push(scanner.nextLine());
                        break;
                    case "B":
                        System.out.print("Type a boolean: ");
                        values.push(scanner.nextBoolean());
                        break;
                }
            }
        }
    }
}
