package com.berly.unsa.cryptography.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.berly.unsa.cryptography.model.PlayfairCipher;

public record PlayfairString(String processedMessage, List<Integer> whiteSpaceIndeces, List<Character[]> diSplitString) {
	
	public static PlayfairString valueOfWithLetterI(String message) {		
		String processedMessage = getProcessMessage(message);
		List<Integer> whiteSpaceIndeces = getWhiteSpaceIndeces(message); 
		
		processedMessage = processedMessage.replace(PlayfairCipher.LETTER_J, PlayfairCipher.LETTER_I);
		
		List<Character[]> splitedProcessedMessage = getSplitedProcessedMessage(processedMessage); 
				
		return new PlayfairString(processedMessage, whiteSpaceIndeces, splitedProcessedMessage);	
	}	
	
	public static PlayfairString valueOfWithLetterJ(String message) {		
		String processedMessage = getProcessMessage(message);
		List<Integer> whiteSpaceIndeces = getWhiteSpaceIndeces(message); 
		
		processedMessage = processedMessage.replace(PlayfairCipher.LETTER_I, PlayfairCipher.LETTER_J);
		
		List<Character[]> splitedProcessedMessage = getSplitedProcessedMessage(processedMessage); 
				
		return new PlayfairString(processedMessage, whiteSpaceIndeces, splitedProcessedMessage);	
	}
	
	
	public static String[] tokenize(String message) {
		
		var messageTokenizer = new StringTokenizer(message.strip().toUpperCase());			
					
		var tokens = new ArrayList<String>();			
		while(messageTokenizer.hasMoreTokens()) {		
			tokens.add(messageTokenizer.nextToken());
		}	
		
		return tokens.toArray(new String[0]);
	}
	
	public static String mix(String message) {
		var tokens = tokenize(message);
		var strBuildre = new StringBuilder();
		
		for (final var TOKEN : tokens) {
			strBuildre.append(TOKEN);
		}
		
		return strBuildre.toString();
	}
	
	private static String getProcessMessage(String message) {
		var messageBuilder = new StringBuilder();
		var tokens = tokenize(message);
		
		for (final var TOKEN : tokens) {
			messageBuilder.append(TOKEN);
		}
		if (messageBuilder.toString().length() % 2 != 0) {
			messageBuilder.append('X');
		}		
		
		return messageBuilder.toString();
	}
	
	private static List<Integer> getWhiteSpaceIndeces(String message) {
		var tokens = tokenize(message);
		List<Integer> whiteSpaceIndeces = new ArrayList<>(); 
		int whiteSpaceIndex = 0;	
		
		if (tokens.length > 1) {			
			for (int i = 0; i < tokens.length - 1; i++) {
				whiteSpaceIndex += tokens[i].length();
				whiteSpaceIndeces.add(whiteSpaceIndex);
			}
		} 
		
		return whiteSpaceIndeces;
	}
	
	private static List<Character[]> getSplitedProcessedMessage(String processedMessage) {			
		List<Character[]> splitedProcessedMessage = new ArrayList<>(); 
		var processedMessageArray = processedMessage.toCharArray();
		int processedMessageSize = processedMessageArray.length;
			
		for (int i = 0, j = 1; j < processedMessageSize; i += 2, j += 2) {
			Character[] letters = { processedMessageArray[i], processedMessageArray[j] }; 
			splitedProcessedMessage.add(letters);
		}		
		
		return splitedProcessedMessage;
	}
}