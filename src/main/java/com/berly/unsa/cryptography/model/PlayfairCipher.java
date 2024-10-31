package com.berly.unsa.cryptography.model;

import java.lang.System.Logger.Level;
import java.util.HashMap;
import java.util.LinkedHashSet;

import com.berly.unsa.cryptography.util.PlayfairString;

public class PlayfairCipher {
	
	private static System.Logger logger = System.getLogger(PlayfairCipher.class.getName());
	
	private char[][] matrix;
	
	public static final String FORMAT_STRING_REGEX = "^[a-zA-Z][a-zA-Z\\s]*$";
	
	public static final char LETTER_I = 73;
	
	public static final char LETTER_J = 74;
	
	public static final int MATRIX_SIZE = 5;
		
	public String encryptWithLetterI(String message, String key) {		
		if (!message.matches(FORMAT_STRING_REGEX) || !key.matches(FORMAT_STRING_REGEX)) {
			throw new StringFormatException();
		}			
		return encrypt(PlayfairString.valueOfWithLetterI(message), createMatrixWithI(key));
	}		
	
	public String encryptWithLetterJ(String message, String key) {
		if (!message.matches(FORMAT_STRING_REGEX) || !key.matches(FORMAT_STRING_REGEX)) {
			throw new StringFormatException();
		}				
		return encrypt(PlayfairString.valueOfWithLetterJ(message), createMatrixWithJ(key));
	}
		
	public String decryptWithLetterI(String message, String key) {		
		if (!message.matches(FORMAT_STRING_REGEX) || !key.matches(FORMAT_STRING_REGEX)) {
			throw new StringFormatException();
		}				
		return decrypt(PlayfairString.valueOfWithLetterI(message), createMatrixWithI(key));
	}		
	
	public String decryptWithLetterJ(String message, String key) {
		if (!message.matches(FORMAT_STRING_REGEX) || !key.matches(FORMAT_STRING_REGEX)) {
			throw new StringFormatException();
		}					
		return decrypt(PlayfairString.valueOfWithLetterJ(message), createMatrixWithJ(key));
	}
	
 	public static char[][] createMatrixWithJ(String key) { 		
		if (!key.matches(FORMAT_STRING_REGEX)) {
			throw new StringFormatException();
		}
 		
		var newMatrix = new char[MATRIX_SIZE][MATRIX_SIZE];
		var processedKey = PlayfairString.mix(key.toUpperCase().replace(LETTER_I, LETTER_J));		
 		var linkHashSetLetters = getListOfAlphabetUsingKey(PlayfairString.mix(processedKey)); 
 		linkHashSetLetters.remove(Character.valueOf(LETTER_I));	
 		
 		var iteratorLinkHashSetLetters = linkHashSetLetters.iterator();
 		
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {
				newMatrix[i][j] = iteratorLinkHashSetLetters.next();
			}
		}

		return newMatrix;	
 	}
 	
 	public static char[][] createMatrixWithI(String key) {
 		
		if (!key.matches(FORMAT_STRING_REGEX)) {
			throw new StringFormatException();
		}
 		
		var newMatrix = new char[MATRIX_SIZE][MATRIX_SIZE];
		var processedKey = PlayfairString.mix(key.toUpperCase().replace(LETTER_J, LETTER_I));		
 		var linkHashSetLetters = getListOfAlphabetUsingKey(PlayfairString.mix(processedKey)); 
 		linkHashSetLetters.remove(Character.valueOf(LETTER_J));	
 		
 		var iteratorLinkHashSetLetters = linkHashSetLetters.iterator();
 		
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {
				newMatrix[i][j] = iteratorLinkHashSetLetters.next();
			}
		}

		return newMatrix;	
 	}
	
	private String putWhiteSpaces(String message, PlayfairString playfairMessage) {		
		int lastIndex = 0;
		var finalMessage = new StringBuilder(); 
			
		for (final var INDEX : playfairMessage.whiteSpaceIndeces()) {
			finalMessage.append(message.substring(lastIndex, INDEX));	
			finalMessage.append(' ');
			lastIndex = INDEX;
		}		
			
		finalMessage.append(message.substring(lastIndex, message.length()));
				
		return finalMessage.toString();
	}
		
	private static LinkedHashSet<Character> getListOfAlphabetUsingKey(String key) {	
							
		var linkHashSetLetters = new LinkedHashSet<Character>();  
		var tempKey = key.toCharArray();
	
		for (final var c : tempKey) {
			linkHashSetLetters.add(c);			
		}
			
		logger.log(Level.DEBUG, linkHashSetLetters.toString());
					
		for (char ascii = 65; ascii < 91; ascii++) {	
			linkHashSetLetters.add(Character.valueOf(ascii));
		}
		
		logger.log(Level.DEBUG, linkHashSetLetters.toString());
		
		return linkHashSetLetters;
	}
	
	private String encrypt(PlayfairString playfairStringMessage, char[][] tempMatrix) {		
		
		/*Matriz de claves*/
		matrix = tempMatrix;
		
		/*Objeto HashMap<Character, Integer[]> que almacena las coordenadas de cada caracter en base a la matriz*/
		var matrixCoordinates = createMatrixCoordinates(matrix);
			
		/*Objeto que almacenara el mensaje encriptado*/
		var encryptedMessage = new StringBuilder();
		
		/*Bucle for que itera sobre cada par de caracteres del mensaje procesado por la clase PlayfairString*/
		for (final var CHARS : playfairStringMessage.diSplitString()) {		
			
			/*Array Integer[] que contiene el indice de la fila y columna de un caracter
			 * dentro de la matriz de claves*/
			var firstCharCoordinates = matrixCoordinates.get(CHARS[0]);
			var secondCharCoordinates = matrixCoordinates.get(CHARS[1]);
				
			/*Comprueba si dos caracteres se encuentran en la misma fila*/
			if (areSameRow(matrix, CHARS[0], CHARS[1])) {								
				logger.log(Level.DEBUG, String.format("Char %s and Char %s are in the same ROW", CHARS[0], CHARS[1]));
				
				/*Para el primer caracter del par, comprueba si se ubica en el final de la fila matriz*/
				if (firstCharCoordinates[1] < MATRIX_SIZE - 1) {
					encryptedMessage.append(matrix[firstCharCoordinates[0]][firstCharCoordinates[1] + 1]);
				} else {
					encryptedMessage.append(matrix[firstCharCoordinates[0]][0]);
				}
				
				/*Para el segundo caracter del par, comprueba si se ubica en el final de la fila de la matriz*/
				if (secondCharCoordinates[1] < MATRIX_SIZE - 1) {
					encryptedMessage.append(matrix[secondCharCoordinates[0]][secondCharCoordinates[1] + 1]);
				} else {
					encryptedMessage.append(matrix[secondCharCoordinates[0]][0]);
				}
					
			/*Comprueba si dos caracteres se encuentran en la misma colunma*/
			} else if(areSameCol(matrix, CHARS[0], CHARS[1])) {				
				logger.log(Level.DEBUG, String.format("Char %s and Char %s are in the same COL", CHARS[0], CHARS[1]));
				
				/*Para el primer caracter del par, comprueba si se ubica en el final de la columna de la matriz*/
				if (firstCharCoordinates[0] < MATRIX_SIZE - 1) {
					encryptedMessage.append(matrix[firstCharCoordinates[0] + 1][firstCharCoordinates[1]]);
				} else {
					encryptedMessage.append(matrix[0][firstCharCoordinates[1]]);
				}
					
				/*Para el segundo caracter del par, comprueba si se ubica en el final de la columna de la matriz*/
				if (secondCharCoordinates[0] < MATRIX_SIZE - 1) {
					encryptedMessage.append(matrix[secondCharCoordinates[0] + 1][secondCharCoordinates[1]]);
				} else {
					encryptedMessage.append(matrix[0][secondCharCoordinates[1]]);
				}
				
			/*Si el par de caracteres no se encuentran en la misma fila ni columna, entonces se ubican en forma rectangular*/		
			} else {
				logger.log(Level.DEBUG, String.format("Char %s and Char %s are neither in the same row nor col", CHARS[0], CHARS[1]));
				encryptedMessage.append(matrix[firstCharCoordinates[0]][secondCharCoordinates[1]]);
				encryptedMessage.append(matrix[secondCharCoordinates[0]][firstCharCoordinates[1]]);			
			}		
		}
				
		/*La funcion putWhiteSpaces coloca los espacios en blanco del mensaje original dentro del mensaje encriptado*/
		return putWhiteSpaces(encryptedMessage.toString(), playfairStringMessage);
	}
	
	private String decrypt(PlayfairString playfairStringMessage, char[][] tempMatrix) {		
		
		/*Matriz de claves*/
		matrix = tempMatrix;
		
		/*Objeto HashMap<Character, Integer[]> que almacena las coordenadas de cada caracter en base a la matriz*/
		var matrixCoordinates = createMatrixCoordinates(matrix);
		
		/*Objeto que almacenara el mensaje encriptado*/
		var decryptedMessage = new StringBuilder();
		
		/*Bucle for que itera sobre cada par de caracteres del mensaje procesado por la clase PlayfairString*/
		for (final var CHARS : playfairStringMessage.diSplitString()) {
			
			/*Array Integer[] que contiene el indice de la fila y columna de un caracter
			 * dentro de la matriz de claves*/
			var firstCharCoordinates = matrixCoordinates.get(CHARS[0]);
			var secondCharCoordinates = matrixCoordinates.get(CHARS[1]);
				
			/*Comprueba si dos caracteres se encuentran en la misma fila*/
			if (areSameRow(matrix, CHARS[0], CHARS[1])) {		
				logger.log(Level.DEBUG, String.format("Char %s and Char %s are in the same ROW", CHARS[0], CHARS[1]));
				
				/*Para el primer caracter del par, comprueba si se ubica en el inicio de la fila matriz*/
				if (firstCharCoordinates[1] > 0) {
					decryptedMessage.append(matrix[firstCharCoordinates[0]][firstCharCoordinates[1] - 1]);
				} else {
					decryptedMessage.append(matrix[firstCharCoordinates[0]][MATRIX_SIZE - 1]);
				}
					
				/*Para el segundo caracter del par, comprueba si se ubica en el inicio de la fila de la matriz*/
				if (secondCharCoordinates[1] > 0) {
					decryptedMessage.append(matrix[secondCharCoordinates[0]][secondCharCoordinates[1] - 1]);
				} else {
					decryptedMessage.append(matrix[secondCharCoordinates[0]][MATRIX_SIZE - 1]);
				}
						
			/*Comprueba si dos caracteres se encuentran en la misma colunma*/
			} else if(areSameCol(matrix, CHARS[0], CHARS[1])) {			
				logger.log(Level.DEBUG, String.format("Char %s and Char %s are in the same COL", CHARS[0], CHARS[1]));
				
				/*Para el primer caracter del par, comprueba si se ubica en el inicio de la columna de la matriz*/
				if (firstCharCoordinates[0] > 0) {
					decryptedMessage.append(matrix[firstCharCoordinates[0] - 1][firstCharCoordinates[1]]);
				} else {
					decryptedMessage.append(matrix[MATRIX_SIZE - 1][firstCharCoordinates[1]]);
				}
				
				/*Para el segundo caracter del par, comprueba si se ubica en el inicio de la columna de la matriz*/	
				if (secondCharCoordinates[0] > 0) {
					decryptedMessage.append(matrix[secondCharCoordinates[0] - 1][secondCharCoordinates[1]]);
				} else {
					decryptedMessage.append(matrix[MATRIX_SIZE - 1][secondCharCoordinates[1]]);
				}
			/*Si el par de caracteres no se encuentran en la misma fila ni columna, entonces se ubican en forma rectangular*/		
			} else {		
				logger.log(Level.DEBUG, String.format("Char %s and Char %s are neither in the same row nor col", CHARS[0], CHARS[1]));
				decryptedMessage.append(matrix[firstCharCoordinates[0]][secondCharCoordinates[1]]);
				decryptedMessage.append(matrix[secondCharCoordinates[0]][firstCharCoordinates[1]]);			
			}		
		}
				
		/*La funcion putWhiteSpaces coloca los espacios en blanco del mensaje original dentro del mensaje desencriptado*/
		return putWhiteSpaces(decryptedMessage.toString(), playfairStringMessage);
	}

	private static HashMap<Character, Integer[]> createMatrixCoordinates(char[][] matrix) {
		var tempMatrixCoordinates = new HashMap<Character, Integer[]>();
		
		for (int i = 0; i < MATRIX_SIZE; i++) {
			for (int j = 0; j < MATRIX_SIZE; j++) {			
				Integer[] coordinates = { i, j };			
				tempMatrixCoordinates.put(Character.valueOf(matrix[i][j]), coordinates);			
			}
		}
		
		logger.log(Level.DEBUG, tempMatrixCoordinates);
		
		return tempMatrixCoordinates;
	}
	
	private static boolean areSameCol(char[][] matrix, char firstChar, char secondChar) {
		
		boolean isFirstChar = false;
		boolean isSecondChar = false;
		
		for (int col = 0; col < MATRIX_SIZE; col++) {
			for (int row = 0; row < MATRIX_SIZE; row++) {
				if (matrix[row][col] == firstChar) {
					isFirstChar = true;
				}
				if (matrix[row][col] == secondChar) {
					isSecondChar = true;
				}
			}
			
			if (isFirstChar && isSecondChar) {
				return true;
			}		
		}
		
		return false;
	}
	
	private static boolean areSameRow(char[][] matrix, char firstChar, char secondChar) {
		
		boolean isFirstChar = false;
		boolean isSecondChar = false;
		
		for (int row = 0; row < MATRIX_SIZE; row++) {
			for (int col = 0; col < MATRIX_SIZE; col++) {
				if (matrix[row][col] == firstChar) {
					isFirstChar = true;
				}
				if (matrix[row][col] == secondChar) {
					isSecondChar = true;
				}
			}
			
			if (isFirstChar && isSecondChar) {
				return true;
			}		
		}
		
		return false;
	}
}
