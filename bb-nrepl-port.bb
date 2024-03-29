#!/usr/bin/env bb

;; From the Babashaka book, a script to launch a babashka nrepl server with a .nrepl-port file.
;; https://book.babashka.org/_/usage/repl.html

(import [java.net ServerSocket]
        [java.io File]
        [java.lang ProcessBuilder$Redirect])

(require '[babashka.wait :as wait])

(let [nrepl-port (with-open [sock (ServerSocket. 0)] (.getLocalPort sock))
      cp (str/join File/pathSeparatorChar ["src" "test"])
      pb (doto (ProcessBuilder. (into ["bb" "--nrepl-server" (str nrepl-port)
                                       "--classpath" cp]
                                      *command-line-args*))
           (.redirectOutput ProcessBuilder$Redirect/INHERIT))
      proc (.start pb)]
  (wait/wait-for-port "localhost" nrepl-port)
  (spit ".nrepl-port" nrepl-port)
  (.deleteOnExit (File. ".nrepl-port"))
  (.waitFor proc))
  