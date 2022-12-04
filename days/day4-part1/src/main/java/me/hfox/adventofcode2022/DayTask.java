package me.hfox.adventofcode2022;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayTask {

    private final List<String> lines;

    public DayTask(List<String> lines) {
        this.lines = lines;
    }

    @SuppressWarnings("unchecked")
    public Object run() {
        return lines.stream().map(line -> {
            Set<Integer>[] sets = new Set[2];
            String[] assignments = line.split(",");
            for (int i = 0; i < 2; i++) {
                String[] assignment = assignments[i].split("-");
                int min = Integer.parseInt(assignment[0]);
                int max = Integer.parseInt(assignment[1]);

                Set<Integer> set = new HashSet<>();
                for (int j = min; j <= max; j++) {
                    set.add(j);
                }

                sets[i] = set;
            }

            return sets;
        }).map(sets -> containsAll(sets[0], sets[1]) || containsAll(sets[1], sets[0])).filter(b -> b).count();
    }

    public boolean containsAll(Set<Integer> a, Set<Integer> b) {
        Set<Integer> set = new HashSet<>(a);
        set.retainAll(b);
        return set.size() == b.size();
    }

}
