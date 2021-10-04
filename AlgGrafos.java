import java.util.Scanner;

public class AlgGrafos {
    public static void main(String[] args) {
        Graph g1 = new Graph();
        g1.open_text("myfiles/grafo01.txt");

        Graph g2 = new Graph();
        g2.open_text("myfiles/grafo02.txt");

        Graph g3 = new Graph();
        g3.open_text("myfiles/grafo03.txt");

        Graph g4 = new Graph();
        g4.open_text("myfiles/grafo04.txt");

        Graph[] graphs = new Graph[]{g1, g2, g3, g4};

        Scanner scanner = new Scanner(System.in);

        System.out.println("Grafo 1: " + g1 + "\n");
        g1.isP4sparse();

        while (true) {
            System.out.println("--------------------------------------------------------------------------");
            System.out.println("Cada opção printa o grafo correspondente e diz se ele é ou não P4-esparso.");
            System.out.println("0. Sair");
            System.out.println("1. Testar grafo 1 novamente");
            System.out.println("2. Testar grafo 2");
            System.out.println("3. Testar grafo 3");
            System.out.println("4. Testar grafo 4");
            System.out.println("--------------------------------------------------------------------------");

            int escolha = scanner.nextInt();

            if (escolha == 0) {
                break;
            }
            else {
                Graph g = graphs[escolha-1];

                System.out.println(g);
                System.out.println();
                g.isP4sparse();
            }
        }
    }
}
