
Mac and Linux download Clojure and create a deps.edn file:

https://clojure.org/guides/getting_started

```
echo '{:deps {org.clojure/clojurescript {:mvn/version "1.10.339"}}}' > deps.edn
```

The first time you run `clj`, Clojure will download around 60 dependencies, around 21Mb
```
> clj
Downloading: org/clojure/clojurescript/1.10.339/clojurescript-1.10.339.pom from https://repo1.maven.org/maven2/
Downloading: com/google/javascript/closure-compiler-unshaded/v20180610/closure-compiler-unshaded-v20180610.pom from https://repo1.maven.org/maven2/
Downloading: com/google/javascript/closure-compiler-main/v20180610/closure-compiler-main-v20180610.pom from https://repo1.maven.org/maven2/
...

```


Window requires Java 8 and cljs.jar:

http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
https://github.com/clojure/clojurescript/releases/download/r1.10.339/cljs.jar


Notes.

`clj -m cljs.main --help`

Don't do this:

`clj -m cljs.main --help | less`

https://stackoverflow.com/questions/27503528/how-to-execute-a-single-clojurescript-file-as-a-script

```
> clj --main cljs.main -i example_print.clj -t node
Exception in thread "main" java.lang.AssertionError: Assert failed: :nodejs target with :none optimizations requires a :main entry
(not (and (= target :nodejs) (= optimizations :none) (not (contains? opts :main))))
> clj --main cljs.main example_print.clj -re node -co "{:target :nodejs :optimizations :whitespace}"
"If printf lacks flush, you won't see the next message."
Hello world. (nil)
```

```
> find ./ \( -path "*org/*" -or -path "*com/*" \) -type f -mtime -1 -exec du {} \; | awk '{total=total+$1}END{print total/1024}'
21.8828
```
