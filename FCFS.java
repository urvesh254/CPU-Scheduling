import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FCFS implements CPUAlgos {
    List<Process> list;
    List<String> gantt;
    private double avgTat = 0;
    private double avgWt = 0;

    public FCFS(List<Process> list) {
        this.list = list;
        gantt = new ArrayList<>();
        System.out.println("FCFS Algorithm.");
    }

    @Override
    public void schedule() {
        // Sorting according to the Arrival Time.
        sort(list);

        // FCFS Logic   
        Process p = list.get(0);
        int exit = p.at;
        gantt.add(p.at + "");
        for (int i = 0; i < list.size(); i++) {
            p = list.get(i);
            if (exit < p.at) {
                exit = p.at;
                gantt.add(" [ IDEAL ] " + exit);
            }
            exit += p.bt;
            p.tat = exit - p.at;
            avgTat += p.tat;
            p.wt = p.tat - p.bt;
            avgWt += p.wt;
            gantt.add(" [ " + p.name + " ] " + exit);
        }

        String str = "";
        for (String s : gantt)
            str += s;

        Process.AvgTat = avgTat / list.size();
        Process.AvgWt = avgWt / list.size();
        Process.Gantt = str;
    }

    private void sort(List<Process> list) {
        Comparator<Process> com = new Comparator<>() {
            public int compare(Process a, Process b) {
                return a.at - b.at;
            }
        };

        Collections.sort(list, com);
    }
}
