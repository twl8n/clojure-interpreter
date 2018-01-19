(ns clojint.core
  (:require [clojure.tools.namespace.repl :as tnr]
            [clojure.repl :refer [doc]]
            [clojure.java.io])
  (:gen-class))

;; Apps will hang after load-file due to running agents that are futures. This same function run in the repl
;; does not hang. Calling shutdown-agents in the repl will exit the repl, so only call shutdown-agents in
;; apps.

;; Need to call (flush) before exiting. Most clojure print functions do no flush, and as a result, a script
;; with that calls print instead of prn may producte no visible outout. Calling flush is a workaround for that.

(defn -main
  [& args]
  (if (some->> args
               first
               clojure.java.io/as-file
               .exists)
    (do
      (try
        (load-file (first args))
        (catch Exception e (.printStackTrace e)))
      (when (= "clojure.core" (str *ns*))
        (shutdown-agents))
      (flush))
    (do 
      (println "Cannot find file. args: " args)
      1)))

