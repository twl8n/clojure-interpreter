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

;; decorate printf to add flush

;; https://github.com/weavejester/decorate

(defmacro redef
  "Redefine an existing value, keeping the metadata intact."
  [name value]
  `(let [m# (meta #'~name)
         v# (def ~name ~value)]
     (alter-meta! v# merge m#)
     v#))

(defmacro decorate
  "Wrap a function in one or more decorators."
  [func & decorators]
  `(redef ~func (-> ~func ~@decorators)))

(defn wrap-printf [func]
  (fn [& args]
    (apply func args)
    (flush)))

;; (decorate printf wrap-printf)

(defn use-clojint-printf []
  (def core-printf clojure.core/printf)
  (defn printf [fmt & args]
    (core-printf fmt args)
    (flush)))

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

