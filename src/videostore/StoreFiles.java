/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package videostore;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Carlos Gochez
 */
public class StoreFiles {
    private RandomAccessFile ram;
    private int precioxEstreno;
    private int precioxNormal;
    public static Scanner lea = new Scanner(System.in);

    public StoreFiles(int pe,int pn){
        this.precioxEstreno = pe;
        this.precioxNormal = pn;
        lea.useDelimiter(System.getProperty("line.separator"));

        try {
            ram = new RandomAccessFile("dvds.vid", "rw");
        } catch (FileNotFoundException ex) {
            System.out.println("Error de Disco: " + ex.getMessage());
        }
    }

    private int getCodigoDvd(){
        try{
            RandomAccessFile rcod = new RandomAccessFile("codigos.vid","rw");
            int codigo;

            if( rcod.length() == 0 ){
                codigo = 1;
            }
            else{
                codigo = rcod.readInt();
            }

            rcod.seek(0);
            rcod.writeInt(codigo + 1);
            rcod.close();


            return codigo;
        }
        catch(Exception e){
            return -1;
        }
    }

    public boolean addDvd(){
        try{
            //irnos hasta el final
            ram.seek(ram.length());

            int codigo = this.getCodigoDvd();

            if( codigo != -1 ){
                System.out.print("Ingrese el Nombre del DVD: ");
                String n = lea.next();

                System.out.print("Duracion del DVD: ");
                double duracion = lea.nextDouble();

                System.out.print("Cantidad de Copias: ");
                int copias = lea.nextInt();

                //ha escribir se ha dicho
                ram.writeInt(codigo);
                ram.writeUTF(n);
                ram.writeDouble(duracion);
                ram.writeInt(copias);
                ram.writeBoolean(true);

                return true;
            }
            return false;
        }
        catch(Exception e){
            return false;
        }
    }

    public void verDvdsDisponibles(){
        try{
            ram.seek(0);
            System.out.println("LISTADO DE DVDS DISPONIBLES");
            System.out.println("----------------------------\n");

            while( ram.getFilePointer() < ram.length() ){
                int codigo = ram.readInt();
                String n = ram.readUTF();
                double dur = ram.readDouble();
                int copias = ram.readInt();
                boolean estreno = ram.readBoolean();

                if( copias > 0 ){
                    System.out.println(codigo + " - " +
                            n + " - " + dur + " - " +
                            copias + " - " +
                            (estreno ? "Estreno" : "Normal"));
                }
            }

        }
        catch(Exception e){

        }
    }
}
