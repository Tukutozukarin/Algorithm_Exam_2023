import java.util.*;

// inspired and edited from undirectedgraph repository
// https://github.com/bogdanmarculescu/algorithms/blob/master/lessons/src/main/java/org/pg4200/les09/UndirectedGraph.java
public class Ex02 {
    protected Map<Vertex, Set<Vertex>> graph = new HashMap<>();

    public static class Vertex {

        String name;
        public Vertex(String name) {
            this.name = name;
        }
        List<String> skis = new ArrayList<>();
        public void addSki(String ski) {
            skis.add(ski);
        }
        public boolean containsSkis(String ski) {
            return skis.contains(ski);
        }
    }

    public List<Vertex> shortestPathToSki (Vertex start, String end) {
        return findPathBFS(start, end);
    }

    public List<List<Vertex>> showAllPathsToSki (Vertex start, String end) {

        return findPathDFS(start, end);
    }




    public void addVertex(Vertex vertex) {
        Objects.requireNonNull(vertex);

        graph.putIfAbsent(vertex, new HashSet<>());
    }


    public void addEdge(Vertex from, Vertex to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);

        addVertex(from);
        addVertex(to);

        graph.get(from).add(to);

        if(! from.equals(to)) {
            //ie, if not a self-loop
            graph.get(to).add(from);
        }
    }


    public int getNumberOfVertices() {
        return graph.size();
    }


    public int getNumberOfEdges() {
        long edges =  graph.values().stream()
                .mapToInt(s -> s.size())
                .sum();

        /*
            As each edge is represented by 2 connections,
            we need to divide by 2.
            Ie, if edge X-Y, we have 1 connection from
            X to Y, and 1 from Y to X.

            However, we also have to consider self-loops X-X
         */

        edges += graph.entrySet().stream()
                .filter(e -> e.getValue().contains(e.getKey()))
                .count();

        return (int) edges / 2;
    }






    public Collection<Vertex> getAdjacents(Vertex vertex) {

        Objects.requireNonNull(vertex);

        return graph.get(vertex);
    }


    // changed end from vertex to string
    public List<List<Vertex>> findPathDFS(Vertex start, String end) {

        if(! graph.containsKey(start) ) {
            return null;
        }

        Set<Vertex> alreadyVisited = new HashSet<>();

        /*
            Java API has a Stack implementation, but it should not
            be used, as based on deprecated Vector class.
            To represent a stack, we need to use Deque (double ended queue).
            See the JavaDocs, eg
            https://docs.oracle.com/javase/9/docs/api/java/util/Deque.html
         */
        Deque<Vertex> stack = new ArrayDeque<>();

        List<List<Vertex>> paths = new ArrayList<>();


        boolean foundPath = true;

        do {
            dfs(alreadyVisited, stack, start, end);

            if (isPathTo(stack, end)) {
                List<Vertex> path = new ArrayList<>(stack);
                Collections.reverse(path);
                // clear the stack and add path to paths, so we can find the next path
                stack.clear();
                paths.add(path);
            }
            else {
                foundPath = false;
            }
        }
        while (foundPath);

        return paths;

    }

    // Changed end from vertex to string
    private void dfs(Set<Vertex> alreadyVisited, Deque<Vertex> stack, Vertex current, String end){
        alreadyVisited.add(current);
        stack.push(current);

        if(isPathTo(stack, end)){
            return;
        }

        for(Vertex connected : getAdjacents(current)){
            if(alreadyVisited.contains(connected)){
                /*
                    If we have already analysed a vertex,
                    no point in re-analyzing it again.
                 */
                continue;
            }

            dfs(alreadyVisited, stack, connected, end);

            if(! isPathTo(stack, end)){
                //backtrack
                stack.pop();
            } else {
                return;
            }
        }
    }

    // changed from vertex to string
    protected boolean isPathTo(Deque<Vertex> stack, String ski){
        return !stack.isEmpty() && stack.peek().containsSkis(ski); // changed from equals to containsSkis(ski)
    }


    // changed end to be string
    public List<Vertex> findPathBFS(Vertex start, String end) {

        if (!graph.containsKey(start) ) {
            // start is not in graph, dont search
            return null;
        }

        // if start has "end" ski path, return start as list.
        if (start.containsSkis(end)) {
            return Arrays.asList(start);
        }

        Queue<Vertex> queue = new ArrayDeque<>();
        Map<Vertex,Vertex> bestParent = new HashMap<>();

        queue.add(start);

        Vertex found = null;
        mainLoop: while(! queue.isEmpty()){

            Vertex parent = queue.poll();

            for(Vertex child : graph.get(parent)){

                if( child.equals(start) || bestParent.get(child) != null){
                    continue;
                }
                bestParent.put(child, parent);

                // instead of child equals end, do child.containsSkis(end)
                if (child.containsSkis(end)) {
                    /*
                        found a path, no need to analyze
                        the rest of the queue
                     */
                    // Since end is not a vertex, need to remember the ending vertex:
                    found = child;

                    break mainLoop;
                }

                queue.add(child);
            }
        }

        // If found is null, we did not find a path
        if(found == null){
            return null;
        }

        /*
            At this point, we know that there is a path.
            So, starting from "end", we need to use the
            bestParent map to backtrack the path from "end"
            to "start"
         */

        List<Vertex> path = new ArrayList<>();
        Vertex current = found; // set current to the last vertex in the path
        while (current != null){
            path.add(0, current);
            current = bestParent.get(current);
        }

        return path;

    }

    public Set<Vertex> findConnected(Vertex vertex) {

        if(! graph.containsKey(vertex)){
            return null;
        }

        Set<Vertex> connected = new HashSet<>();
        findConnected(connected, vertex);

        return connected;
    }


    public Set<Vertex> findConnected(Iterable<Vertex> vertices) {

        Set<Vertex> connected = new HashSet<>();
        if(vertices==null){
            return connected;
        }

        for(Vertex vertex : vertices) {
            if(! graph.containsKey(vertex)){
                continue;
            }
            findConnected(connected, vertex);
        }

        return connected;
    }

    private void findConnected(Set<Vertex> connected, Vertex vertex){
        if(connected.contains(vertex)){
            return;
        }

        connected.add(vertex);

        /*
            Look and add all connected vertices.
            Then, reapply this code recursively on
            all these connected vertices.
         */

        graph.get(vertex).stream()
                .filter(c -> ! connected.contains(c))
                .forEach(c -> findConnected(connected, c));
    }




    static Ex02 test = null;
    static Vertex vk = new Vertex("Voksenkollen");
    static Vertex fs = new Vertex("Frognerseteren");
    static Vertex ms = new Vertex("Majorstuen");
    static Vertex nt = new Vertex("Nationalteateret");

    public static void main(String[] args) {

        setup();
        shortest();
        all();
    }




    public static void setup() {
        test = new Ex02();

        vk.addSki("Lysløypa i Frognerseteråsen");
        vk.addSki("Voksenkollen - Øvresetertjern");
        test.addVertex(vk);

        fs.addSki("Lysløypa i Frognerseteråsen");
        fs.addSki("Hølet");
        test.addVertex(fs);

        ms.addSki("Lysløypa i Danglebæråsen");
        ms.addSki("Dingle");
        test.addVertex(ms);

        nt.addSki("Lysløypa i Tunellen");
        nt.addSki("Hølet");
        test.addVertex(nt);

        test.addEdge(vk, fs);
        test.addEdge(vk, ms);
        test.addEdge(ms, nt);
        test.addEdge(nt, fs);
    }

    public static void shortest() {
        var path = test.shortestPathToSki(vk, "Dingle");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }

        System.out.println("");
        path = test.shortestPathToSki(vk, "Lysløypa i Tunellen");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }

        System.out.println("");
        path = test.shortestPathToSki(vk, "Hølet");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }

        System.out.println("");
        path = test.shortestPathToSki(fs, "Hølet");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }
    }

    public static void all() {
        var paths = test.showAllPathsToSki(vk, "Dingle");
        System.out.println("From Voksenkollen to Dingle:");
        for (var path: paths) {
            System.out.println("path:");
            for (var v : path) {
                System.out.print(" - " +v.name);
            }
        }

        System.out.println("");
        paths = test.showAllPathsToSki(vk, "Hølet");
        System.out.println("From Voksenkollen to Hølet:");
        for (var path: paths) {
            System.out.println("path:");
            for (var v : path) {
                System.out.print(" - " +v.name);
            }
            System.out.println("");
        }


    }
}
