Feature: Using a file as input and other file as an output
  In order to tokenize the file
  Using a file as an input
  Using a file as an output

  Scenario Outline: tokenize input file.
    Given the fixture file "<input_file>"
    And the language "<language>"
    And I put them through the kernel
    Then the output should match the fixture "<output_file>"
  Examples:
    | language | input_file    | output_file   |
    | en       | input-en.kaf  | output-en.kaf |
    | es       | input-es.kaf  | output-es.kaf |
    | fr       | input-fr.kaf  | output-fr.kaf |
    | it       | input-it.kaf  | output-it.kaf |
    
