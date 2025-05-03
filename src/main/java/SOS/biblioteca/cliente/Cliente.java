package SOS.biblioteca.cliente;

import org.springframework.beans.factory.annotation.Autowired;

public class Cliente {

    static BibliotecaService service = new BibliotecaService();

    public static void main(String[] args) {

        System.out.println("Añadir 20 empleados para las pruebas");
        String[] nombres = {
                "Ana", "Luis", "María", "Carlos", "Elena",
                "Miguel", "Sandra", "Javier", "Sara", "Pedro",
                "Paula", "Sofía", "José", "Martina", "Diego",
                "Rosa", "Fernando", "Lucía", "Andrés", "Carmen"
        };
        /* 
        for (String nombre : nombres) {
            service.postEmpleado(nombre);
        }*/
/* 
        System.out.println("#### Añadir empleado Laura ####");
        service.postEmpleado("Laura");
        System.out.println();
        System.out.println("#### Añadir empleado Laura ####");
        service.postEmpleado("Laura");
        System.out.println();
        System.out.println("#### Obtener empleado 21 ####");
        service.getEmpleado(21);
        System.out.println();
        System.out.println("#### Obtener empleado 30 ####");
        service.getEmpleado(30);
        System.out.println();
        System.out.println("#### Actualizar empleado 21  ####");
        service.putEmpleado(21, "Laura Castillo");
        System.out.println();
        System.out.println("#### Actualizar empleado 30 ####");
        service.putEmpleado(30, "Laura Castillo");
        System.out.println();
        System.out.println("#### Eliminar empleado 21 ####");
        service.deleteEmpleado(1);
        System.out.println();
        System.out.println("#### Eliminar empleado 30 ####");
        service.deleteEmpleado(30);
        System.out.println();
        System.out.println("#### Listar todos los empleados con paginación page:0 size:3 ####");
        service.getEmpleados(0, 3);
        System.out.println();
        */
    }
}
