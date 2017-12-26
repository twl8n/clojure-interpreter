#!/usr/bin/java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
(require '[clojure.java.shell :as shell])

(let [home (System/getenv "HOME")
      clojint-sh (str home "/bin/clojint.sh")]
  (println "Running lien uberjar...")
  (println (:out (shell/sh "lein" "uberjar")))
  (println "copying uberjar: " 
           (:out 
            (shell/sh "cp" "-v" "target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar" (str home "/bin/clojint.jar"))))
  (spit clojint-sh (str "java -jar " home "/bin/clojint.jar $1"))
  (shell/sh "chmod" "+x" clojint-sh)
  (println "clojint shell wrapper: " clojint-sh)
  (print (:out (shell/sh "ls" "-l" clojint-sh))))
