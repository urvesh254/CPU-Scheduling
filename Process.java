public class Process {
    public static int TQ;
    public static double AvgTat;
    public static double AvgWt;
    public static String Gantt;
    public int pid;
    public String name;
    public int at;
    public int bt;
    public int wt;
    public int tat;
    public int priority;

    public Process(int pid, String name, int at, int bt, int priority) {
        this.pid = pid;
        this.name = name;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
    }

    public String toString() {
        return "Process [ Name = " + name + ", AT = " + at + ", BT = " + bt + ", Priority = " + priority + " ]";
    }
}
