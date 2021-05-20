import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;

public class RoundRobin implements CPUAlgos {
    private List<Process> list;
    private List<String> gantt;
    private Map<Integer, ArrayList<Process>> map;
    private Queue<Process> queue;
    private int tq, min;
    private double avgTat = 0;
    private double avgWt = 0;

    public RoundRobin(List<Process> list) {
        this.list = list;
        tq = Process.TQ;
        gantt = new ArrayList<>();
        map = new HashMap<>();
        queue = new LinkedList<>();
    }

    @Override
    public void schedule() {
        sort(list);
        generateListByAT(list);

        // Logic of RoundRobin
        ArrayList<Process> temp = map.get(min);
        int exit = 0;
        for (Process p : temp)
            queue.add(p);

        try {
            Process p;
            while (!queue.isEmpty()) {
                p = queue.remove();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str = "";
        for (String s : gantt)
            str += s;

        Process.AvgTat = avgTat / list.size();
        Process.AvgWt = avgWt / list.size();
        Process.Gantt = str;
    }

    private void generateListByAT(List<Process> list) {
        int i;
        Process p = list.get(0);
        map.put(p.at, new ArrayList<Process>());
        map.get(p.at).add(p);
        min = p.at;
        for (i = 1; i < list.size(); i++) {
            p = list.get(i);
            min = min < p.at ? min : p.at;
            if (p.at != list.get(i - 1).at) {
                map.put(p.at, new ArrayList<Process>());
            }
            map.get(p.at).add(p);
        }

        /* Printing the Map */
        // for (int key : map.keySet()) {
        //     System.out.println("\n" + key + " : ");
        //     map.get(key).forEach(System.out::println);
        // }
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
