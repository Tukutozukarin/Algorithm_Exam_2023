import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Ex02Test {

    Ex02 graph = null;
    Ex02.Vertex vk = new Ex02.Vertex("Voksenkollen");
    Ex02.Vertex fs = new Ex02.Vertex("Frognerseteren");
    Ex02.Vertex ms = new Ex02.Vertex("Majorstuen");
    Ex02.Vertex nt = new Ex02.Vertex("Nationalteateret");

    @BeforeTest
    public void setup() {
        graph = new Ex02();

        vk.addSki("Lysløypa i Frognerseteråsen");
        vk.addSki("Voksenkollen - Øvresetertjern");
        graph.addVertex(vk);

        fs.addSki("Lysløypa i Frognerseteråsen");
        fs.addSki("Hølet");
        graph.addVertex(fs);

        ms.addSki("Lysløypa i Danglebæråsen");
        ms.addSki("Dingle");
        graph.addVertex(ms);

        nt.addSki("Lysløypa i Tunellen");
        nt.addSki("Hølet");
        graph.addVertex(nt);

        graph.addEdge(vk, fs);
        graph.addEdge(vk, ms);
        graph.addEdge(ms, nt);
        graph.addEdge(nt, fs);
    }

    @Test
    public void shortest() {
        var path = graph.shortestPathToSki(vk, "Dingle");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }

        System.out.println("");
        path = graph.shortestPathToSki(vk, "Lysløypa i Tunellen");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }

        System.out.println("");
        path = graph.shortestPathToSki(vk, "Hølet");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }

        System.out.println("");
        path = graph.shortestPathToSki(fs, "Hølet");
        System.out.println("path:");
        for (var v : path) {
            System.out.print(" - " +v.name);
        }
    }

    @Test
    public void all() {
        var paths = graph.showAllPathsToSki(vk, "Dingle");
        System.out.println("From Voksenkollen to Dingle:");
        for (var path: paths) {
            System.out.println("path:");
            for (var v : path) {
                System.out.print(" - " +v.name);
            }
        }

        System.out.println("");
        paths = graph.showAllPathsToSki(vk, "Hølet");
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
