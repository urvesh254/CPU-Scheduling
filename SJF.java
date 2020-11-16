import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SJF implements CPUAlgos {
    List<Process> list;
    List<String> gantt;
    boolean[] visited;
    private double avgTat = 0;
    private double avgWt = 0;

    public SJF(List<Process> list) {
        this.list = list;
        visited = new boolean[list.size()];
        gantt = new ArrayList<>();
        System.out.println("SJF Algorithm.");
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
        Process mb;
        int exit = temp.bt + temp.at;
        int min;

        temp.tat = exit;
        avgTat += temp.tat;
        gantt.add(temp.at + " [ " + temp.name + " ] " + exit);

        for (int i = 1; i < list.size(); i++) {
            if (visited[i])
                continue;
            mb = list.get(i);
            min = i;
            if (exit < mb.at) {
                exit = mb.at;
                gantt.add(" [ IDEAL ] " + exit);
            }
            for (int j = 1; j < list.size(); j++) {
                temp = list.get(j);
                if (!visited[j] && exit >= temp.at && (mb.bt > temp.bt || (mb.bt == temp.bt && mb.at > temp.at))) {
                    mb = temp;
                    min = j;
                }
            }
            visited[min] = true;
            exit += mb.bt;
            mb.tat = exit - mb.at;
            avgTat += mb.tat;
            mb.wt = mb.tat - mb.bt;
            avgWt += mb.wt;
            gantt.add(" [ " + mb.name + " ] " + exit);
            if (min != i)
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
                    return a.bt - b.bt;
                else
                    return diff;
            }
        };

        Collections.sort(list, com);
    }
}
