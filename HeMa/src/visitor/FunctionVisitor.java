package visitor;

import java.util.ArrayList;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class FunctionVisitor extends VoidVisitorAdapter<Object> {
	private ArrayList<MethodDeclaration> m_Nodes = new ArrayList<>();

	@Override
	public void visit(MethodDeclaration node, Object arg) {
		AnnotationExpr annotation = node.getAnnotationByClass(Override.class);
		
		if(annotation == null && node.getBody() != null)
			m_Nodes.add(node);

		super.visit(node, arg);
	}

	public ArrayList<MethodDeclaration> getMethodDeclarations(){
		return m_Nodes;
	}
}
