require 'open3'

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
        :language => DEFAULT_LANGUAGE,
        :heads    => 'synt'
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
        @args    = options.delete(:args) || []
        @options = DEFAULT_OPTIONS.merge(options)
      end

      ##
      # Builds the command used to execute the kernel.
      #
      # @return [String]
      #
      def command
        return "java -jar #{kernel} -l #{options[:language]} -g " \
          "#{options[:heads]} #{args.join(' ')}"
      end

      ##
      # Runs the command and returns the output of STDOUT, STDERR and the
      # process information.
      #
      # @param [String] input The input to process.
      # @return [Array]
      #
      def run(input)
        unless File.file?(kernel)
          raise "The Java kernel (#{kernel}) does not exist"
        end

        return Open3.capture3(command, :stdin_data => input)
      end

      ##
      # Runs the command and takes care of error handling/aborting based on the
      # output.
      #
      # @see #run
      #
      def run!(input)
        stdout, stderr, process = run(input)

        if process.success?
          puts stdout

          STDERR.puts(stderr) unless stderr.empty?
        else
          abort stderr
        end
      end

      protected

      ##
      # @return [String]
      #
      def core_dir
        return File.expand_path('../../../../core/target', __FILE__)
      end

      ##
      # @return [String]
      #
      def kernel
        return File.join(core_dir, 'ehu-parse-1.0.jar')
      end
    end # Base
  end # ConstituentParsers
end # Opener
