import clustering.linkage.impls.AverageLinkageStrategy;
import clustering.linkage.interfaces.LinkageStrategy;
import clustering.models.Cluster;
import clustering.process.strategy.impls.DefaultClusteringAlgorithm;
import clustering.process.strategy.interfaces.ClusteringAlgorithm;
import graphs.*;
import parsers.Jdt;
import parsers.Spoon;

import java.awt.*;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    static String  projectSourcePath="resources/TicTacToe-Game/src";

    public static void main(String[] args) throws IOException {
        Scanner sc= new Scanner(System.in);
        System.out.println("\nMenu : ");
        System.out.println("1 : JDT Test.");
        System.out.println("2 : Spoon Test.");
        System.out.println("0 : Exit.");

        System.out.print("What do you choose : ");
        int input = sc.nextInt();

        while(input > 2 || input < 0 ){
            System.out.print("Wrong input, choose again : ");
            input = sc.nextInt();
        }

        if (input==0){
            System.exit(0);
        }
        if (input==1){
            System.out.println("JDT Call Graph");
            Graph jdtGraph = new JDTCallGraph(new Jdt(projectSourcePath)).createCallGraph();
            System.out.println(jdtGraph.printInvocatins());

            CouplingGraphTools couplingGraphTools = new CouplingGraphTools(jdtGraph);
            couplingGraphTools.calculateMetrics();
            CouplingGraph couplingGraph = couplingGraphTools.getCouplingGraph();
            System.out.println(couplingGraph.toString());

        }
        else if (input==2){
            System.out.println("Spoon Call Graph");
            Graph spoonGraph = new SpoonCallGraph(new Spoon(projectSourcePath)).createCallGraph();
            System.out.println(spoonGraph.printInvocatins());

            CouplingGraphTools couplingGraphTools = new CouplingGraphTools(spoonGraph);
            couplingGraphTools.calculateMetrics();
            CouplingGraph couplingGraph = couplingGraphTools.getCouplingGraph();
            System.out.println(couplingGraph.toString());
        }
    }

    private static java.util.List<Cluster> selection_cluster(Cluster dendgr) {

        List<Cluster> R = new ArrayList<>();

        Stack<Cluster> parcoursCluster = new Stack<>();

        parcoursCluster.push(dendgr);

        while (!parcoursCluster.isEmpty()) {

            Cluster parent = parcoursCluster.pop();

            Cluster cl1 = parent.getChildren().get(0);
            Cluster cl2 = parent.getChildren().get(1);

            if (cl1 == null || cl2 == null) {
                R.add(parent);
                continue;
            }

            if ( S(parent) > avg( S(cl1) , S(cl2) ) ) {
                R.add(parent);
            } else {
                parcoursCluster.push(cl1);
                parcoursCluster.push(cl2);
            }
        }
        return R;

    }

    private static Double S(Cluster parent) {
        return parent.getDistanceValue();
    }

    private static Double avg(double value1, double value2) {
        return ( value1 + value1 ) / 2 ;
    }

}
