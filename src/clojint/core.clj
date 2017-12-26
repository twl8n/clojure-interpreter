(ns clojint.core
  (:require [clojure.tools.namespace.repl :as tnr]
            [clojure.repl :refer [doc]]
            [clojure.java.io])
  (:gen-class))

;; https://stackoverflow.com/questions/36740239/clojure-how-to-execute-shell-commands-with-piping
;; right: (sh "bash" "-c" "ls -a | grep Doc")
;; wrong: (sh "bash" "-c" "ls -a" "|" "grep Doc")

;; The whole issue with shutdown-agents is a side effect of clojure.java.shell and crappy futures.

;; https://clojuredocs.org/clojure.java.shell/sh
;; sh is implemented using Clojure futures.  See examples for 'future'
;; for discussion of an undesirable 1-minute wait that can occur before
;; your standalone Clojure program exits if you do not use shutdown-agents.

(defn -main
  [& args]
  (if (some->> args
               first
               clojure.java.io/as-file
               .exists)
    (do
      (load-file (first args))
      ;; Apps will hang here due to running agents that are futures. This same function run in the repl does not hang.
      ;; Calling shutdown-agents in the repl will exit the repl, so only call shutdown-agents in apps.
      (when (= "clojure.core" (str *ns*))
               (shutdown-agents)))
    (do 
      (println "Cannot find file. args: " args)
      1)))

