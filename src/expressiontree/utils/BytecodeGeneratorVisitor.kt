package expressiontree.utils

import expressiontree.ir.*
import java.util.*

/**
 * Created by nikit on 27-Apr-16
 */
class BytecodeGeneratorVisitor : IRVisitor {
    private val data = Stack<String>();
    private var labelIdx = 0;

    override fun visit(st: AssignmentStatement) {
        val expr = data.pop()
        data.pop()
        val variable = st.variable.name
        data.push("$expr\nsave $variable")
    }

    override fun visit(exp: BinaryExpression) {
        val e2 = data.pop()
        val e1 = data.pop()
        var op = ""
        when (exp.op) {
            "==" -> op = "eq"
            "!=" -> op = "not\neq"
            ">" -> op = "gt"
            "<" -> op = "lt"
            ">=" -> op = "gt\neq"
            "<=" -> op = "lt\neq"
            "+" -> op = "add"
            "-" -> op = "sub"
            "*" -> op = "mul"
            "/" -> op = "div"
            "%" -> op = "mod"
            "." -> op = "concat"
        }

        data.push("$e2\n$e1\n$op")
    }

    override fun visit(st: BlockOfStatements) {
        val sb = StringBuilder()
        for (i in 0..st.statements.size - 1) {
            val all = data.pop()
            val lineBuilder = StringBuilder()
            val lines = StringTokenizer(all, "\n")
            while (lines.hasMoreTokens()) {
                val line = lines.nextToken()
                lineBuilder.append(line).append("\n")
            }
            sb.insert(0, lineBuilder.toString())
        }
        data.push(sb.toString())
    }

    override fun visit(exp: TernaryExpression?) {
        val e2 = data.pop()
        val e1 = data.pop()
        val condition = data.pop()
        val label1 = Integer.toString(labelIdx++)
        val label2 = Integer.toString(labelIdx++)

        data.push("$condition\nfjmp $label1\n$e1\njmp $label2\nlabel $label1\n$e2\nlabel $label2")
    }

    override fun visit(st: IfStatement) {
        val elsePart = if (st.elsePart != null) data.pop() else ""
        val thenPart = data.pop()
        val condition = data.pop()
        val label1 = Integer.toString(labelIdx++)
        val label2 = Integer.toString(labelIdx++)

        data.push("$condition\nfjmp $label1\n$thenPart\njmp $label2\nlabel $label1$elsePart\nlabel $label2")
    }

    override fun visit(exp: Constant) {
        data.push("push ${exp.type.toString().substring(0, 1)}${exp.value}");
    }

    override fun visit(st: PrintStatement) {
        val sb = StringBuilder()
        for (i in 0..st.expressions.size - 1) {
            sb.append("\n${data.pop()}")
        }
        sb.append("\nprint ${st.expressions.size}")
        data.push(sb.toString())
    }

    override fun visit(st: ReadStatement) {
        val sb = StringBuilder()
        for (i in 0..st.variables.size - 1) {
            data.pop()
            sb.append("\nread ${st.variables[i].type.toString().substring(0, 1)}")
            sb.append("\nsave ${st.variables[i].name}")
        }
        data.push(sb.toString())
    }

    override fun visit(exp: UnaryExpression) {
        var op = ""
        when (exp.operator) {
            "+" -> op = "uplus"
            "-" -> op = "uminus"
            "!" -> op = "not"
        }
        data.push("${data.pop()}\n$op")
    }

    override fun visit(exp: Variable) {
        data.push("load ${exp.name}")
    }

    override fun visit(st: WhileStatement?) {

    }

    override fun visit(st: ForStatement?) {

    }

    override fun toString(): String {
        return data.peek()
    }
}