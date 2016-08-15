/**
 * 	ClassReflector.
 * 
 * 	This is the primary class that reflects and validates a class.
 * 
 * 	<p><b>General flow:</b><p>
 * 			
 * 	<b>1.</b> Tester.java file calls ClassReflector's constructor,
 * 	providing the reflected class' name.<p>
 *
 * 	<b>2.</b> ClassReflector will call reflect() to fill in ClassReflector's 
 * 	HashMaps, so ClassReflector will be populated with the specified 
 * 	class's constructors, methods, and fields. The keys are the names
 *	of each type (String) while the values are the actual types themselves.<p>
 * 
 * 	Example: 													<br>
 * 		method:	setHeight(float x)	<-- Method in .java file	<br>
 * 		key:	"setHeight"			<-- String of method name	<br>
 * 		value: 	setHeight(float)	<-- Actual Method object	<br><p>
 * 
 * 	<b>3.</b> With the hashmaps populated, multiple analytics can be performed.
 * 	You can compare one ClassReflector's hashmaps of constructors, methods,
 * 	and fields with another ClassReflector. Or you can see if a specific
 * 	method has the correct signature. Or you can verify ClassReflector's
 * 	fields with a string of "correct" fields, with the correct type
 * 	and accessibility modifiers.<p>
 * 
 * @author - Sammy Nimnuch. 8/11/16
 */

package dyehard.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;


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
     * Instantiates a new class reflector.
     * @param 	target the target
     */
    public ClassReflector(String target) {
        reflected = false;
        validated = false;
        className = target;
        reflect();
    }
    
    /**
     * The Class which ClassReflector is currently reflecting.
     * @return The class. Null if not reflecting anything.
     */
    public Class<?> getReflectedClass(){
    	Class<?> toReturn = null;
    	
    	// Assign toReturn to the provided class name
    	try {
    		toReturn = Class.forName(className);
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    		System.out.println("ClassReflector - Class Retrieval Failed");
    	}
    	return toReturn;	// Return the class
    }

    /**
     * Get the class' constructors, fields, and methods. Store it within
     * ClassReflector's hashmaps.
     * @return 	true, if successful. False if class is invalid/null.
     */
    public boolean reflect() {
    	// Check if the ClassReflector has already been reflected
    	if (reflected)
    		return true;
    	
    	try {	
            Class<?> reflectedClass = Class.forName(className);
            int i = 0;
            
            // Populate the HashMap with the class' fields
            for (Field field : reflectedClass.getDeclaredFields()){
            	fields.put(field.getName(), field);
            }
            
            // Populate the HashMap with the class' constructors
            for (Constructor<?> constructor : reflectedClass.getDeclaredConstructors()) {
                String temp = constructor.getName() + Integer.toString(i);
                constructors.put(temp, constructor);
                i++;
            }
            
            // Populate the HashMap with the methods
            for (Method method : reflectedClass.getDeclaredMethods()) {
                methods.put(method.getName(), method);
            }

        } catch (ClassNotFoundException x) {
            System.out.println(x.getMessage());
            x.printStackTrace();
            return false;
        }
        reflected = true;		// set flag to reflected
        return true;
    }

    /**
     * To validate the class. Checks to see if it has the appropriate constructors
     * and methods with String arrays of correct constructors and methods.
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
     * Create and return an object by calling its constructor.
     * The signature must match the Object's constructor signature exactly.
     * 
     * @param 	arguments, the necessary parameters for the constructor
     * @return 	the newly created object if successful. Else return null.
     */
    public Object createObj(Object... arguments) {
    	// Get the object's constructor, and create a new instance of it with
    	// the arguments.
        try {
        	for (Constructor<?> toFind : constructors.values()){
        		// Get the array of constructor parameters
        		Class<?>[] params = toFind.getParameterTypes();
        		
        		// if the length and all arguments are all the same, return it.
        		if (params.length == arguments.length){
        			for (int i = 0; i < params.length; i++){
        				if (!params[i].isAssignableFrom(arguments[i].getClass())){
        					return null;
        				}
        			}
        			return toFind.newInstance(arguments);
        		}
        	}
        	return null;
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Invoke method by name and signature.
     * 
     * @param 	obj - the object being used
     * @param 	methodName - the method's name from the object
     * @param 	params - the parameters required for the method
     * @return 	the object if successful. Else return false.
     */
    public Object invokeMethod(Object obj, String methodName, Object... params) {
    	// Check to see if the method is within the HashMap of methods.
        if (!methods.containsKey(methodName)) {
        	System.out.println("not a method");
            return false;
        } 
        
        // If so, try to invoke the object's methods with the parameters.
        else {	
            try {
                return methods.get(methodName).invoke(obj, params);
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
     * Checks if is reflected.
     * 
     * @return true, if reflected
     */
    public boolean isReflected() {
        return reflected;
    }

    /**
     * Checks if the class has been validated.
     * 
     * @return 	true, if validated
     */
    public boolean isValidated() {
        return validated;
    }

    /**
     * Gets the class name
     * 
     * @return the class name
     */
    public String getClassName() {
        return className;
    }
    
    /**
     * Return the Field object specified by its name.
     * @param fieldName - The name of the Field.
     * @return The Field Object if found, null otherwise.
     */
    public Field getFieldByName(String fieldName){
    	if (fields.containsKey(fieldName))
    		return fields.get(fieldName);
    	else
    		return null;
    }
    
    /**
     * Return the Method object specified by its name.
     * @param methodName - The name of the Method.
     * @return The Method Object if found, null otherwise.
     */
    public Method getMethodByName(String methodName){
    	if (methods.containsKey(methodName))
    		return methods.get(methodName);
    	else
    		return null;
    }
    
    /**
     * Return the value specified by the field name.
     * 
     * @param 	fieldName - the String name of the field
     * @param 	obj - an instance of the class to extract information from.
     * @return 	The object wrapped in its respective primitive class, or null if 
     * 			not found.
     */
    public Object getFieldValue(String fieldName, Object obj){
    	// Try to get the field by name, and return the value
    	try {
			Field f = Class.forName(className).getDeclaredField(fieldName);
			return f.get(obj);
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException|
				IllegalAccessException| ClassNotFoundException e) {
			System.out.println(fieldName + " not found.");
			e.printStackTrace();
		}
		return null;
    }
    
    /**
     * Verify the contents of fields with a provided "correct fields" array.
     * 
     * @param arrOfFields - the correct array of Field members
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
    
    /**
     * Verify the Hashmap of methods against an array of "correct" Methods.
     * 
     * @param constructArr - the array of "correct" methods
     */
    public void verifyMethods(Method[] methodArr){
    	// currentField	=	From the Student Obj. Hashmap of Methods
    	// toCheck 		=	From the array. This array has the correct parameters
    	Method currentField = null;
    	
    	boolean flag = true;
    	
    	System.out.println("-------- List of Student Methods --------");
    	for(Method toPrint : methods.values())
    		System.out.println(toPrint);
    	System.out.println("----------------------------------------");
    	
    	System.out.println("-------- List of Correct Methods --------");
    	for(Method toPrint2 : methodArr)
    		System.out.println(toPrint2);
    	System.out.println("----------------------------------------");
    	
    	for (Method toCheck : methodArr){
    		flag = true;
    		// First check -- see if the name matches 
    		if (methods.containsKey(toCheck.getName())){
    			
    			// If there's a match, set currentField to it
    			currentField = methods.get(toCheck.getName());
    			
    			// Second check -- see if they share the same return type
    			if (currentField.getReturnType() != toCheck.getReturnType()){
    				System.out.println("Found " + toCheck.getName() 
    				+ " but the return type is " + currentField.getReturnType() 
    				+ " instead of " + toCheck.getReturnType());
    			}
    			
    			// Third check -- see if they share the same modifiers
				// (public, static, private, etc.)
				if (currentField.getModifiers() != toCheck.getModifiers()){
					System.out.println("Found " + toCheck.getName() 
					+ " but the modifier/accessbility flag(s) are incorrect");
				}
				
				// Fourth check -- see if the parameters are the same
				if (currentField.getGenericParameterTypes().length != 
						toCheck.getGenericParameterTypes().length)
					flag = false;
				else{
					// Sort to eliminate parameter order
					HashMap<String, Integer> paramHM = new HashMap<String, Integer>();
					HashMap<String, Integer> paramHM2 = new HashMap<String, Integer>();
					
					// For each field parameter, store inside hashmap with count
					for (int i = 0; i < currentField.getGenericParameterTypes().length; i++){
						String name = currentField.getGenericParameterTypes()[i].toString();
						if (paramHM.containsKey(name) == false)
							paramHM.put(name, 1);
						else
							paramHM.put(name, paramHM.get(name) + 1);
					}
					
					// Do the same with the other method
					for (int i = 0; i < toCheck.getGenericParameterTypes().length; i++){
						String name = toCheck.getGenericParameterTypes()[i].toString();
						if (paramHM2.containsKey(name) == false)
							paramHM2.put(name, 1);
						else
							paramHM2.put(name, paramHM2.get(name) + 1);
					}
					
					if (paramHM.equals(paramHM2) == false)
						flag = false;
				}
	
				if (flag == false){
					System.out.print("Found " + toCheck.getName() + " but the parameter(s) are ");
					for (int i = 0; i < currentField.getGenericParameterTypes().length; i++)
						System.out.print(currentField.getGenericParameterTypes()[i] + " ");
					
					System.out.print("instead of ");
					for (int i = 0; i < toCheck.getGenericParameterTypes().length; i++)
						System.out.print(toCheck.getGenericParameterTypes()[i]);
					System.out.print("\n");
				}
    		}
    		// Unable to find method by name.
    		else
    			System.out.println("Cannot find method by name: " + toCheck.getName());
    	}
    }
    
    /**
     * Return the stored Fields, in an array format.
     * @return the stored Fields objects in an array
     */
    public Field[] getArrayOfFields(){
    	// Need to do this since returning fields.toArray() is Object[]
    	// and will crash if you try to type-cast it as Field[]
    	Field[] retFields = fields.values().toArray(new Field[fields.size()]);
    	return retFields;
    }
    
    /**
     * Return the stored Methods, in an array format.
     * @return the stored Methods objects in an array
     */
    public Method[] getArrayOfMethods(){
    	Method[] retMethods = methods.values().toArray(new Method[methods.size()]);
    	return retMethods;
    }
    
    /**
     * Return the stored Constructors, in an array format.
     * @return the stored Constructor objects in an array
     */
    @SuppressWarnings("rawtypes")
	public Constructor[] getArrayOfConstructors(){
    	Constructor[] retConstructors = constructors.values().
    			toArray(new Constructor[constructors.size()]);
    	return retConstructors;
    }
    
    
    /**
     * Verify the Hashmap of constructors against an array of "correct" Constructors.
     * The constructor names can be different, as this method compares the 
     * parameters of the correct constructors.
     * 
     * @param constructArr - the array of "correct" constructors
     */
    // CONSTRUCTORS MUST BE DECLARED IN A SPECIFIC ORDER
    // The student class can be named differently than the testing class
    public void verifyConstructors(Constructor<?>[] constructArr){
    	
    	// Check each parameter for each constructor between toCheck[i] 
    	// and constructArr[i].
    	boolean rightMods, rightParamType, rightParamNum;
    	
    	for (Constructor<?> toCheck : constructArr){
    		rightParamNum = rightMods = rightParamType = true;
    		for (int i = 0; i < constructArr.length; i++){
    			
    			// First Check -- The modifiers (public, private, etc.)
    			if (constructArr[i].getModifiers() != toCheck.getModifiers()){
					rightMods = false;
				}
    			
    			// Second Check -- Number of parameters
    			if (constructArr[i].getParameterTypes().length != toCheck.getParameterTypes().length){
    				rightParamNum = false;
    			}
    			else {
    				// Third Check -- Parameter types.
        			for (int paramCounter = 0; paramCounter < constructArr[i].getGenericParameterTypes().length; paramCounter++){
        				if (constructArr[i].getGenericParameterTypes()[paramCounter] 
        						!= toCheck.getGenericParameterTypes()[paramCounter]){
        					rightParamType = false;
            			}
        			}
    			}
    			// Print out the results.
    			if (rightMods == true && rightParamType == true && rightParamNum == true){
    				System.out.println("Found " + constructArr[i].toString());
    			} else {
    				String toPrint = constructArr[i].toString() + " not found." 
    						+ "this is due to incorrect: ";
    				if (rightMods == false)
    					toPrint += "accessibility modifiers, ";
    				if (rightParamNum == false)
    					toPrint += "number of parameters, ";
    				if (rightParamType == false)
    					toPrint += "number of parameter types, ";
    				System.out.println(toPrint + "please check your code and try again.");
    			}
    		}	
    	}
    }
    
    public String printDetails(){
    	String toRet = "";
    	
    	toRet += "\n--- Fields ---\n";
    	if (fields.isEmpty())
    		toRet += "(none)\n";
    	for (Field fie : fields.values())
        	toRet += fie.toString() + '\n';
    	
    	toRet += "\n--- Constructors ---\n";
    	if (constructors.isEmpty())
    		toRet += "(none)\n";
    	for (Constructor<?> con : constructors.values())
    		toRet += con.toString() + '\n';
    	
    	toRet += "\n--- Methods ---\n";
    	if (methods.isEmpty())
    		toRet += "(none)";
    	for (Method meth : methods.values())
    		toRet += meth.toString() + "\n";
    	
    	toRet += '\n';
    	
    	return toRet;
    }
}