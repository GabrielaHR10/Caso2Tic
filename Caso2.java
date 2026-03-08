import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Caso2 {

    public static void main(String[] args) throws IOException {
        if (args.length != 6) {
            System.err.println("Debes pasar los parámetros de la guía.");//esto francamente debería ser más exacto
            System.exit(1);
        }

        int nf1 = Integer.parseInt(args[0]);
        int nc1 = Integer.parseInt(args[1]);
        int nf2 = Integer.parseInt(args[2]);
        int nc2 = Integer.parseInt(args[3]);
        int tp = Integer.parseInt(args[4]);
        String nombreArchivo = args[5];

        long dirM1 = 0L;  //asumimos que las direcciones lógicas empiezan desde 0.
        long dirM2 = (long) nf1 * nc1 * 4; //direccion de inicio de la segunda matriz.
        long dirM3 = dirM2 + (long) nf2 * nc2 * 4;//direccion de inicio de la tercera matriz.

        //Calculamos el número de páginas virtuales que se necesitan para almacenar las tres matrices.
        long bytesT = dirM3 + (long) nf1 * nc2 * 4; 
        long np = (long) Math.ceil((double) bytesT / tp); //esto es basicamente la función techo para que el número de páginas sea 
        // un entero y si es decimal redondee siempre para arriba y no para abajo.

        
        // el primer término corresponde a los 3 fors anidados que por cada ciclo accede a 2 direcciones virtuales.
        // el segundo término se refiere a los 2 fors anidados que construyen la terceramatriz, accediendo a
        // una dirección virtual por cada elemento de la matriz. 

        long nr = (long) nf1 * nc2 * nc1 * 2L + (long) nf1 * nc2;

        try (BufferedWriter b = new BufferedWriter(new FileWriter(nombreArchivo))) {

                b.write("TP="  + tp); 
                b.newLine();
                b.write("NF1=" + nf1);
                b.newLine();
                b.write("NC1=" + nc1);
                b.newLine();
                b.write("NF2=" + nf2);
                b.newLine();
                b.write("NC2=" + nc2);
                b.newLine();
                b.write("NR="  + nr);
                b.newLine();
                b.write("NP="  + np);
                b.newLine();


            for (int i = 0; i < nf1; i++) {
                for (int j = 0; j < nc2; j++) {
                    for (int k = 0; k < nc1; k++) {

                        
                        //DIRECCION POR ELEMENTO DE LA PRIMERA MATRIZ.

                        long dir1 = dirM1 + ((long) i * nc1 + k) * 4;
                        long pag1 = dir1 / tp;
                        long offset1 = dir1 % tp;
                        b.write("[M1-" + i + "-" + k + "]," + pag1 + "," + offset1);
                        b.newLine();

                        //DIRECCION POR ELEMENTO DE LA SEGUNDA MATRIZ.
                        long dir2 = dirM2 + ((long) k * nc2 + j) * 4;
                        long pag2 = dir2 / tp;
                        long offset2 = dir2 % tp;
                        b.write("[M2-" + k + "-" + j + "]," + pag2 + "," + offset2);
                        b.newLine();
                    }

                    //DIRECCION POR ELEMENTO DE LA TERCERA MATRIZ.
                    long dir3 = dirM3 + ((long) i * nc2 + j) * 4;
                    long pag3 = dir3 / tp;
                    long offset3 = dir3 % tp;
                    b.write("[M3-" + i + "-" + j + "]," + pag3 + "," + offset3);
                    b.newLine();
                }
            }

            System.out.println("Archivo generado exitosamente: " + nombreArchivo);
        
        }


    
}

}

