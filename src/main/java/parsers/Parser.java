package parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class Parser<T> {

	protected String projectPath;
	protected T parser;
	
	public Parser(String projectPath) {
		setProjectPath(projectPath);
	}
	
	public String getProjectPath() {
		return projectPath;
	}
	
	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	
	public T getParser() {
		return parser;
	}
	
	public List<File> listJavaFiles(String filePath){
		File folder = new File(filePath);
		List<File> javaFiles = new ArrayList<>();
		String fileName = "";
		
		for (File file: folder.listFiles()) {
			fileName = file.getName();
			
			if (file.isDirectory())
				javaFiles.addAll(listJavaFiles(file.getAbsolutePath()));
			else if (fileName.endsWith(".java"))
				javaFiles.add(file);
		}
		
		return javaFiles;
	}
	
	public List<File> listJavaProjectFiles(){
		return listJavaFiles(getProjectPath());
	}
	
}