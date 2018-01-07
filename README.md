#### clojint the clojure interpreter

This project allows you to create a Clojure interpreter. I use it as a replacement for Perl and bash shell automation tasks.

It takes 2 or 3 seconds to spin up the interpreter, but that's much better than ```lien run``` and I've fixed
the shutdown-agents problem.

#### todo

- what is this and does it have anything to do with lein uberjar

```
:omit-source true
```

#### Usage

1) lein uberjar
2) ./local_release.clj

You need to run ```lein uberjar``` once. After that, you can ```local_release.clj```
(or ```./local_release.clj```)

You can run your .clj files several ways:
```
* use the explicit uberjar location
/usr/bin/java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar example_1.clj


* use the clojint.sh shell wrapper, after running local_release.clj
clojint.sh example_1.clj


* Use java -jar with the uberjar in ~/bin/ but only after running local_release.clj
java -jar ~/bin/clojint.jar example_1.clj
```

Scripts like example_3.clj which have a shebang (#!), can simply be executed as long as they have excute privs. Give them execute privs with ```chmod +x```

```
chmod +x example_3.clj
./example_3.clj
```

After adding dependencies to project.clj, you must re-build the uberjar which is the Clojure interpreter clojint.

Leiningen is basically serving as the package manager for the interpreter, building all dependencies into the uberjar.

There is probably a 65k limit on .clj file size.

```
> lein uberjar
Compiling clojint.core
Created /Users/twl/Sites/git_repos/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT.jar
Created /Users/twl/Sites/git_repos/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
> java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar example_2.clj
{:width 386, :height 284}
>
```

I use a shell script to wrap a java -jar command. -jar only takes an explicit filename. The shebang (#!) will not interpolate paths. As a result, you cannot say "#!java -jar ~/bin/clojint.jar" or "#!/java -jar $HOME/clojint.jar". 


Other people have thought about making an uberjar an executable:

https://superuser.com/questions/912955/how-to-make-a-java-jar-file-to-be-a-single-file-executable

#### Huge speed increase with drip

Use drip as a direct replacement for the "java" command. Drip runs a java and a daemon in the background. On
my machine drip is roughly 33x faster for a small script where nearly all the time is startup.

```
> time java -jar ~/bin/clojint.jar example_import1.clj
2018-01-06T21:42:46.003-05:00
-365
java -jar ~/bin/clojint.jar example_import1.clj  2.36s user 0.11s system 195% cpu 1.265 total
> time drip -jar ~/bin/clojint.jar example_import1.clj
2018-01-06T21:42:51.269-05:00
-365
drip -jar ~/bin/clojint.jar example_import1.clj  0.07s user 0.08s system 35% cpu 0.438 total
```

https://github.com/ninjudd/drip

Installation was easy, but I already have gcc (XCode) installed, so I didn't have to deal with that. The first
time drip is run, it builds itself.

```
10957  curl -L https://raw.githubusercontent.com/ninjudd/drip/master/bin/drip > ~/bin/drip
10959  chmod 755 ~/bin/drip
10964  drip -jar ~/bin/clojint.jar example_import1.clj
```

I've put a naive hack into local_install.clj so that if drip is available, it will be used instead of "java".

You should kill java drip processes before ```lein uberwar``` or ```./local_release.clj```. Drip caches things.

```
pkill -fl drip
```


#### Working with shell commands from Clojure

https://stackoverflow.com/questions/36740239/clojure-how-to-execute-shell-commands-with-piping
right: (shell/sh "bash" "-c" "ls -a | grep Doc")
wrong: (shell/sh "bash" "-c" "ls -a" "|" "grep Doc")

See example_3.clj

#### Philosophical considerations

Dependencies are managed via lein and project.clj. Rebuild the uberjar after changing dependencies.

Each interpreted .clj script needs to do its own requirements and imports.

#### How this works

https://clojure.org/reference/evaluation

#### Problems you may encounter


This error probably means you are trying to require a package when you should be importing it:

```
:type java.io.FileNotFoundException
:message "Could not locate javax/imageio/ImageIO__init.class or javax/imageio/ImageIO.clj on classpath."
```   

#### What's up with (shutdown-agents in core.clj?

The whole issue with shutdown-agents is a side effect of clojure.java.shell being implemented with futures.

https://clojuredocs.org/clojure.java.shell/sh

sh is implemented using Clojure futures.  See examples for 'future' for discussion of an undesirable 1-minute
wait that can occur before your standalone Clojure program exits if you do not use shutdown-agents.

#### Local release

https://stackoverflow.com/questions/27833454/how-to-use-a-lein-exec-task-in-release-tasks-when-releasing-a-clojure-leiningen

#### License

Copyright © 2017 Tom Laudeman

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

