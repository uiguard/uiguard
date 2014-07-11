package com.uiguard.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thoughtworks.selenium.SeleniumException;
import com.uiguard.UiGuardSettings;

public class CommandExecutor {
	
	private CommandExecutor(){};
	
	private static CommandExecutor ce;
	
	public static CommandExecutor getInstance(){
		if(ce == null){
			ce = new CommandExecutor();
		}
		return ce;
	}

	public String execute(final String exeName, final String ... args) {
		final ProcessRunner pr = new ProcessRunner(exeName,args);
		FutureTask<String> ft = new FutureTask<String>(pr){

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				pr.cancel();
				return super.cancel(mayInterruptIfRunning);
			}
			
		};
		ExecutorService st = Executors.newSingleThreadExecutor();
		st.submit(ft);
		try {
			return ft.get(20, TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}finally{
			ft.cancel(true);
			st.shutdown();
		}
		return null;
		
	}
	
	public void killProcess(final String processName) {
		Process process;
		try {
			process = Runtime.getRuntime().exec("tasklist");
			Scanner sc = new Scanner(process.getInputStream());
			
			Pattern pattern = Pattern.compile(getProcessNameForPattern(processName) + "[ ]*([0-9]*)");
			
			while(sc.hasNextLine()){
				killProcessFromScanLine(sc.nextLine(), pattern);
			}
		} catch (IOException e) {
			throw new SeleniumException(e);
		}
	}
	
	private static final int MAXSCANLINEPROCESSNAMELENGTH = 25;
	
	private static String getProcessNameForPattern(final String processName){
		return processName.length()>MAXSCANLINEPROCESSNAMELENGTH ? 
					processName.substring(0, MAXSCANLINEPROCESSNAMELENGTH):
					processName;
	}
	
	private static String getProcessSimpleName(final String processName){
		final String processNameReplaceGap = processName.replace("\\", "/");
		return processNameReplaceGap.substring(processNameReplaceGap.lastIndexOf("/")+1, processNameReplaceGap.length());
	}

	private static void killProcessFromScanLine(final String line, final Pattern pattern)
			throws IOException {
		Matcher matcher = pattern.matcher(line);
		while(matcher.find()){
			if(matcher.groupCount()>0){
				String id = matcher.group(1);
				if(!(id == null || "".equals(id))){
					Runtime.getRuntime().exec("tskill " + Integer.parseInt(id));
				}
			}
		}
	}
	
	private class ProcessRunner implements Callable<String> {
		
		private Process p;
				
		final private String exeName;
		
		final private String paramStr;
		
		public ProcessRunner(String exeName, String ... args){
			this.exeName = exeName;
			String ts = "";
			if(null != args){
				ts = " ";
				for(String s : args){
					ts += s+" "; 
				}
			}
			this.paramStr = ts;
		}
		
		public boolean cancel(){
			killProcess(getProcessSimpleName(exeName));
			p.destroy();
			return true;
		}
		
		@Override
		public String call() throws Exception {
			try {
				p = Runtime.getRuntime().exec(getExeAbsolutePath(exeName) + paramStr);
				BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
				StringBuffer sb = new StringBuffer();
				for(String line = br.readLine();line!=null;line = br.readLine()){
					sb.append(line);
				}
				return sb.toString();
			} catch (IOException e) {
			}
			return null;
		}
		
		private String getExeAbsolutePath(final String exeName){
			if( UiGuardSettings.AssistPath.contains(":"))
				return new File(getExeSettingPath(exeName)).getAbsolutePath();
			return new File(getExePathUnderUserDir(exeName)).getAbsolutePath();
		}
		
		private String getExePathUnderUserDir(final String exeName){
			return System.getProperty("user.dir") + File.separator + getExeSettingPath(exeName);
		}
		
		private String getExeSettingPath(final String exeName){
			return UiGuardSettings.AssistPath+File.separator+exeName;
		}
		
	}
	
}
