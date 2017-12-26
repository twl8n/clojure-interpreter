(ns clojint.core
  (:require [clojure.tools.namespace.repl :as tnr]
            [clojure.repl :refer [doc]]
            [clojure.java.io])
  (:gen-class))

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
      ;; Apps will hang here due to running agents that are futures. This same function run in the repl does not hang.
      ;; Calling shutdown-agents in the repl will exit the repl, so only call shutdown-agents in apps.
      (when (= "clojure.core" (str *ns*))
               (shutdown-agents)))
    (do 
      (println "Cannot find file. args: " args)
      1)))

