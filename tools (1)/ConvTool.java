package tools;

import java.text.SimpleDateFormat;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvTool {
    private static String LOG_PATH;
    private static int LOG_DEPTH = 5;
	
	public static void init(){
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		LOG_PATH = new File("log").getAbsolutePath() + "/logs_" + format.format(date) + ".log";
	}

    public static void main(String[] args) {
		init();
		
        System.out.println("ConvTool OK!");
        System.out.println("LOG_PATH: " + LOG_PATH);
		System.out.println("aaa: " + parseInt("aaa"));
		System.out.println("123: " + parseInt("123"));
		
    }
	
    /**
     * ログのトレースする深さを取得(デフォルト:5)
     */
	public static int getLogDepth(){
		return LOG_DEPTH;
	}
	
    /**
     * ログのトレースする深さを設定(デフォルト:5)
     */
	public static void setLogDepth(int depth){
		LOG_DEPTH = depth;
	}
	
    /**
     * 入力した文字列が整数に変換可能かを判定
     *
     * @param strValue 入力文字列
     * @return 変換可能かどうか
     */
    public static boolean isNumber(String strValue) {
        String strRegex = "\\A[-]?[0-9]+\\z";
        Pattern pattern = Pattern.compile(strRegex);
        Matcher matcher = pattern.matcher(strValue);
        return matcher.find();
    }

    /**
     * 入力した文字列を整数に変換
     *
     * @param strValue 入力文字列
     * @return 整数(エラー時は-999)
     */
    public static int parseInt(String strValue) {
        if (ConvTool.isNumber(strValue)) {
            return Integer.parseInt(strValue);
        } else {
			printLog("数字ではありません。");
            return -999;
        }
    }
	
	/**
	 * ログをログファイルに保存する関数
	 * @param message メッセージ
	 */
	public static void printLog(String message){
		printLog(message, true);
	}
	
	/**
	 * ログをログファイルに保存する関数
	 * @param message メッセージ
	 * @param trace トレースをログに表示するか
	 */
	public static void printLog(String message, boolean trace){
		StringBuilder msg = new StringBuilder("----------------------------------------------------------------- logstart -----------------------------------------------------------------\n");
		msg.append("message : ");
		msg.append(message);
		msg.append("\n");
		if(trace){
			msg.append(traceLog());
			msg.append("\n");
		}
		writeFile(LOG_PATH, msg.toString(), true);
	}
	
	/**
	 * トレースログ取得関数
	 */
	public static String traceLog() {
		StringBuilder text = new StringBuilder();
		StackTraceElement[] traceElement = Thread.currentThread().getStackTrace();
		boolean isLoging = false;
		int logCount = 0;
		for(StackTraceElement e: traceElement){
			if(logCount >= LOG_DEPTH){
				break;
			}
			
			String methodName = e.getMethodName();
			if(isLoging){
				String className = e.getClassName();
				text.append("Method：").append(className).append(".").append(methodName).append("\n");
				logCount++;
			}
			if(methodName.equals("traceLog")){
				isLoging = true;
			}
		}
		return text.toString();
	}
	
    /**
     * ファイル読み込み関数
     *
     * @param filePath ファイルパス(windows例：C:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\em\WEB-INF\classes\Test.txt)
     * @return ファイルデータ
     * @throws IOException
     */
    public static String readFile(String filePath) throws IOException {
        return ConvTool.readFile(new File(filePath));
    }

    /**
     * ファイル読み込み関数
     *
     * @param file ファイルクラス
     * @return ファイルデータ
     * @throws IOException
     */
    public static String readFile(File file) throws IOException {
        StringBuilder lines = new StringBuilder();

        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr);
        String str;
        while ((str = reader.readLine()) != null) {
            lines.append(str);
        }

        return lines.toString();
    }

    /**
     * ファイル書き込み関数
     *
     * @param filePath ファイルパス(windows例：C:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\em\WEB-INF\classes\Test.txt)
     * @param data     ファイルに書き込むデータ(例：こんにちは。\n良い天気ですね。)
     * @return ファイル書き込みに成功したか
     */
    public static boolean writeFile(String filePath, String data) {
        return ConvTool.writeFile(new File(filePath), data);
    }

    /**
     * ファイル書き込み関数
     *
     * @param filePath ファイルパス(windows例：C:\Program Files\Apache Software Foundation\Tomcat 7.0\webapps\em\WEB-INF\classes\Test.txt)
     * @param data     ファイルに書き込むデータ(例：こんにちは。\n良い天気ですね。)
     * @param append   追記するかどうか
     * @return ファイル書き込みに成功したか
     */
    public static boolean writeFile(String filePath, String data, boolean append) {
        return ConvTool.writeFile(new File(filePath), data, append);
    }

    /**
     * ファイル書き込み関数
     *
     * @param file ファイルクラス
     * @param data     ファイルに書き込むデータ(例：こんにちは。\n良い天気ですね。)
     * @return ファイル書き込みに成功したか
     */
    public static boolean writeFile(File file, String data) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(data);
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * ファイル書き込み関数
     *
     * @param file ファイルクラス
     * @param data     ファイルに書き込むデータ(例：こんにちは。\n良い天気ですね。)
     * @param append   追記するかどうか
     * @return ファイル書き込みに成功したか
     */
    public static boolean writeFile(File file, String data, boolean append) {
        try {
            FileOutputStream fos = new FileOutputStream(file, append);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(data);
            bw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}