package dyehard.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * The Class ClassReflector.
 * 
 * @purpose	This is the primary class that reflects and validates a class given 
 * 			a set of Constructors and Methods.
 * 
 * 			General flow: 
 * 
 * 			1. Other class calls ClassReflector's constructor,
 * 			providing the reflected class' name. 
 * 
 * 			2. Then the other class will call ClassReflector's reflect() 
 * 			to fill in ClassReflector's HashMaps, so ClassReflector will be 
 * 			populated with the other class's constructors and methods.
 * 
 * 			3. Given an array of strings, ClassReflector's validate() can
 * 			determine how alike ClassReflector's constructors and methods are
 * 			to the array of strings. 
 * 
 * @see		StudentObjectManager.java
 */
public class ClassReflector {
    
    /** The HashMap of constructors. */
    private final HashMap<String, Constructor<?>> constructors = new HashMap<String, Constructor<?>>();
    
    /** The HashMap of methods. */
    private final HashMap<String, Method> methods = new HashMap<String, Method>();
    
    /** The HashMap of fields. */
    private final HashMap<String, Field> fields = new HashMap<String, Field>();

    /** The class name. */
    private final String className;
    
    /** The reflected status. */
    private boolean reflected;
    
    /** The validated status. */
    private boolean validated;

    /**
     * @purpose	Instantiates a new class reflector.
     * @param 	target the target
     */
    public ClassReflector(String target) {
        reflected = false;
        validated = false;
        className = target;
    }

    /**
     * @purpose	Get the class' constructors, fields, and methods. Store it within
     * 			ClassReflector's hashmaps
     * @return 	true, if successful. False if class is invalid/null.
     */
    public boolean reflect() {
    	try {	
    		System.out.println("Populating hashmap with constructors and methods");
        	// Set the class name
            Class<?> reflectedClass = Class.forName(className);
            
            // System.out.format("Class:%n  %s%n%n", c.getCanonicalName());
            // Package p = c.getPackage();
            // System.out.format("Package:%n  %s%n%n", (p != null ? p.getName()
            // : "-- No Package --"));

            int i = 0;
            
            for (Field field : reflectedClass.getDeclaredFields()){
            	fields.put(field.getName(), field);
            }
            
            // Populate the HashMap with the constructors
            for (Constructor<?> constructor : reflectedClass.getDeclaredConstructors()) {
                String temp = constructor.getName() + Integer.toString(i);
                constructors.put(temp, constructor);
                i++;
                
                // Print out for debug
                // System.out.println(constructor.getName() + "   " +
                // constructor);
            }
            
            // Populate the HashMap with the methods
            for (Method method : reflectedClass.getDeclaredMethods()) {
                methods.put(method.getName(), method);
                
                // Print out for debug
                //System.out.println(method.getName() + "   "
                //  + method.toGenericString());
            }

        } catch (ClassNotFoundException x) {
            System.out.println(x.getMessage());
            x.printStackTrace();
            return false;
        }
        reflected = true;		// set flag to reflected
        System.out.println("------------ End reflect() ------------");
        return true;
    }

    /**
     * @purpose To validate the class. Checks to see if it has the appropriate
     * 			constructions and fields.
     *
     * @param 	constructsArr the array of constructors passed by StudentObjectManager
     * @param 	methodsArr the array of methods passed by StudentObjectManager
     * @return 	true, if the constructors and methods are valid.
     */
    public boolean validate(String[] constructsArr, String[] methodsArr) {
        // must reflect first
        if (!reflected) {
        	System.out.println("Must reflect first");
            return false;
        }
        
        int matchingConstructors = 0;
        
        // For each string within the given array of constructors
        for (String constructToTest : constructsArr) {
            matchingConstructors = 0;
            
            // For each constructor within the hashmap
            for (Constructor<?> hashmapConstructor : constructors.values()) {
            	
            	// if the given constructor matches the current hashmap
            	// constructor, increment matchingConstructors
                if (constructToTest.equals(hashmapConstructor.toGenericString())) {
                    matchingConstructors++;
                }
            }
            
            // If there were no matches, return false
            if (matchingConstructors < 1) {
            	System.out.println(constructToTest);
                return false;
            }
        }
        
        // For each string methodToTest, within the given array of methods
        for (String methodToTest : methodsArr) {
        	
            int matchingMethods = 0;
            
            // For each method within the hashmap
            for (Method hashmapMethod : methods.values()) {
            	
            	// if the given string methodToTest matches the current hashmap
            	// method, increment matchingMethods
                if (methodToTest.equals(hashmapMethod.toGenericString())) {
                    matchingMethods++;
                }
            }
            
            // If there were no matches, return false
            if (matchingMethods < 1) {
            	System.out.println(methodToTest);
                return false;
            }
        }
        validated = true;	// Set flag that the object has been validated
        return true;
    }

    /**
     * @purpose	To create and return an object by calling its constructor.
     * 			The signature must match the Object's constructor signature
     * 			exactly.
     * @param 	construc, the name of the object's class
     * @param 	arguments, the necessary parameters for the constructor
     * @return 	the newly created object if successful. Else return null.
     */
    public Object createObj(String construc, Object... arguments) {
    	// Get the object's constructor, and create a new instance of it with
    	// the arguments.
        try {
            return constructors.get(construc).newInstance(arguments);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @purpose to Invoke method by name and signature.
     * @param 	obj, the object being used
     * @param 	method, the method's name from the object
     * @param 	params, the parameters required for the method
     * @return 	the object if successful. Else return false.
     */
    public Object invokeMethod(Object obj, String method, Object... params) {
    	// Check to see if the method is within the HashMap of methods.
        if (!methods.containsKey(method)) {
        	System.out.println("not a method");
            return false;
        } 
        
        // If so, try to invoke the object's methods with the parameters.
        else {	
            try {
                return methods.get(method).invoke(obj, params);
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("not a method");
                return false;
            }
        }
    }

    /**
     * @purpose Checks if is reflected.
     * @return 	true, if is reflected
     */
    public boolean isReflected() {
        return reflected;
    }

    /**
     * @purpose	Checks if is validated.
     * @return 	true, if is validated
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * @purpose Gets the class name
     * @return the class name
     */
    public String getClassName() {
        return className;
    }
    
    public boolean hasMethod(String methodToCheck){
    	return false;
    }
    
    public void getFields(String className){
    	try {
    	    Class<?> classType = Class.forName(className);
    	    Field[] fieldsArr = classType.getDeclaredFields();

    	} catch (ClassNotFoundException x) {
    	    x.printStackTrace();
    	}
    }
    
    /**
     * Verify the contents of fields with a provided "correct fields" array.
     * @param arrOfFields, the correct array of Field members
     */
    public void verifyFields(Field[] arrOfFields){
    	// currentField	=	From the Student Obj. Hashmap of Fields
    	// toCheck 		=	From the array. This array has the correct parameters
    	Field currentField = null;
    	
    	for (Field toCheck : arrOfFields){
    		
    		// First check -- see if the name matches 
    		if (fields.containsKey(toCheck.getName())){
    			
    			// If there's a match, set currentField to it
    			currentField = fields.get(toCheck.getName());
    			
    			// Second check -- see if they share the same type
    			if (currentField.getGenericType() == toCheck.getGenericType()){
    				
    				// Third check -- see if they share the same modifiers
    				// (public, static, private, etc.)
    				if (currentField.getModifiers() == toCheck.getModifiers()){
    					System.out.println("Found " + toCheck.getName());
    				}
    				// They don't share the same modifiers.
    				else{
    					System.out.println("Found " + toCheck.getName() 
    					+ " but the modifier/accessbility flag(s) are incorrect");
    				}
    			}
    			// They don't share the same type.
    			else{
    				System.out.println("Found " + toCheck.getName() + " but the type is " 
    			+ currentField.getType() + " instead of " + toCheck.getType());
    			}
    		}
    		// Unable to find field.
    		else{
    			System.out.println("Cannot find field by name: " + toCheck.getName());
    		}
        }
    }
}