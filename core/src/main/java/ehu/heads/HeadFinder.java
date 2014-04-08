
package ehu.heads;

import opennlp.tools.parser.Parse;

/**
 *
 */


public interface HeadFinder {
	
	/**
	 * It reads a Parse object and adds the heads for each constituent following
	 * some head rules. 
	 * 
	 * @param parse
	 */
	public void printHeads(Parse parse);

}
