require 'open3'
require 'stringio'

require 'java'
require  File.expand_path('../../../../core/target/ehu-parse-1.0.jar', __FILE__)

import 'ehu.parse.Annotate'
import 'ehu.parse.Resources'
import 'ixa.kaflib.KAFDocument'
import 'java.io.InputStreamReader'
import 'ehu.heads.CollinsHeadFinder'

require_relative 'base/version'

module Opener
  module ConstituentParsers
    ##
    # The base constituent parser kernel that supports multiple languages such
    # as English and Spanish.
    #
    # @!attribute [r] args
    #  @return [Array]
    # @!attribute [r] options
    #  @return [Hash]
    #
    class Base
      attr_reader :args, :options

      ##
      # The default language to use.
      #
      # @return [String]
      #
      DEFAULT_LANGUAGE = 'en'.freeze

      ##
      # Hash containing the default options to use.
      #
      # @return [Hash]
      #
      DEFAULT_OPTIONS = {
        :args     => [],
        :language => DEFAULT_LANGUAGE
      }.freeze

      ##
      # @param [Hash] options
      #
      # @option options [Array] :args The commandline arguments to pass to the
      #  underlying Java code.
      #
      # @see Opener::ConstituentParsers::DEFAULT_OPTIONS
      #
      def initialize(options = {})
        options  = DEFAULT_OPTIONS.merge(options)
        @args    = options.delete(:args) || []
        @options = options
      end

      ##
      # Runs the command and returns the output of STDOUT, STDERR and the
      # process information.
      #
      # @param [String] input The input to process.
      # @return [Array]
      #
      def run(input)
        input     = StringIO.new(input) unless input.kind_of?(IO)
        annotator = Annotate.new(language)
        reader    = InputStreamReader.new(input.to_inputstream)
        kaf       = KAFDocument.create_from_stream(reader)
        kaf.add_linguistic_processor("constituents","ehu-parse-"+language,"now","1.0")

        if heads?
          head_finder = CollinsHeadFinder.new(language)
          annotator.parseWithHeads(kaf, head_finder)
        else
          annotator.parse(kaf)
        end

        return kaf.to_string
      end
      #
      ##
      # @return [String]
      #
      def language
        return options[:language]
      end

      def heads?
        true
      end

    end # Base
  end # ConstituentParsers
end # Opener
