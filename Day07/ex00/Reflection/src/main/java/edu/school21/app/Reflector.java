package edu.school21.app;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Reflector {
    private final Scanner sc;

    public Reflector() {
        sc = new Scanner(System.in);
    }

    public void runReflector() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, NoSuchFieldException {
        String className = chooseClassName();
        Class myClass = returnClassInfo(className);
        Object userInstance = createObjectOfClass(myClass);
        changeOneField(myClass, userInstance);
        runOneMethod(myClass, userInstance);
    }

    public String chooseClassName() {
        System.out.println("Classes:" + "\n\t- User" + "\n\t- Car");
        System.out.println("---------------------");
        System.out.println("Enter class name:");
        String input = sc.nextLine();
        String className = "emptyClass";
        if (input.equals("User")) {
            className = "edu.school21.classes.User";
        } else if (input.equals("Car")) {
            className = "edu.school21.classes.Car";
        }
        return className;
    }
    public Class returnClassInfo(String className) throws ClassNotFoundException {
        Class myClass = Class.forName(className);
        System.out.println("---------------------");

        System.out.println("fields:");
        Field[] fields = myClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("\t\t" + field.getType().getSimpleName() + " " + field.getName());
        }

        System.out.println("methods:");
        Method[] methods = myClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("\t\t" + method.getReturnType().getSimpleName() + " " +
                    method.getName() + "(" +
                    (method.getParameterTypes().length > 0 ? Arrays.stream(method.getParameterTypes())
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(", ")) : "") + ")");
        }
        return myClass;
    }

    public Object createObjectOfClass(Class myClass)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        System.out.println("---------------------");
        System.out.println("Let's create an object.\n");
        Field[] fields = myClass.getDeclaredFields();
        Class<?>[] fieldTypes = new Class[fields.length];
        Object[] values = new Object[fields.length];

        for (int i = 0; i < fieldTypes.length; i++) {
            System.out.println(fields[i].getName() + ":");
            String input = sc.nextLine();

            if (fields[i].getType() == int.class) {
                fieldTypes[i] = int.class;
                values[i] = Integer.parseInt(input);
            } else if (fields[i].getType() == String.class) {
                fieldTypes[i] = String.class;
                values[i] = input;
            } else if (fields[i].getType() == Long.class) {
                fieldTypes[i] = Long.class;
                values[i] = Long.parseLong(input);
            } else if (fields[i].getType() == boolean.class) {
                fieldTypes[i] = Boolean.class;
                values[i] = Boolean.parseBoolean(input);
            } else if (fields[i].getType() == Double.class) {
                fieldTypes[i] = Double.class;
                values[i] = Double.parseDouble(input);
            } else {
                System.out.println("Unsupported parameter type.");
                return null;
            }
        }

        Object userInstance = myClass.getConstructor(fieldTypes)
                .newInstance(values);
        System.out.println("Object created: " + userInstance);

        return userInstance;

    }

    private void changeOneField(Class myClass, Object classInstance) throws NoSuchFieldException, IllegalAccessException {
        System.out.println("---------------------");
        System.out.println("Enter name of the field for changing:");
        String fieldName = sc.nextLine();
        Field field = myClass.getDeclaredField(fieldName);
        field.setAccessible(true);

        System.out.println("Enter " + field.getType().getSimpleName() + " value:");
        String value = sc.nextLine();

        if (field.getType() == String.class) {
            field.set(classInstance, value);
        } else if (field.getType() == int.class) {
            field.set(classInstance, Integer.parseInt(value));
        } else if (field.getType() == Double.class) {
            field.set(classInstance, Double.parseDouble(value));
        } else if (field.getType() == Long.class) {
            field.set(classInstance, Long.parseLong(value));
        } else if (field.getType() == Boolean.class) {
            field.set(classInstance, Boolean.parseBoolean(value));
        }  else {
            System.out.println("Unsupported field type.");
        }

        System.out.println("Object updated: " + classInstance);
    }

    private void runOneMethod(Class myClass, Object classInstance) throws InvocationTargetException, IllegalAccessException {
        System.out.println("---------------------");
        System.out.println("Enter name of the method for call:");
        String methodName = sc.nextLine();

        Method[] methods = myClass.getDeclaredMethods();
        Method chosenMethod = null;

        for (Method method : methods) {
            if (methodName.equals(method.getName() + "(" +
                    (method.getParameterTypes().length > 0 ? Arrays.stream(method.getParameterTypes())
                            .map(Class::getSimpleName)
                            .collect(Collectors.joining(", ")) : "") + ")")) {
                chosenMethod = method;
                break;
            }
        }

        if (chosenMethod == null) {
            System.out.println("No such method found.");
            return;
        }

        Class<?>[] parameterTypes = chosenMethod.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            System.out.println("Enter " + parameterTypes[i].getSimpleName() + " value:");
            String input = sc.nextLine();

            if (parameterTypes[i] == int.class || parameterTypes[i] == Integer.class) {
                parameters[i] = Integer.parseInt(input);
            } else if (parameterTypes[i] == String.class) {
                parameters[i] = input;
            } else if (parameterTypes[i] == Long.class) {
                parameters[i] = Long.parseLong(input);
            } else if (parameterTypes[i] == boolean.class) {
                parameters[i] = Boolean.parseBoolean(input);
            } else if (parameterTypes[i] == Double.class) {
                parameters[i] = Double.parseDouble(input);
            } else {
                System.out.println("Unsupported parameter type.");
                return;
            }
        }

        Object result = chosenMethod.invoke(classInstance, parameters);
        if (result != null) {
            System.out.println("Method returned:\n" + result);
        }
    }
    private String[] getValuesFromInput(Method method) {
        String[] values = new String[method.getParameterTypes().length];
        int i = 0;
        for (Class el : method.getParameterTypes()) {
            System.out.println("Enter " + el.getSimpleName() + " value");
            values[i++] = sc.nextLine();
        }
        return values;
    }
}
