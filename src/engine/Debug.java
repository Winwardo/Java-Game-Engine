package engine;

public class Debug {
	
	public final static int dbgGENERIC = 0;
	public final static int dbgMOUSEPOS = 1;
	
	final static boolean isDebug = true;
	
	public static boolean[] debugs = new boolean[2];
	
	public static void setDebugs() {
		debugs[dbgGENERIC] = true;
		debugs[dbgMOUSEPOS] = false;
	}
	
	static public void debugPrintln(String input, int msgType) {
		if(isDebug) {
			if(debugs[msgType]) {
				System.out.println(input);
			}
		}
	}
	
}
