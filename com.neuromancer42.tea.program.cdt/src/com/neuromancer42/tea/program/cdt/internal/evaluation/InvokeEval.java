package com.neuromancer42.tea.program.cdt.internal.evaluation;

import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IType;

public class InvokeEval implements IEval {
    private final int function;
    private final int[] arguments;
    private final IASTExpression debugExpr;

    public InvokeEval(IASTExpression expr, int fReg, int[] aRegs) {
        this.function = fReg;
        this.arguments = aRegs;
        this.debugExpr = expr;
    }

    public int getFunction() {
        return function;
    }

    public int[] getArguments() {
        return arguments;
    }

    @Override
    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        sb.append("invoke(#").append(function);
        for (int arg : arguments) {
            sb.append(",#").append(arg);
        }
        sb.append(")");
        sb.append(debugExpr.getClass().getSimpleName());
        sb.append("[");
        sb.append(debugExpr.getRawSignature());
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int[] getOperands() {
        int[] operands = new int[1 + arguments.length];
        operands[0] = function;
        System.arraycopy(arguments, 0, operands, 1, arguments.length);
        return operands;
    }

    @Override
    public IASTExpression getExpression() {
        return debugExpr;
    }

    @Override
    public IType getType() {
        return debugExpr.getExpressionType();
    }
}
