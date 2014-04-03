/*
 * Copyright 2013 Rodrigo Agerri

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package ehu.parse;

import ixa.kaflib.KAFDocument;
import ixa.kaflib.WF;

import java.util.List;

import opennlp.tools.parser.Parse;
import ehu.heads.HeadFinder;

/**
 * @author ragerri
 * 
 */
public class Annotate {

	private ConstituentParsing parser;

	// private Models modelRetriever;

	public Annotate(String lang) {
		// modelRetriever = new Models();
		// InputStream parseModel =
		// modelRetriever.getParseModelInputStream(lang);
		// parser = new ConstituentParsing(parseModel);
		parser = new ConstituentParsing(lang);
	}

	private String getSentenceFromTokens(String[] tokens) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.length; i++) {
			sb.append(tokens[i]).append(" ");
		}
		String sentence = sb.toString();
		return sentence;
	}

	/**
	 * This method produces constituent parsing in KAF with headwords marked
	 * 
	 * @param KAFDocument
	 *            kaf
	 * @param HeadFinder
	 *            headFinder
	 * @throws Exception
	 */

	public String parseWithHeads(KAFDocument kaf, HeadFinder headFinder)
			throws Exception {

		StringBuffer parsingDoc = new StringBuffer();
		List<List<WF>> sentences = kaf.getSentences();
		for (List<WF> sentence : sentences) {
			// get array of token forms from a list of WF objects
			String[] tokens = new String[sentence.size()];
			for (int i = 0; i < sentence.size(); i++) {
				tokens[i] = sentence.get(i).getForm();
			}

			// Constituent Parsing
			String sent = getSentenceFromTokens(tokens);
			Parse parsedSentence[] = parser.parse(sent, 1);
			for (Parse parse : parsedSentence) {
				headFinder.printHeads(parse);
			}
			for (Parse parsedSent : parsedSentence) {
				parsedSent.show(parsingDoc);
				parsingDoc.append("\n");
			}
		}
		kaf.addConstituencyFromParentheses(parsingDoc.toString());
		return kaf.toString();
	}

	/**
	 * This method provides constituent parsing in KAF
	 * 
	 * @param KAFDocument
	 *            kaf
	 * @throws Exception
	 */
	public String parse(KAFDocument kaf) throws Exception {

		StringBuffer parsingDoc = new StringBuffer();
		List<List<WF>> sentences = kaf.getSentences();
		for (List<WF> sentence : sentences) {
			// get array of token forms from a list of WF objects
			String[] tokens = new String[sentence.size()];
			for (int i = 0; i < sentence.size(); i++) {
				tokens[i] = sentence.get(i).getForm();
			}

			// Constituent Parsing
			String sent = this.getSentenceFromTokens(tokens);
			Parse parsedSentence[] = parser.parse(sent, 1);
			for (int i = 0; i < parsedSentence.length; i++) {
				parsedSentence[i].show(parsingDoc);
				parsingDoc.append("\n");
			}
		}
		kaf.addConstituencyFromParentheses(parsingDoc.toString());
		return kaf.toString();
	}

}
