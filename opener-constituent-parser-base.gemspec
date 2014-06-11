require File.expand_path(
  '../lib/opener/constituent_parsers/base/version',
  __FILE__
)

Gem::Specification.new do |gem|
  gem.name        = 'opener-constituent-parser-base'
  gem.version     = Opener::ConstituentParsers::Base::VERSION
  gem.authors     = ['development@olery.com']
  gem.summary     = 'Constituent parser that supports various languages.'
  gem.description = gem.summary
  gem.has_rdoc    = 'yard'

  gem.required_ruby_version = '>= 1.9.2'

  gem.files = Dir.glob([
    'core/target/ehu-parse-*.jar',
    'lib/**/*',
    '*.gemspec',
    'README.md'
  ]).select { |file| File.file?(file) }

  gem.executables = Dir.glob('bin/*').map { |file| File.basename(file) }

  gem.add_development_dependency 'opener-build-tools'
  gem.add_development_dependency 'rspec', '~> 3.0'
  gem.add_development_dependency 'cucumber'
  gem.add_development_dependency 'rake'
end
