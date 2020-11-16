import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PriorityNP implements CPUAlgos {
    List<Process> list;
    List<String> gantt;
    boolean[] visited;
    private double avgTat = 0;
    private double avgWt = 0;

    public PriorityNP(List<Process> list) {
        this.list = list;
        visited = new boolean[list.size()];
        gantt = new ArrayList<>();
        System.out.println("Priority Non Pre-emtive Algorithm.");
    }

    @Override
    public void schedule() {
        // Sorting according to the Arival Time.
        sort(list);

        for (Process p : list) {
            System.out.println(p);
        }

        // SJF Logic
        Process temp = list.get(0);
        Process mp;
        int exit = temp.bt + temp.at;
        int max;

        temp.tat = exit;
        avgTat += temp.tat;
        gantt.add(temp.at + " [ " + temp.name + " ] " + exit);

        for (int i = 1; i < list.size(); i++) {
            if (visited[i])
                continue;
            mp = list.get(i);
            max = i;
            if (exit < mp.at) {
                exit = mp.at;
                gantt.add(" [ IDEAL ] " + exit);
            }
            for (int j = 1; j < list.size(); j++) {
                temp = list.get(j);
                if (!visited[j] && exit >= temp.at
                        && (mp.priority < temp.priority || (mp.priority == temp.priority && mp.at > temp.at))) {
                    mp = temp;
                    max = j;
                }
            }
            visited[max] = true;
            exit += mp.bt;
            mp.tat = exit - mp.at;
            avgTat += mp.tat;
            mp.wt = mp.tat - mp.bt;
            avgWt += mp.wt;
            gantt.add(" [ " + mp.name + " ] " + exit);
            if (max != i)
                i--;
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
                int diff = a.at - b.at;
                if (diff == 0)
                    return b.priority - a.priority;
                else
                    return diff;
            }
        };

        Collections.sort(list, com);
    }
}
