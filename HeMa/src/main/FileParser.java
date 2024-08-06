package main;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;

import util.Counter;
import util.MethodDataCollect;
import visitor.FunctionVisitor;

public class FileParser {
	private String code;
	private CompilationUnit m_CompilationUnit;

	public FileParser(String code) {
		this.code = code;
	}

	public CompilationUnit getParsedFile() {
		return m_CompilationUnit;
	}

	public void  extractFeatures(boolean status) throws ParseException, IOException {

		m_CompilationUnit = parseFileWithRetries(code);
		FunctionVisitor functionVisitor = new FunctionVisitor();

		functionVisitor.visit(m_CompilationUnit, null);
		ArrayList<MethodDeclaration> nodes = functionVisitor.getMethodDeclarations();

		if (status){
			Counter.methodDeclarations.addAll(nodes);
		}else {
			Counter.methodDeclarationsUnsafe.addAll(nodes);
		}
	}

	public void  extractFeaturesCSV() throws ParseException, IOException {
		m_CompilationUnit = parseFileWithRetries(code);
		FunctionVisitor functionVisitor = new FunctionVisitor();

		functionVisitor.visit(m_CompilationUnit, null);
		ArrayList<MethodDeclaration> nodes = functionVisitor.getMethodDeclarations();
		List<String> collectLines = MethodDataCollect.collectLines(nodes);
		Counter.createCVS.addAll(collectLines);
	}

	public CompilationUnit parseFileWithRetries(String code) throws IOException {
		final String classPrefix = "public class Test {";
		final String classSuffix = "}";
		final String methodPrefix = "SomeUnknownReturnType f() {";
		final String methodSuffix = "return noSuchReturnValue; }";

		String originalContent = code;
		String content = originalContent;
		CompilationUnit parsed = null;
		try {
			parsed = JavaParser.parse(content);
		} catch (ParseProblemException e1) {
			// Wrap with a class and method
			try {
				content = classPrefix + methodPrefix + originalContent + methodSuffix + classSuffix;
				parsed = JavaParser.parse(content);
			} catch (ParseProblemException e2) {
				// Wrap with a class only
				content = classPrefix + originalContent + classSuffix;
				parsed = JavaParser.parse(content);
			}
		}

		return parsed;
	}
}
