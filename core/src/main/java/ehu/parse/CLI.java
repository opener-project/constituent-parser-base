package ehu.parse;


import ixa.kaflib.KAFDocument;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import ehu.heads.CollinsHeadFinder;
import ehu.heads.HeadFinder;

/**
 * Constituent Parsing for 4 languages: English, Spanish, French and Italian
 * 
 * @version 1.0
 * 
 */

public class CLI {

  /**
   * 
   * 
   * BufferedReader (from standard input) and BufferedWriter are opened. The
   * module takes KAF and reads the header, and the text elements and uses
   * Annotate class to provide constituent parsing of sentences, which are
   * provided via standard output.
   * 
   * @param args
   * @throws Exception 
   */

  public static void main(String[] args) throws Exception {

    Namespace parsedArguments = null;

    // create Argument Parser
    ArgumentParser parser = ArgumentParsers.newArgumentParser(
        "ehu-parse-1.0.jar").description(
        "ehu-parse-1.0 is a multilingual Constituent Parsing module "
            + "developed by IXA NLP Group based on Apache OpenNLP API.\n");

    // specify language
    parser
        .addArgument("-l", "--lang")
        .choices("en", "es", "it", "fr")
        .required(false)
        .help(
            "It is REQUIRED to choose a language to perform annotation with ixa-pipe-parse");

    parser
        .addArgument("--noHeads")
        .action(Arguments.storeFalse())
        .required(false)
        .help("Do not print headWords");
    
    parser.addArgument("-t","--timestamp").action(Arguments.storeTrue()).help("flag to make timestamp static for continous " +
        "integration testing");

    /*
     * Parse the command line arguments
     */

    // catch errors and print help
    try {
      parsedArguments = parser.parseArgs(args);
    } catch (ArgumentParserException e) {
      parser.handleError(e);
      System.out
          .println("Run java -jar target/ehu-parse-1.0.jar -help for details");
      System.exit(1);
    }

    /*
     * Load language and headFinder parameters
     */
   
    BufferedReader breader = null;
    BufferedWriter bwriter = null;
    
    try {
      breader = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
      bwriter = new BufferedWriter(new OutputStreamWriter(System.out, "UTF-8"));
      KAFDocument kaf = KAFDocument.createFromStream(breader);
      // language parameter
      String lang;
      if (parsedArguments.get("lang") == null) { 
          lang = kaf.getLang();
        }
        else { 
         lang =  parsedArguments.getString("lang");
        }
      // static timestamp for continuous integration
      if (parsedArguments.getBoolean("timestamp") == true) {
        kaf.addLinguisticProcessor("constituents","ehu-parse-"+lang,"now", "1.0");
      }
      else {
        kaf.addLinguisticProcessor("constituents", "ehu-parse-"+lang, "1.0");
      }
      Annotate annotator = new Annotate(lang);
      
      // choosing HeadFinder: (Collins rules for English and derivations of it
      // for other languages; sem (Semantic headFinder re-implemented from
      // Stanford CoreNLP).
      // Default: sem (semantic head finder).
      

      if (parsedArguments.getBoolean("noHeads")) {
        HeadFinder headFinder = new CollinsHeadFinder(lang);

        // parse with heads
        bwriter.write(annotator.parseWithHeads(kaf, headFinder));
      }
        // parse without heads
      else {
        bwriter.write(annotator.parse(kaf));
      }

      bwriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
