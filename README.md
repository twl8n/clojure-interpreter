
#### You probably want Babashka

My `clojint` interpreter is crude. You are probably looking for the real Clojure interpreter called "babashka".

Babashka is a true Clojure interpreter (and more) is [Babashka](https://github.com/borkdude/babashka "Babashka github repo") and [sci (small clojure interpreter)](https://github.com/borkdude/sci "Small Clojure Interpreter").

https://github.com/borkdude/babashka
 
https://github.com/borkdude/sci

Michiel Borkent aka borkdude has put and immense amount of work into Babashka, and it is a very cool tool. Besides being a true Clojure interpreter, it can (as far as I can tell) be extended on the fly with a pod system. 

My very simple and naive clojint interpreter is fundamentally just the Clojure `load-file` command, which
evals Clojure code. Effective, but naive, and limited. This project is here as something historical, which
someone may find useful.

I've installed babashka and use it for my coding projects.


#### Why?

I initially had the idea to do a Clojure interpreter in 2016 or 2017. I wanted a single executable install
that would quickly launch clojure and run clojure. I love Clojure, and I'm not going back to Perl, but Perl
launches in under 1ms. Clojure and Java are very slow to launch, anywhere from 400ms to several seconds.

Clojure toolchains and dependencies can be very confusing. Even though I was never able to get on-the-fly
dependencies working, I have always been able to manage dependencies via `lein`. Combined with building a
clojure interpreter via `lein uberjar`, I was able to (more or less) add modules to my crude interpreter.



#### Quickstart

I follow these steps:

1) Clone the clojure-interpreter (via `git clone` or similar)
2) Add necessary dependencies to the clojint project.clj
3) Build and install clojint (see Quickstart below)
4) Launch the dev app with a command that includes classpath. 

If you have .clj files in ./src then add ":src" to the classpath. The first item in the classspath is the full path to the clojing jar file.

I use the command below to run my definitionary ring web server app. Note that all the args after clojint.core
are passed to the clojint interpreter. The interpreter will run any arbitrary function named in the -m arg.

```
java -cp ~/bin/clojint.jar:src clojint.core src/defini/server.clj -m defini.server/-main
```

Please note that when I deploy the definitionary app, I'll deploy it as an uberjar created by `lein uberjar`.
I would be using Clojure tools.deps aka deps.edn, but I can't figure out how to build an uberjar reliably
except with `lein`. We live in a funny world.


#### Quickstart

This document is evolving.

These instructions probably work on Mac OS and Linux. Windows users will need bash extensions and/or PowerShell and/or cygwin. 

I've added a pre-built clojint.jar to the repo. You might be able to simply download the repo, run ./local_release.clj and start using the examples.

Open your Terminal app and run commands in the terminal. (Granted, there is some implicit background knowledge
here, like where is the Terminal app, how to enter commands, etc.)

Download this file via your web browser or via curl:

https://github.com/twl8n/clojure-interpreter/archive/master.zip

`curl -LO https://github.com/twl8n/clojure-interpreter/archive/master.zip`

unzip via command line or by double-clicking the master.zip file:

`unzip master.zip`

Unzip will create a local directory called clojure-interpreter-master.

Change dir (cd) into the new directory:

`cd clojure-interpreter-master/`

Run the local_release.clj script that will copy the interpreter .jar file to a standard location:

`./local_release.clj`

Run one of the examples to confirm it all worked:

`./example_print.clj`

```
cd clojure-interpreter-master/
./local_release.clj
./example_print.clj
```

Lots of little things can keep any of these steps from working. If you get error messages, just google them.

One common issue is your path. You might need to add ~/bin and you might need to create ~/bin. These commands may help:

```
mkdir -p $HOME/bin
export PATH=$PATH:$HOME/bin
```


#### GraalVM

The GraalVM .tar.gz is 330M.
https://github.com/taylorwood/clj.native-image

GraalVM replaces your standard JDK with a much, much faster launching equivalent. GraalVM has both `java` and
`jar` (as far as I know). Aside from installing GraalVM and making it your standard JDK, there are not
differences. If you install the GraalVM extension, then you can make native binaries for your platform.


```
wget https://github.com/oracle/graal/releases/download/vm-19.2.0.1/graalvm-ce-darwin-amd64-19.2.0.1.tar.gz
tar -xzf graalvm-ce-darwin-amd64-19.2.0.1.tar.gz
sudo mv graalvm-ce-19.2.0.1/ /Library/Java/JavaVirtualMachines
/usr/libexec/java_home -V
/usr/libexec/java_home -v 1.8
# edit .zshrc
source ~/.zshrc
java -version
gu install native-image
cd ~/clojure-interpreter
clojure -A:native-image
ls -l core
./core
```
edit your ~/.bash_profile, ~/.bashrc, or ~/.zshrc

```
export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)
export PATH=$JAVA_HOME/bin:$PATH
```

```
> /usr/libexec/java_home -V
Matching Java Virtual Machines (2):
    1.8.0_221, x86_64:	"GraalVM CE 19.2.0.1"	/Library/Java/JavaVirtualMachines/graalvm-ce-19.2.0.1/Contents/Home
    1.8.0_131, x86_64:	"Java SE 8"	/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home

/Library/Java/JavaVirtualMachines/graalvm-ce-19.2.0.1/Contents/Home
> which native-image
/Library/Java/JavaVirtualMachines/graalvm-ce-19.2.0.1/Contents/Home/bin/native-image
> clojure -A:native-image
Cloning: https://github.com/taylorwood/clj.native-image.git
Checking out: https://github.com/taylorwood/clj.native-image.git at 7708e7fd4572459c81f6a6b8e44c96f41cdd92d4
Downloading: org/clojure/tools.deps.alpha/0.7.516/tools.deps.alpha-0.7.516.pom from https://repo1.maven.org/maven2/
Downloading: org/slf4j/jcl-over-slf4j/1.7.25/jcl-over-slf4j-1.7.25.jar from https://repo1.maven.org/maven2/
Downloading: org/clojure/tools.deps.alpha/0.7.516/tools.deps.alpha-0.7.516.jar from https://repo1.maven.org/maven2/
Compiling clojint.core
Compiling clojint.foo
Compiling core
[core:2074]    classlist:  15,576.16 ms
[core:2074]        (cap):   5,548.07 ms
[core:2074]        setup:   8,569.80 ms
[core:2074]   (typeflow):  10,589.28 ms
[core:2074]    (objects):   4,956.73 ms
[core:2074]   (features):     728.29 ms
[core:2074]     analysis:  16,644.38 ms
[core:2074]     (clinit):     350.04 ms
[core:2074]     universe:   1,089.25 ms
[core:2074]      (parse):   2,405.11 ms
[core:2074]     (inline):   3,599.99 ms
[core:2074]    (compile):  19,846.41 ms
[core:2074]      compile:  26,936.65 ms
[core:2074]        image:   1,915.42 ms
[core:2074]        write:     638.92 ms
[core:2074]      [total]:  71,793.31 ms
> time ./core
Hello, World!
./core  0.00s user 0.00s system 56% cpu 0.009 total


```

#### More details about clojint, the clojure interpreter

This project allows you to create a Clojure interpreter. I use it as a replacement for Perl and bash shell automation
tasks.

It takes 2 or 3 seconds to spin up the interpreter, but that's much better than ```lien run``` and I've fixed
the shutdown-agents problem.

I'm assuming that you are familiar with Linux/OSX terminal sessions, and that you have a text editor. Windows
users will need cygwin, or the bash extensions to Powershell (I guess).


Clojure has what is essentially an interpreter, as well as a repl (clj), however there are use cases where the
Clojure solution is unworkable, or less than ideal. My interpreter has Clojure under the hood, but I've added
some features for "scripting" and for quick-and-dirty tiny coding projects. I've also included commonly
Clojure libraries in my interpreter so it can be used for scripting without getting into dependency
management. I'll probably add more Clojure libraries over time, as need (my need) dictates. Since this is
github, anyone can clone my interpreter and add, remove, or change dependencies in their copy.

I prepared a comparison:

```
| feature                       | clojure/clj  | clojint interpreter |
|-------------------------------+--------------+---------------------|
| download size MB              | ~ 22MB       | ~ 20MB              |
| requirements                  | rlwrap, java | java                |
| Windows                       | no           | yes                 |
| Mac [1]                       | yes          | yes                 |
| Linux                         | yes          | yes                 |
| deps.edn [2]                  | yes          | no (maybe?)         |
| includes SQLite adapeters     | no           | yes                 |
| includes PostgreSQL adapeters | no           | yes                 |
| includes clostach             | no           | yes                 |
| includes ring                 | no           | yes                 |
| includes java/jdbc            | no           | yes                 |
| adding dependencies           | deps.edn     | lein[3]             |
```
[1] Mac installation of clojure normally uses the "brew" software installer, although you can probably use "curl"
and unpack the .tar.gz files yourself.

[2] deps.edn is a feature of clojure that allows you to manage dependencies. In other words, to use additional
clojure software ilbraries. Unfortunately, as of 2018-02-22 it can't be turned off, so if you run a script via
clojure in a directory of a project that uses deps.edn, you are forced to use those deps. This is a big
problem if the deps happen to be broken.

[3] There is a way to add dependencies dynamically using pomegranate. 


#### printf and buffered output

In Clojure the only print function that calls flush is prn. Since many scripting applications need unbuffered output
I've hacked together a method to make printf unbuffered. In your script call ```(clojint.core/unbuf-printf)```

In a script where you defn printf, the only way to quiet the warning

`WARNING: printf already refers to: #'clojure.core/printf in namespace: user, being replaced by: #'user/printf`

is to use the (ns ... (:refer-clojure ...))

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

- if target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar is newer than clojint.jar, then use newer

- mkdir -p ~/bin

- run the repl of clojint: java -cp ~/bin/clojint.jar clojure.main

- Clojure https://clojure.org/guides/getting_started#_installation_on_linux

- lein install https://leiningen.org/



```
cd ~/bin
curl -O https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein
chmod +x lein
./lein
curl -O https://download.clojure.org/install/linux-install-1.9.0.391.sh
chmod +x linux-install-1.9.0.391.sh
sudo ./linux-install-1.9.0.391.sh
```

- local_release.clj silently fails if target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar doesn't exist.

- Is there a substitute for clj-serial which is in a non-https repo?

- Add an example for XML parsing

- Add an example for base64 encode/decode.

- Add an example for Postgres

- Continue with the experiment to dynamically load modules.

- what is :omit-source and does it have anything to do with lein uberjar? Will using it make a smaller uberjar? Would a smaller uberjar be an advantage besides saving disk?

- Can the clojint use rlwrap just like Clojure clj? (Yes, and ^C exits, just like clj)

```
rlwrap -r -q '\"' -b "(){}[],^%#@\";:'" java -cp ~/bin/clojint.jar clojure.main "$@"

:omit-source true
```

- what is `jar -cvfe` and how would one use it with clojure? 

- update install and exec due to limitations in shebang. 

https://www.in-ulm.de/~mascheck/various/shebang/


#### Installing

If you do not have a github account, you can clone the repository anonymously:

```
git clone git://github.com/twl8n/clojure-interpreter.git
```

If you have a github account, and your computer is set up for command line ssh access to github, then this command should work:
```
git clone git@github.com:twl8n/clojure-interpreter.git
```


Clojint requires Leiningen (and Clojure). https://leiningen.org Leiningen has a self-install script, and
Leiningen will install Clojure. In fact, installing Leiningen is the easiest way to install (and use) Clojure.
Once Leiningen is installed, clone the clojure interpreter git repository, and, change into that directory,
and run `lein uberjar`.

Here is a terminal session transcript:

```
> git clone git://github.com/twl8n/clojure-interpreter.git
Cloning into 'clojure-interpreter'...
remote: Counting objects: 55, done.
remote: Compressing objects: 100% (37/37), done.
remote: Total 55 (delta 17), reused 47 (delta 9), pack-reused 0
Unpacking objects: 100% (55/55), done.
Checking connectivity... done.
> cd clojure-interpreter
> lein uberjar
Retrieving com/layerware/hugsql-adapter-clojure-jdbc/0.4.8/hugsql-adapter-clojure-jdbc-0.4.8.pom from clojars
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
Retrieving clj-time/clj-time/0.14.2/clj-time-0.14.2.jar from clojars
Compiling clojint.core
Created /home/ubuntu/src/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT.jar
Created /home/ubuntu/src/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
```


#### Usage

Run `lien uberjar` once. 

1) ./local_release.clj
2) ./example_shell.clj
3) Add new dependency to project.clj, as necessary for one of your Clojure scripts
4) ./local_release.clj
5) ./your_new_script.clj

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

I use a shell script to wrap a java -jar command. -jar only takes an explicit filename. The shebang (#!) will
not interpolate paths. As a result, you cannot say "#!java -jar ~/bin/clojint.jar" or "#!/java -jar
$HOME/clojint.jar".


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

right: `(shell/sh "bash" "-c" "ls -a | grep Doc")`

wrong: `(shell/sh "bash" "-c" "ls -a" "|" "grep Doc")`

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

This error:
```
java.lang.IllegalArgumentException: db-spec org.sqlite.SQLiteConnection@1d23ff23 is missing a required
parameter, compiling:(/Users/twl/Sites/git_repos/clojure-interpreter/./example_sqlite_1.clj:68:1)
```

Fixed by turning `(jdbc/get-connection dbspec-sqlite)`

into `{:connection (jdbc/get-connection dbspec-sqlite)}`


#### What's up with (shutdown-agents in core.clj?

The whole issue with shutdown-agents is a side effect of clojure.java.shell being implemented with futures.

https://clojuredocs.org/clojure.java.shell/sh

sh is implemented using Clojure futures.  See examples for 'future' for discussion of an undesirable 1-minute
wait that can occur before your standalone Clojure program exits if you do not use shutdown-agents.

#### Local release

https://stackoverflow.com/questions/27833454/how-to-use-a-lein-exec-task-in-release-tasks-when-releasing-a-clojure-leiningen

#### Links

[Maria Cloud, interactive beginner Clojure exercises running in your web browser](https://www.maria.cloud/)

Maria Cloud "Learn Clojure with Shapes" walks you through sample Clojure statements, explaining everything. You run the statements in your browser at the Maria Cloud web site. Note: at the bottom of the page is a brief description of the command near your cursor.

Maria Cloud is a great way to instantly try out some Clojure commands. 

[Getting Started with Clojure](https://clojure.org/guides/getting_started)

Getting started with Clojure is a guide for experience programmers who are new to Clojure. There is an
expectation that you have passing familiarity with Linux command line and that you have a text editor or IDE
that you're comfortable with.

#### Amazon Web Services

AWS Cloud9 is a nifty way to learn to program. There is nothing to install, but you do have to run the
gauntlet of AWS strangeness (unless someone sets it up for you). Cloud9 is worth the effort, and new users get
1 year of free services. If you create your own account, you will be a "root" user. You will experience some
odd behavior, which I hope to document soon. Short answer: ignore the AWS warning about "root login detected"
and simply do your work. Creating an IAM user is an advanced topic.

If someone has created an IAM user account for you, and shared their Cloud9 environment with you, and assuming
their environment is in US East (Ohio) you need to do this:

After logging in (as a non-root user):

- Services -> Cloud9 (under Developer Tools, or use the search)
- Select a region -> US East (Ohio)
- Hamburger (upper left under AWS) -> Shared with you
- Open IDE 

There are two Open IDE links. I think they are the same. It takes a couple of minutes for the IDE to fully
open because AWS is actually launching an EC2 linux server.

Confusingly, if you don't select a region, AWS will keep showing the Cloud9 intro page, no matter what options
you choose. This is typical of AWS where something is always broken and/or weird. You never get used to it
because you are always discovering some new, bizarre issue. Feel free to complain. It isn't clear that Google
cloud is any better, and I suspect Azure is worse.

Cloud9 is fairly cool, but not fully cooked.

Do this:

- Login as the non-root user (root user must not be logged in anywhere in AWS)

- Go to a project with a lambda, open the "AWS Resources" panel. Right click on the local lambda and choose "deploy".

- It fails:

"AWS CloudFormation deployment errors
The following errors were encountered when deploying your application
The security token included in the request has expired"

This is an epic failure in an environment where the purpose of shared accounts is learning to code, especially
learning to use Lambda. The students can't deploy a remote lambda, at least not from withing the Cloud9 IDE.
Pretty typical AWS half baked.

The error dialog box text is also not selectable, so you have to manually type the error into a web browser to
search for a resolution.



#### Reading list

The clojure interpreter is software for daily scripting, and for learning. In keeping with the learning theme, here is a fledgling reading list:

[Beating the Averages, Paul Graham, where he introduces the Blub Paradox](http://www.paulgraham.com/avg.html)

["Out of the Tar Pit", Ben Moseley, Peter Marks](https://github.com/papers-we-love/papers-we-love/blob/master/design/out-of-the-tar-pit.pdf)

[Papers we love at Github](https://github.com/papers-we-love/papers-we-love)


[Clojure Programming Concepts](https://en.wikibooks.org/wiki/Clojure_Programming/Concepts)

#### Experiments

See experiments.clj

Does not work with pomegranate:
```
> lein repl
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
nREPL server started on port 49729 on host 127.0.0.1 - nrepl://127.0.0.1:49729
REPL-y 0.3.7, nREPL 0.2.12
Clojure 1.8.0
Java HotSpot(TM) 64-Bit Server VM 1.8.0_131-b11
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)
 Results: Stored in vars *1, *2, *3, an exception in *e

clojint.core=>
```

After `lein upgrade`

```
> lein repl      
nREPL server started on port 49879 on host 127.0.0.1 - nrepl://127.0.0.1:49879
org.eclipse.aether.resolution.DependencyResolutionException: Could not transfer artifact org.clojure:clojure:jar:1.3.0 from/to central (https://repo1.maven.org/maven2/): GET request of: org/clojure/clojure/1.3.0/clojure-1.3.0.jar from central failed
```

Fixed by excluding `[org.clojure/clojure]` from com.cemerick/pomegranate.

```
[com.cemerick/pomegranate "1.0.0" :exclusions [org.clojure/clojure]]
```

#### pomegranate not working

```
;; => FileNotFoundException Could not locate clj_http/client__init.class or clj_http/client.clj on classpath. Please check that namespaces with dashes use underscores in the Clojure file name.  clojure.lang.RT.load (RT.java:456)

;; (clojure.core/load "/clj_http/client")
;; FileNotFoundException Could not locate clj_http/client__init.class or clj_http/client.clj on classpath. Please check that namespaces with dashes use underscores in the Clojure file name.  clojure.lang.RT.load (RT.java:456)
```

Running example_dynamic_dependency.clj I get this error:

```
(clojure.core/load "/clj_http/client")
java.io.FileNotFoundException: Could not locate clj_http/client__init.class or clj_http/client.clj on classpath. Please check that namespaces with dashes use underscores in the Clojure file name., compiling:(/Users/twl/Sites/git_repos/clojure-interpreter/./example_dynamic_dependency.clj:20:1)
```


#### Standardized on clojure.java.jdbc

At one point I played with funcool's JDBC connector. It has some nice features, but in the end the standard
Clojure java jdbc connector is probably more future proof, and more standard.

From the old project.clj:

```
[com.layerware/hugsql-core "0.4.8"] ;; use with funcool/clojure.jdbc
[com.layerware/hugsql-adapter-clojure-jdbc "0.4.8"] ;; use with funcool/clojure.jdbc
[funcool/clojure.jdbc "0.9.0"] ;; use with hugsql-core and hugsql-adapter-clojure-jdbc
```


#### Leiningen profiles and project.clj

```
[cider/cider-nrepl "0.17.0"]
[org.clojure/tools.nrepl "0.2.13"]
```

Question: Is it better to have these in each project.clj or my (global-ish) ~/.lein/profile.clj? At the moment, the
nrepl dependency is in profile.clj, and I keep the tools dependency in each app's project.clj.

What should I do for my public git repos where people will download something and have to deal with my
project.clj? It seems odd to say "Installation: edit your profile.clj to include additional deps..."

Answer: I'd say always on the `profiles.clj`. Although you could put that on the project (as it's shared
between all the nrepl clients), there is a strong binding between its version and the particular IDE's nrepl
client version.

These are only used for development/debugging. If you have a REPL available on a production server, then it's
quite alright to have the "Check that your cider version matches the currently deployed version" right next to
the "Be very careful."


#### Graalvm doesn't work

```
> export JAVA_HOME=/Users/twl/bin/labsjdk1.8.0_161-jvmci-0.42/Contents/Home/    
> lein uberjar                                                              
Compiling clojint.core
Created /Users/twl/src/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT.jar
Created /Users/twl/src/clojure-interpreter/target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
> ~/bin/graalvm-1.0.0-rc1/Contents/Home/bin/native-image -cp target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar clojint.core

... Unsupported constructor java.lang.ClassLoader.<init>(ClassLoader) ...
```

The following command does build an executable, and that executable fails with the same execption as above:

`~/bin/graalvm-1.0.0-rc1/Contents/Home/bin/native-image -H:+ReportUnsupportedElementsAtRuntime -cp target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar clojint.core`

example of something else to try:

`native-image -cp `lein cp`:target/uberjar/hello-0.1.0-SNAPSHOT-standalone.jar hello.core`

Not working. This reports missing directory/file for zillions of paths and files that simply don't exist and never will.

`~/bin/graalvm-1.0.0-rc1/Contents/Home/bin/native-image -cp `lein cp`:target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar clojint.core`

#### Github

https://stackoverflow.com/questions/35619305/enabling-git-in-windows-10-command-line

https://superuser.com/questions/960378/how-do-i-make-githubs-git-shell-appear-in-windows-10-search

https://help.github.com/desktop/guides/getting-started-with-github-desktop/installing-github-desktop/#platform-windows

#### AWS command line interface

Often the most efficient way to interact with AWS is via the command line interface, the cli. For AWS this is a Python tool the awscli, or simple aws.

https://docs.aws.amazon.com/cli/latest/userguide/installing.html

If you choose to use Python 3, the use pip3 instead of pip.

```
# install just for one user (recommended):
pip3 install awscli --upgrade --user

# install for all users:
sudo pip3 install awscli --upgrade

# Where did pip3 install a package (more or less)
pip3 show awscli

# After install, if there was already something on the path. Refresh Linux/Mac path search:
rehash

```

#### AWS Cloud9 via awscli

If you are not in us-east-1 and/or if your `~/.aws/config` specifies us-east-1, but your envs are in some other region, there won't be any output, and no errors or warnings.

```
# Describes all members:
aws cloud9 --region us-east-2 describe-environment-memberships --environment-id <your env id>

# Only describes the owner/default]:
aws cloud9 --region us-east-2 describe-environment-memberships

# Describes the envs for zeus:
aws cloud9 describe-environment-memberships --profile zeus --region us-east-2

```

#### License

Copyright © 2018 Tom Laudeman

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.

