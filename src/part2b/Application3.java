package part2b;

import java.util.*;

import part1ab.Truck;
import support.TimeLimit;

public class Application3 extends TimeLimit {
    /**
     * handles the 3b problem, but on a smaller scale since computing for the
     * whole cargo is too time intensive
     */
    Pento3DTruck mostValuableTruck = new Pento3DTruck(); // the diamond operator

    public Truck getMostValuableTruck(){
        Truck mostValuableTruck = new Truck();
        double[][][] mostValuableCargoSpace = this.mostValuableTruck.getCargoSpace();
        double[][][] cargoSpace = new double[5][8][33];
        for (int i = 0; i <5; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 33; k++){
                    cargoSpace[i][j][k] = mostValuableCargoSpace[i][j][k%3];
                    cargoSpace[i][j+4][k] = mostValuableCargoSpace[i][j][k%3];
                }
            }
            mostValuableTruck.setCargoSpace(cargoSpace);
        }
        System.out.println(cargoSpace[1][1].length);
        mostValuableTruck.setCargoSpace(cargoSpace);
        mostValuableTruck.printCargoSpace();
        mostValuableTruck.truckValue();
        return mostValuableTruck;
    }
    /**
     fills the space of truck as much as possible
     but optimizing it's route with placing algorithm
     @param truck
     */
    public boolean fillTruck(Pento3DTruck truck){
        System.out.println("here");
        startTime = System.currentTimeMillis();
        totalCombinations++;
        ArrayList<double[][][]> items = new Pento3D().getGreedyPents3D();
        for (double[][][] nextItem : items){
            Pento3DTruck newTruck = new Pento3DTruck();
            newTruck.setCargoSpace(truck.getCargoSpace());
            fillTruck(truck,nextItem);
        }
        return false;
    }
    /**
     * continues the recursion
     * @param truck
     * @param item
     * @return recursive call with next item
     */
    public boolean fillTruck(Pento3DTruck truck, double[][][] item){
        if(truck.isFull()) {
            return true;
        }
        else if (checkLimits()){
            System.out.println("here");
            return true;
        }
        else if (!truck.doesItemFit(item)) {
            return false;
        }
        else{
            if (truck.truckValue() > mostValuableTruck.truckValue() ){
                mostValuableTruck.setCargoSpace(truck.getCargoSpace());
            }
            ArrayList<double[][][]> items = new Pento3D().getGreedyPents3D();
            for (double[][][] nextItem : items) {
                Pento3DTruck truck2 = new Pento3DTruck();
                truck2.setCargoSpace(truck.getCargoSpace());
                truck2.placeItem(item);
                if(fillTruck(truck2,nextItem)){
                    return true;
                }
            }
            return false;
        }
    }
}
