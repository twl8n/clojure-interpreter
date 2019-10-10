#!./bootstrap.sh
(require '[clojure.java.shell :as shell]
         '[clojure.java.io :as io])

;; (use-clojint-printf)

;; If drip is found by which, use that as the java-cmd.

(let [home (System/getenv "HOME")
      local-bin (str home "/bin")
      clojint-sh (str home "/bin/clojint.sh")
      clojint-jar (str home "/bin/clojint.jar")
      java-cmd (or (not-empty (clojure.string/trim (:out (shell/sh "which" "drip")))) "java")]

  (if (not (.exists (io/as-file local-bin)))
    (do
      ;; (shell/sh "mkdir " "-p" local-bin)
      (println "Creating bin: " local-bin)
      (io/make-parents (str local-bin "/tmp.txt"))
      (prn "done")))


  ;; When we're using drip, kill any existing daemon before building and deploying
  ;; a new copy of the interpreter. Oh, and warn the user.
  (if (= java-cmd "drip")
    (do
      (printf "You seem to have drip, so we will use it.\n")
      (shell/sh "drip" "kill")))

  (if (.exists (io/file "clojint.jar"))
    (println (str "copying local clojint.jar to " clojint-jar ": ")
             (:out 
              (shell/sh "cp" "-v" "clojint.jar" clojint-jar)))    
    (do
      ;; We really should check that lein exists before trying to use it.
      (println "Running lien uberjar...")
      (println (:out (shell/sh "lein" "uberjar")))
      (println "copying uberjar: " 
               (:out 
                (shell/sh "cp" "-v" "target/uberjar/clojint-standalone.jar" (str home "/bin/clojint.jar"))))))

  ;; -Xverify:none saves around 0.3 to 0.4 sec on my machine
  ;; I'll take that since java takes an eternity to launch.

  ;; Because clojint.sh is a shell script, we can use ~/ and we don't have to use an absolute 
  ;; home directory path (which varies from system to system).

  (spit clojint-sh (format "%s -Xverify:none -jar ~/bin/clojint.jar $1" java-cmd))
  (shell/sh "chmod" "+x" clojint-sh)
  (println "clojint shell wrapper: " clojint-sh)
  (print (:out (shell/sh "ls" "-l" clojint-sh))))
