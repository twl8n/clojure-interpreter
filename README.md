#### clojint the clojure interpreter

This project allows you to create a Clojure interpreter. I use it as a replacement for Perl and bash shell automation tasks.

It takes 2 or 3 seconds to spin up the interpreter, but that's much better than ```lien run``` and I've fixed
the shutdown-agents problem.

I'm assuming that you are familiar with Linux/OSX terminal sessions, and that you have a text editor. Windows
users will need cygwin, or the bash extensions to Powershell (I guess).

#### printf and buffered output

In Clojure the only print function that calls flush is prn. Since many scripting applications need unbuffered output
I've hacked together a method to make printf unbuffered. In your script call ```(clojint.core/unbuf-printf)```

In a script where you defn printf, the only way to quiet the warning `WARNING: printf already refers to: #'clojure.core/printf in namespace: user, being replaced by: #'user/printf
` is to use the (ns ... (:refer-clojure ...))
```
(ns ecs-content
  (:require [clojure.java.shell :as shell]
            [clojure.data])
  (:refer-clojure :exclude [printf]))
```

In clojint/core.clj, two other methods should work:

```
(require '[clojure.core :exclude printf])
;; OR
(refer-clojure :exclude [printf])
```


#### todo

- what is :omit-source and does it have anything to do with lein uberjar? Will using it make a smaller uberjar? Would a smaller uberjar be an advantage besides saving disk?

```
:omit-source true
```

- what is ```jar -cvfe``` and how would one use it with clojure? 

- + update install and exec due to limitations in shebang. 

https://www.in-ulm.de/~mascheck/various/shebang/


#### Installing

Clojint requires Leiningen (and Clojure). https://leiningen.org Leiningen has a self-install script, and Leiningen will install Clojure. In fact, installing Leiningen is the easiest way to install (and use) Clojure. Once Leiningen is installed, clone the clojure interpreter git repository, and, change into that directory, and run ```lein uberjar```.

Here is a terminal session transcript:

```
> git clone git@github.com:twl8n/clojure-interpreter.git
Cloning into 'clojure-interpreter'...
remote: Counting objects: 55, done.
remote: Compressing objects: 100% (37/37), done.
remote: Total 55 (delta 17), reused 47 (delta 9), pack-reused 0
Unpacking objects: 100% (55/55), done.
Checking connectivity... done.
> cd clojure-interpreter
> lein uberjar
Retrieving com/layerware/hugsql-adapter-clojure-jdbc/0.4.8/hugsql-adapter-clojure-jdbc-0.4.8.pom from clojars
Retrieving funcool/clojure.jdbc/0.9.0/clojure.jdbc-0.9.0.pom from clojars
Retrieving org/xerial/sqlite-jdbc/3.21.0/sqlite-jdbc-3.21.0.pom from central
Retrieving clojure/java-time/clojure.java-time/0.3.1/clojure.java-time-0.3.1.pom from clojars
Retrieving clj-time/clj-time/0.14.2/clj-time-0.14.2.pom from clojars
Retrieving org/imgscalr/imgscalr-lib/4.2/imgscalr-lib-4.2.pom from central
Retrieving image-resizer/image-resizer/0.1.9/image-resizer-0.1.9.pom from clojars
Retrieving org/xerial/sqlite-jdbc/3.21.0/sqlite-jdbc-3.21.0.jar from central
Retrieving org/imgscalr/imgscalr-lib/4.2/imgscalr-lib-4.2.jar from central
Retrieving com/layerware/hugsql-adapter-clojure-jdbc/0.4.8/hugsql-adapter-clojure-jdbc-0.4.8.jar from clojars
Retrieving clojure/java-time/clojure.java-time/0.3.1/clojure.java-time-0.3.1.jar from clojars
Retrieving image-resizer/image-resizer/0.1.9/image-resizer-0.1.9.jar from clojars
Retrieving funcool/clojure.jdbc/0.9.0/clojure.jdbc-0.9.0.jar from clojars
Retrieving clj-time/clj-time/0.14.2/clj-time-0.14.2.jar from clojars
Compiling clojint.core
Created /home/ubuntu/src/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT.jar
Created /home/ubuntu/src/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
```


#### Usage

1) lein uberjar
2) ./local_release.clj
3) ./example_3.clj
4) Add new dependency to project.clj, as necessary for one of your Clojure scripts
5) ./local_release.clj
6) ./your_new_script.clj

You need to run ```lein uberjar``` once. After that, you can ```local_release.clj```
(or ```./local_release.clj```)

You can run your .clj files several ways. My favorite is to use a shebang (#!) as shown in example_3.clj.

* Scripts like example_3.clj which have a shebang as the first line (#!/usr/bin/env clojint.sh), can simply be
executed as long as they have execute privs. Give them execute privs with ```chmod +x```
```
chmod +x example_3.clj
./example_3.clj
```
* use the explicit uberjar command, with your clj as the final argument:
```
/usr/bin/java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar example_1.clj
```
* use the clojint.sh shell wrapper, with your clj as the final argument. Run local_release.clj at least once.
```
clojint.sh example_1.clj
```
* Use java -jar with the uberjar in ~/bin/, with our clj as the final argument. You must first run local_release.clj at least once.
```
java -jar ~/bin/clojint.jar example_1.clj
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

#### HugSQL examples

The more complete example for HugSQL is https://github.com/twl8n/hugsql-demo

Briefly, in full.sql are some SQL queries and the comments are HugSQL formatted configuration.

`-- :name <query-name> [<query execution type> <query return type>]`

In the `:name` key, the first argument is the name of the query. For example "check-empty"
```
-- :name check-empty :? :1
```

The somewhat optional second and third args are execution type and execution return value

Type:

* :! insert, update, or delete
* :? select

Return:

* :n number of effected rows from insert, update, delete, or DDL [1] statements like `create table ...` 
* :1 return only a single row that is a result of a select
* :* return list of maps of all rows

[1] Data Definition Language which for SQL databases is SQL. Many statements are standard, but each database has extensions and alternate syntax.


#### JVM startup time


The startup time of the JVM can be improved, but won't ever be as fast as Perl, Python, or bash. 

https://purelyfunctional.tv/article/the-legend-of-long-jvm-startup-times/


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

https://github.com/technomancy/leiningen/wiki/Faster

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

