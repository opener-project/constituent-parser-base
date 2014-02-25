require_relative '../../lib/opener/constituent_parsers/base'
require 'rspec/expectations'
require 'tempfile'

def kernel_root
  File.expand_path("../../../", __FILE__)
end

def kernel(language)
  return Opener::ConstituentParsers::Base.new(:language => language, :args=>['-t'])
end
