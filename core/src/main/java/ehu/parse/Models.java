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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import opennlp.tools.parser.ParserModel;

public class Models {

	private static Map<String, ParserModel> parserModelMap = new HashMap<String, ParserModel>();

	// private InputStream parseModel;
	private InputStream headsFile;

	public InputStream getParseModelInputStream(String cmdOption) {
		InputStream parseModel = null;
		if (cmdOption.equals("en")) {
			parseModel = getClass().getResourceAsStream(
					"/en-parser-chunking.bin");
		}

		if (cmdOption.equals("es")) {
			parseModel = getClass().getResourceAsStream(
					"/es-parser-chunking.bin");
		}
		if (cmdOption.equals("it")) {
			parseModel = getClass().getResourceAsStream(
					"/it-parser-chunking.bin");
		}
		if (cmdOption.equals("fr")) {
			parseModel = getClass().getResourceAsStream(
					"/fr-parser-chunking.bin");
		}
		return parseModel;
	}

	public InputStream getHeadRulesFile(String cmdOption) {

		if (cmdOption.equals("en")) {
			headsFile = getClass().getResourceAsStream("/en-head-rules");
		}

		if (cmdOption.equals("es")) {
			headsFile = getClass().getResourceAsStream("/es-head-rules");
		}
		if (cmdOption.equals("it")) {
			headsFile = getClass().getResourceAsStream("/it-head-rules");
		}
		if (cmdOption.equals("fr")) {
			headsFile = getClass().getResourceAsStream("/fr-head-rules");
		}
		return headsFile;
	}

	public ParserModel getParserModel(String lang) {
		ParserModel model = parserModelMap.get(lang);
		if (model == null) {
			model = loadModel(lang);
			parserModelMap.put(lang, model);
		}
		return model;
	}

	private ParserModel loadModel(String lang) {
		InputStream modelInputStream = null;
		try {
			modelInputStream = getParseModelInputStream(lang);
			ParserModel model = new ParserModel(modelInputStream);
			return model;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (modelInputStream != null) {
				try {
					modelInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
