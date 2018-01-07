#!/usr/bin/java -jar target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar
(require '[clojure.java.shell :as shell])

;; If drip is found by which, use that as the java-cmd.

(let [home (System/getenv "HOME")
      clojint-sh (str home "/bin/clojint.sh")
      java-cmd (or (not-empty (clojure.string/trim (:out (shell/sh "which" "drip")))) "java")]
  (println "Running lien uberjar...")
  (println (:out (shell/sh "lein" "uberjar")))
  (println "copying uberjar: " 
           (:out 
            (shell/sh "cp" "-v" "target/uberjar/clojint-0.1.0-SNAPSHOT-standalone.jar" (str home "/bin/clojint.jar"))))
  ;; -Xverify:none saves around 0.3 to 0.4 sec on my machine
  ;; I'll take that since java takes an eternity to launch.
  (spit clojint-sh (format "%s -Xverify:none -jar %s/bin/clojint.jar $1" java-cmd home))
  (shell/sh "chmod" "+x" clojint-sh)
  (println "clojint shell wrapper: " clojint-sh)
  (print (:out (shell/sh "ls" "-l" clojint-sh))))
