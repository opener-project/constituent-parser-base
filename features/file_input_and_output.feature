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
    | en       | input.kaf     | out.kaf       |
    | en       | input_en.kaf  | out_en.kaf    |
    | es       | input_es.kaf  | out_es.kaf    |
    | fr       | input_fr.kaf  | out_fr.kaf    |
    | it       | input_it.kaf  | out_it.kaf    |
    
