(ns clojint.core
  (:require [clojure.tools.namespace.repl :as tnr]
            [clojure.repl :refer [doc]]
            [clojure.java.io])
  (:refer-clojure :exclude [printf]) 
  (:gen-class))

;; Apps will hang after load-file due to running agents that are futures. This same function run in the repl
;; does not hang. Calling shutdown-agents in the repl will exit the repl, so only call shutdown-agents in
;; apps.

;; Need to call (flush) before exiting. Most clojure print functions do not flush, and as a result, a script
;; with that calls print instead of prn may producte no visible outout. Calling flush is a workaround for that.

;; There are several ways to force printf to be followed by flush, but this seems to be the simplest.
;; Call unbuf-printf from your code, and all subsequent printf calls will use the flush version.
;; See experiments.clj for the other things that were tried.

(defn unbuf-printf []
  (require '[clojint.core :refer [printf]])
  (def core-printf clojure.core/printf)
  (defn printf [fmt & args]
    (apply core-printf fmt args)
    (flush)))

;; java -cp target/uberjar/clojint-standalone.jar clojint.core

;; This supports -m <main>
;; java -cp ~/bin/clojint.jar:src clojint.core src/defini/server.clj -m defini.server/-main


;; Unlike normal clojure, -m allows execution of any arbitrary function, albeit only fns that have zero args.
;; java -cp ~/bin/clojint.jar:src clojint.core src/defini/server.clj -m somens/somefn


;; The interpreter has a crude concept of args, but it works for now.
;; todo: add -cp to add classpath dirs?

(defn -main
  [& args]
  (if (some->> args
               first
               clojure.java.io/as-file
               .exists)
    (do
      (try
        (load-file (first args))
        (when (and (>= 3 (count args))
                   (= "-m" (second args))
                   (resolve (symbol (nth args 2))))
          ((eval (symbol (nth args 2)))))
        (catch Exception e (.printStackTrace e)))
      (when (= "clojure.core" (str *ns*))
        (shutdown-agents))
      (flush))
    (do 
      (println "Cannot find file. args: " args)
      1)))

