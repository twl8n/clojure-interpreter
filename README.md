#### clojint the clojure interpreter

This error probably means you are trying to require a package when you should be importing it:

```
:type java.io.FileNotFoundException
:message "Could not locate javax/imageio/ImageIO__init.class or javax/imageio/ImageIO.clj on classpath."
```   

#### Usage

After adding dependencies to project.clj build the uberjar which is the Clojure interpreter.

Leiningen is basically serving as the package manager, building all dependencies into the uberjar.

```
> lein uberjar
Compiling clojint.core
Created /Users/twl/Sites/git_repos/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT.jar
Created /Users/twl/Sites/git_repos/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
> java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar example_2.clj
{:width 386, :height 284}
>
```

#### License

Copyright © 2017 Tom Laudeman

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

