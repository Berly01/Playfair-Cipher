package com.berly.unsa.cryptography.model;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class AmscoCipher {

	private static final Logger LOGGER = System.getLogger(AmscoCipher.class.getName());
	
	private AmscoCipher() {}
		
	public static void main (String[] args) {
		
		var message = "ESTOYSEGURO";	
		var key = new ArrayList<Integer>();
		key.add(3);
		key.add(2);
		key.add(1);
			
		System.out.println("Mensaje Original: " + message);	
		System.out.println("LLave: " + key.toString());	
		System.out.println("Mensaje Encriptado: " + encrypt(message, key));
	}
	
    public static String encrypt(String message, List<Integer> key) { 
    	
    	checkKey(key);
    	
        var blocks = splitIntoAmscoBlocks(message);
        var matrix = createMatrix(blocks, key.size());    
        var loggerSB = new StringBuilder();
        
        loggerSB.append("\nMatrix\n");
    	key.stream().forEach(k -> loggerSB.append(k + "  "));
    	loggerSB.append('\n');
    	
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
            	loggerSB.append(matrix[row][col] + "  ");
            }
            loggerSB.append('\n');
        }
               
        LOGGER.log(Level.INFO, loggerSB);       
        return readMatrixByKey(matrix, key);
    }
    
    private static List<String> splitIntoAmscoBlocks(String message) {
        List<String> blocks = new ArrayList<>();
        boolean singleChar = true;
        int blockSize;
        
        int i = 0;

        while (i < message.length()) {
            blockSize = singleChar ? 1 : 2;
            
            if (i + blockSize > message.length()) {
            	blocks.add(message.substring(message.length() - 1, message.length()) + "?");
            	break;
            }
            else {
                blocks.add(message.substring(i, i + blockSize));
                i += blockSize;
                singleChar = !singleChar; 
            }
            
        }
       
        return blocks;
    }

    private static String[][] createMatrix(List<String> blocks, int columns) {
        int rows = (int) Math.ceil((double) blocks.size() / columns);
        String[][] matrix = new String[rows][columns];
        int blockIndex = 0;

        LOGGER.log(Level.DEBUG, String.format("Rows: %d Cols: %d", rows, columns));
     
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                if (blockIndex < blocks.size()) {
                    matrix[row][col] = blocks.get(blockIndex);
                } else {
                    matrix[row][col] = "?"; 
                }    
                ++blockIndex;
            }
        }      	
        return matrix;
    }

    private static String readMatrixByKey(String[][] matrix, List<Integer> key) {
        var encryptedText = new StringBuilder();   	
        var keyPerColHash = keyPerCol(key, matrix);
        
        for (int col : key) {
            for (int row = 0; row < matrix.length; row++) {
                encryptedText.append(matrix[row][keyPerColHash.get(col)]);
            }
        }
   
        return encryptedText.toString();
    }
    
    private static HashMap<Integer, Integer> keyPerCol(List<Integer> key, String[][] matrix) {	
    	var keyPerColHash = new HashMap<Integer, Integer>();
    	
    	for (int col = 0; col < matrix[0].length; col++) {
    		keyPerColHash.put(key.get(col), col);
    	}
    	
    	return keyPerColHash;
    }
    
    private static void checkKey(List<Integer> key) {     
    	
    	if (key.isEmpty()) {
    		throw new IllegalKeyArrayException();
    	}
    	 	
    	/*LinkedHashSet<Integer> garantiza que ninguna llave de columna se repita*/
    	var uniquekeysLinkedHashSet = new LinkedHashSet<Integer>();  	  	 	
    	List<Integer> tempKey = new ArrayList<>();
    			   	 	
    	key.stream().forEach(tempKey::add); 	
    	tempKey.stream().forEach(uniquekeysLinkedHashSet::add);
       	   	
    	if (uniquekeysLinkedHashSet.size() != tempKey.size()) {
    		throw new IllegalKeyArrayException();
    	} 		      		   	 
    }
}