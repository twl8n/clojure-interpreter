(ns clojint.foo
  (:require [clojure.tools.namespace.repl :as tnr]
            [clojure.repl :refer [doc]]
            [clojure.java.io])
  (:refer-clojure :exclude [printf]) 
  (:gen-class))

;; java -cp target/uberjar/clojint-standalone.jar clojint.foo

;; https://stackoverflow.com/questions/11880037/call-java-class-method-from-jar-not-main
;; In Java parlance: You can specify a class name, but not which method to call, that always has to be:

;; public static void main(String[] argv).

(defn -main []
  (prn "hello"))

