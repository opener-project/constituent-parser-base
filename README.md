# Constituent Base Parsers

This repository contains the source code of the base constituent parser which
supports both English and Spanish. The parser takes KAF documents (with <wf>
elements as it needs tokenized text) as standard input and outputs constituent
syntactic analysis in treebank format, one sentence per line. It also provides
an option of outputting the constituent heads, as defined by Collins PhD
thesis.

## Requirements

* Java 1.7 or newer
* Ruby 1.9.2 or newer

Development requirements:

* Maven
* Bundler

## Installation

Installing as a regular Gem:

    gem install opener-constituent-parser-base

Using Bundler:

    gem 'opener-constituent-parser-base',
      :git    => 'git@github.com:opener-project/constituent-parser-base.git',
      :branch => 'master'

Using specific install:

    gem install specific_install
    gem specific_install opener-constituent-parser-base \
       -l https://github.com/opener-project/constituent-parser-base.git

## Usage

    cat some_input_file.kaf | constituent-parser-base

## Contributing

First make sure all the required dependencies are installed:

    bundle install

Then compile the required Java code:

    bundle exec rake compile

For this you'll need to have Java 1.7 and Maven installed. These requirements
are verified for you before the Rake task calls Maven.

## Testing

To run the tests (which are powered by Cucumber), simply run the following:

    bundle exec rake

This will take care of verifying the requirements, installing the required Java
packages and running the tests.

For more information on the available Rake tasks run the following:

    bundle exec rake -T

## Structure

This repository comes in two parts: a collection of Java source files and Ruby
source files. The Java code can be found in the `core/` directory, everything
else will be Ruby source code.
