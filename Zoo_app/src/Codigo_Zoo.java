import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;
import weka.core.Instance;

import java.io.*;
import java.util.Scanner;

public class Codigo_Zoo {

    public static void main(String[] args) throws Exception {
        //Se realiza la carga del dataset de zoo.arff donde esta la informacion de nuestros sets
        DataSource data_original = new DataSource("src/archivos/zoo.arff");
        Instances dataset_original = data_original.getDataSet();
        //Seleccionamos la clase del atributo principal a buscar
        dataset_original.setClass(dataset_original.attribute("type"));
        //Marcamos los atributos a descartar, la lista original es de 17 atributos, por lo que descartamos el que estara en la pocision
        //0 Que es el nombre del animal y la pocision 16 que es directamente el tipo del animal
        int [] atributos_a_descartar = new int[]{0,16};

       
        //Removemos manualmente estos atributos que seleccionamos anteriormente para descartarlos
        Remove descartar = new Remove();
        descartar.setAttributeIndicesArray(atributos_a_descartar);
        descartar.setInputFormat(dataset_original);



        //La instancia con nuestra data es el resultado de filtrar el dataset original con los datos a descartar
        Instances data = Filter.useFilter(dataset_original, descartar);

        //Seleccionamos el clasificador que vamos a utilizar para el modelo, en este caso vamos a utilizar NaivesBayes
        NaiveBayes clasificador_bayes = new NaiveBayes();
        clasificador_bayes.buildClassifier(data);

        //Creamos el modelo de evaluacion una vez se creo el clasificador
        Evaluation evaluacion = new Evaluation(data);
        evaluacion.evaluateModel(clasificador_bayes, data);

        //Mostramos en consola los resultados de evaluar el modelo con los datos del dataset
        System.out.println(evaluacion.toSummaryString("\n///////////////// Resultados //////////////////\n", false));

        //Llamamos a la funcion entrada para solicitar los datos de la nueva instancia
        String nueva_instancia = entrada();

        //Una vez preparada la nueva instancia , cargamos dichos datos en otro archivo arff
        File file = new File("src/archivos/zoo_auxiliar.arff");
        FileWriter escritura = new FileWriter(file, true);
        escritura.write(nueva_instancia);
        escritura.close();

        //Cargamos nuevamente el dataset con la nueva instancia agregada 
        DataSource data_nueva_instancia = new DataSource("src/archivos/zoo_auxiliar.arff");

        //Obtenemos el dataset de la nueva instancia que acabamos de crear y adicionamos el mismo al dataset completo con el resto de las instancias
        Instances nuevo_dataset = data_nueva_instancia.getDataSet();
        data.add(nuevo_dataset.get(0));

        //Mostramos los resultados de la ejecucion
        System.out.println(data.toSummaryString());

        //Realizamos la evaluacion del modelo
        Evaluation evaluation = new Evaluation(data);
        evaluation.evaluateModel(clasificador_bayes, data);

        //Mostramos los resultados de la evaluacion
        System.out.println(evaluation.toSummaryString("\n////////////////////////Results//////////////////////\n", false));

        //Obtenemos el resultado final que contiene la prediccion de nuestra nueva instancia
        int resultado_final = (int) clasificador_bayes.classifyInstance(data.lastInstance());
        //Funcion para remover la instancia anterior de nuestro archivo auxiliar
        Remover(nueva_instancia);
        //Funcion para mostrar el resultado final en pantalla
        salida_final(resultado_final);

    }

    //Funcion para mostrar el resultado obtenido tras realizar la prediccion
    public static void salida_final(int a){
        int resultado_final = a;
        if (resultado_final == 0){

            System.out.println("Es un mamifero");

        }else if (resultado_final == 1){

            System.out.println("Es un ave");

        }else if (resultado_final == 2){

            System.out.println("Es un reptil");

        }else if (resultado_final == 3){

            System.out.println("Es un anfibio");

        }else if (resultado_final == 4){

            System.out.println("Es un insecto");

        }else if (resultado_final == 5){

            System.out.println("Es un invertebrado");

        }
    }

    //Funcion para capturar los atributos de la nueva instancia
    public static String entrada(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Introduzca true or false si el animal tiene pelo");
        String hair = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal tiene plumas");
        String feathers = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal pone huevos");
        String eggs = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal da leche");
        String milk = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal vuela");
        String airborne = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal es acuatico");
        String aquatic = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal es un depredador");
        String predator = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal tiene dientes");
        String toothed = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal es vertebrado");
        String backbone = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal respira");
        String breathes = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal es venenoso");
        String venomous = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal tiene aletas");
        String fins = entrada.nextLine();

        System.out.println("Introduzca la cantidad de patas que tiene el animal (0,2,4,5,6,8)");
        String legs = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal tiene cola");
        String tail = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal es domestico");
        String domestic = entrada.nextLine();

        System.out.println("Introduzca true or false si el animal tiene tamaño de gato");
        String catsize = entrada.nextLine();

        String nueva_instancia = hair+","+feathers+","+eggs+","+milk+","+airborne+","+aquatic+","+predator+","+toothed+","+backbone+","+breathes+","+venomous+","+fins+","+legs+","+tail+","+domestic+","+catsize;
        System.out.println(nueva_instancia);

        return nueva_instancia;
    }

    public static void Remover(String last) throws IOException {
        //Abrimos el archivo para su edicion  
        File zoo_auxiliar = new File("src/archivos/zoo_auxiliar.arff");

        //Leemos a traves del buffer el contenido del archivo auxiliar
        BufferedReader entrada = new BufferedReader(new FileReader("src/archivos/zoo_auxiliar.arff"));
        String line = null;
        PrintWriter print_writer = new PrintWriter(new FileWriter(zoo_auxiliar));


        //Removemos con un trim los ultimos caracteres y procedemos a cerrar el input y el archivo
        while ((line = entrada.readLine()) != null) {
            if (!line.trim().equals(last)) {
                print_writer.println(line);
                print_writer.flush();
            }
        }
        print_writer.close();
        entrada.close();


        //Escribimos el contenido de la plantilla de atributos (Con los atributos a descartar) dentro de nuestro archivo auxiliar
        FileWriter escritura = new FileWriter(zoo_auxiliar, true);
        escritura.write("@relation flags\n" +
                "\n" +
                "@ATTRIBUTE hair {false, true}\n" +
                "@ATTRIBUTE feathers {false, true}\n" +
                "@ATTRIBUTE eggs {false, true}\n" +
                "@ATTRIBUTE milk {false, true}\n" +
                "@ATTRIBUTE airborne {false, true}\n" +
                "@ATTRIBUTE aquatic {false, true}\n" +
                "@ATTRIBUTE predator {false, true}\n" +
                "@ATTRIBUTE toothed {false, true}\n" +
                "@ATTRIBUTE backbone {false, true}\n" +
                "@ATTRIBUTE breathes {false, true}\n" +
                "@ATTRIBUTE venomous {false, true}\n" +
                "@ATTRIBUTE fins {false, true}\n" +
                "@ATTRIBUTE legs INTEGER [0,9]\n" +
                "@ATTRIBUTE tail {false, true}\n" +
                "@ATTRIBUTE domestic {false, true}\n" +
                "@ATTRIBUTE catsize {false, true}\n" +
                "\n" +
                "@DATA\n");
        escritura.close();
    }

}
